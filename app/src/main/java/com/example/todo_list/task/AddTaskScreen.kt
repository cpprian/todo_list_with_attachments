package com.example.todo_list.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.todo_list.components.PriorityComponent
import com.example.todo_list.constants.toInt
import com.example.todo_list.db.Task
import com.example.todo_list.ui.view.TaskViewModel
import java.util.*

@Composable
fun AddTaskScreen(viewModel: TaskViewModel, onNavigateUp: () -> Unit) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
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
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PriorityComponent(
            priority = viewModel.priority.value,
            onPrioritySelected = { viewModel.priority.value = it })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                Button(
                    onClick = {
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { view, year, month, dayOfMonth ->
                                selectedDate.set(Calendar.YEAR, year)
                                selectedDate.set(Calendar.MONTH, month)
                                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            },
                            selectedDate.get(Calendar.YEAR),
                            selectedDate.get(Calendar.MONTH),
                            selectedDate.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.show()
                    }
                ) {
                    Text(text = "Date")
                }
                Text(
                    text = "${selectedDate.get(Calendar.DAY_OF_MONTH)}/" +
                            "${selectedDate.get(Calendar.MONTH)}/${selectedDate.get(Calendar.YEAR)}",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                Button(
                    onClick = {
                        val timePickerDialog = TimePickerDialog(
                            context,
                            { view, hourOfDay, minute ->
                                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                selectedTime.set(Calendar.MINUTE, minute)
                            },
                            selectedTime.get(Calendar.HOUR_OF_DAY),
                            selectedTime.get(Calendar.MINUTE),
                            false
                        )
                        timePickerDialog.show()
                    }
                ) {
                    Text(text = "Time")
                }
                Text(
                    text = "${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newTask = Task(
                    title = title,
                    description = description,
                    priority = viewModel.priority.value.toInt(),
                    doneAt = selectedDate.timeInMillis + selectedTime.timeInMillis
                )
                viewModel.insertTask(newTask)
                onNavigateUp()
            }
        ) {
            Text(text = "Add")
        }
    }
}