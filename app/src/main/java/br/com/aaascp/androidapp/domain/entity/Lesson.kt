package br.com.aaascp.androidapp.domain.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Lesson(
        @PrimaryKey val id: String,
        val title: String,
        val areaId: String
);