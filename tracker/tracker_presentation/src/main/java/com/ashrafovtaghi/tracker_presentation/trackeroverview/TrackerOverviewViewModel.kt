package com.ashrafovtaghi.tracker_presentation.trackeroverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.core.util.UiEvent
import com.ashrafovtaghi.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    private var getFoodForDateJob: Job? = null

    init {
        refreshFood()
        preferences.saveShouldShowOnboarding(false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    trackerUseCases.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFood()
                }
            }

            TrackerOverviewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFood()
            }

            TrackerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFood()
            }

            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }

    private fun refreshFood() {
        getFoodForDateJob?.cancel()
        getFoodForDateJob = trackerUseCases.getFoodForDateUseCase(state.date)
            .onEach { foodList ->
                val nutrientResult = trackerUseCases.calculateMealNutrientsUseCase(foodList)
                state = state.copy(
                    totalCalories = nutrientResult.totalCalories,
                    totalCarbs = nutrientResult.totalCarbs,
                    totalProtein = nutrientResult.totalProtein,
                    totalFat = nutrientResult.totalFat,
                    carbsGoal = nutrientResult.carbsGoal,
                    proteinGoal = nutrientResult.proteinGoal,
                    fatGoal = nutrientResult.fatGoal,
                    caloriesGoal = nutrientResult.caloriesGoal,
                    trackedFoods = foodList,
                    meals = state.meals.map { meal ->
                        val nutrientForMeals =
                            nutrientResult.mealNutrients[meal.mealType] ?: return@map meal.copy(
                                carbs = 0,
                                protein = 0,
                                fat = 0,
                                calories = 0
                            )
                        meal.copy(
                            carbs = nutrientForMeals.carbs,
                            protein = nutrientForMeals.protein,
                            fat = nutrientForMeals.fat,
                            calories = nutrientForMeals.calories
                        )
                    }
                )
            }.launchIn(viewModelScope)
    }
}