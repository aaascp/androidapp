package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.domain.entity.Area
import br.com.aaascp.androidapp.infra.source.local.dao.AreaDao
import br.com.aaascp.androidapp.infra.source.remote.body.response.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.util.FunctionUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaRepository @Inject constructor(
        private val endpoint: AreaEndpoint,
        private val areaDao: AreaDao) {

    fun getSubjectList(): LiveData<List<Area>> {
        refreshSubjectList()
        return areaDao.getAll()
    }

    private fun refreshSubjectList() {
        endpoint.getAreaList().enqueue(
                RepositoryCallbackBase(
                        FunctionUtils.Companion.Runnable1({
                            it?.data?.let {
                                areaDao.removeAll()
                                areaDao.save(transform(it))
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

