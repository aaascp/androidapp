package br.com.aaascp.androidapp.util

import android.content.Context
import android.net.ConnectivityManager
import br.com.aaascp.androidapp.MainApplication
import java.io.IOException
import java.io.InputStream

class AndroidUtils {

    private val context: Context = MainApplication.component.getApplicationContext()

    fun isOnline(): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager

        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun getAssetFile(filename: String): InputStream? {
        return try {
            context.assets.open(filename)
        } catch (exception: IOException) {
            // TODO Silently failing - Log Exception on some crash tool
            null
        }
    }
}
