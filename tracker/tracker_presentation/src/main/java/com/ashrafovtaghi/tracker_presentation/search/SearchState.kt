package com.ashrafovtaghi.tracker_presentation.search

data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFoods: List<TrackableFoodUIState> = emptyList()
)
