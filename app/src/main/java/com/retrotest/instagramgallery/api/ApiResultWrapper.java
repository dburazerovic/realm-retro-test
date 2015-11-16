package com.retrotest.instagramgallery.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.retrotest.instagramgallery.api.dto.Meta;
import com.retrotest.instagramgallery.api.dto.Pagination;

/**
 * Wrapper for instagram api results
 *
 * Created by Damir Burazerovic on 8/4/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResultWrapper<T> {

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("data")
    private T data;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
