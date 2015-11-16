package com.retrotest.instagramgallery.api;

import com.retrotest.instagramgallery.api.service.InstagramUserService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public interface SpiceManagerProvider {

    public static final Class INSTAGRAM_USER_SERVICE = InstagramUserService.class;

    /**
     * Returns the spice manager for the requested service class.
     *
     * @param spiceManagerServiceClass
     * @return SpiceManager
     */
    public SpiceManager getSpiceManager(Class spiceManagerServiceClass);
}
