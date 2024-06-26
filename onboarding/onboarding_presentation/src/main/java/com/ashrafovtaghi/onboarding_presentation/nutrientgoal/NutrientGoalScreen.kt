package com.ashrafovtaghi.onboarding_presentation.nutrientgoal

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashrafovtaghi.core.R
import com.ashrafovtaghi.core.util.UiEvent
import com.ashrafovtaghi.core_ui.LocalSpacing
import com.ashrafovtaghi.onboarding_presentation.components.ActionButton
import com.ashrafovtaghi.onboarding_presentation.components.UnitTextField

@Composable
fun NutrientGoalScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    viewModel: NutrientGoalViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                UiEvent.NavigateUp -> Unit
                is UiEvent.ShowSnackbar -> scaffoldState.snackbarHostState.showSnackbar(
                    message = event.message.asString(context)
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = viewModel.state.carbsRation,
                onValueChange = {
                    viewModel.onEvent(
                        NutrientGoalEvent.OnCarbsRatioChanged(it)
                    )
                },
                unit = stringResource(
                    id = R.string.percent_carbs
                )
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = viewModel.state.proteinRation,
                onValueChange = {
                    viewModel.onEvent(
                        NutrientGoalEvent.OnProteinRatioChanged(it)
                    )
                },
                unit = stringResource(
                    id = R.string.percent_proteins
                )
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UnitTextField(
                value = viewModel.state.fatRation,
                onValueChange = {
                    viewModel.onEvent(
                        NutrientGoalEvent.OnFatRatioChanged(it)
                    )
                },
                unit = stringResource(
                    id = R.string.percent_fats
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onEvent(NutrientGoalEvent.OnNextClicked) },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}