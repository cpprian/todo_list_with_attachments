package com.example.todo_list.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.example.todo_list.task.TaskList
import com.example.todo_list.ui.view.TaskViewModel

@Composable
fun MainScreen(viewModel: TaskViewModel, onAddTaskClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        TaskList(viewModel = viewModel)
    }
}
