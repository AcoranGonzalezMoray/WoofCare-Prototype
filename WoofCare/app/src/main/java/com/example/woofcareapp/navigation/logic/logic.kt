package com.example.woofcareapp.navigation.logic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.woofcareapp.screens.Home.HomeScreen
import com.example.woofcareapp.screens.Profile.ProfileScreen
import com.example.woofcareapp.screens.Search.ItemDetails.ItemDetailsScreen
import com.example.woofcareapp.screens.Search.SearchScreen



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun  Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
    }
}