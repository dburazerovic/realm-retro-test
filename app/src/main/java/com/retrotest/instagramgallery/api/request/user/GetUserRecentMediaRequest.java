package com.retrotest.instagramgallery.api.request.user;

import com.retrotest.instagramgallery.api.ApiResultWrapper;
import com.retrotest.instagramgallery.api.dto.MediaList;
import com.retrotest.instagramgallery.api.networkhub.InstagramUserHub;
import com.retrotest.instagramgallery.constants.Constants;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * Created by Damir Burazerovic on 8/4/15.
 */
public class GetUserRecentMediaRequest extends RetrofitSpiceRequest<ApiResultWrapper<MediaList>, InstagramUserHub> {

    private final String COUNT     = "count";
    private final String CLIENT_ID = "client_id";
    private final String MIN_ID    = "min_id";
    private final String MAX_ID    = "max_id";

    private String minID;
    private String maxID;

    public GetUserRecentMediaRequest() {
        this(null);
    }

    /**
     * Constructor for updating content with latest api results
     * @param lowestId
     */
    public GetUserRecentMediaRequest(String lowestId) {
        this(lowestId, null);
    }

    public GetUserRecentMediaRequest(String minID, String maxID) {
        super((Class<ApiResultWrapper<MediaList>>)(Class<?>)ApiResultWrapper.class, InstagramUserHub.class);
        this.minID = minID;
        this.maxID = maxID;
    }

    @Override
    public ApiResultWrapper<MediaList> loadDataFromNetwork() throws Exception {

        HashMap<String, String> params = new HashMap<>();
        params.put(CLIENT_ID, Constants.INSTAGRAM_CLIENT_ID);
        params.put(COUNT, "32");

        // add params for minimal id
        // service will return media later than this min_id
        if (StringUtils.isNotEmpty(minID)) {
            params.put(MIN_ID, minID);
        }

        // add params for maximal id
        // service will return media earlier than this min_id
        if (StringUtils.isNotEmpty(maxID)) {
            params.put(MAX_ID, maxID);
        }

        return getService().getRecentInstagramMedia(params);
    }
}
