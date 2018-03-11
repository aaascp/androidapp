package br.com.aaascp.androidapp.infra.repository

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.domain.entity.Lesson
import br.com.aaascp.androidapp.infra.source.local.dao.LessonDao
import br.com.aaascp.androidapp.infra.source.remote.body.request.AreaFilter
import br.com.aaascp.androidapp.infra.source.remote.body.request.LessonFilterRequestBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.LessonResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import br.com.aaascp.androidapp.util.FunctionUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonRepository @Inject constructor(
        private val endpoint: LessonEndpoint,
        private val lessonDao: LessonDao) {


    fun getAllForArea(areaId: String): LiveData<List<Lesson>> {
        refreshSubjectList(areaId)
        return lessonDao.getAllForArea(areaId)
    }

    private fun refreshSubjectList(areaId: String) {
        endpoint.getAllForArea(
                LessonFilterRequestBody(
                        AreaFilter(areaId)).toString())
                .enqueue(
                        RepositoryCallbackBase(
                                FunctionUtils.Companion.Runnable1({
                                    it?.data?.let {
                                        lessonDao.removeAllForArea(areaId)
                                        lessonDao.save(transform(it))
                                    }

                                })
                        )
                )
    }

    private fun transform(lessons: List<LessonResponseBody>): List<Lesson> {
        return lessons.map {
            Lesson(it.id, it.title, it.area.id)
        }
    }
}