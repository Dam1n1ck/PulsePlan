package com.pulseplan.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannerDao {

    @Query("SELECT * FROM planner_items ORDER BY isCompleted ASC, dateEpochDay ASC, startHour ASC, startMinute ASC")
    fun observeAllItems(): Flow<List<PlannerItem>>

    @Query("SELECT * FROM planner_items WHERE isCompleted = 0 AND dateEpochDay IS NOT NULL")
    suspend fun getAllActiveScheduledItems(): List<PlannerItem>

    @Query("SELECT * FROM planner_items WHERE id = :id LIMIT 1")
    suspend fun getItemById(id: Long): PlannerItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PlannerItem): Long

    @Update
    suspend fun updateItem(item: PlannerItem)

    @Delete
    suspend fun deleteItem(item: PlannerItem)
}