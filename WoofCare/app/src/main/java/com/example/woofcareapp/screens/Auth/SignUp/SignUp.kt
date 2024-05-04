package com.example.woofcareapp.screens.Auth.SignUp

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignUpScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordMatches by remember { mutableStateOf(true) }
    var accountType by remember { mutableStateOf(0) }
    var location by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf(111111111) }
    var age by remember { mutableStateOf(18) }
    var isAgeError by remember { mutableStateOf(false) }

    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedAccountType by remember { mutableStateOf(0) }
    var accountTypes = listOf("Looking for Services", "Providing Care Services", "Providing Training and Domestication Services")
    var showDialog by remember { mutableStateOf(false) }


    val isError = name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()
    val errorMessage = if (isError) {
        val emptyField = listOf(
            "Name" to name,
            "Email" to email,
            "Password" to password,
            "Confirm Password" to confirmPassword,
            "Phone" to phone.toString(),
            "Account Type" to accountType.toString(),
            "Location" to location,
            "Age" to age.toString()
        ).firstOrNull { it.second.isBlank() }

        "${emptyField?.first ?: ""} is required"
    } else null

    val context = LocalContext.current

    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Color.Black,
        focusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        backgroundColor = prominentWoof,
    )
    val onSignUp:() -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val user = User(0,name, email, password,selectedAccountType,0,"","",phone.toLong(),0, age)

                val response = RetrofitInstance.api.signUp(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val joinResponse = response.body()
                        if (joinResponse != null){
                            Toast.makeText(context, "Successful Registration", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        }
                        else Log.d("excepcionUserA", "jjj")
                    } else {
                        try {
                            val errorBody = response.errorBody()?.string()

                            Toast.makeText(context, "$errorBody", Toast.LENGTH_SHORT).show()

                            Log.d("excepcionUserB", errorBody ?: "Error body is null")
                        } catch (e: Exception) {
                            Log.e("excepcionUserB", "Error al obtener el cuerpo del error: $e")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC","${e}")

            }
        }

    }
    val keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email
    )

    Column {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.navigate("login") }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back to Login", tint = Color.White)
                }
            },
            backgroundColor = DarkButtonWoof,
            title = { androidx.compose.material3.Text(text = "Sign Up", color = Color.White) }
        )
        if (showDialog) {
            ShowDialog(
                onDismiss = { showDialog = false},
                onSuccessfully = {a,b ->
                    location = b.toString()
                    onSignUp()
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backWoof),
            contentAlignment = Alignment.Center

        ) {
            // Agregar el icono en la esquina superior izquierda
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally // Alineación central horizontal
            ) {
                if (isError) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = name,
                    isError = isError,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = email,
                    isError = isError,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors
                )
                //if(!isValidEmail(email))Text("put a valid email", color = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = password,
                    isError = isError,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = confirmPassword,
                    isError = isError,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {
                        confirmPassword = it
                        passwordMatches = password == it
                    },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors
                )
                if (!passwordMatches) {
                    Text("Passwords do not match", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))


                TextField(
                    value = phone.toString(),
                    isError = isError,
                    onValueChange = { if (it.isDigitsOnly()) phone = it.toIntOrNull() ?: 0 },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors,
                )


                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = age.toString(),
                    isError = isError,
                    onValueChange = { newText ->
                        if (newText.isDigitsOnly()) {
                            val newAge = newText.toIntOrNull() ?: 0
                            age = newAge
                            if (age < 18) {
                                Toast.makeText(context, "You must be of legal age", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors
                )



                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.background(DarkButtonWoof),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = accountTypes[selectedAccountType],
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
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("login")},
                        colors = ButtonDefaults.buttonColors(DarkButtonWoof),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text("Cancel", color = Color.White)
                    }

                    Button(
                        //onClick = {showDialog = true},
                        onClick = {onSignUp() },
                        colors = ButtonDefaults.buttonColors(DarkButtonWoof),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text("Join", color = Color.White)
                    }
                }
            }
        }
    }
}


@Composable
fun ShowDialog(onDismiss: () -> Unit, onSuccessfully: (Any, Any) -> Unit) {
    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var locationPermissionGranted by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Location permission added", Toast.LENGTH_SHORT).show()
            fusedLocationClient.lastLocation
                .addOnSuccessListener { locationResult: Location? ->
                    locationResult?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                    }
                }
        } else {
            Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
        }
    }
    // Función para verificar y solicitar permisos de ubicación
    fun requestLocationPermission() {
        locationPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!locationPermissionGranted) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { locationResult: Location? ->
                    locationResult?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                    }
                }
        }
    }


    // Verificar y solicitar permisos de ubicación al inicio
    LaunchedEffect(Unit) {
        requestLocationPermission()
    }

    val chooseImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = uri // Asigna la URI seleccionada a la variable
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backWoof)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Botón para elegir una imagen de la galería
            ElevatedButton(
                onClick = {
                    // Abrir la galería cuando se hace clic en el botón
                    chooseImageLauncher.launch("image/*")
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(48.dp)
                ) {
                    // Icono para elegir una imagen de la galería
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Add Photo",
                        tint = Color.White
                    )
                }
            }

            // Botón para solicitar permisos de ubicación
            ElevatedButton(
                onClick = {
                    Log.d("ubicaion", latitude.toString())

                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text ="Get Location", color = Color.White)
            }
        }

        Row( modifier = Modifier.fillMaxSize().weight(1f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center

        ) {
            ElevatedButton(
                modifier = Modifier
                    .padding(top = 8.dp),
                onClick = {
                    onDismiss.invoke()
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = DarkButtonWoof
                )
            ) {
                Text(text ="Back", color = Color.White)
            }
            Spacer(modifier = Modifier.padding(horizontal = 60.dp))
            ElevatedButton(
                modifier = Modifier
                    .padding(top = 8.dp),
                onClick = {
                    if (selectedImageUri != null) {
                        // Si se seleccionó una imagen, llama a onSuccessfully con la URI de la imagen y otro dato
                        onSuccessfully.invoke(selectedImageUri!!, "location")
                    } else {
                        // Si no se seleccionó ninguna imagen, puedes manejarlo como desees (por ejemplo, mostrar un mensaje de error)
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = DarkButtonWoof
                )
            ) {
                Text(text ="Continue", color = Color.White)
            }
        }
    }
}
