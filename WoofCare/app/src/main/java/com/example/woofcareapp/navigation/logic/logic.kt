package com.example.woofcareapp.navigation.logic

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.woofcareapp.screens.Chat.ChatScreen
import com.example.woofcareapp.screens.FAQ.FaqView
import com.example.woofcareapp.screens.Home.HomeScreen
import com.example.woofcareapp.screens.Info.Product.ProductInfoScreen
import com.example.woofcareapp.screens.Info.Service.ServiceInfoScreen
import com.example.woofcareapp.screens.Info.User.UserInfoScreen
import com.example.woofcareapp.screens.Notifications.NotificactionsScreen
import com.example.woofcareapp.screens.Profile.ProfileScreen
import com.example.woofcareapp.screens.Search.SearchScreen
import com.example.woofcareapp.screens.Service.ServiceScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
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
        composable("service") {
            ServiceScreen(navController)
        }
        composable("chat") {
            ChatScreen(navController)
        }
        composable("productInfo") {
            ProductInfoScreen(navController)
        }
        composable("userInfo") {
            UserInfoScreen(navController)
        }
        composable("serviceInfo") {
            ServiceInfoScreen(navController)
        }
        composable("notifications") {
            NotificactionsScreen(navController)
        }
        composable("FAQ") {
            FaqView(navController)
        }
    }

}