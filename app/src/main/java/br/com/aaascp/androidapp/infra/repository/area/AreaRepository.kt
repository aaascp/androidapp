package br.com.aaascp.androidapp.infra.repository.area

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.source.local.entity.Area

interface AreaRepository {
    fun getAll(): LiveData<List<Area>>
}