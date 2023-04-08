package com.example.todo_list.task

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo_list.ui.view.TaskViewModel

@Composable
fun EditTaskScreen(
    id: Int,
    viewModel: TaskViewModel,
    onNavigateUp: () -> Unit
) {
    val task = viewModel.getSelectedTask(id).collectAsState(initial = null)
    var title by remember { mutableStateOf(task.value?.title) }
    var description by remember { mutableStateOf(task.value?.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Task",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = title?:"",
            onValueChange = { title = it },
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description?:"",
            onValueChange = { description = it },
            label = { Text(text = "Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val updatedTask = task.value?.copy(
                    title = title?:"",
                    description = description?:""
                )
                if (updatedTask != null) {
                    viewModel.insertTask(updatedTask)
                }
                onNavigateUp()
            }
        ) {
            Text(text = "Save")
        }
    }
}
