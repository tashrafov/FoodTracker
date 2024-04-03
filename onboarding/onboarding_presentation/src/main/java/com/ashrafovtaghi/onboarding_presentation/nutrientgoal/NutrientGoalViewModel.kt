package com.ashrafovtaghi.onboarding_presentation.nutrientgoal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.core.domain.usecase.FilterOutDigits
import com.ashrafovtaghi.core.navigation.Route
import com.ashrafovtaghi.core.util.UiEvent
import com.ashrafovtaghi.onboarding_domain.usecase.ValidateNutrients
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateNutrients: ValidateNutrients
) : ViewModel() {

    var state by mutableStateOf<NutrientGoalState>(NutrientGoalState())
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbsRatioChanged -> {
                state = state.copy(
                    carbsRation = filterOutDigits(event.ratio)
                )
            }

            is NutrientGoalEvent.OnFatRatioChanged -> {
                state = state.copy(
                    fatRation = filterOutDigits(event.ratio)
                )
            }

            is NutrientGoalEvent.OnProteinRatioChanged -> {
                state = state.copy(
                    proteinRation = filterOutDigits(event.ratio)
                )
            }

            NutrientGoalEvent.OnNextClicked -> {
                val result = validateNutrients(
                    state.carbsRation,
                    state.proteinRation,
                    state.fatRation
                )
                when (result) {
                    is ValidateNutrients.Result.Success -> {
                        preferences.saveCarbRatio(result.carbsRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)
                        viewModelScope.launch {
                            _uiEvents.send(UiEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }
                    }

                    is ValidateNutrients.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvents.send(UiEvent.ShowSnackbar(result.message))
                        }
                    }
                }
            }
        }
    }
}