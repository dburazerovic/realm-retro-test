package com.retrotest.instagramgallery.api.event;

/**
 * Abstract event for events with custom value
 *
 * Created by Damir Burazerovic on 8/11/15.
 */
public class AbstractValueEvent<T> {

    private T value;


    public AbstractValueEvent(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
