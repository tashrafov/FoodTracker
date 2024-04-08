package com.ashrafovtaghi.tracker_presentation.trackeroverview

import androidx.annotation.DrawableRes
import com.ashrafovtaghi.core.R
import com.ashrafovtaghi.core.util.UIText
import com.ashrafovtaghi.tracker_domain.model.MealType

data class Meal(
    val name: UIText,
    @DrawableRes val drawablePath: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false
)

val defaultMeals = listOf(
    Meal(
        name = UIText.StringResource(R.string.breakfast),
        drawablePath = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast,
    ),
    Meal(
        name = UIText.StringResource(R.string.lunch),
        drawablePath = R.drawable.ic_lunch,
        mealType = MealType.Lunch,
    ),
    Meal(
        name = UIText.StringResource(R.string.dinner),
        drawablePath = R.drawable.ic_dinner,
        mealType = MealType.Dinner,
    ),
    Meal(
        name = UIText.StringResource(R.string.snacks),
        drawablePath = R.drawable.ic_snack,
        mealType = MealType.Snack,
    )

)
