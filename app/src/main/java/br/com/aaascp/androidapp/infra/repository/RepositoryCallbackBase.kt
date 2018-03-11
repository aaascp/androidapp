package br.com.aaascp.androidapp.infra.repository

import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.util.FunctionUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class RepositoryCallbackBase<T> @Inject constructor(
        private val success: FunctionUtils.Companion.Runnable1<T>,
        private val failure: FunctionUtils.Companion.Runnable1<T>? = null,
        private val executor: Executor = MainApplication.component.getExecutor()
) : Callback<T> {

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        response?.body()?.let {
            success.parameter = it
            executor.execute(success)
        }
    }

    override fun onFailure(call: Call<T>?, throwable: Throwable?) {
        failure?.let {
            executor.execute(failure)
        }
    }
}