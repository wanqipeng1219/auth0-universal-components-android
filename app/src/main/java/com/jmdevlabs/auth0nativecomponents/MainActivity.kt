package com.jmdevlabs.auth0nativecomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jmdevlabs.auth0nativecomponents.ui.HomeScreen
import com.jmdevlabs.auth0nativecomponents.ui.LoginScreen
import com.jmdevlabs.auth0nativecomponents.ui.SettingsScreen
import com.jmdevlabs.auth0nativecomponents.ui.theme.Auth0NativeComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Auth0NativeComponentsTheme {
                val authViewModel: AuthViewModel = viewModel()
                val authState by authViewModel.authState.collectAsState()
                val navController = rememberNavController()

                LaunchedEffect(authState) {
                    when (authState) {
                        is AuthState.LoggedIn -> navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                        is AuthState.LoggedOut -> navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                        is AuthState.Loading -> Unit
                    }
                }

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(viewModel = authViewModel)
                    }
                    composable("home") {
                        val state = authState
                        if (state is AuthState.LoggedIn) {
                            HomeScreen(
                                userProfile = state.userProfile,
                                viewModel = authViewModel,
                                onNavigateToSettings = { navController.navigate("settings") }
                            )
                        }
                    }
                    composable("settings") {
                        SettingsScreen()
                    }
                }
            }
        }
    }
}
