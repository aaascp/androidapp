package br.com.aaascp.androidapp.presentation.util

import android.arch.lifecycle.Observer


class ObserverUtil {

    companion object {

        class OnChanged<Observed>(
                private val callback: (Observed) -> Unit
        ) : Observer<Observed> {

            override fun onChanged(data: Observed?) {
                data?.let {
                    callback(data)
                }
            }
        }
    }
}