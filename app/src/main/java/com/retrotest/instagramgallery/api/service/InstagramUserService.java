package com.retrotest.instagramgallery.api.service;

import com.retrotest.instagramgallery.api.networkhub.InstagramUserHub;
import com.retrotest.instagramgallery.constants.Constants;
import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

/**
 * Created by Damir Burazerovic on 8/5/15.
 */
public class InstagramUserService extends RetrofitJackson2SpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(InstagramUserHub.class);
    }

    @Override
    protected String getServerUrl() {
        return Constants.INSTAGRAM_API_BASE_URL;
    }
}
