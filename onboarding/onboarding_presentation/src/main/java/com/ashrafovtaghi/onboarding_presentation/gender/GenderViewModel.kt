package com.ashrafovtaghi.onboarding_presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.domain.models.Gender
import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(private val preferences: Preferences) : ViewModel() {
    var selectedGender by mutableStateOf<Gender>(Gender.Male)
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    fun onGenderClicked(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClicked() {
        viewModelScope.launch {
            preferences.saveGender(selectedGender)
            _uiEvents.send(UiEvent.Success)
        }
    }
}