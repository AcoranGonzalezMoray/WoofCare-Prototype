package com.example.woofcareapp.screens.Info.Service


import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.screens.Info.Product.ImageSlider
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof

@Composable
fun ServiceInfoScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val service = DataRepository.getServicePlus()
    val user = DataRepository.getUsers()?.filter { user: User -> user.id == service?.uid  }?.get(0)
    // Estados para rastrear si cada sección está expandida o no
    val nameExpanded = remember { mutableStateOf(true) }
    val typeExpanded = remember { mutableStateOf(true) }
    val statusExpanded = remember { mutableStateOf(true) }
    val publicationDateExpanded = remember { mutableStateOf(true) }
    val descriptionExpanded = remember { mutableStateOf(true) }
    val priceExpanded = remember { mutableStateOf(true) }

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
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
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
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Text(text = user.name+", 34", color = Color.White)
                    }else {
                        Image(
                            painter = rememberImagePainter("https://images.hola.com/imagenes/estar-bien/20221018219233/buenas-personas-caracteristicas/1-153-242/getty-chica-feliz-t.jpg?tx=w_680"),
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Text(text = "Sandra, 34", color = Color.White)
                    }
                }
            },
            backgroundColor = DarkButtonWoof
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            if(service != null) {
                // Banner or Slider of images
                ImageSlider(bannerUrls = service.bannerUrl)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                // Service Info
                ExpandableItem(
                    title = "Name",
                    content = service.name,
                    expanded = nameExpanded,
                    onClick = { nameExpanded.value = !nameExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Type",
                    content = "Type: ${service.type}",
                    expanded = typeExpanded,
                    onClick = { typeExpanded.value = !typeExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Status",
                    content = "Status: ${service.status}",
                    expanded = statusExpanded,
                    onClick = { statusExpanded.value = !statusExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Publication Date",
                    content = "Publication Date: ${service.publicationDate}",
                    expanded = publicationDateExpanded,
                    onClick = { publicationDateExpanded.value = !publicationDateExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Description",
                    content = service.description,
                    expanded = descriptionExpanded,
                    onClick = { descriptionExpanded.value = !descriptionExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Price",
                    content = "Price: $${service.price}",
                    expanded = priceExpanded,
                    onClick = { priceExpanded.value = !priceExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("login") },
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text("Cancel", color = DarkButtonWoof)
                    }

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(DarkButtonWoof),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text("Contact", color = Color.White)
                    }
                }
            }
        }
    }
}
