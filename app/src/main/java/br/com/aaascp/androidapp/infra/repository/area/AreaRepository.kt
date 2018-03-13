package br.com.aaascp.androidapp.infra.repository.area

import br.com.aaascp.androidapp.infra.repository.Listing
import br.com.aaascp.androidapp.infra.source.local.entity.Area

interface AreaRepository {
    fun getAll(): Listing<Area>
}