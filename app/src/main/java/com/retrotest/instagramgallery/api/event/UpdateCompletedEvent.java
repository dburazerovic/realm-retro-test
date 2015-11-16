package com.retrotest.instagramgallery.api.event;

/**
 * Event will triggered when updating data is completed
 *
 * true - data updated
 * false - data has not been updated
 *
 * Created by Damir Burazerovic on 8/11/15.
 */
public class UpdateCompletedEvent extends AbstractValueEvent<Boolean>{
    public UpdateCompletedEvent(Boolean value) {
        super(value);
    }
}
