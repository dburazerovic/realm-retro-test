package com.retrotest.instagramgallery.api.event;

import android.util.Log;

import com.retrotest.instagramgallery.InstagramGalleryApp;
import com.octo.android.robospice.persistence.exception.SpiceException;

import retrofit.RetrofitError;

/**
 * Api error event
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
public class ApiErrorEvent {

    private String errorMessage;
    private SpiceException exception;

    public ApiErrorEvent(String errorMessage, SpiceException exception) {
        this.errorMessage = errorMessage;
        this.exception = exception;

        RetrofitError retrofitError = (RetrofitError) exception.getCause();
        this.errorMessage = "There was an network error, please try again";
        Log.d(InstagramGalleryApp.LOG, "Retrofit error: " + retrofitError.getMessage());

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public SpiceException getException() {
        return exception;
    }
}
