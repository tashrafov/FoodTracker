package com.ashrafovtaghi.tracker_domain.usecase

import com.ashrafovtaghi.core.domain.models.ActivityLevel
import com.ashrafovtaghi.core.domain.models.Gender
import com.ashrafovtaghi.core.domain.models.GoalType
import com.ashrafovtaghi.core.domain.models.UserInfo
import com.ashrafovtaghi.core.domain.preferences.Preferences
import com.ashrafovtaghi.tracker_domain.model.MealType
import com.ashrafovtaghi.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random


class CalculateMealNutrientsUseCaseTest {

    private lateinit var calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackerFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrientsUseCase(trackerFoods)
        val breakfastCalories =
            result.mealNutrients.values.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.calories }

        val expectedCalories =
            trackerFoods.filter { it.mealType is MealType.Breakfast }.sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }

    @Test
    fun `Protein for dinner properly calculated`() {
        val trackerFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrientsUseCase(trackerFoods)
        val dinnerProteins = result.mealNutrients.values.filter { it.mealType is MealType.Dinner }
            .sumOf { it.protein }

        val expectedProtein =
            trackerFoods.filter { it.mealType is MealType.Dinner }.sumOf { it.protein }

        assertThat(dinnerProteins).isEqualTo(expectedProtein)
    }
}