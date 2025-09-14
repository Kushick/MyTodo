package com.example.mytodo.repository

import androidx.lifecycle.LiveData
import com.example.mytodo.data.TodoModel
import com.example.mytodo.room.ToDODao

class todoRepository(private val toDODao: ToDODao) {

    val alltoDos: LiveData<List<TodoModel>> = toDODao.getTodo()

    suspend fun addToDO(todoModel: TodoModel) {
        toDODao.insertTodo(todoModel)
    }

    suspend fun deleteToDo(todoModel: TodoModel) {
        toDODao.deleteTodo(todoModel)
    }

    suspend fun updateToDo(todoModel: TodoModel){
        toDODao.insertTodo(todoModel)
    }
}
