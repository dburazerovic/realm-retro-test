package com.retrotest.instagramgallery.api.listener.user;

import com.retrotest.instagramgallery.InstagramGalleryApp;
import com.retrotest.instagramgallery.api.ApiResultWrapper;
import com.retrotest.instagramgallery.api.dto.Media;
import com.retrotest.instagramgallery.api.dto.MediaList;
import com.retrotest.instagramgallery.api.event.ApiUnknownErrorEvent;
import com.retrotest.instagramgallery.api.event.UpdateCompletedEvent;
import com.retrotest.instagramgallery.api.listener.AbstractRequestListener;
import com.retrotest.instagramgallery.db.entities.InstagramMedia;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Result listener for api call
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
public class GetUserRecentMediaListener extends AbstractRequestListener<ApiResultWrapper<MediaList>> {

    @Override
    public void onRequestSuccess(ApiResultWrapper<MediaList> wrapper) {
        if (wrapper.getMeta() != null && wrapper.getMeta().getCode() == 200) {
            // we have receive valid result with no error

            if (wrapper.getData() != null && wrapper.getData().size() > 0) {

                Realm realm = Realm.getDefaultInstance();

                RealmResults<InstagramMedia> allRecords = realm.where(InstagramMedia.class).findAllSorted("createdTime", false);

                if (allRecords.size() > 0) {

                    InstagramMedia firstItem = allRecords.first();

                    if (firstItem.getId().equals(wrapper.getData().get(0).getId())) {
                        InstagramGalleryApp.getEventBus().post(new UpdateCompletedEvent(false));
                    } else {
                        updateData(wrapper);
                    }
                } else {
                    // if database is empty
                    updateData(wrapper);
                }

            } else {
                InstagramGalleryApp.getEventBus().post(new UpdateCompletedEvent(true));
            }
        } else {
            InstagramGalleryApp.getEventBus().post(new ApiUnknownErrorEvent("Server error"));
        }
    }

    private void updateData(ApiResultWrapper<MediaList> wrapper) {
        // update data
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (Media media : wrapper.getData()) {
            InstagramMedia instagramMedia = new InstagramMedia(media);
            InstagramMedia realmMedia = realm.copyToRealmOrUpdate(instagramMedia);
        }
        realm.commitTransaction();

        InstagramGalleryApp.getEventBus().post(new UpdateCompletedEvent(true));
    }
}
