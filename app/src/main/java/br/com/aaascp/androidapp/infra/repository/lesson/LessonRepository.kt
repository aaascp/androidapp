package br.com.aaascp.androidapp.infra.repository.lesson

import br.com.aaascp.androidapp.infra.repository.Listing
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson

interface LessonRepository {
    fun getForArea(areaId: String): Listing<Lesson>
}