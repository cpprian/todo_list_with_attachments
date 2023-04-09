package com.example.todo_list.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todo_list.R
import com.example.todo_list.components.DateTimePicker
import com.example.todo_list.components.PriorityComponent
import com.example.todo_list.constants.toInt
import com.example.todo_list.db.Task
import com.example.todo_list.ui.view.TaskViewModel
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskScreen(viewModel: TaskViewModel, onNavigateUp: () -> Unit) {
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    val selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val selectedTime by remember { mutableStateOf(Calendar.getInstance()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Task",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = stringResource(R.string.title_task)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        PriorityComponent(
            priority = viewModel.priority.value,
            onPrioritySelected = { viewModel.priority.value = it })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = stringResource(R.string.description_task)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = tag,
            onValueChange = { tag = it },
            label = { Text(text = stringResource(R.string.category_task)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateTimePicker(
            doneAt = selectedDate.timeInMillis + selectedTime.timeInMillis - Calendar.getInstance().timeInMillis,
            selectedDate = selectedDate,
            selectedTime = selectedTime)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newTask = Task(
                    title = title,
                    description = description,
                    priority = viewModel.priority.value.toInt(),
                    doneAt = selectedDate.timeInMillis + selectedTime.timeInMillis - Calendar.getInstance().timeInMillis,
                    tag = tag,
                )
                viewModel.insertTask(newTask)
                onNavigateUp()
            }
        ) {
            Text(text = "Add")
        }
    }
}