package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.content.Context
import br.com.aaascp.androidapp.domain.entity.Lesson
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.dao.LessonDao
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.body.request.AreaFilter
import br.com.aaascp.androidapp.infra.source.remote.body.request.LessonFilterRequestBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.LessonResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class LessonRepository(context: Context) {

    private val db = provideDatabase(context)
    private val lessonDao = db.lessonDao()
    private val executor =
            ThreadPoolExecutor(
                    2,
                    2,
                    0,
                    TimeUnit.MILLISECONDS,
                    LinkedBlockingQueue<Runnable>())


    fun getAllForArea(areaId: String): LiveData<List<Lesson>> {
        refreshSubjectList(areaId)
        return lessonDao.getAllForArea(areaId)
    }

    private fun refreshSubjectList(areaId: String) {
        val lessonEndpointEndpoint =
                ServiceGenerator.createService(LessonEndpoint::class.java)

        lessonEndpointEndpoint
                .getAllForArea2(
                        LessonFilterRequestBody(
                                AreaFilter(areaId)).toString())
                .enqueue(MyCallback(lessonDao, executor))
    }

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "appdb.db"
        ).fallbackToDestructiveMigration()
                .build()
    }

    class MyCallback(
            private val lessonDao: LessonDao,
            private val executor: Executor) : Callback<DataResponseBody<List<LessonResponseBody>>> {
        override fun onFailure(
                call: Call<DataResponseBody<List<LessonResponseBody>>>?,
                t: Throwable?) {

        }

        override fun onResponse(
                call: Call<DataResponseBody<List<LessonResponseBody>>>?,
                response: Response<DataResponseBody<List<LessonResponseBody>>>?) {

            response?.body()?.let {
                executor.execute({
                    //                    lessonDao.removeAllForArea(areaId)
                    lessonDao.save(transform(it.data))
                })
            }
        }

        private fun transform(lessons: List<LessonResponseBody>): List<Lesson> {
            return lessons.map {
                Lesson(it.id, it.title, it.area.id)
            }
        }
    }
}
