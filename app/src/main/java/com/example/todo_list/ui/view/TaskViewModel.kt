package com.example.todo_list.ui.view

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_list.constants.Priority
import com.example.todo_list.db.Task
import com.example.todo_list.db.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    var tasks: LiveData<List<Task>> = taskDao.getAll().asLiveData()
    var priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    var sortBy: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var onlyUncompleted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var sortOption: MutableStateFlow<String> = MutableStateFlow("")
    var notificationLength: MutableStateFlow<Int> = MutableStateFlow(0)

    fun getTasks() {
        tasks = taskDao.getAll().asLiveData()
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(taskId)
        }
    }

    fun getSelectedTask(taskId: Int): LiveData<Task> {
        return requireNotNull(taskDao.getSelectedTask(taskId).asLiveData())
    }

    fun sortByPriorityLow() {
        tasks = taskDao.sortByPriorityLow().asLiveData()
    }

    fun sortByPriorityHigh() {
        tasks = taskDao.sortByPriorityHigh().asLiveData()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Task>> {
        return taskDao.searchDatabase(searchQuery).asLiveData()
    }

    fun getUncompletedTasks() {
        tasks = taskDao.getUncompletedTasks().asLiveData()
    }

    fun getTasksByTag(tag: String) {
        tasks = taskDao.getTasksByTag(tag).asLiveData()
    }

    fun getTags(): LiveData<List<String>> {
        return taskDao.getTags().asLiveData()
    }

    fun checkTask(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.checkTask(taskId, isCompleted)
        }
    }
}

