package com.ashrafovtaghi.foodtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashrafovtaghi.core.navigation.Route
import com.ashrafovtaghi.foodtracker.navigation.navigate
import com.ashrafovtaghi.foodtracker.ui.theme.FoodTrackerTheme
import com.ashrafovtaghi.onboarding_presentation.activity.ActivityScreen
import com.ashrafovtaghi.onboarding_presentation.age.AgeScreen
import com.ashrafovtaghi.onboarding_presentation.gender.GenderScreen
import com.ashrafovtaghi.onboarding_presentation.goal.GoalScreen
import com.ashrafovtaghi.onboarding_presentation.height.HeightScreen
import com.ashrafovtaghi.onboarding_presentation.nutrientgoal.NutrientGoalScreen
import com.ashrafovtaghi.onboarding_presentation.weight.WeightScreen
import com.ashrafovtaghi.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState, onNavigate = navController::navigate
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState, onNavigate = navController::navigate
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState, onNavigate = navController::navigate
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.TRACKER_OVERVIEW) {

                        }
                        composable(Route.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, device = Devices.PIXEL_2, showSystemUi = true)
@Composable
fun GreetingPreview() {
    FoodTrackerTheme {
        WelcomeScreen(onNavigate = {})
    }
}