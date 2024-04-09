package com.ashrafovtaghi.tracker_presentation.trackeroverview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.ashrafovtaghi.core_ui.CarbColor
import com.ashrafovtaghi.core_ui.FatColor
import com.ashrafovtaghi.core_ui.ProteinColor

@Composable
fun NutrientsBar(
    carbs: Int,
    protein: Int,
    fat: Int,
    calories: Int,
    calorieGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colors.background
    val caloriesExceedColor = MaterialTheme.colors.error
    val carbWidthRatio = remember {
        Animatable(0f)
    }
    val proteinWidthRation = remember {
        Animatable(0f)
    }
    val fatWidthRation = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = carbs) {
        carbWidthRatio.animateTo(
            targetValue = ((carbs * 4f) / calorieGoal),
        )
    }

    LaunchedEffect(key1 = protein) {
        proteinWidthRation.animateTo(
            targetValue = ((protein * 4f) / calorieGoal),
        )
    }

    LaunchedEffect(key1 = fat) {
        fatWidthRation.animateTo(
            targetValue = ((fat * 9f) / calorieGoal),
        )
    }

    Canvas(modifier = modifier) {
        if (calories <= calorieGoal) {
            val carbsWidth = carbWidthRatio.value * size.width
            val proteinWidth = proteinWidthRation.value * size.width
            val fatWidth = fatWidthRation.value * size.width
            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = FatColor,
                size = Size(fatWidth + proteinWidth + carbsWidth, size.height),
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = ProteinColor,
                size = Size(proteinWidth + carbsWidth, size.height),
                cornerRadius = CornerRadius(100f)
            )

            drawRoundRect(
                color = CarbColor,
                size = Size(carbsWidth, size.height),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            drawRoundRect(
                color = caloriesExceedColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}