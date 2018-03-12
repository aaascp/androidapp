package br.com.aaascp.androidapp.infra.repository.lesson

import android.arch.paging.PagedList
import android.arch.paging.PagingRequestHelper
import android.support.annotation.MainThread
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.adapter.LessonAdapter
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonFilterRequestBody
import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import br.com.aaascp.androidapp.util.createStatusLiveData

class LessonBoundaryCallback(
        private val areaId: String,
        private val endpoint: LessonEndpoint,
        private val handleResponse: (String, List<Lesson>?) -> Unit)
    : PagedList.BoundaryCallback<Lesson>() {

    private val filter = LessonFilterRequestBody(areaId).serialize()
    val helper = MainApplication.component.getPagingRequestHelper()
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            endpoint.getForArea(filter)
                    .enqueue(createWebserviceCallback(it))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: Lesson) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            endpoint.getForArea(
                    filter,
                    itemAtEnd.indexInResponse)
                    .enqueue(createWebserviceCallback(it))
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Lesson) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallback(
            pagingRequestHelper: PagingRequestHelper.Request.Callback
    ): RepositoryCallbackBase<LessonResponse> {

        return RepositoryCallbackBase({
            handleResponse(areaId, LessonAdapter.adapt(it.data))
            pagingRequestHelper.recordSuccess()
        }, {
            pagingRequestHelper.recordFailure(it)
        })
    }
}