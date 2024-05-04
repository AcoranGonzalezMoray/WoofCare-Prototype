package com.example.woofcareapp.screens.Profile

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.R
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.screens.Search.ItemDetails.RatingBar
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@SuppressLint("Recycle")
@ExperimentalAnimationApi
@Composable
fun ProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val user = DataRepository.getUser()
    var accountTypes = listOf("Looking for Services", "Providing Care Services", "Providing Training and Domestication Services")
    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedAccountType by remember { mutableStateOf(user?.accountType) }

    // Estados para rastrear si cada sección está expandida o no
    val nameState = remember { mutableStateOf(user?.name ?: "") }
    val emailState = remember { mutableStateOf(user?.email ?: "") }
    val passwordState = remember { mutableStateOf("12345678") } // Modificar según tu lógica de manejo de contraseñas
    val locationState = remember { mutableStateOf(user?.location ?: "") }
    val phoneState = remember { mutableStateOf(user?.phone?.toString() ?: "") }
    val ratingExpanded = remember { mutableStateOf(true) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    var context = LocalContext.current;

    val onUpdateImage: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Verifica si se ha seleccionado una imagen
                if (selectedImageUri != null) {
                    // Convierte la imagen en un ByteArray
                    val inputStream = context.contentResolver.openInputStream(selectedImageUri!!)
                    val byteArray = inputStream?.readBytes()
                    val imageRequestBody = byteArray?.toRequestBody("image/*".toMediaTypeOrNull())

                    // Obtén el ID del usuario (asumiendo que ya está disponible)
                    val userIdRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), DataRepository.getUser()?.id.toString())
                    val typeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "0")

                    // Crea el MultipartBody.Part para la imagen
                    val imagePart = imageRequestBody?.let { MultipartBody.Part.createFormData("image", "image.jpg", it) }

                    // Envía la solicitud para actualizar la imagen del usuario
                    val response =
                        imagePart?.let {
                            RetrofitInstance.api.updateImageUser(userIdRequestBody,typeRequestBody,
                                it
                            )
                        }

                    withContext(Dispatchers.Main) {
                        if (response != null) {
                            if (response.isSuccessful) {
                                val joinResponse = response.body()
                                if (joinResponse != null){
                                    user?.profileUrl = joinResponse.toString()
                                    if (user != null) {
                                        Toast.makeText(context, "Successful Updated", Toast.LENGTH_SHORT).show()
                                        DataRepository.setUser(user)
                                        navController.navigate("profile")
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    Log.d("asdf", "IMAGEEEEEEEEEN")
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción
                Log.d("excepcionUserC", "${e}")
            }
        }
    }
    val onUpdateUser: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Actualiza el usuario en el servidor
                val userLocal = User(
                    id = user?.id!!,
                    name = nameState.value,
                    email = emailState.value,
                    password = if(passwordState.value != "12345678")passwordState.value else user?.password!!,
                    accountType = selectedAccountType!!, // Modificar según tus necesidades
                    suscriptionType = user?.suscriptionType!!, // Modificar según tus necesidades
                    location = locationState.value,
                    profileUrl = DataRepository.getUser()?.profileUrl ?: "",
                    phone = phoneState.value.toLong(),
                    statusAccount = user.statusAccount, // Modificar según tus necesidades
                    age = user.age
                )

                val userResponse = RetrofitInstance.api.updateUser(user.id, userLocal)
                if (userResponse.isSuccessful) {
                    // Actualización exitosa
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Successful Updated", Toast.LENGTH_SHORT).show()
                        DataRepository.setUser(userLocal)
                        navController.navigate("profile")
                    }
                } else {
                    // Error al actualizar el usuario
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error updating user", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción
                Log.d("excepcionUserC", "${e}")
            }
        }
    }

    val chooseImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = uri // Asigna la URI seleccionada a la variable
            onUpdateImage()
        }
    }


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
                            if(user.profileUrl.isNotBlank()){
                                Image(
                                    painter = rememberImagePainter(user.profileUrl),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }else{
                                Image(
                                    painter =  painterResource(id = R.drawable.profile),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
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
                                                chooseImageLauncher.launch("image/*")
                                            },
                                            backgroundColor = DarkButtonWoof,
                                            modifier = Modifier.padding(8.dp).size(40.dp)
                                        ) {
                                            Icon(Icons.Default.CameraAlt, contentDescription = "Edit", tint = Color.White)
                                        }
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
                                        text = user.name + ", " +user.age,
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
                    EditableTextField(title = "Phone", value = phoneState.value, onValueChange = { phoneState.value = it })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    ExpandableItem(
                        title = "Account Type",
                        content = {
                            Row(
                                modifier = Modifier.background(DarkButtonWoof),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = accountTypes[selectedAccountType!!],
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .weight(9f)
                                        .background(prominentWoof)
                                        .padding(16.dp)
                                )

                                DropdownMenu(
                                    expanded = isMenuExpanded,
                                    onDismissRequest = { isMenuExpanded = false },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(prominentWoof)
                                ) {
                                    accountTypes.forEachIndexed { index, type ->
                                        DropdownMenuItem(onClick = {
                                            selectedAccountType = index
                                            isMenuExpanded = false
                                        },modifier = Modifier
                                            .padding(5.dp)
                                            .border(
                                                width = 0.5.dp,
                                                color = DarkButtonWoof,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                        ){
                                            Text(text = type, color = Color.DarkGray )

                                        }
                                    }
                                }
                                Icon(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            isMenuExpanded = true
                                        },
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        },
                        expanded = ratingExpanded,
                        onClick = { ratingExpanded.value = !ratingExpanded.value }
                    )
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
            onClick = { onUpdateUser() },
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

    if(title=="Password"){
        TextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = PasswordVisualTransformation(), // Muestra asteriscos en lugar de los caracteres
            colors = customTextFieldColors,
            label = { Text(title) },
            modifier = Modifier.fillMaxWidth()
        )
    }else {
        TextField(
            value = value,
            onValueChange = onValueChange,
            colors = customTextFieldColors,
            label = { Text(title) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}