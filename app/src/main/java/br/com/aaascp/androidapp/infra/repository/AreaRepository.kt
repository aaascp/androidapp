package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.domain.entity.Area
import br.com.aaascp.androidapp.infra.source.local.dao.AreaDao
import br.com.aaascp.androidapp.infra.source.remote.body.response.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaRepository @Inject constructor(
        private val endpoint: AreaEndpoint,
        private val areaDao: AreaDao,
        private val executor: Executor) {

    fun getSubjectList(): LiveData<List<Area>> {
        refreshSubjectList()
        return areaDao.getAll()
    }

    private fun refreshSubjectList() {
        endpoint.getAreaList().enqueue(MyCallback(areaDao, executor))
    }

    class MyCallback(
            private val areaDao: AreaDao,
            private val executor: Executor) : Callback<DataResponseBody<List<AreaResponseBody>>> {
        override fun onFailure(
                call: Call<DataResponseBody<List<AreaResponseBody>>>?,
                t: Throwable?) {

        }

        override fun onResponse(
                call: Call<DataResponseBody<List<AreaResponseBody>>>?,
                response: Response<DataResponseBody<List<AreaResponseBody>>>?) {

            response?.body()?.let {
                executor.execute({
                    //                    areaDao.removeAll()
                    areaDao.save(transform(it.data))
                })
            }
        }

        private fun transform(lessons: List<AreaResponseBody>): List<Area> {
            return lessons.map {
                Area(it.id, it.title)
            }
        }
    }
}

