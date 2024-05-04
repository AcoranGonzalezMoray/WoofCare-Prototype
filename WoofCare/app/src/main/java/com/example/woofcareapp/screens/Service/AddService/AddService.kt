package com.example.woofcareapp.screens.Service.AddService

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AddServiceScreen(navController: NavController) {
    val user = DataRepository.getUser()

    var accountTypes = listOf("Looking for Services", "Providing Care Services", "Providing Training and Domestication Services")
    var statusTypes = listOf("Disponible", "No disponible")
    var selectedStatusType by remember { mutableStateOf(0) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    val ratingExpanded = remember { mutableStateOf(true) }

    val nameState = remember { mutableStateOf("") }
    val typeState = remember { mutableStateOf(accountTypes[user!!.accountType]) }
    val statusState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val priceState = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val service = remember {
        mutableStateOf(
            Service(
                id = 0,
                name = "",
                type = 0,
                status = 0,
                publicationDate = "",
                description = "",
                price = 0.0,
                uid = 0,
                reviewId = 0,
                bannerUrl = listOf("https://www.escueladesarts.com/wp-content/uploads/guarderia-perros.jpg")
            )
        )
    }

    val nameExpanded = remember { mutableStateOf(true) }
    val typeExpanded = remember { mutableStateOf(true) }
    val statusExpanded = remember { mutableStateOf(true) }
    val descriptionExpanded = remember { mutableStateOf(true) }
    val priceExpanded = remember { mutableStateOf(true) }
    var images by remember { mutableStateOf(service.value.bannerUrl.toMutableList()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backWoof)
    ) {
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
                        modifier = Modifier
                            .clickable {
                                if (user != null) {
                                    DataRepository.setUserPlus(user)
                                }
                                navController.navigate("userInfo")
                            },
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
                            Text(text = user.name+", "+user.age, color = Color.White)
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
                if (service != null) {
                    // Banner or Slider of images
                    ImageSlider(bannerUrls = images, onchange = {
                        images.add(images.size, it)
                    })
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    // Service Info
                    EditableItem(
                        title = "Name",
                        text = nameState.value,
                        onTextChanged = { nameState.value = it },
                        expanded = nameExpanded,
                        onExpandToggle = { nameExpanded.value = !nameExpanded.value },
                    )
                    EditableItem(
                        title = "Type",
                        text = typeState.value,
                        onTextChanged = { typeState.value = it },
                        expanded = typeExpanded,
                        onExpandToggle = { typeExpanded.value = !typeExpanded.value },
                        rd = true
                    )
                    ExpandableItem(
                        title = "Status",
                        content = {
                            Row(
                                modifier = Modifier.background(DarkButtonWoof),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = statusTypes[selectedStatusType!!],
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
                                    statusTypes.forEachIndexed { index, type ->
                                        DropdownMenuItem(onClick = {
                                            selectedStatusType = index
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


                    EditableItem(
                        title = "Description",
                        text = descriptionState.value,
                        onTextChanged = { descriptionState.value = it },
                        expanded = descriptionExpanded,
                        onExpandToggle = { descriptionExpanded.value = !descriptionExpanded.value },
                    )
                    EditableItem(
                        title = "Price",
                        text = priceState.value,
                        onTextChanged = { priceState.value = it },
                        expanded = priceExpanded,
                        onExpandToggle = { priceExpanded.value = !priceExpanded.value },)
                }
            }
        }
        FloatingActionButton(
            onClick = { /* Acción cuando se hace clic en el botón */ },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            backgroundColor = DarkButtonWoof
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
    }
}

@Composable
fun EditableItem(
    title: String,
    text: String,
    onTextChanged: (String) -> Unit,
    expanded: MutableState<Boolean>,
    onExpandToggle: () -> Unit,
    rd: Boolean = false
) {
    ExpandableItem(
        title = title,
        expanded = expanded,
        content = {
            val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor =  DarkButtonWoof,
                backgroundColor = prominentWoof,
                focusedBorderColor =  DarkButtonWoof,
                focusedLabelColor = Color.White,
            )

            TextField(
                value = text,
                readOnly = rd,
                onValueChange = { onTextChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                colors = customTextFieldColors,
                textStyle = MaterialTheme.typography.body1
            )
        },
        onClick = { onExpandToggle() }
    )
}



@SuppressLint("MutableCollectionMutableState")
@Composable
fun ImageSlider(bannerUrls: List<String>, onchange: (String) -> Unit) {
    var pagerState = rememberPagerState(pageCount = { bannerUrls.size })
    var selectedPage = remember { mutableStateOf(0) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val chooseImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Agrega la nueva URL de la imagen a la lista de URLs
            selectedImageUri = uri
            onchange(uri.toString())
        }
    }



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
                }
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
