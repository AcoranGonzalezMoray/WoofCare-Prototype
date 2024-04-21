package com.example.woofcareapp.screens.Info.Product

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
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof

@Composable
fun ProductInfoScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val product = DataRepository.getProductPlus()

    // Estados para rastrear si cada sección está expandida o no
    val nameExpanded = remember { mutableStateOf(true) }
    val descriptionExpanded = remember { mutableStateOf(true) }
    val priceExpanded = remember { mutableStateOf(true) }
    val locationExpanded = remember { mutableStateOf(true) }
    val companyExpanded = remember { mutableStateOf(true) }

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
            title = { Text(text = "Product Info", color = Color.White) },
            backgroundColor = DarkButtonWoof
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            if(product!=null){
                // Banner or Slider of images
                ImageSlider(bannerUrls = product.bannerUrls)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                // Product Info
                ExpandableItem(
                    title = "Name",
                    content = product.name,
                    expanded = nameExpanded,
                    onClick = { nameExpanded.value = !nameExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                ExpandableItem(
                    title = "Description",
                    content = product.description,
                    expanded = descriptionExpanded,
                    onClick = { descriptionExpanded.value = !descriptionExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                ExpandableItem(
                    title = "Price",
                    content = "Price: $${product.price}",
                    expanded = priceExpanded,
                    onClick = { priceExpanded.value = !priceExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                ExpandableItem(
                    title = "Location",
                    content = "Location: ${product.location}",
                    expanded = locationExpanded,
                    onClick = { locationExpanded.value = !locationExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                ExpandableItem(
                    title = "Company",
                    content = "Company: ${product.companyName}",
                    expanded = companyExpanded,
                    onClick = { companyExpanded.value = !companyExpanded.value }
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("login")},
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
@Composable
fun ExpandableItem(
    title: String,
    content: String,
    expanded: MutableState<Boolean>,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = typography.h6, modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (expanded.value) "Collapse" else "Expand"
            )
        }
        if (expanded.value) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, style = typography.body1)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(bannerUrls: List<String>) {
    val pagerState = rememberPagerState(pageCount = { bannerUrls.size })
    val selectedPage = remember { mutableStateOf(0) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            HorizontalPager(state = pagerState) { page ->
                selectedPage.value = page
                Image(
                    painter = rememberImagePainter(bannerUrls[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                repeat(bannerUrls.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = if (index == selectedPage.value) Color.White else Color.Gray,
                                shape = CircleShape
                            )
                            .padding(horizontal = 4.dp),
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                }
            }
        }
    }
}


