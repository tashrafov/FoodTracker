package com.ashrafovtaghi.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.R
import com.ashrafovtaghi.core.domain.usecase.FilterOutDigits
import com.ashrafovtaghi.core.util.UIText
import com.ashrafovtaghi.core.util.UiEvent
import com.ashrafovtaghi.tracker_domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnAmountForFoodChanged -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map { trackableFoodUIState ->
                        if (trackableFoodUIState.food == event.food) {
                            trackableFoodUIState.copy(amount = filterOutDigits(event.amount))
                        } else {
                            trackableFoodUIState
                        }
                    }
                )
            }

            is SearchEvent.OnQueryChanged -> {
                state = state.copy(query = event.query)
            }

            SearchEvent.OnSearch -> {
                executeSearch()
            }

            is SearchEvent.OnSearchFocusChanged -> {
                state = state.copy(isHintVisible = !event.hasFocus && state.query.isBlank())
            }

            is SearchEvent.OnToggleTrackableFoods -> {
                state = state.copy(
                    trackableFoods = state.trackableFoods.map { trackableFoodUIState ->
                        if (trackableFoodUIState.food == event.food) {
                            trackableFoodUIState.copy(isExpanded = !trackableFoodUIState.isExpanded)
                        } else {
                            trackableFoodUIState
                        }
                    }
                )
            }

            is SearchEvent.OnTrackFood -> {
                trackFood(event)
            }
        }
    }

    private fun executeSearch() = viewModelScope.launch(Dispatchers.IO) {
        state = state.copy(
            isSearching = true, trackableFoods = emptyList()
        )
        trackerUseCases
            .searchFoodUseCase(state.query)
            .onSuccess {
                state = state.copy(
                    isSearching = false,
                    trackableFoods = it.map { food ->
                        TrackableFoodUIState(food = food)
                    },
                    query = ""
                )
            }
            .onFailure {
                state = state.copy(isSearching = false)
                _uiEvent.send(UiEvent.ShowSnackbar(UIText.StringResource(R.string.error_something_went_wrong)))
            }
    }

    private fun trackFood(event: SearchEvent.OnTrackFood) = viewModelScope.launch(Dispatchers.IO) {
        val uiState = state.trackableFoods.find { it.food == event.food } ?: return@launch
        trackerUseCases.trackFoodUseCase(
            food = uiState.food,
            amount = uiState.amount.toIntOrNull() ?: return@launch,
            mealType = event.mealType,
            date = event.date
        )
        _uiEvent.send(UiEvent.NavigateUp)
    }
}