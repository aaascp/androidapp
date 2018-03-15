package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import br.com.aaascp.androidapp.infra.repository.Status.*

enum class ListState {
    LOADING,
    EMPTY,
    ERROR,
    FILLED
}

data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        val refresh: () -> Unit,
        val retry: () -> Unit) {

    val listState = MediatorLiveData<ListState>()

    init {
        listState.addSource(pagedList, Observer {
            it?.let {
                listState.value = when (it.isEmpty()) {
                    true -> ListState.EMPTY
                    false -> ListState.FILLED
                }
            }
        })

        listState.addSource(networkState, Observer {
            val shouldReplace = listState.value != ListState.FILLED
            it?.let {
                listState.value = when (it.status) {
                    RUNNING -> if (shouldReplace) ListState.LOADING else ListState.FILLED
                    FAILED -> if (shouldReplace) ListState.ERROR else ListState.FILLED
                    SUCCESS -> if (shouldReplace) ListState.EMPTY else ListState.FILLED
                }
            }
        })
    }

}