package com.example.woofcareapp.screens.Auth.SignUp

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof
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

    var isMenuExpanded by remember { mutableStateOf(false) }
    var selectedAccountType by remember { mutableStateOf(0) }
    val accountTypes = listOf("Looking for Services", "Providing Care Services", "Providing Training and Domestication Services")


    val isError = name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()
    val errorMessage = if (isError) {
        val emptyField = listOf(
            "Name" to name,
            "Email" to email,
            "Password" to password,
            "Confirm Password" to confirmPassword,
            "Phone" to phone.toString(),
            "Account Type" to accountType.toString(),
            "Location" to location
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
                val user = User(0,name, email, password,0,0,"","",0,0,0)

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
        Box(
            modifier = Modifier.fillMaxSize().background(backWoof),
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
                    value = location,
                    isError = isError,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = customTextFieldColors
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = phone.toString(),
                    isError = isError,
                    onValueChange = { phone = it.toInt() },
                    label = { Text("Phone") },
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
                        modifier = Modifier.fillMaxWidth().background(prominentWoof)
                    ) {
                        accountTypes.forEachIndexed { index, type ->
                            DropdownMenuItem(onClick = {
                                selectedAccountType = index
                                isMenuExpanded = false
                            },modifier = Modifier.padding(5.dp)
                                .border(
                                width = 0.5.dp,
                                color = DarkButtonWoof,
                                shape = RoundedCornerShape(8.dp) )
                            ){
                                Text(text = type, color = Color.DarkGray )

                            }
                        }
                    }
                    Icon(
                        modifier = Modifier.weight(1f).clickable(
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
                        onClick = {onSignUp()},
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