package com.ashrafovtaghi.onboarding_presentation.nutrientgoal

sealed class NutrientGoalEvent {
    data class OnCarbsRatioChanged(val ratio: String) : NutrientGoalEvent()
    data class OnProteinRatioChanged(val ratio: String) : NutrientGoalEvent()
    data class OnFatRatioChanged(val ratio: String) : NutrientGoalEvent()
    data object OnNextClicked : NutrientGoalEvent()
}