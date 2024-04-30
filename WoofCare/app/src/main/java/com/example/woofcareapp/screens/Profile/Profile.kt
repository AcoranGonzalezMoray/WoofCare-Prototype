package com.example.woofcareapp.screens.Profile

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.screens.Search.ItemDetails.RatingBar
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof

@ExperimentalAnimationApi
@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val user = DataRepository.getUser()

    // Estados para rastrear si cada sección está expandida o no
    val nameState = remember { mutableStateOf(user?.name ?: "") }
    val emailState = remember { mutableStateOf(user?.email ?: "") }
    val passwordState = remember { mutableStateOf("") } // Modificar según tu lógica de manejo de contraseñas
    val locationState = remember { mutableStateOf(user?.location ?: "") }
    val phoneState = remember { mutableStateOf(user?.phone?.toString() ?: "") }
    val ratingExpanded = remember { mutableStateOf(true) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backWoof)
    ) {
        Column {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = Color.White
                        )
                    }
                },
                title = { Text(text = "Profile", color = Color.White) },
                backgroundColor = DarkButtonWoof
            )
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                if(user!=null){
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(vertical = 6.dp),
                        elevation = 4.dp
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Fondo de la imagen de usuario
                            Image(
                                painter = rememberImagePainter(user.profileUrl),
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            // Capa superior con los botones y el nombre del usuario
                            Surface(
                                color = Color(0x44000000), // Color con alfa reducido
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        FloatingActionButton(
                                            onClick = {
                                                DataRepository.setUserPlus(user.copy(
                                                    name = nameState.value,
                                                    email = emailState.value,
                                                    location = locationState.value,
                                                    phone = phoneState.value.toLongOrNull() ?: 0L // Convierte el texto del teléfono en Long o 0 si no se puede convertir
                                                ))
                                                navController.navigate("userInfo")
                                            },
                                            backgroundColor = DarkButtonWoof,
                                            modifier = Modifier.padding(8.dp).size(40.dp)
                                        ) {
                                            Icon(Icons.Default.RemoveRedEye, contentDescription = "Edit", tint = Color.White)
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = user.name + ", 34",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    // Campos editables
                    EditableTextField(title = "Name", value = nameState.value, onValueChange = { nameState.value = it })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    EditableTextField(title = "Email", value = emailState.value, onValueChange = { emailState.value = it })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    EditableTextField(title = "Password", value = passwordState.value, onValueChange = { passwordState.value = it })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    EditableTextField(title = "Location", value = locationState.value, onValueChange = { locationState.value = it })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    EditableTextField(title = "Phone", value = phoneState.value, onValueChange = { phoneState.value = it })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    ExpandableItem(
                        title = "Rating",
                        content = { RatingBar(2.2) },
                        expanded = ratingExpanded,
                        onClick = { ratingExpanded.value = !ratingExpanded.value }
                    )
                }
            }
        }
        // Botón flotante
        FloatingActionButton(
            onClick = { /* Acción cuando se hace clic en el botón */ },
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            backgroundColor = DarkButtonWoof
        ) {
            // Contenido del botón flotante (por ejemplo, un icono)
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
    }
}

@Composable
fun EditableTextField(title: String, value: String, onValueChange: (String) -> Unit) {
    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor =  DarkButtonWoof,
        backgroundColor = prominentWoof,
        focusedBorderColor =  DarkButtonWoof,
        focusedLabelColor = Color.White,
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = customTextFieldColors,
        label = { Text(title) },
        modifier = Modifier.fillMaxWidth()
    )
}