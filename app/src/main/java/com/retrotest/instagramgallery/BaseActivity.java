package com.retrotest.instagramgallery;

import android.app.Activity;

import com.retrotest.instagramgallery.api.SpiceManagerProvider;
import com.octo.android.robospice.SpiceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public class BaseActivity extends Activity implements SpiceManagerProvider {


    private Map<Class, SpiceManager> mSpiceManagers = new HashMap<>();

    {
        mSpiceManagers.put(SpiceManagerProvider.INSTAGRAM_USER_SERVICE, new SpiceManager(SpiceManagerProvider.INSTAGRAM_USER_SERVICE));
    }

    @Override
    protected void onStart() {
        for(Map.Entry<Class, SpiceManager> entry: mSpiceManagers.entrySet()) {
            entry.getValue().start(this);
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        InstagramGalleryApp.getEventBus().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        InstagramGalleryApp.getEventBus().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        for(Map.Entry<Class, SpiceManager> entry: mSpiceManagers.entrySet()) {
            entry.getValue().shouldStop();
        }
        super.onStop();
    }

    @Override
    public SpiceManager getSpiceManager(Class spiceManagerServiceClass) {
        return mSpiceManagers.get(spiceManagerServiceClass);
    }
}
