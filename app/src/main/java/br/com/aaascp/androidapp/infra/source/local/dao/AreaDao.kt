package br.com.aaascp.androidapp.infra.source.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import br.com.aaascp.androidapp.infra.source.local.entity.Area

@Dao
interface AreaDao {

    @Query("SELECT * from Area")
    fun getAll(): LiveData<List<Area>>

    @Insert(onConflict = REPLACE)
    fun save(areas: List<Area>)

    @Query("DELETE FROM Area")
    fun removeAll()
}
