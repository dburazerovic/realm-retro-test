package com.retrotest.instagramgallery.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Instagram Images
 *
 * Created by Damir Burazerovic on 8/5/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Images {

    @JsonProperty("low_resolution")
    ImageData lowResolution;

    @JsonProperty("thumbnail")
    ImageData thumbnail;

    @JsonProperty("standard_resolution")
    ImageData standardResolution;

    public Images() {
    }

    public ImageData getLowResolution() {
        return lowResolution;
    }

    public void setLowResolution(ImageData lowResolution) {
        this.lowResolution = lowResolution;
    }

    public ImageData getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageData thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageData getStandardResolution() {
        return standardResolution;
    }

    public void setStandardResolution(ImageData standardResolution) {
        this.standardResolution = standardResolution;
    }
}
