package com.retrotest.instagramgallery.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A page adapter which works with a large data set by reusing views.
 */
public abstract class ListPageAdapter<T> extends PagerAdapter {

    // Views that can be reused.
    private final List<View> mDiscardedViews = new ArrayList<View>();
    // Views that are already in use.
    private final SparseArray<View> mBindedViews = new SparseArray<View>();

    protected final ArrayList<T> mItems;
    public ListPageAdapter(Context context, int viewRes) {
        mItems = new ArrayList<T>();
        mInflator = LayoutInflater.from(context);
        mResourceId = viewRes;
    }
    private final LayoutInflater mInflator;

    private final int mResourceId;

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == mBindedViews.get(mItems.indexOf(obj));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = mBindedViews.get(position);
        if (view != null) {
            mDiscardedViews.add(view);
            mBindedViews.remove(position);
            container.removeView(view);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View child = mDiscardedViews.isEmpty() ?
                mInflator.inflate(mResourceId, container, false) :
                mDiscardedViews.remove(0);

        T data = mItems.get(position);
        initView(child, data, position);

        mBindedViews.append(position, child);
        container.addView(child, 0);
        return data;
    }

    public void add(T item) {
        mItems.add(item);
    }

    public T remove(int position) {
        return mItems.remove(position);
    }

    public void clear() {
        mItems.clear();
    }

    /**
     * Initiate the view here
     */
    public abstract void initView(View v, T item, int position);
}
