package br.com.aaascp.androidapp.infra.source.local.dao.area

import android.arch.paging.DataSource
import br.com.aaascp.androidapp.infra.source.local.entity.Area

interface AreaLocalDataSource {

    fun getAll(): DataSource.Factory<Int, Area>

    fun save(areas: List<Area>)

    fun removeAll()

    fun getNextIndex(): Int

}