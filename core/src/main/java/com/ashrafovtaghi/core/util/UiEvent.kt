package com.ashrafovtaghi.core.util

sealed class UiEvent {
    data object Success : UiEvent()
    data object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UIText) : UiEvent()
}