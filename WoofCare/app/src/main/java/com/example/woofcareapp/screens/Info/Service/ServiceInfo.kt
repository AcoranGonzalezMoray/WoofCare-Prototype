package com.example.woofcareapp.screens.Info.Service


import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.Badge
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.models.Request
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.screens.Info.Product.ImageSlider
import com.example.woofcareapp.screens.Search.ItemDetails.RatingBar
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(DelicateCoroutinesApi::class)
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
    val ratingExpanded = remember { mutableStateOf(true) }
    val context = LocalContext.current


    val onSendServiceMessage: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val formattedDateTime = currentDateTime.format(formatter)
                var message = Message(
                        id = 0,
                        uidSender = DataRepository.getUser()!!.id,
                        uidReceiver = service!!.uid,
                        message = "Hello, I am interested in this service",
                        serviceId = service.id,
                        sentDate = formattedDateTime,
                        type = "0"
                    )
                var request = Request(
                    id= 0,
                    uidReceiver= service.uid.toString(),
                    uidSender= DataRepository.getUser()?.id.toString(),
                    serviceId= 3,
                    status= "0",
                    creationDate =  "2024-04-05T10:00:00Z"
                )

                val messagePetition = RetrofitInstance.api.createMessage(message)
                if (messagePetition.isSuccessful) {
                    val body = messagePetition.body()
                    val requestPetition = RetrofitInstance.api.createRequest(request)
                    if (requestPetition.isSuccessful) {}
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
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
                if(service != null) {
                    // Banner or Slider of images
                    ImageSlider(bannerUrls = service.bannerUrl.split(";").dropLast(1))
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    // Service Info
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Name",
                        content = { Text(text = service.name, style = typography.body1, color = Color.White,) },
                        expanded = nameExpanded,
                        onClick = { nameExpanded.value = !nameExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Type",
                        content = { CustomBadge(type = service.type)},
                        expanded = typeExpanded,
                        onClick = { typeExpanded.value = !typeExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Status",
                        content = { CustomBadge(status = service.status)},
                        expanded = statusExpanded,
                        onClick = { statusExpanded.value = !statusExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    val publicationDate = if (service.publicationDate.contains(".")) {
                        service.publicationDate.split(".").first()
                    } else {
                        service.publicationDate
                    }
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Publication Date",
                        content = { Text(text = "Publication Date: ${publicationDate}", style = typography.body1,color = Color.White,) },
                        expanded = publicationDateExpanded,
                        onClick = { publicationDateExpanded.value = !publicationDateExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Description",
                        content = { Text(text = service.description, style = typography.body1, color = Color.White,) },
                        expanded = descriptionExpanded,
                        onClick = { descriptionExpanded.value = !descriptionExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Price",
                        content = { Text(text = "Price: $${service.price}", style = typography.body1, color = Color.White,) },
                        expanded = priceExpanded,
                        onClick = { priceExpanded.value = !priceExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Row(
                        Modifier
                            .background(DarkButtonWoof)
                            .padding(15.dp)
                    ) {ExpandableItem(
                        title = "Rating",
                        content = { RatingBar(2.2) },
                        expanded = ratingExpanded,
                        onClick = { ratingExpanded.value = !ratingExpanded.value }
                    )}
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }
        FloatingActionButton(
            onClick = {
                onSendServiceMessage()
                navController.navigate("chats")
            },
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            backgroundColor = DarkButtonWoof
        ) {
            Icon(
                imageVector = Icons.Default.ChatBubbleOutline,
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CustomBadge(type: Int = -1, status: Int = -1){
    if(type != -1){
        if(type == 0){
            Badge(
                backgroundColor = Color(0xFF006400),
                content = {Text(text = "Dog Trainer Service", color = Color.White)}
            )
        }
        if(type == 1){
            Badge(
                backgroundColor = Color(0xFF006400),
                content = {Text(text = "Dog Caregiver Service", color = Color.White)}
            )
        }
    }

    if(status != -1){
        if(status == 0){
            Badge(
                backgroundColor = Color(0xFF006400),
                content = {Text(text = "Service Available", color = Color.White)}
            )
        }
        if(status == 1){
            Badge(
                backgroundColor = Color.Red,
                content = {Text(text = "Service Temporarily Unavailable", color =Color.White)}
            )
        }
    }
}