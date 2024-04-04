package com.ashrafovtaghi.tracker_domain.di

import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.tracker_domain.repository.TrackerRepository
import com.ashrafovtaghi.tracker_domain.usecase.CalculateMealNutrientsUseCase
import com.ashrafovtaghi.tracker_domain.usecase.DeleteTrackedFoodUseCase
import com.ashrafovtaghi.tracker_domain.usecase.GetFoodForDateUseCase
import com.ashrafovtaghi.tracker_domain.usecase.SearchFoodUseCase
import com.ashrafovtaghi.tracker_domain.usecase.TrackFoodUseCase
import com.ashrafovtaghi.tracker_domain.usecase.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {
    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFoodUseCase = TrackFoodUseCase(repository),
            searchFoodUseCase = SearchFoodUseCase(repository),
            getFoodForDateUseCase = GetFoodForDateUseCase(repository),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(repository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
    }
}