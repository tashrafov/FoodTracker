package com.ashrafovtaghi.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.R
import com.ashrafovtaghi.core.domain.models.Gender
import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.core.domain.usecase.FilterOutDigits
import com.ashrafovtaghi.core.navigation.Route
import com.ashrafovtaghi.core.util.UIText
import com.ashrafovtaghi.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    var height by mutableStateOf(if (preferences.loadUserInfo().gender == Gender.Male) "180" else "150")
        private set

    fun onHeightChanged(height: String) {
        if (height.length <= 3) {
            this.height = filterOutDigits(height)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull() ?: run {
                _uiEvents.send(UiEvent.ShowSnackbar(UIText.StringResource(R.string.error_height_cant_be_empty)))
                return@launch
            }
            preferences.saveHeight(heightNumber)
            _uiEvents.send(UiEvent.Navigate(Route.WEIGHT))
        }
    }
}