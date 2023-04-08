package com.example.todo_list.task

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.todo_list.ui.view.TaskViewModel

@Composable
fun TaskList(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.observeAsState(emptyList())

    LazyColumn {
        items(tasks.size) { index ->
            TaskItem(task = tasks[index], onDeleteClick = { viewModel.deleteTask(tasks[index].id) })
        }
    }
}
