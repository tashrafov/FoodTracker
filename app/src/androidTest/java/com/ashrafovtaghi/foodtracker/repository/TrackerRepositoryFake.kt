package com.ashrafovtaghi.foodtracker.repository

import com.ashrafovtaghi.tracker_domain.model.TrackableFood
import com.ashrafovtaghi.tracker_domain.model.TrackedFood
import com.ashrafovtaghi.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class TrackerRepositoryFake : TrackerRepository {

    var shouldReturnError = false

    private val foods = mutableListOf<TrackedFood>()
    var searchResults = listOf<TrackableFood>()

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(searchResults)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        foods.add(food)
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        foods.remove(food)
    }

    override fun getFoodForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return flow { foods.filter { it.date == date } }
    }
}