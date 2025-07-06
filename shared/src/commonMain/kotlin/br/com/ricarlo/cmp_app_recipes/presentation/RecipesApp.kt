package br.com.ricarlo.cmp_app_recipes.presentation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.savedstate.read
import br.com.ricarlo.designsystem.MyApplicationTheme
import br.com.ricarlo.home.presentation.HomeScreen
import br.com.ricarlo.login.presentation.LoginScreen
import br.com.ricarlo.network.utils.logger
import br.com.ricarlo.shared.BuildConfig
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipesApp() {

    val navController = rememberNavController()

//    LaunchedEffect(Unit) {
//        EventBus.events.filterIsInstance<NavUri>().collect { uri ->
//            if (navController.graph.hasDeepLink(uri)) {
//                navController.navigate(uri)
//            }
//        }
//    }
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "login",
                enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) },
            ) {
                composable(
                    route = "home",
                    deepLinks = listOf(
                        navDeepLink { uriPattern = "https://${BuildConfig.APPLICATION_ID}/home" }
                    )
                ) {
                    HomeScreen(
                        navController = navController
                    )
                }
                composable(
                    route = "login",
                    deepLinks = listOf(
                        navDeepLink {
                            uriPattern = "https://${BuildConfig.APPLICATION_ID}/login/{id}"
                        }
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.read { getStringOrNull("id") }
                    logger.debug { "ID: $id" }

                    LoginScreen(
                        navController = navController
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    RecipesApp()
}
