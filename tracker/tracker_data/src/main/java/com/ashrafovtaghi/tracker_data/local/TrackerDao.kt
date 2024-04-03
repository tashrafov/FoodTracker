package com.ashrafovtaghi.tracker_data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashrafovtaghi.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Query("SELECT * FROM tracked_food where dayOfMonth = :dayOfMonth and month = :month and year = :year")
    fun getFoodForDate(dayOfMonth: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}