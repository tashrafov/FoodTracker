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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ashrafovtaghi.core.domain.preferences.Preferences
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
import com.ashrafovtaghi.tracker_presentation.search.SearchScreen
import com.ashrafovtaghi.tracker_presentation.trackeroverview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
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
                        startDestination = if(shouldShowOnboarding) Route.WELCOME else Route.TRACKER_OVERVIEW
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
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                },
                            )
                        ) {
                            val date = LocalDate.now()
                            val mealName = it.arguments?.getString("mealName").orEmpty()
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth") ?: date.dayOfMonth
                            val month = it.arguments?.getInt("month") ?: date.monthValue
                            val year = it.arguments?.getInt("year") ?: date.year
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = { navController.navigateUp() })
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