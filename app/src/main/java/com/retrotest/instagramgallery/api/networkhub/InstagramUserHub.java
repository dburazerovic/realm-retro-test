package com.retrotest.instagramgallery.api.networkhub;

import com.retrotest.instagramgallery.api.ApiResultWrapper;
import com.retrotest.instagramgallery.api.dto.MediaList;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public interface InstagramUserHub {

    @GET("/users/25025320/media/recent")
    ApiResultWrapper<MediaList> getRecentInstagramMedia(@QueryMap Map<String, String> params);

}
