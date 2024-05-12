package com.example.woofcareapp.screens.FAQ

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.MarkUnreadChatAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.R
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof

@Composable
fun FaqView(navController: NavController) {
    val firstExpanded = remember { mutableStateOf(false) }
    var searchValue = remember { mutableStateOf("") }
    val customTextFieldColors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = backWoof, // Fondo transparente
        cursorColor = DarkButtonWoof, // Color del cursor
        textColor = DarkButtonWoof, // Color del texto
        placeholderColor = Color.White, // Color del marcador de posición
        leadingIconColor = Color.White, // Color del icono de liderazgo
        trailingIconColor = Color.White,
        disabledPlaceholderColor = Color.White,
        disabledBorderColor = Color.White,
        focusedBorderColor = Color.White,
        unfocusedBorderColor = Color.White,
        disabledLabelColor = Color.White,
        focusedLabelColor = DarkButtonWoof,
        unfocusedLabelColor = Color.Black
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(backWoof),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF333333), Color(0xFF666666))
                    )
                ),

        ) {
            Image(
                painter = rememberImagePainter("https://us.123rf.com/450wm/sherjaca/sherjaca1010/sherjaca101000020/8041381-perro-australiano-raza-joven-negro-kelpie-cachorro-inocente-y-lindo-aspecto-pup-retrato-sobre-negro.jpg?ver=6"),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds // Ajusta la escala de la imagen para que se adapte al tamaño del contenedor
            )
            TopAppBar(
                navigationIcon = {
                    androidx.compose.material.IconButton(onClick = { navController.navigate("home") }) {
                        androidx.compose.material.Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = Color.White
                        )
                    }
                },
                title = { androidx.compose.material.Text(text = "FAQ", color = Color.White) },
                backgroundColor = Color.Transparent
            )



            TextField(
                value = searchValue.value,
                onValueChange = { searchValue.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 20.dp, end = 20.dp),
                textStyle = MaterialTheme.typography.body1,
                singleLine = true,
                label = { Text(text = "Search") },
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = DarkButtonWoof) // Color del ícono
                    }
                },
                colors = customTextFieldColors,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 160.dp, start = 80.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.MarkUnreadChatAlt, contentDescription = "", tint = Color.White, modifier = Modifier.padding(end = 10.dp))
                Column {
                    Text(text = "Support Email", color = Color.White, fontSize = 25.sp)
                    Text(text = "woofcare.support.gmail.com", color = Color.White, fontSize = 15.sp)
                }
            }
        }
        LazyColumn(Modifier.padding(start = 20.dp, end = 20.dp),verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Text(
                    text = "Welcome to our FAQ page!",
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
            item {
                Row(
                    Modifier
                        .background(DarkButtonWoof)
                        .padding(30.dp)
                ) {
                    ExpandableItem(
                        title = "What is WoofCare?",
                        content = {
                            Text(
                                text = "WoofCare is a platform designed for dog lovers looking for more than just grooming services. It's a passionate community dedicated to the well-being and happiness of your beloved pets."
                            )
                        },
                        expanded = firstExpanded,
                        onClick = { firstExpanded.value = !firstExpanded.value }
                    )
                }
            }

            item {
                Row(
                    Modifier
                        .background(DarkButtonWoof)
                        .padding(30.dp)
                ) {
                ExpandableItem(
                    title = "How does WoofCare work?",
                    content = {
                        Text(
                            text = "WoofCare allows you to connect with trusted caregivers who understand and love dogs as much as you do. Our advanced search feature lets you find the perfect companion for your furry friend based on specific criteria such as location, size, hours, and personal preferences."
                        )
                    },
                    expanded = firstExpanded,
                    onClick = { firstExpanded.value = !firstExpanded.value }
                )}
            }

            item {
                Row(
                    Modifier
                        .background(DarkButtonWoof)
                        .padding(30.dp)
                ) {
                ExpandableItem(
                    title = "What can I expect from caregiver profiles on WoofCare?",
                    content = {
                        Text(
                            text = "Each profile on WoofCare tells a story of love and dedication to dogs. Whether you're an owner, sitter, or trainer, your profile showcases your experience and includes photos of the welcoming environment where your pets will be cared for, along with honest reviews from other dog lovers."
                        )
                    },
                    expanded = firstExpanded,
                    onClick = { firstExpanded.value = !firstExpanded.value }
                )}
            }
            item {
                Row(
                    Modifier
                        .background(DarkButtonWoof)
                        .padding(30.dp)
                ) {
                ExpandableItem(
                    title = "How can I trust the caregivers on WoofCare?",
                    content = {
                        Text(
                            text = "We prioritize the safety and well-being of your pets. Caregivers on WoofCare undergo a thorough screening process, including background checks and verification of experience and qualifications. Additionally, our reviews and ratings system allows you to read feedback from other owners to make informed decisions."
                        )
                    },
                    expanded = firstExpanded,
                    onClick = { firstExpanded.value = !firstExpanded.value }
                )}
            }

            item {
                Row(
                    Modifier
                        .background(DarkButtonWoof)
                        .padding(30.dp)
                ) {
                ExpandableItem(
                    title = "What services are available on WoofCare?",
                    content = {
                        Text(
                            text = "WoofCare offers a wide range of services to meet the needs of every dog owner. From dog walking and pet sitting to grooming and training, you can find trusted caregivers who provide personalized care for your furry friends."
                        )
                    },
                    expanded = firstExpanded,
                    onClick = { firstExpanded.value = !firstExpanded.value }
                )}
            }

            item {
                Row(
                    Modifier
                        .background(DarkButtonWoof)
                        .padding(30.dp)
                ) {
                ExpandableItem(
                    title = "How can I communicate with caregivers on WoofCare?",
                    content = {
                        Text(
                            text = "Our integrated messaging system allows you to communicate with caregivers in real-time. Share instructions, ask questions, and receive updates on your pet's well-being directly through the app."
                        )
                    },
                    expanded = firstExpanded,
                    onClick = { firstExpanded.value = !firstExpanded.value }

                )}
            }
        }

    }
}