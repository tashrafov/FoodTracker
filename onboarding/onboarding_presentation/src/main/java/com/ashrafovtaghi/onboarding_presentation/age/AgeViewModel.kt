package com.ashrafovtaghi.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafovtaghi.core.R
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
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    var age by mutableStateOf("20")
        private set

    fun onAgeChanged(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: run {
                _uiEvents.send(UiEvent.ShowSnackbar(UIText.StringResource(R.string.error_age_cant_be_empty)))
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvents.send(UiEvent.Navigate(Route.HEIGHT))
        }
    }
}