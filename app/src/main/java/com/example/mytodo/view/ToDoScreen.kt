package com.example.mytodo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytodo.data.TodoModel
import com.example.mytodo.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(todoViewModel: TodoViewModel) {

    val todos by todoViewModel.todo.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "My Todo",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily.Serif
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, "Add"
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(todos) { todo ->
                TodoCard(todo, todoViewModel)
            }

        }

    }

    if (showDialog) {
        CustomAlertdialog(
            todoModel = TodoModel(
                title = "",
                description = "",
                date = "",
                time = ""
            ),
            onDismiss = { showDialog = false },
            onSave = { title, description ->
                todoViewModel.insertTodo(
                    TodoModel(
                        title = title,
                        description = description,
                        date = getCurrentDate(),
                        time = getCurentTime()
                    )
                )
                showDialog = false
            }
        )
    }
}

fun getCurentTime(): String {

    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
}

fun getCurrentDate(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
}


@Composable
fun CustomAlertdialog(
    todoModel: TodoModel,
    onDismiss: () -> Unit,
    onSave: (title: String, description: String) -> Unit
) {

    var title by remember { mutableStateOf(todoModel.title) }
    var description by remember { mutableStateOf(todoModel.description) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Enter your todo"
            )
        },

        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description ?: "",
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        },

        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank() && description!!.isNotBlank()) {
                        onSave(title, description ?: "")
                        onDismiss()
                    }
                }
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }


    )

}

@Composable
fun TodoCard(todoModel: TodoModel, todoViewModel: TodoViewModel) {
    var isChecked by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(5.dp)

    ) {
        Column(
            modifier=Modifier.fillMaxSize().padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = todoModel.title,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textDecoration = if (isChecked) TextDecoration.LineThrough
                        else TextDecoration.None
                    )
                    Text(text = todoModel.description ?: "",
                        fontSize = 18.sp,)
                }
                Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })

                IconButton(
                    onClick = {
                        todoViewModel.deleteTodo(todoModel)
                    }
                ) {
                    Icon(
                        Icons.Filled.Delete, "Delete"
                    )
                }
            }
            Divider(
                modifier = Modifier.height(2.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = todoModel.date
                )
                Text(
                    text = todoModel.time
                )
            }
        }
    }
}

