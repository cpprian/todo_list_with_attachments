package com.example.todo_list.task

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import androidx.navigation.NavController
import com.example.todo_list.constants.toColor
import com.example.todo_list.constants.toPriority
import com.example.todo_list.db.Task

@Composable
fun TaskItem(
    task: Task,
    navController: NavController,
    onDeleteClick: () -> Unit,
    onTaskCheckChanged: (Task, Boolean) -> Unit
) {
    val context = LocalContext.current

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
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { isChecked ->
                        onTaskCheckChanged(task, isChecked)
                        Toast.makeText(
                            context,
                            "Task ${task.title} is ${if (isChecked) "completed" else "not completed"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = task.title,
                    modifier = Modifier
                        .padding(end = 15.dp),
                    style = MaterialTheme.typography.h6,
                    color = if (task.isCompleted) Color.Gray else Color.Black
                )
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(
                            color = toPriority(task.priority).toColor(),
                            shape = MaterialTheme.shapes.small)
                )
                if (task.attachment != null) {
                    Icon(
                        imageVector = Icons.Filled.Attachment,
                        contentDescription = null
                    )
                }
            }
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    navController.navigate("edit_task_screen/${task.id}")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null
                )
            }
        }
    }
}