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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.Request
import com.example.woofcareapp.api.services.RetrofitInstance
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val excludedRoutes = setOf("profile","productInfo", "userInfo", "FAQ", "serviceInfo", "chat", "addService", "editService")
    var dialog by remember { mutableStateOf(false) }
    var items by remember { mutableStateOf(emptyList<Request>()) }

    val onLoadGetRequest: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val requestPetition = RetrofitInstance.api.getRequests()

                if (requestPetition.isSuccessful) {
                    if(DataRepository.getUser()?.accountType != 0){
                        items = requestPetition.body()!!.filter { it.uidReceiver.toInt() == DataRepository.getUser()?.id }
                    }
                    dialog = true
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }

    val onUpdateRequest: (item:Request) -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val requestPetition = RetrofitInstance.api.updateRequest(it.id, it)

                if (requestPetition.isSuccessful) {
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }

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
            if (currentRoute !in excludedRoutes) {
                TopAppBar(
                    backgroundColor = DarkButtonWoof,
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 12.dp)
                        ) {

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu", tint = Color.White, modifier = Modifier.clickable {
                                    scope.launch { scaffoldState.drawerState.open() }
                                }  )
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.icon),
                                    contentDescription = "")
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {
                                Icon(imageVector = Icons.Filled.Notifications, contentDescription = "menu", tint = Color.White,
                                    modifier = Modifier.clickable {
                                        onLoadGetRequest()
                                    })

                            }

                        }
                    },
                )

            }



        },

        bottomBar = {
            if (currentRoute !in excludedRoutes) {
                BottomBar(
                    screens = ScreenModel().screensInHomeFromBottomNav,
                    navController = navController,

                    )
            }

        },
    ) {
        Navigation(navController = navController)
    }


    if (dialog) {
        AlertDialog(
            backgroundColor = backWoof,
            onDismissRequest = { dialog = false },
            title = {
                Text(text = "Notification Request Service")
            },
            text = {
                if(items.isNotEmpty()){
                    LazyColumn {
                        items(items) { item ->
                            Spacer(modifier = Modifier.padding(10.dp))
                            val user  = DataRepository.getUsers()?.filter {
                                it.id == item.uidSender.toInt()
                            }?.first()
                            Row(modifier = Modifier.background(DarkButtonWoof),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.defaultprofile),
                                    contentDescription = user?.name,
                                    modifier = Modifier
                                        .size(60.dp) // Toma todo el espacio disponible
                                        .padding(4.dp)
                                        .clip(shape = RoundedCornerShape(16.dp))
                                        .clickable {
                                            if (user != null) {
                                                DataRepository.setUserPlus(user)
                                                navController.navigate("userInfo")
                                            }
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = user?.name + " wants to hire one of your services\n",
                                    modifier = Modifier
                                        .weight(0.65f) // Toma todo el espacio disponible
                                        .padding(end = 8.dp, start = 4.dp) // Espacio entre la imagen y el texto
                                )
                                // Iconos
                                Column(
                                    modifier = Modifier
                                        .weight(0.15f) // Tamaño fijo para los iconos
                                        .padding(end = 8.dp), // Espacio entre el texto y los iconos
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    if(item.status.toInt() == 0){
                                        Icon(Icons.Filled.RemoveRedEye, contentDescription = null, Modifier.clickable {
                                            val service = DataRepository.getServices()
                                                ?.first { it.id == item.serviceId }
                                            if (service != null) {
                                                DataRepository.setServicePlus(service)
                                                dialog = false
                                                navController.navigate("serviceInfo")
                                            };
                                        })
                                        Icon(Icons.Filled.Close, contentDescription = null, tint = Color.Red, modifier = Modifier.clickable {
                                            item.status = "2"
                                            onUpdateRequest(item)
                                        })
                                        Icon(Icons.Filled.Done, contentDescription = null, tint = Color.Green, modifier = Modifier.clickable {
                                            item.status = "1"
                                            onUpdateRequest(item)
                                        })
                                    }else if (item.status.toInt() == 2){
                                        Icon(Icons.Filled.Close, contentDescription = null, tint = Color.Red)
                                    }else{
                                        Icon(Icons.Filled.Done, contentDescription = null, tint = Color.Green)
                                    }
                                }

                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { dialog = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkButtonWoof)
                ) {
                    Text("Close", color = Color.White)
                }
            }
        )
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
                        scope.launch { scaffoldState.drawerState.close() }
                        navController.navigate("FAQ")
                    })
                    .height(45.dp)

                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "FAQ",
                    fontSize = 18.sp,
                    color = Color.Yellow
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()
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
                    Text(text = "Delete Account", color = Color.White)
                }
            }
        }
    }
}
