package com.example.todo_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo_list.task.AboutTaskScreen
import com.example.todo_list.task.AddTaskScreen
import com.example.todo_list.task.EditTaskScreen
import com.example.todo_list.ui.MainScreen
import com.example.todo_list.ui.theme.Todo_listTheme
import com.example.todo_list.ui.view.TaskViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Todo_listTheme{
                TodoListApp(viewModel = viewModel)
            }
        }
    }

    @Composable
    fun TodoListApp(viewModel: TaskViewModel) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "main_screen") {
            composable("main_screen") {
                MainScreen(
                    viewModel = viewModel,
                    navController = navController,
                    onAddTaskClick = {
                        navController.navigate("add_task_screen")
                    }
                )
            }
            composable("add_task_screen") {
                AddTaskScreen(
                    viewModel = viewModel,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = "edit_task_screen/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
                EditTaskScreen(
                    viewModel = viewModel,
                    id = taskId,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = "about_task_screen/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
                AboutTaskScreen(
                    viewModel = viewModel,
                    id = taskId,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
