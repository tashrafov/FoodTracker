package com.ashrafovtaghi.tracker_domain.usecase

import com.ashrafovtaghi.tracker_domain.model.TrackedFood
import com.ashrafovtaghi.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodForDateUseCase(
    private val repository: TrackerRepository
) {

    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> =
        repository.getFoodForDate(date)
}