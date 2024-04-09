package com.ashrafovtaghi.tracker_presentation.search

import com.ashrafovtaghi.tracker_domain.model.MealType
import com.ashrafovtaghi.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChanged(val query: String) : SearchEvent()
    object OnSearch : SearchEvent()
    data class OnToggleTrackableFoods(val food: TrackableFood) : SearchEvent()
    data class OnAmountForFoodChanged(
        val food: TrackableFood,
        val amount: String
    ) : SearchEvent()

    data class OnTrackFood(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate,
        val amount: String
    ) : SearchEvent()

    data class OnSearchFocusChanged(val hasFocus: Boolean) : SearchEvent()
}