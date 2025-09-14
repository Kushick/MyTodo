package com.example.mytodo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytodo.data.TodoModel

@Database(entities = [TodoModel::class], version = 1)
abstract class ToDoDB: RoomDatabase() {

    abstract fun todoDao(): ToDODao
}