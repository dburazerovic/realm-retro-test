package com.retrotest.instagramgallery.api.listener;

import android.util.Log;

import com.retrotest.instagramgallery.InstagramGalleryApp;
import com.retrotest.instagramgallery.api.event.ApiErrorEvent;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public abstract class AbstractRequestListener<RESULT> implements RequestListener<RESULT> {

    private static final String DEFAULT_ERROR_MESSAGE = "Request error";

    private String errorMessage;

    public AbstractRequestListener() {
        this(DEFAULT_ERROR_MESSAGE);
    }

    public AbstractRequestListener(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Log.i(InstagramGalleryApp.LOG, "onRequestFailure");
        Log.e(InstagramGalleryApp.LOG, errorMessage, spiceException);
        if (errorMessage != null) {
            InstagramGalleryApp.getEventBus().post(new ApiErrorEvent(errorMessage, spiceException));
        }
    }
}
