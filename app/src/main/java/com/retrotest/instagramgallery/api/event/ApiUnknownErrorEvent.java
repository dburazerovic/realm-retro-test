package com.retrotest.instagramgallery.api.event;

/**
 * Dispatch event when error is unknown
 *
 * Created by Damir Burazerovic on 8/11/15.
 */
public class ApiUnknownErrorEvent extends AbstractValueEvent<String> {
    public ApiUnknownErrorEvent(String value) {
        super(value);
    }
}
