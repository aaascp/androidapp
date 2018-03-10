package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import br.com.aaascp.androidapp.domain.entity.Area
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.dao.AreaDao
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.body.response.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class AreaRepository(private val context: Context) {

    private val db = provideDatabase(context)
    private val areaDao = db.areaDao()
    private val executor =
            ThreadPoolExecutor(
                    2,
                    2,
                    0,
                    TimeUnit.MILLISECONDS,
                    LinkedBlockingQueue<Runnable>())


    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "appdb.db"
        ).fallbackToDestructiveMigration()
                .build()
    }

    fun getSubjectList(): LiveData<List<Area>> {
        refreshSubjectList()
        return areaDao.getAll()
    }

    private fun refreshSubjectList() {
        val areaEndpoint = ServiceGenerator.createService(AreaEndpoint::class.java)
        areaEndpoint.getAreaList().enqueue(MyCallback(areaDao, executor))
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

