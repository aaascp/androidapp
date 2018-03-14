package br.com.aaascp.androidapp.infra.source.remote.endpoint

import android.os.Handler
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallMock<T>(
        private val response: Response<T>,
        private val result: Boolean
) : Call<T> {

    override fun enqueue(callback: Callback<T>) {
        Handler().postDelayed({
            when (result) {
                true -> callback.onResponse(this, response)
                false -> callback.onFailure(this, Throwable("FAILURE"))
            }
        }, 5000)
    }

    override fun isExecuted(): Boolean {
        TODO("not implemented")
    }

    override fun clone(): Call<T> {
        TODO("not implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("not implemented")
    }

    override fun cancel() {
        TODO("not implemented")
    }

    override fun execute(): Response<T> {
        TODO("not implemented")
    }

    override fun request(): Request {
        TODO("not implemented")
    }

}