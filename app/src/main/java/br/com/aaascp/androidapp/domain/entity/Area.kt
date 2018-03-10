package br.com.aaascp.androidapp.domain.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Area(
        @PrimaryKey val id: String,
        val title: String
);