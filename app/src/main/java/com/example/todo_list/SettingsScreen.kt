package com.example.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo_list.ui.view.TaskViewModel

@Composable
fun SettingsScreen(
    viewModel: TaskViewModel,
    onNavigateUp: () -> Unit
) {
    var onlyUncompleted by remember { mutableStateOf(viewModel.onlyUncompleted.value) }
    var selectedSortOption by remember { mutableStateOf(viewModel.sortOption.value) }
    var isExpanded by remember { mutableStateOf(false) }

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
                onCheckedChange = { onlyUncompleted = it })
            Text(
                text = "Hide completed tasks",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sort by:",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .clickable(onClick = { isExpanded = !isExpanded })
            ) {
                Text(
                    text = selectedSortOption,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterStart)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    viewModel.getTags().observeAsState().value?.forEach { tag ->
                        DropdownMenuItem(onClick = { selectedSortOption = tag }) {
                            if (tag.isNotEmpty()) {
                                Text(text = tag)
                            }
                        }
                    }
                    DropdownMenuItem(onClick = { selectedSortOption = "None" }) {
                        Text(text = "None")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.onlyUncompleted.value = onlyUncompleted
            viewModel.sortOption.value = selectedSortOption

            if (selectedSortOption != "None") {
                viewModel.getTasksByTag(selectedSortOption)
                if (onlyUncompleted) {
                    viewModel.getUncompletedTasks()
                }
            } else {
                viewModel.getTasks()
                if (onlyUncompleted) {
                    viewModel.getUncompletedTasks()
                }
            }

            onNavigateUp()
        }) {
            Text(text = "Save")
        }
    }
}