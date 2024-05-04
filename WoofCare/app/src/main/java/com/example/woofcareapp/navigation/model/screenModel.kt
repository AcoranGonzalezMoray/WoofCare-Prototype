package com.example.woofcareapp.navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.woofcareapp.navigation.repository.DataRepository

class ScreenModel {

    sealed class HomeScreens(
        val route: String,
        val title: String,
        val icon: ImageVector
    ) {
        object Home : HomeScreens("home", "Home", Icons.Filled.Home)
        object Search : HomeScreens("search", "Search", Icons.Filled.Search)
        object Service : HomeScreens("service", "Service", Icons.Filled.Pets)
        object Chat : HomeScreens("chats", "Chat", Icons.Filled.ChatBubble)
        object Profile : HomeScreens("profile", "Profile", Icons.Filled.Person)
    }

    val repo = DataRepository.getUser()?.accountType
    var screensInHomeFromBottomNav = listOf(
        HomeScreens.Home, HomeScreens.Search, HomeScreens.Chat ,HomeScreens.Profile
    )

    init {
        if(repo != 0){
            screensInHomeFromBottomNav = listOf(
                HomeScreens.Home, HomeScreens.Search, HomeScreens.Service , HomeScreens.Chat ,HomeScreens.Profile
            )
        }
    }
}
