package com.retrotest.instagramgallery.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Media data contains main property for instagram media
 * Some properties missing and can be added later
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {



    @JsonProperty("id")
    private String id;

    @JsonProperty("link")
    private String link;

    @JsonProperty("created_time")
    private Long   createdTime;

    @JsonProperty("images")
    private Images images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
