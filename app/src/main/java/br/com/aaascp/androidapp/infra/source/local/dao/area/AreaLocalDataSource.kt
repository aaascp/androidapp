package br.com.aaascp.androidapp.infra.source.local.dao.area

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.source.local.entity.Area

interface AreaLocalDataSource {

    fun getAll(): LiveData<List<Area>>

    fun save(areas: List<Area>)

    fun removeAll()

}