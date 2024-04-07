package com.example.woofcareapp

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.woofcareapp.navigation.logic.Navigation
import com.example.woofcareapp.navigation.model.ScreenModel
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.navigation.widget.BottomBar
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigationScreen(navControllerLogin: NavController,sharedPreferences: SharedPreferences) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = true,

        drawerContent = {
            Drawer(

                item = ScreenModel().screensInHomeFromBottomNav,
                sharedPreferences,
                navController = navController,
                navControllerLogin = navControllerLogin,
                scope = scope,
                scaffoldState = scaffoldState

            )
        },
        topBar = {
            TopAppBar(
                backgroundColor = DarkButtonWoof,
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon),
                            contentDescription = "")
                    }
                },
            )



        },

        bottomBar = {
            BottomBar(
                screens = ScreenModel().screensInHomeFromBottomNav,
                navController = navController,

                )

        },
    ) {
        Navigation(navController = navController)
    }


}


@Composable
fun Drawer(
    item: List<ScreenModel.HomeScreens>,
    sharedPreferences: SharedPreferences,
    navController: NavController,
    navControllerLogin: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    var showDialog by remember { mutableStateOf(false) }

    val deleteAccount:()->Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            var user =DataRepository.getUser()
            if(user!=null){
                try {

                }catch (e: Exception) {
                    Log.e("excepcionUserB", "Error al obtener el cuerpo del error: $e")
                }
            }

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkButtonWoof)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        ) {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        // Handle dismissal if needed
                        showDialog = false
                    },
                    title = {
                        Text(text = "Alert")
                    },
                    text = {
                        Text(text ="Are you sure you want to delete this account?")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                sharedPreferences.edit().clear().apply()
                                deleteAccount()
                                showDialog = false
                            }, colors = ButtonDefaults.buttonColors(Color.Black)
                        ) {
                            Text("Confirm", color = Color.White)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                // Handle dismissal action (e.g., cancel)
                                showDialog = false
                            }, colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text("Cancel",  color = Color.White)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkButtonWoof) ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.height(40.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        //DataRepository.LogOut()
                        sharedPreferences.edit {
                            remove("TOKEN_KEY")
                            remove("USER_KEY")
                        }
                        navControllerLogin.navigate("login")
                        scope.launch { scaffoldState.drawerState.close() }

                    })
                    .height(45.dp)

                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "Log Out",
                    fontSize = 18.sp,
                    color = Color.Red
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Button(
                    onClick = {showDialog = true},
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp) // Agrega espacio a la izquierda del botón
                ) {
                    Text(text = "Delete Account")
                }
            }
        }
    }
}
