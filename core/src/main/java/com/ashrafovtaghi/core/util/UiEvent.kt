package com.ashrafovtaghi.core.util

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UIText) : UiEvent()
}