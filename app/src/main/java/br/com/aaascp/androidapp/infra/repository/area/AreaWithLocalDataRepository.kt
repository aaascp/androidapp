package br.com.aaascp.androidapp.infra.repository.area

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import br.com.aaascp.androidapp.infra.adapter.AreaAdapter
import br.com.aaascp.androidapp.infra.repository.Listing
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: AreaEndpoint
) : AreaRepository {

    override fun getAll(): Listing<Area> {

        val boundaryCallback =
                AreaBoundaryCallback(
                        endpoint,
                        this::insertResultIntoDb)

        val dataSourceFactory = db.areaDao().getAll()
        val builder =
                LivePagedListBuilder(
                        dataSourceFactory,
                        AreaEndpoint.NETWORK_PAGE_SIZE)
                        .setBoundaryCallback(boundaryCallback)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger, {
            refresh()
        })

        return Listing(
                pagedList = builder.build(),
                networkState = boundaryCallback.networkState,
                retry = {
                    boundaryCallback.helper.retryAllFailed()
                },
                refresh = {
                    refreshTrigger.value = null
                },
                refreshState = refreshState
        )
    }

    private fun insertResultIntoDb(areas: List<Area>?) {

        areas?.let {
            db.runInTransaction {
                val start = db.areaDao().getNextIndex()
                val items = it.mapIndexed { index, area ->
                    area.indexInResponse = start + index
                    area
                }
                db.areaDao().save(items)
            }
        }
    }

    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        endpoint.getAll().enqueue(
                RepositoryCallbackBase({
                    db.runInTransaction {
                        db.areaDao().removeAll()
                        insertResultIntoDb(AreaAdapter.adapt(it.data))
                    }
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    networkState.value = NetworkState.error(it.message)
                }))
        return networkState
    }
}

