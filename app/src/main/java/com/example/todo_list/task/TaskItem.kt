package com.example.todo_list.task

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo_list.db.Task

@Composable
fun TaskItem(task: Task, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h6
            )
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null
                )
            }
        }
    }
}