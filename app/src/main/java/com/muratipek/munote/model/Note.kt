package com.muratipek.munote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "notes")
data class Note (
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val note: String
    ){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}