package br.com.aaascp.androidapp.infra.repository.lesson

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import br.com.aaascp.androidapp.infra.adapter.LessonAdapter
import br.com.aaascp.androidapp.infra.repository.Listing
import br.com.aaascp.androidapp.infra.repository.NetworkState
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonFilterRequestBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: LessonEndpoint
) : LessonRepository {

    override fun getForArea(areaId: String): Listing<Lesson> {

        val boundaryCallback =
                LessonBoundaryCallback(
                        areaId,
                        endpoint,
                        this::insertResultIntoDb)

        val dataSourceFactory = db.lessonDao().getForArea(areaId)
        val builder =
                LivePagedListBuilder(
                        dataSourceFactory,
                        LessonEndpoint.NETWORK_PAGE_SIZE)
                        .setBoundaryCallback(boundaryCallback)

        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger, {
            refresh(areaId)
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

    private fun insertResultIntoDb(
            areaId: String,
            lessons: List<Lesson>?) {

        lessons?.let {
            db.runInTransaction {
                val start = db.lessonDao().getNextIndexForArea(areaId)
                val items = it.mapIndexed { index, lesson ->
                    lesson.indexInResponse = start + index
                    lesson
                }
                db.lessonDao().save(items)
            }
        }
    }

    private fun refresh(areaId: String): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        endpoint.getForArea(
                LessonFilterRequestBody(areaId).serialize())
                .enqueue(
                        RepositoryCallbackBase({
                            db.runInTransaction {
                                db.lessonDao().removeAllForArea(areaId)
                                insertResultIntoDb(
                                        areaId,
                                        LessonAdapter.adapt(it.data))
                            }
                            networkState.postValue(NetworkState.LOADED)
                        }, {
                            networkState.value = NetworkState.error(it.message)
                        })
                )
        return networkState
    }
}
