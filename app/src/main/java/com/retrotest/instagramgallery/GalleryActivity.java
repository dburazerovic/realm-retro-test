package com.retrotest.instagramgallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.retrotest.instagramgallery.adapter.InstagramListPageAdapter;
import com.retrotest.instagramgallery.adapter.InstagramMediaAdapter;
import com.retrotest.instagramgallery.api.SpiceManagerProvider;
import com.retrotest.instagramgallery.api.event.ApiUnknownErrorEvent;
import com.retrotest.instagramgallery.api.event.UpdateCompletedEvent;
import com.retrotest.instagramgallery.api.listener.user.GetUserRecentMediaListener;
import com.retrotest.instagramgallery.api.request.user.GetUserRecentMediaRequest;
import com.retrotest.instagramgallery.db.entities.InstagramMedia;
import com.retrotest.instagramgallery.helper.ConnectionHelper;
import com.retrotest.instagramgallery.menu.CircleMenu;
import com.squareup.otto.Subscribe;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class GalleryActivity extends BaseActivity implements RealmChangeListener, AbsListView.OnScrollListener {

    @InjectView(com.retrotest.instagramgallery.R.id.galleryGrid)
    GridView galleryGrid;

    @InjectView(com.retrotest.instagramgallery.R.id.galleryPager)
    ViewPager galleryPager;

    @InjectView(com.retrotest.instagramgallery.R.id.galleryPagerLayout)
    RelativeLayout galleryPagerLayout;

    @InjectView(com.retrotest.instagramgallery.R.id.circleMenu)
    CircleMenu circleMenu;

    @InjectView(com.retrotest.instagramgallery.R.id.loadingLayout)
    RelativeLayout loadingLayout;

    @InjectView(com.retrotest.instagramgallery.R.id.notificationLayout)
    RelativeLayout notificationLayout;

    private InstagramMedia currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.retrotest.instagramgallery.R.layout.activity_gallery);
        ButterKnife.inject(this);

        galleryPagerLayout.setVisibility(View.GONE);
        circleMenu.setVisibility(View.GONE);
        circleMenu.setInAnimation(AnimationUtils.loadAnimation(this, com.retrotest.instagramgallery.R.anim.fade_in_animation));
        circleMenu.setOutAnimation(AnimationUtils.loadAnimation(this, com.retrotest.instagramgallery.R.anim.fade_out_animation));

        // add scroll listener
        galleryGrid.setOnScrollListener(this);


        // create realm instance
        Realm realm = Realm.getInstance(this);
        realm.addChangeListener(this);

        // get all records
        RealmResults<InstagramMedia> allRecords = realm.where(InstagramMedia.class).findAllSorted("createdTime", false);

        // get non deleted records
        RealmResults<InstagramMedia> mediaRealmResults = realm.where(InstagramMedia.class).equalTo("isDeleted", false).findAllSorted("createdTime", false);

        InstagramMediaAdapter gridAdapter = new InstagramMediaAdapter(this, mediaRealmResults, true);
        galleryGrid.setAdapter(gridAdapter);


        if (allRecords.size() > 0 && mediaRealmResults.size() == 0) {
            // all items are deleted - show notification layout
            notificationLayout.setVisibility(View.VISIBLE);
        }

        GetUserRecentMediaRequest request;
        if (allRecords != null && allRecords.size() > 0) {
            loadingLayout.setVisibility(View.GONE);
            InstagramMedia media = allRecords.first();
            // get newest media
            request = new GetUserRecentMediaRequest(media.getId());
        } else {
            loadingLayout.setVisibility(View.VISIBLE);
            request = new GetUserRecentMediaRequest();
        }
        if (ConnectionHelper.hasConnection(this)) {
            getSpiceManager(SpiceManagerProvider.INSTAGRAM_USER_SERVICE).execute(request, new GetUserRecentMediaListener());
        } else {
            // TODO: create receiver for connection change
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @OnClick(com.retrotest.instagramgallery.R.id.galleryCloseButton)
    public void onCloseButton() {
        galleryPagerLayout.setVisibility(View.GONE);
        galleryPager.setAdapter(null);
    }

    @OnItemClick(com.retrotest.instagramgallery.R.id.galleryGrid)
    public void onGalleryItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(InstagramGalleryApp.LOG, "item click");
        circleMenu.setVisibility(View.GONE);

        List<InstagramMedia> mediaList = new LinkedList<>();

        InstagramListPageAdapter pagerAdapter = new InstagramListPageAdapter(this);
        for (int i = 0; i < galleryGrid.getAdapter().getCount(); i++) {
            pagerAdapter.add((InstagramMedia) galleryGrid.getAdapter().getItem(i));
        }

        galleryPager.setAdapter(pagerAdapter);
        galleryPager.setCurrentItem(position);
        galleryPagerLayout.setVisibility(View.VISIBLE);
    }

    @OnItemLongClick(com.retrotest.instagramgallery.R.id.galleryGrid)
    public boolean onGalleryItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(InstagramGalleryApp.LOG, "item long click");

        currentItem = ((InstagramMediaAdapter)galleryGrid.getAdapter()).getItem(position);

        circleMenu.showMenuForView(view);

        return true;
    }

    /**
     * On realm change content
     */
    @Override
    public void onChange() {
        Log.i(InstagramGalleryApp.LOG, "Content changed");
        if (galleryGrid.getAdapter().getCount() > 0 ) {
            loadingLayout.setVisibility(View.GONE);
            notificationLayout.setVisibility(View.GONE);
        }

    }

    /**
     * When gallery is updated (or not show notification to user)
     * @param event
     */
    @Subscribe
    public void onUpdateCompleted(UpdateCompletedEvent event) {
        if (event != null && event.getValue() != null) {
            String notificationMessage = event.getValue()
                    ? getString(com.retrotest.instagramgallery.R.string.update_completed_successfully)
                    : getString(com.retrotest.instagramgallery.R.string.update_completed_no_new_data);

            Toast.makeText(this, notificationMessage, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * If some unknown error was received from server
     * @param event
     */
    @Subscribe
    public void onUnknownError(ApiUnknownErrorEvent event) {
        if (event != null && StringUtils.isNotEmpty(event.getValue())) {
            Toast.makeText(this, event.getValue(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (galleryPagerLayout.getVisibility() == View.VISIBLE) {
            galleryPagerLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(com.retrotest.instagramgallery.R.id.deleteBtn)
    public void deleteItem() {
        Log.i(InstagramGalleryApp.LOG, "delete item");
        circleMenu.setVisibility(View.GONE);

        // show confirmation popup

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(com.retrotest.instagramgallery.R.string.delete_item_title)
                .setMessage(com.retrotest.instagramgallery.R.string.delete_item_message)
                .setPositiveButton(com.retrotest.instagramgallery.R.string.delete_item_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // mark object as deleted
                        Realm realm = Realm.getInstance(getApplicationContext());

                        realm.beginTransaction();

                        currentItem.setIsDeleted(true);

                        realm.commitTransaction();
                    }
                })
                .setNegativeButton(com.retrotest.instagramgallery.R.string.delete_item_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Facebook button click
     */
    @OnClick(com.retrotest.instagramgallery.R.id.facebookBtn)
    public void shareFacebook() {
        Log.i(InstagramGalleryApp.LOG, "share facebook");
        circleMenu.setVisibility(View.GONE);
        initShareIntent("facebook", "Instagram Gallery Share", currentItem.getLink());
    }

    /**
     * Twitter button click
     */
    @OnClick(com.retrotest.instagramgallery.R.id.twitterBtn)
    public void shareTwitter() {
        Log.i(InstagramGalleryApp.LOG, "share twitter");
        circleMenu.setVisibility(View.GONE);
        initShareIntent("twitter", "Instagram Gallery Share", currentItem.getLink());

    }

    /**
     * Google button click
     */
    @OnClick(com.retrotest.instagramgallery.R.id.googleplusBtn)
    public void shareGooglePlus() {
        Log.i(InstagramGalleryApp.LOG, "share google plus");
        circleMenu.setVisibility(View.GONE);

        // NOTICE: not implemented - related to developer account
        Toast.makeText(this, "Google plus sharing is currently not available", Toast.LENGTH_SHORT).show();
    }

    /**
     * Universal sharing method
     *
     * @param type of application: twitter, facebook etc.
     * @param subject message subject
     * @param textMessage message content
     */
    private void initShareIntent(String type, String subject, String textMessage) {

        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT,  subject);
        share.putExtra(Intent.EXTRA_TEXT, textMessage);

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type) ) {

                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }

            startActivity(Intent.createChooser(share, "Select"));
        } else {
            Toast.makeText(this, "No sharing apps available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (circleMenu.getVisibility() == View.VISIBLE) {
            circleMenu.setVisibility(View.GONE);
        }
    }
}
