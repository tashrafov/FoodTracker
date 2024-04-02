package com.ashrafovtaghi.onboarding_presentation.weight

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
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    var weight by mutableStateOf(if (preferences.loadUserInfo().gender == Gender.Male) "80.0" else "50.0")
        private set

    fun onWeightChanged(weight: String) {
        if (weight.length <= 5) {
            this.weight = weight
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: run {
                _uiEvents.send(UiEvent.ShowSnackbar(UIText.StringResource(R.string.error_weight_cant_be_empty)))
                return@launch
            }
            preferences.saveWeight(weightNumber)
            _uiEvents.send(UiEvent.Navigate(Route.ACTIVITY))
        }
    }
}