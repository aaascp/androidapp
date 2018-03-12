package br.com.aaascp.androidapp.util

import android.content.Context
import android.net.ConnectivityManager
import br.com.aaascp.androidapp.MainApplication


class AndroidUtils {

    fun isOnline(): Boolean {
        val context = MainApplication.component.getApplicationContext()
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager

        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
