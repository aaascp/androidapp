package br.com.aaascp.androidapp.infra.repository.area

import android.arch.paging.PagedList
import android.arch.paging.PagingRequestHelper
import android.support.annotation.MainThread
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.adapter.AreaAdapter
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.remote.body.AreaResponse
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.util.createStatusLiveData

class AreaBoundaryCallback(
        private val endpoint: AreaEndpoint,
        private val handleResponse: (List<Area>?) -> Unit)
    : PagedList.BoundaryCallback<Area>() {

    val helper = MainApplication.component.getPagingRequestHelper()
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            endpoint.getAll()
                    .enqueue(createWebserviceCallback(it))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Area) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            endpoint.getAll(
                    itemAtEnd.indexInResponse,
                    AreaEndpoint.NETWORK_PAGE_SIZE)
                    .enqueue(createWebserviceCallback(it))
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Area) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallback(
            pagingRequestHelper: PagingRequestHelper.Request.Callback
    ): RepositoryCallbackBase<AreaResponse> {

        return RepositoryCallbackBase({
            handleResponse(AreaAdapter.adapt(it.data))
            pagingRequestHelper.recordSuccess()
        }, {
            pagingRequestHelper.recordFailure(it)
        })
    }
}