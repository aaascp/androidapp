package br.com.aaascp.androidapp.infra.adapter

import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonResponseBody

class LessonAdapter {

    companion object {
        fun adapt(lessons: List<LessonResponseBody>): List<Lesson> {
            return lessons.map {
                Lesson(it.id, it.title, it.area.id)
            }
        }
    }
}
