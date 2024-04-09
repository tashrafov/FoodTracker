package com.ashrafovtaghi.tracker_presentation.search

import com.ashrafovtaghi.tracker_domain.model.TrackableFood

data class TrackableFoodUIState(
    val food: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)
