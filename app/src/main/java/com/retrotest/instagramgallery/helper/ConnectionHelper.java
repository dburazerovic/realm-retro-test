package com.retrotest.instagramgallery.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Helper class for connection availability checking
 *
 * Created by Damir Burazerovic on 8/5/15.
 */
public class ConnectionHelper {

    public static Boolean hasConnection(Context context) {
        Boolean isConnected;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // check if we have connection
        isConnected = (networkInfo != null && networkInfo.isConnected());

        return isConnected;
    }
}
