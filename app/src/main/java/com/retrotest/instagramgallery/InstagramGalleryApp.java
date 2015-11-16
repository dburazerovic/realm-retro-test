package com.retrotest.instagramgallery;

import android.app.Application;

import com.androidquery.callback.BitmapAjaxCallback;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public class InstagramGalleryApp extends Application {

    public static String LOG = "Instagram Gallery - Log";

    private static Bus mEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mEventBus = new Bus(ThreadEnforcer.ANY);
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onLowMemory() {
        BitmapAjaxCallback.clearCache();
        super.onLowMemory();
    }

    public static Bus getEventBus() {
        return mEventBus;
    }
}
