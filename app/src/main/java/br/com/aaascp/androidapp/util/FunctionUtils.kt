package br.com.aaascp.androidapp.util

class FunctionUtils {

    companion object {
        class Runnable1<T>(
                private val task: (T?) -> Unit
        ) : Runnable {

            var parameter: T? = null

            override fun run() {
                task(parameter)
            }
        }
    }
}