package org.taitasciore.android.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by roberto on 22/03/17.
 */


/**
 * Helper class
 */
public final class NetworkUtils {

    /**
     * Determines network status
     * @param context Current context
     * @return True if there's network connection. False otherwise
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
