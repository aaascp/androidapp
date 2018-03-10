package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.domain.entity.Lesson
import br.com.aaascp.androidapp.infra.source.local.dao.LessonDao
import br.com.aaascp.androidapp.infra.source.remote.body.request.AreaFilter
import br.com.aaascp.androidapp.infra.source.remote.body.request.LessonFilterRequestBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.LessonResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonRepository @Inject constructor(
        private val endpoint: LessonEndpoint,
        private val lessonDao: LessonDao,
        private val executor: Executor) {


    fun getAllForArea(areaId: String): LiveData<List<Lesson>> {
        refreshSubjectList(areaId)
        return lessonDao.getAllForArea(areaId)
    }

    private fun refreshSubjectList(areaId: String) {
        endpoint.getAllForArea(
                        LessonFilterRequestBody(
                                AreaFilter(areaId)).toString())
                .enqueue(
                        MyCallback(
                                areaId,
                                lessonDao,
                                executor))
    }

    class MyCallback(
            private val areaId: String,
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
                    lessonDao.removeAllForArea(areaId)
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
