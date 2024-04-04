package com.ashrafovtaghi.tracker_data.repository

import com.ashrafovtaghi.tracker_data.local.TrackerDao
import com.ashrafovtaghi.tracker_data.mapper.toTrackableFood
import com.ashrafovtaghi.tracker_data.mapper.toTrackedFood
import com.ashrafovtaghi.tracker_data.mapper.toTrackedFoodEntity
import com.ashrafovtaghi.tracker_data.remote.OpenFoodApi
import com.ashrafovtaghi.tracker_domain.model.TrackableFood
import com.ashrafovtaghi.tracker_domain.model.TrackedFood
import com.ashrafovtaghi.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val trackerDao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(query = query, page = page, pageSize = pageSize)
            Result.success(searchDto.products.mapNotNull { it.toTrackableFood() })
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackerDao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        trackerDao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return trackerDao.getFoodForDate(date.dayOfMonth, date.monthValue, date.year)
            .map { entities -> entities.map { it.toTrackedFood() } }
    }
}