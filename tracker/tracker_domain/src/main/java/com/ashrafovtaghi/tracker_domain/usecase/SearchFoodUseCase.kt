package com.ashrafovtaghi.tracker_domain.usecase

import com.ashrafovtaghi.tracker_domain.model.TrackableFood
import com.ashrafovtaghi.tracker_domain.repository.TrackerRepository

class SearchFoodUseCase(
    private val trackerRepository: TrackerRepository
) {

    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>> = when (query.isEmpty()) {
        true -> Result.success(emptyList<TrackableFood>())
        else -> trackerRepository.searchFood(query.trim(), page, pageSize)
    }
}