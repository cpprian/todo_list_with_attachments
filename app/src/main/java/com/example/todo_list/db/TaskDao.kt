package com.example.todo_list.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM tasks ORDER BY priority DESC")
    fun sortByPriorityLow(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY priority ASC")
    fun sortByPriorityHigh(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 0")
    fun getUncompletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE tag = :tag")
    fun getTasksByTag(tag: String): Flow<List<Task>>

    @Query("SELECT DISTINCT tag FROM tasks")
    fun getTags(): Flow<List<String>>
}
