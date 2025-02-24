package com.example.notesapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey (autoGenerate = true ) val id : Int,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "note") val note : String)
