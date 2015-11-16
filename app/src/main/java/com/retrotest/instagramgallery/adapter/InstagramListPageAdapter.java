package com.retrotest.instagramgallery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.retrotest.instagramgallery.R;
import com.retrotest.instagramgallery.db.entities.InstagramMedia;

import org.apache.commons.lang3.RandomUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Page adapter
 *
 * Created by Damir Burazerovic on 8/6/15.
 */
public class InstagramListPageAdapter extends ListPageAdapter<InstagramMedia> {

    private final int NUM_OF_COLORS = 8;

    private AQuery aQuery;
    private final Context mContext;

    public InstagramListPageAdapter(Context context) {
        super(context, R.layout.item_gallery_pager);
        aQuery = new AQuery(context);
        mContext = context;
    }

    @Override
    public void initView(View view, InstagramMedia item, int position) {

        ImageView imageView = (ImageView) view.findViewById(R.id.standardImage);
        LinearLayout colorsLayout = (LinearLayout) view.findViewById(R.id.colorsLayout);

        // getting cached low res bitmap
        Bitmap preset = aQuery.getCachedImage(item.getLowResUrl());
        final List<Integer> bitmapColors = bitmapColors(preset, NUM_OF_COLORS);

        createSpectar(colorsLayout, bitmapColors);

        ImageOptions options = new ImageOptions();
        options.memCache = true;
        options.fileCache = true;
        options.preset = preset;

        // this is fall back image, but of course it should not be android launcher icon
        options.fallback = R.drawable.ic_launcher;

        aQuery.id(imageView).image(item.getStandardResUrl(), options);
    }

    /**
     * Method return list of colors on random pixels on image
     * @param bitmap source bitmap
     * @param numberOfPoints
     * @return
     */
    private List<Integer> bitmapColors(Bitmap bitmap, int numberOfPoints) {
        List<Integer> colors = new LinkedList<>();
        if (bitmap != null) {
            int maxX = bitmap.getWidth() - 1;
            int maxY = bitmap.getHeight() - 1;
            int i = 0;
            while (i < numberOfPoints) {
                int randomX = RandomUtils.nextInt(0, maxX);
                int randomY = RandomUtils.nextInt(0, maxY);

                int pixel = bitmap.getPixel(randomX, randomY);

                colors.add(pixel);
                i++;
            }
        }
        return colors;
    }


    private void createSpectar(LinearLayout spectralLayout, List<Integer> colors) {
        if (spectralLayout != null) {

            spectralLayout.removeAllViews();
            spectralLayout.setWeightSum(colors.size());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;

            for (Integer color : colors) {
                ImageView layout = new ImageView(mContext);
                layout.setLayoutParams(params);
                layout.setAdjustViewBounds(true);
                layout.setMinimumWidth(20);
                layout.setBackgroundColor(color);
                spectralLayout.addView(layout);
            }
        }
        spectralLayout.invalidate();
    }
}
