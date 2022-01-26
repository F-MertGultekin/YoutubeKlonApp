package com.example.youtubeklonapp.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VIDEO_ENTITY")
data class VideoEntity(

    @ColumnInfo(name = "VIDEO_ID") val videoId: String?,
    @ColumnInfo(name = "IS_FAVORITE") val isFavorite: String

)
{
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}

