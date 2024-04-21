package com.example.woofcareapp.screens.Info.User
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof


@Composable
fun UserInfoScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val user = DataRepository.getUserPlus()

    // Estados para rastrear si cada sección está expandida o no
    val nameExpanded = remember { mutableStateOf(true) }
    val emailExpanded = remember { mutableStateOf(true) }
    val passwordExpanded = remember { mutableStateOf(true) }
    val locationExpanded = remember { mutableStateOf(true) }
    val phoneExpanded = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backWoof)
    ) {
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
            title = { Text(text = "User Info", color = Color.White) },
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
                                        onClick = { /* Acción del primer botón */ },
                                        backgroundColor = DarkButtonWoof, // Color naranja
                                        modifier = Modifier.padding(8.dp).size(40.dp)
                                    ) {
                                        Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Add", tint = Color.White)
                                    }
                                    FloatingActionButton(
                                        onClick = { /* Acción del segundo botón */ },
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
                                    text = user.name+", 34",
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

                // User Info
                ExpandableItem(
                    title = "Name",
                    content = user.name,
                    expanded = nameExpanded,
                    onClick = { nameExpanded.value = !nameExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Email",
                    content = user.email,
                    expanded = emailExpanded,
                    onClick = { emailExpanded.value = !emailExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Password",
                    content = "********", // Replace with appropriate masking logic
                    expanded = passwordExpanded,
                    onClick = { passwordExpanded.value = !passwordExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Location",
                    content = user.location,
                    expanded = locationExpanded,
                    onClick = { locationExpanded.value = !locationExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Phone",
                    content = user.phone.toString(),
                    expanded = phoneExpanded,
                    onClick = { phoneExpanded.value = !phoneExpanded.value }
                )
            }
        }
    }
}
