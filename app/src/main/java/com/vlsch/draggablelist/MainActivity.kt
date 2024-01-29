package com.vlsch.draggablelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vlsch.draggablelist.ui.screen.GridListScreen
import com.vlsch.draggablelist.ui.screen.LazyListScreen
import com.vlsch.draggablelist.ui.screen.categories.VerticalComplexScreen
import com.vlsch.draggablelist.ui.theme.DraggableListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DraggableListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(
                        listOf(
                            Screen.List,
                            Screen.ComplexList,
                            Screen.GridList,
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun MyApp(screens: List<Screen>) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(screens, navController)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.List.route, Modifier.padding(innerPadding)) {
            composable(Screen.List.route) {
                LazyListScreen()
            }
            composable(Screen.ComplexList.route) {
                VerticalComplexScreen()
            }
            composable(Screen.GridList.route) {
                GridListScreen()
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    screens: List<Screen>,
    navController: NavController,
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = { Icon(screen.icon, contentDescription = null) },
                label = {
                    Text(
                        stringResource(screen.resourceId),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    )
                },
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object List : Screen("list", R.string.vertical_list, Icons.Filled.MoreVert)
    object ComplexList : Screen("drag_and_release", R.string.drag_and_release, Icons.Default.More)
    object GridList : Screen("grid", R.string.grid_list, Icons.Default.Menu)
}

