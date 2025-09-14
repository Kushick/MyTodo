package com.example.mytodo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val description: String?,
    val date: String,
    val time: String
)
