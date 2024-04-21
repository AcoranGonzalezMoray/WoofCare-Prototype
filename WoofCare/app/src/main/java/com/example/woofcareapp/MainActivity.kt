package com.example.woofcareapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Auth.ForgotPassword.ForgotPassword
import com.example.woofcareapp.screens.Auth.Login.Login
import com.example.woofcareapp.screens.Auth.SignUp.SignUpScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        setContent {
            val navController = rememberNavController()
            Navigation(navController, sharedPreferences)
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController, sharedPreferences: SharedPreferences) {
    NavHost(navController = navController, startDestination = "bottomScreen") {
        // Pantallas de Autenticacion
        composable("login") {
            Login(navController = navController) { loggedIn,user,token ->
                DataRepository.setUser(user)
                if (loggedIn) {
                    CoroutineScope(Dispatchers.Main).launch {
                        navController.navigate("bottomScreen")
                    }
                }
            }
        }

        composable("forgotPassword"){
            ForgotPassword(navController = navController) {}
        }

        composable("signUp"){
            SignUpScreen(navController = navController)
        }

        //Aplicacion Con sus Funciones
        composable("bottomScreen") {
            BottomNavigationScreen(navController, sharedPreferences)
        }
    }
}


