package com.example.mytodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mytodo.data.TodoModel
import com.example.mytodo.repository.todoRepository
import com.example.mytodo.room.ToDoDB
import com.example.mytodo.ui.theme.MyTodoTheme
import com.example.mytodo.view.ToDoScreen
import com.example.mytodo.viewmodel.TodoViewModel
import com.example.mytodo.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db=Room.databaseBuilder(
            applicationContext,
            ToDoDB::class.java,
            "todo"
        ).fallbackToDestructiveMigration()
            .build()

        val repository= todoRepository(db.todoDao())

        val factory= TodoViewModelFactory(repository)

        val todoViewModel= ViewModelProvider(this,factory)[TodoViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            ToDoScreen(
                todoViewModel
            )
        }
    }
}
