package com.ashrafovtaghi.foodtracker.navigation

import androidx.navigation.NavController
import com.ashrafovtaghi.core.util.UiEvent

fun  NavController.navigate(uiEvent: UiEvent.Navigate) {
    this.navigate(uiEvent.route)
}