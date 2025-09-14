package com.example.mytodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodo.data.TodoModel
import com.example.mytodo.repository.todoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repo:todoRepository): ViewModel() {

    private val _todos = repo.alltoDos
    val todo: LiveData<List<TodoModel>> = _todos

    fun insertTodo(todoModel: TodoModel){
        viewModelScope.launch {
            repo.addToDO(todoModel)
        }
    }

    fun deleteTodo(todoModel: TodoModel){
        viewModelScope.launch {
            repo.deleteToDo(todoModel)
        }
    }

    fun updateTodo(todoModel: TodoModel){
        viewModelScope.launch {
            repo.updateToDo(todoModel)
        }
    }


}