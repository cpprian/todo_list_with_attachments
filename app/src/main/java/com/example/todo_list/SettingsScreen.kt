package com.example.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo_list.ui.view.TaskViewModel
import java.util.*


@Composable
fun SettingsScreen(
    viewModel: TaskViewModel,
    onNavigateUp: () -> Unit
) {
    var onlyUncompleted by remember { mutableStateOf(viewModel.onlyUncompleted.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = onlyUncompleted,
                onCheckedChange = {
                    onlyUncompleted = it
                    if (it) {
                        viewModel.getUncompletedTasks()
                    } else {
                        viewModel.getTasks()
                    }
            })
            Text(
                text = "Hide completed tasks",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.onlyUncompleted.value = onlyUncompleted
            onNavigateUp()
        }) {
            Text(text = "Save")
        }
    }
}