package com.example.woofcareapp.screens.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.woofcareapp.api.models.Product
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Search.ItemDetails.RatingBar
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.services.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("MutableCollectionMutableState")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val userList by remember {mutableStateOf(DataRepository.getUsers()?.toMutableList())}
    val productList by remember {mutableStateOf(DataRepository.getProducts()?.toMutableList())}
    val serviceList by remember { mutableStateOf(DataRepository.getServices()?.toMutableList())}
    val context = LocalContext.current

    val onLoadGetData: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val usersPetition = RetrofitInstance.api.getUsers()
                val productsPetition = RetrofitInstance.api.getProducts()
                val servicesPetition = RetrofitInstance.api.getServices()
                if (usersPetition.isSuccessful && productsPetition.isSuccessful && servicesPetition.isSuccessful) {
                    val services = servicesPetition.body()
                    val users = usersPetition.body()
                    val products = productsPetition.body()

                    if (services != null) serviceList?.let {
                        it += services
                        it.distinct()
                    }

                    if (users != null) userList?.let {
                        it += users
                        it.distinct()
                    }

                    if (products != null) productList?.let {
                        it += products
                        it.distinct()
                    }
                    userList?.let { DataRepository.setUsers(it) }
                    serviceList?.let { DataRepository.setServices(it) }
                    productList?.let { DataRepository.setProducts(it) }
                    serviceList?.let {
                        val distinctServiceList = it.distinctBy { service -> service.id }
                        val shuffledServiceList = distinctServiceList.shuffled()
                        serviceList!!.clear()
                        serviceList!!.addAll(shuffledServiceList)
                    }
                    Log.d("LISTA", serviceList?.count().toString())
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }
    LaunchedEffect(Unit) {
        onLoadGetData()
        val userId = DataRepository.getUID()
        if (userId != null) {
            try {
                val response = RetrofitInstance.api.getUserById(userId.toString())
                if (response.isSuccessful) {
                    val user: User? = response.body()
                    if (user != null) {
                        DataRepository.setUser(user)
                    }
                } else {
                    Log.e("excepcionUserB", "Error al obtener el usuario: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("excepcionUserB", "Error al obtener el usuario: $e")
            }
        }
    }

    val list = shuffleUsersAndProducts(userList!!, productList!!)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backWoof)
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxSize()
        ) {
            item {
                LazyRow(
                    modifier = Modifier
                        .height(100.dp)
                ) {
                    items(list) { item ->
                        Item(item = item, navController)
                    }
                }
            }
            items(serviceList!!) { service ->
                UserItemServices(service, navController)
            }
            item { 
                Spacer(modifier = Modifier.padding(vertical = 40.dp))
            }
        }
    }
}
@Composable
fun UserItemServices(service: Service, navController: NavController) {
    val user = DataRepository.getUsers()?.filter { user: User -> user.id == service?.uid  }?.get(0)
    val context = LocalContext.current
    val onSendServiceMessage: (service:Service) -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val formattedDateTime = currentDateTime.format(formatter)
                var message = Message(
                    id = 0,
                    uidSender = DataRepository.getUser()!!.id,
                    uidReceiver = it.uid,
                    message = "Hello, I am interested in this service",
                    serviceId = it.id,
                    sentDate = formattedDateTime,
                    type = "0"
                )

                val messagePetition = RetrofitInstance.api.createMessage(message)
                if (messagePetition.isSuccessful) {
                    val body = messagePetition.body()

                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Fondo de la imagen de usuario
            Image(
                painter = rememberImagePainter(service.bannerUrl.split(";")[0]),
                contentDescription = "Service Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Capa superior con los botones y el nombre del usuario
            Surface(
                color = Color(0x44000000), // Color con alfa reducido
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,

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
                            }else {
                                Image(
                                    painter = rememberImagePainter("https://images.hola.com/imagenes/estar-bien/20221018219233/buenas-personas-caracteristicas/1-153-242/getty-chica-feliz-t.jpg?tx=w_680"),
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.End
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    onSendServiceMessage(service)
                                    navController.navigate("chats")
                                },
                                backgroundColor = DarkButtonWoof, // Color naranja
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Add", tint = Color.White)
                            }
                            FloatingActionButton(
                                onClick = {
                                    DataRepository.setServicePlus(service);
                                    navController.navigate("serviceInfo")
                                },
                                backgroundColor = DarkButtonWoof,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(40.dp)
                            ) {
                                Icon(Icons.Default.RemoveRedEye, contentDescription = "Edit", tint = Color.White)
                            }
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = service.name,
                                color = Color.White,
                                fontSize = DataRepository.getSizeOfText(),
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Cada fila ocupará el 50% del ancho
                            horizontalArrangement = Arrangement.End
                        ) {
                            RatingBar(rating = 3.5)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Item(item: Any, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(90.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        when(item){
            is User -> {
                Image(
                    painter = rememberImagePainter(
                        data = item.profileUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = item.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            DataRepository.setUserPlus(item)
                            navController.navigate("userInfo")
                        },
                    contentScale = ContentScale.Crop
                )
            }
            is Product -> {
                Image(
                    painter = rememberImagePainter(
                        data = item.bannerUrls.split(";")[0],
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = item.name,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            DataRepository.setProductPlus(item)
                            navController.navigate("productInfo")
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


fun shuffleUsersAndProducts(users: List<User>, products: List<Product>): List<Any> {
    val combinedList = users.toMutableList() + products // Combinar las listas de usuarios y productos
    val shuffledList = mutableListOf<Any>()

    val random = java.util.Random()
    val indices = combinedList.indices.toMutableList()

    while (indices.isNotEmpty()) {
        val randomIndex = random.nextInt(indices.size)
        val index = indices.removeAt(randomIndex)
        shuffledList.add(combinedList[index])
    }

    return shuffledList.distinct()
}


