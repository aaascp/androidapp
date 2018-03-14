package br.com.aaascp.androidapp.infra.repository

import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.util.FunctionUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class RepositoryCallbackBase<T : Any> @Inject constructor(
        private val success: (T) -> Unit,
        private val failure: (Throwable) -> Unit = {},
        private val executor: Executor = MainApplication.component.getExecutor()
) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val runnable = FunctionUtils.Companion.Runnable1(success)
        val handled = handleResponse(response)
        if(!handled) {
            response.body()?.let {
                runnable.parameter = it
                executor.execute(runnable)
            }
        }
    }

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        failure(throwable)
    }

    private fun handleResponse(response: Response<T>): Boolean {
        when(response.code()) {
            401 -> {
                failure(Throwable(response.message()))
                return true
            }
        }
        return false
    }
}