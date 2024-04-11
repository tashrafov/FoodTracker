package com.ashrafovtaghi.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.domain.models.GoalType
import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val preferences: Preferences) : ViewModel() {
    var selectedGoal by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    fun onGoalTypeSelected(goalType: GoalType) {
        selectedGoal = goalType
    }

    fun onNextClicked() {
        viewModelScope.launch {
            preferences.saveGoalType(selectedGoal)
            _uiEvents.send(UiEvent.Success)
        }
    }
}