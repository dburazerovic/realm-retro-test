package com.retrotest.instagramgallery.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.retrotest.instagramgallery.R;
import com.retrotest.instagramgallery.db.entities.InstagramMedia;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Main adapter for grid view
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
public class InstagramMediaAdapter extends RealmBaseAdapter<InstagramMedia> implements ListAdapter {

    private AQuery aQuery;
    private ImageOptions options;

    public class GalleryItemHolder {

        @InjectView(R.id.galleryItemImage)
        ImageView galleryItemImage;

        @InjectView(R.id.galleryItemProgress)
        ProgressBar galleryItemProgress;

        public GalleryItemHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public InstagramMediaAdapter(Context context, RealmResults<InstagramMedia> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        aQuery = new AQuery(context);
        options = new ImageOptions();
        options.memCache = true;
        options.fileCache = true;
        options.animation = R.anim.scale_animation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GalleryItemHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gallery_image,parent, false);
            viewHolder = new GalleryItemHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GalleryItemHolder) convertView.getTag();
        }

        final InstagramMedia item = realmResults.get(position);
        aQuery.id(viewHolder.galleryItemImage).image(item.getLowResUrl(), true, true);
        viewHolder.galleryItemProgress.setVisibility(View.GONE);

        aQuery.id(convertView).animate(R.anim.scale_animation);
        return convertView;
    }

    public RealmResults<InstagramMedia> getRealmResults() {
        return realmResults;
    }
}
