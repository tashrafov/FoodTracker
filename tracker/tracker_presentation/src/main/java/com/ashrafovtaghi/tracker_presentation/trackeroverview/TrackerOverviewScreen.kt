package com.ashrafovtaghi.tracker_presentation.trackeroverview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashrafovtaghi.core.util.UiEvent
import com.ashrafovtaghi.core_ui.LocalSpacing
import com.ashrafovtaghi.tracker_presentation.trackeroverview.components.DaySelector
import com.ashrafovtaghi.tracker_presentation.trackeroverview.components.NutrientsHeader
import java.time.LocalDate

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit, viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    var currentDate = LocalDate.now()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick) },
                onNextDayClick = { viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
    }
}