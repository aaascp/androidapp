package br.com.aaascp.androidapp.infra.repository.lesson

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.dao.lesson.LessonLocalDataSource
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonFilterRequestBody
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonResponseBody
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import br.com.aaascp.androidapp.util.FunctionUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LessonWithLocalDataRepository @Inject constructor(
        private val endpoint: LessonEndpoint,
        private val lessonLocalDataSource: LessonLocalDataSource
) : LessonRepository {

    override fun getAllForArea(areaId: String): LiveData<List<Lesson>> {
        refreshLessonList(areaId)
        return lessonLocalDataSource.getAllForArea(areaId)
    }

    private fun refreshLessonList(areaId: String) {
        endpoint.getAllForArea(
                LessonFilterRequestBody(areaId).serialize())
                .enqueue(
                        RepositoryCallbackBase(
                                FunctionUtils.Companion.Runnable1({
                                    it?.data?.let {
                                        lessonLocalDataSource.removeAllForArea(areaId)
                                        lessonLocalDataSource.save(transform(it))
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