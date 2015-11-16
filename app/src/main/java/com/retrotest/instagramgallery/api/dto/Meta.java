package com.retrotest.instagramgallery.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Instagram Meta object
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    @JsonProperty("code")
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
