package br.com.aaascp.androidapp.infra.repository.area

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.dao.area.AreaLocalDataSource
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.remote.body.response.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.util.FunctionUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaWithLocalDataRepository @Inject constructor(
        private val endpoint: AreaEndpoint,
        private val areaLocalDataSource: AreaLocalDataSource
) : AreaRepository {

    override fun getAll(): LiveData<List<Area>> {
        refreshSubjectList()
        return areaLocalDataSource.getAll()
    }

    private fun refreshSubjectList() {
        endpoint.getAll().enqueue(
                RepositoryCallbackBase(
                        FunctionUtils.Companion.Runnable1({
                            it?.data?.let {
                                areaLocalDataSource.removeAll()
                                areaLocalDataSource.save(transform(it))
                            }
                        })
                )
        )
    }

    private fun transform(lessons: List<AreaResponseBody>): List<Area> {
        return lessons.map {
            Area(it.id, it.title)
        }
    }
}

