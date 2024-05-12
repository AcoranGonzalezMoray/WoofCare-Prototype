package com.example.woofcareapp.screens.Info.Product

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Search.ItemDetails.RatingBar
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
    val ratingExpanded = remember { mutableStateOf(true) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backWoof)
    ){
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
                    ImageSlider(bannerUrls = product.bannerUrls.split(";"))
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    // Product Info
                    ExpandableItem(
                        title = "Name",
                        content = { Text(text = product.name, style = typography.body1) },
                        expanded = nameExpanded,
                        onClick = { nameExpanded.value = !nameExpanded.value }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    ExpandableItem(
                        title = "Description",
                        content = { Text(text = product.description, style = typography.body1) },
                        expanded = descriptionExpanded,
                        onClick = { descriptionExpanded.value = !descriptionExpanded.value }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    ExpandableItem(
                        title = "Price",
                        content = { Text(text = "Price: $${product.price}", style = typography.body1) },
                        expanded = priceExpanded,
                        onClick = { priceExpanded.value = !priceExpanded.value }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    ExpandableItem(
                        title = "Location",
                        content = { Text(text = "Location: ${product.location}", style = typography.body1) },
                        expanded = locationExpanded,
                        onClick = { locationExpanded.value = !locationExpanded.value }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    ExpandableItem(
                        title = "Company",
                        content = { Text(text = "Company: ${product.companyName}", style = typography.body1) },
                        expanded = companyExpanded,
                        onClick = { companyExpanded.value = !companyExpanded.value }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    ExpandableItem(
                        title = "Rating",
                        content = { RatingBar(2.2) },
                        expanded = ratingExpanded,
                        onClick = { ratingExpanded.value = !ratingExpanded.value }
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }
        FloatingActionButton(
            onClick = {
                val webUrl = product?.webUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                startActivity(context, intent, null)
            },
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            backgroundColor = DarkButtonWoof
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
    }
}
@Composable
fun ExpandableItem(
    title: String,
    content: @Composable () -> Unit,
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
            content()
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
                                color = if (index == selectedPage.value) backWoof else DarkButtonWoof,
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


