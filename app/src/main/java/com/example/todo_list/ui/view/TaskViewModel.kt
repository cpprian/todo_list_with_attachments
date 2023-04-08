package com.example.todo_list.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_list.db.Task
import com.example.todo_list.db.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    val tasks: LiveData<List<Task>> = taskDao.getAll().asLiveData()

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
        return taskDao.getSelectedTask(taskId).asLiveData()
    }

    fun sortByPriorityLow(): LiveData<List<Task>> {
        return taskDao.sortByPriorityLow().asLiveData()
    }

    fun sortByPriorityHigh(): LiveData<List<Task>> {
        return taskDao.sortByPriorityHigh().asLiveData()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Task>> {
        return taskDao.searchDatabase(searchQuery).asLiveData()
    }

    fun getUncompletedTasks(): LiveData<List<Task>> {
        return taskDao.getUncompletedTasks().asLiveData()
    }

    fun getTasksByTag(tag: String): LiveData<List<Task>> {
        return taskDao.getTasksByTag(tag).asLiveData()
    }

    fun getTags(): LiveData<List<String>> {
        return taskDao.getTags().asLiveData()
    }
}

