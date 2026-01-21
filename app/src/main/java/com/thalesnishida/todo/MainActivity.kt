package com.thalesnishida.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thalesnishida.todo.navigation.AppNavHost
import com.thalesnishida.todo.navigation.Home
import com.thalesnishida.todo.presetention.ui.theme.LocalWindowSizeClass
import com.thalesnishida.todo.presetention.ui.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)

            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass.widthSizeClass
            ) {
                TodoList()
            }
        }
    }
}

enum class Destination(
    val route: Any,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    AddTodo(Home, "Album", Icons.Default.Add, "Album"),
    HomeScreen(Home, "Home", Icons.Default.Home, "Home"),
    PLAYLISTS(Home, "Playlist", Icons.Default.Delete, "Playlist")
}

@SuppressLint("RestrictedApi")
@Composable
fun TodoList(
    navController: NavHostController = rememberNavController(),
) {
    val navController = rememberNavController()
    val startDestination = Destination.HomeScreen
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    TodoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavHost(navController)
            }
        }
    }
}

