package br.com.aaascp.androidapp.util

class FunctionUtils {

    companion object {
        open class Runnable1<T : Any>(
                private val task: (T) -> Unit
        ) : Runnable {

            lateinit var parameter: T

            override fun run() {
                task(parameter)
            }
        }
    }
}