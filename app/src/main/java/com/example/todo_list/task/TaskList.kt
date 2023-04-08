package com.example.todo_list.task

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.todo_list.ui.view.TaskViewModel

@Composable
fun TaskList(
    viewModel: TaskViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.tasks.observeAsState(emptyList())

    LazyColumn {
        items(tasks.size) { index ->
            TaskItem(task = tasks[index], navController = navController, onDeleteClick = {
                viewModel.deleteTask(tasks[index].id)
            })
        }
    }
}
