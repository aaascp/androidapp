package br.com.aaascp.androidapp.infra.source.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.aaascp.androidapp.domain.entity.Lesson

@Dao
interface LessonDao {

    @Query("SELECT * FROM Lesson WHERE areaId = :areaId")
    fun getAllForArea(areaId: String): LiveData<List<Lesson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(lessons: List<Lesson>)

    @Query("DELETE FROM Lesson WHERE areaId = :areaId")
    fun removeAllForArea(areaId: String)
}