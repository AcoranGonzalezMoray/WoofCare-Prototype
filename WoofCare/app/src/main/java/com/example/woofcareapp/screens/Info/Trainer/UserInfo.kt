package com.example.woofcareapp.screens.Info.Trainer
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
                // Profile Image
                Image(
                    painter = rememberImagePainter(user.profileUrl),
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
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
