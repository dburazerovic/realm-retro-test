package com.retrotest.instagramgallery.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Image data object with link to instagram image
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageData {

    @JsonProperty("url")
    private String url;

    @JsonProperty("width")
    private int width;

    @JsonProperty("height")
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
