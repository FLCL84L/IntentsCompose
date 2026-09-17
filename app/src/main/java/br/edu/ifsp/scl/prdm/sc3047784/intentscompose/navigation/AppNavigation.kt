package br.edu.ifsp.scl.prdm.sc3047784.intentscompose.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.ifsp.scl.prdm.sc3047784.intentscompose.screens.AddWordScreen
import br.edu.ifsp.scl.prdm.sc3047784.intentscompose.screens.HomeScreen

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object AddWord : Screen("add_word/{currentString}") {

        const val ARG_CURRENT_STRING = "currentString"

        fun createRoute(currentString: String): String {
            return "add_word/${Uri.encode(currentString)}"
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(
                navController = navController,
                onAddWord = { currentString ->

                    navController.navigate(
                        Screen.AddWord.createRoute(currentString)
                    )
                }
            )
        }

        composable(
            route = Screen.AddWord.route,
            arguments = listOf(
                navArgument(Screen.AddWord.ARG_CURRENT_STRING) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val currentString =
                backStackEntry.arguments
                    ?.getString(Screen.AddWord.ARG_CURRENT_STRING)
                    ?: ""

            AddWordScreen(
                currentString = currentString,
                onConcatenate = { word ->

                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("word", word)

                    navController.popBackStack()
                }
            )
        }
    }
}
