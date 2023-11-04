package app.midterm.twitter.ui.component

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.midterm.twitter.data.MessagesStorage
import app.midterm.twitter.ui.MainViewModel
import app.midterm.twitter.ui.Screen
import app.midterm.twitter.ui.theme.TwitterTheme

@Preview(showSystemUi = true)
@Composable
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {
    val navController = rememberNavController()
    TwitterTheme {
        Column {
            NavHost(
                navController = navController,
                startDestination = Screen.Feed.tag,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                modifier = Modifier.weight(1f)
            ) {
                composable(Screen.Feed.tag) {
                    Content(navController)
                }
                composable(
                    Screen.Feed.tag + "/{index}",
                    arguments = listOf(
                        navArgument("index") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    val index = it.arguments?.getInt("index") ?: 0
                    val message = MessagesStorage.messages.collectAsState().value
                    MessageDetailsScreen(
                        message = kotlin.runCatching {
                            message[index]
                        }.getOrElse {
                            return@composable
                        },
                        index = index,
                        navController = navController
                    )
                }
                composable(route = Screen.Profile.tag) {
                    ProfileScreen()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf(
                    "Feed",
                    "Profile"
                ).forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .clickable {
                                when (it) {
                                    "Feed" -> navController.navigate(Screen.Feed.tag)
                                    "Profile" -> navController.navigate(Screen.Profile.tag)
                                }
                            }
                            .padding(vertical = 12.dp)
                            .weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
