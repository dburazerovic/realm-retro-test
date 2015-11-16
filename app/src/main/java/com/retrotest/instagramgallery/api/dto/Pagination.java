package com.retrotest.instagramgallery.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Instagram Pagination class
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination {

    @JsonProperty("next_url")
    private String nextURL;

    @JsonProperty("next_max_id")
    private String nextMaxId;

    public Pagination() {
    }

    public Pagination(String nextURL, String nextMaxId) {
        this.nextURL = nextURL;
        this.nextMaxId = nextMaxId;
    }

    public String getNextURL() {
        return nextURL;
    }

    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }

    public String getNextMaxId() {
        return nextMaxId;
    }

    public void setNextMaxId(String nextMaxId) {
        this.nextMaxId = nextMaxId;
    }
}
