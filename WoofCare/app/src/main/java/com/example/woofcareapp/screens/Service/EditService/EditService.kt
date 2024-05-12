package com.example.woofcareapp.screens.Service.AddService

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("MutableCollectionMutableState")
@Composable
fun EditServiceScreen(navController: NavController) {
    val user = DataRepository.getUser()
    val service = remember {DataRepository.getServicePlus()!!}

    var accountTypes = listOf("Looking for Services", "Providing Care Services", "Providing Training and Domestication Services")
    var statusTypes = listOf("Disponible", "No disponible")
    var selectedStatusType by remember { mutableStateOf(service.status) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    val ratingExpanded = remember { mutableStateOf(true) }

    val nameState = remember { mutableStateOf(service.name) }
    val typeState = remember { mutableStateOf(accountTypes[user!!.accountType]) }
    val statusState = remember { mutableStateOf(service.status) }
    val descriptionState = remember { mutableStateOf(service.description) }
    val priceState = remember { mutableStateOf(service.price) }
    val scrollState = rememberScrollState()
    val nameExpanded = remember { mutableStateOf(true) }
    val typeExpanded = remember { mutableStateOf(true) }
    val context = LocalContext.current;
    val descriptionExpanded = remember { mutableStateOf(true) }
    val priceExpanded = remember { mutableStateOf(true) }

    var images by remember { mutableStateOf(service!!.bannerUrl.split(";").dropLast(1)) }


    val onUploadService: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                service.name = nameState.value
                service.type = user!!.accountType
                service.status = selectedStatusType
                service.description = descriptionState.value
                service.price = priceState.value
                service.uid = DataRepository.getUser()?.id ?: 0

                if (images.isNotEmpty()) {

                    val response = RetrofitInstance.api.updateService(service.id, service = service)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val joinResponse = response.body()
                            if (joinResponse != null){
                                user?.profileUrl = joinResponse.toString()
                                if (user != null) {
                                    Toast.makeText(context, "Service Successful Updated", Toast.LENGTH_SHORT).show()
                                    navController.navigate("service")
                                }
                            }
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
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
                    IconButton(onClick = { navController.popBackStack() }) {
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
                    ImageSlider(false,bannerUrls = images, onchange = {
                        images = mutableListOf()
                        for (uri in it) (images as MutableList<String>).add(uri)
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
                        text = priceState.value.toString()+"€",
                        onTextChanged = { priceState.value = it.split("€")[0].toDouble() },
                        expanded = priceExpanded,
                        onExpandToggle = { priceExpanded.value = !priceExpanded.value },)
                }
            }
        }
        FloatingActionButton(
            onClick = { onUploadService() },
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