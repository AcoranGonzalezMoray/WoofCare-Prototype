package com.example.woofcareapp.screens.Search.ItemDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.ui.theme.DarkButtonWoof

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)


@Composable
fun previewItem(service: Service, navController: NavController) {
    val user = DataRepository.getUsers()?.filter { user: User -> user.id == service?.uid  }?.get(0)

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
            // Fondo de la imagen de usuario
            Image(
                painter = rememberImagePainter(service.bannerUrl[0]),
                contentDescription = "Service Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
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
                            modifier = Modifier.fillMaxWidth()
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
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.End
                        ) {
                            FloatingActionButton(
                                onClick = { /* Acción del primer botón */ },
                                backgroundColor = DarkButtonWoof, // Color naranja
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Add", tint = Color.White)
                            }
                            FloatingActionButton(
                                onClick = {
                                    DataRepository.setServicePlus(service);
                                    navController.navigate("serviceInfo")
                                },
                                backgroundColor = DarkButtonWoof,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.RemoveRedEye, contentDescription = "Edit", tint = Color.White)
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
                            modifier = Modifier.fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.Start
                        ) {
                            androidx.compose.material.Text(
                                text = service.name,
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
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





@Composable
fun RatingBar(
    rating: Double,
    starSize: Dp = 24.dp,
) {
    Row {
        for (i in 1 until 6) {
            val icon = if (i <= rating) Icons.Filled.Star else if (i - 0.5 <= rating) Icons.Filled.StarHalf else null
            icon?.let {
                Icon(
                    it,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(starSize)
                )
            }
        }
    }
}