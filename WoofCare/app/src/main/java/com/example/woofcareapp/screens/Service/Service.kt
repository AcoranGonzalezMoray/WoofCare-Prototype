package com.example.woofcareapp.screens.Service

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Home.Item
import com.example.woofcareapp.screens.Home.UserItemServices
import com.example.woofcareapp.screens.Search.ItemDetails.RatingBar
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ServiceScreen(navController: NavController) {

    val context = LocalContext.current
    var services  by remember { mutableStateOf(emptyList<com.example.woofcareapp.api.models.Service>()) }

    val onLoadServices: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getServices()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val joinResponse = response.body()
                        if (joinResponse != null){
                            services = joinResponse.filter { it.uid == DataRepository.getUser()?.id }
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción
                Log.d("excepcionUserC", "${e}")
            }
        }
    }



    LaunchedEffect(Unit){
        onLoadServices()
    }

    Box( modifier = Modifier
        .fillMaxSize()
        .background(backWoof)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backWoof)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxSize()
            ) {
                items(services) { service ->
                    ServicesByMe(service, navController)
                }
                item {
                    Spacer(modifier = Modifier.padding(vertical = 40.dp))
                }
            }
        }
        // Botón flotante
        FloatingActionButton(
            onClick = { navController.navigate("addService")},
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 80.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = DarkButtonWoof
        ) {
            // Contenido del botón flotante (por ejemplo, un icono)
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
    }
}


@Composable
fun ServicesByMe(service: Service, navController: NavController) {
    val user = DataRepository.getUser()
    var dialogView by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val onDeleteServices: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.deleteService(service.id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val joinResponse = response.body()
                        if (joinResponse != null){
                            Toast.makeText(context, "Service Successful  Deleted", Toast.LENGTH_SHORT).show()
                            navController.navigate("service")
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción
                Log.d("excepcionUserC", "${e}")
            }
        }
    }


    if (dialogView) {
        AlertDialog(
            onDismissRequest = { dialogView = false },
            title = {
                Text(text = "Alert Delete")
            },
            text = {
                Text(text = "Are you sure you want to delete the service?")
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 12.dp)
                ) {
                    Button(
                        onClick = { dialogView = false },
                        colors = ButtonDefaults.buttonColors(DarkButtonWoof),
                        modifier = Modifier.padding(8.dp).padding(start = 30.dp)
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }
                    Button(
                        onClick = {
                            onDeleteServices()
                            dialogView = false
                        },
                        colors = ButtonDefaults.buttonColors(DarkButtonWoof),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Continue", color = Color.White)
                    }
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp),
            backgroundColor = backWoof // Cambiar color de fondo del diálogo
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            var url = service.bannerUrl.split(";")[0]
            if(url.isNotEmpty()){
                Image(
                    painter = rememberImagePainter(url),
                    contentDescription = "Service Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }else {
                Image(
                    painter = rememberImagePainter("https://www.escueladesarts.com/wp-content/uploads/guarderia-perros.jpg"),
                    contentDescription = "Service Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            // Capa superior con los botones y el nombre del usuario
            Surface(
                color = Color(0x44000000), // Color con alfa reducido
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            if(user != null){
                                Image(
                                    painter = rememberImagePainter(user?.profileUrl),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                            }else {
                                Image(
                                    painter = rememberImagePainter("https://images.hola.com/imagenes/estar-bien/20221018219233/buenas-personas-caracteristicas/1-153-242/getty-chica-feliz-t.jpg?tx=w_680"),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.End
                        ) {
                            FloatingActionButton(
                                onClick = { dialogView = true },
                                backgroundColor = DarkButtonWoof, // Color naranja
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.DeleteForever, contentDescription = "Add", tint = Color.Red)
                            }
                            FloatingActionButton(
                                onClick = {
                                    DataRepository.setServicePlus(service);
                                    navController.navigate("editService")
                                },
                                backgroundColor = DarkButtonWoof,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Blue)
                            }
                            FloatingActionButton(
                                onClick = {
                                    DataRepository.setServicePlus(service);
                                    navController.navigate("serviceInfo")
                                },
                                backgroundColor = DarkButtonWoof, // Color naranja
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.RemoveRedEye, contentDescription = "Add", tint = Color.White)
                            }
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = service.name,
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.End
                        ) {
                            RatingBar(rating = 3.5)
                        }
                    }
                }

            }
        }
    }
}