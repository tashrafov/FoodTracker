package com.ashrafovtaghi.tracker_domain.usecase

import com.ashrafovtaghi.tracker_domain.model.TrackedFood
import com.ashrafovtaghi.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteTrackedFoodUseCase(private val repository: TrackerRepository) {

    suspend operator fun invoke(food: TrackedFood) = withContext(Dispatchers.IO){
        repository.deleteTrackedFood(food)
    }
}