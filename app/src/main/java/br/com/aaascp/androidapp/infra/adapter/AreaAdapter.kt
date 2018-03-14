package br.com.aaascp.androidapp.infra.adapter

import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.remote.body.area.AreaResponseBody

class AreaAdapter {
    companion object {
        fun adapt(lessons: List<AreaResponseBody>): List<Area> {
            return lessons.map {
                Area(it.id, it.title, it.subject.title)
            }
        }
    }
}
