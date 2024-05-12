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
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.woofcareapp.api.services.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("MutableCollectionMutableState")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val userList by remember { mutableStateOf(mutableListOf(
            User(
                id = 146,
                name = "John Doe",
                email = "john.doe@example.com",
                password = "password123",
                accountType = 0,
                suscriptionType = 1,
                location = "New York",
                profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
                phone = 1234567890,
                age = 18,
                statusAccount = 1
            ),
            User(
                id = 2456,
                name = "Jane Smith",
                email = "jane.smith@example.com",
                password = "password456",
                accountType = 1,
                suscriptionType = 2,
                location = "Los Angeles",
                profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
                phone = 9876543210,
                age = 18,
                statusAccount = 1
            ),
            User(
                id = 3456,
                name = "Alice Johnson",
                email = "alice.johnson@example.com",
                password = "password789",
                accountType = 2,
                suscriptionType = 0,
                location = "Chicago",
                profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
                phone = 5555555555,
                age = 18,
                statusAccount = 1
            ),
            User(
                id = 3456,
                name = "Alice Johnson",
                email = "alice.johnson@example.com",
                password = "password789",
                accountType = 2,
                suscriptionType = 0,
                location = "Chicago",
                profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
                phone = 5555555555,
                age = 18,
                statusAccount = 1
            ),
            User(
                id = 4456,
                name = "Alice Johnson",
                email = "alice.johnson@example.com",
                password = "password789",
                accountType = 2,
                suscriptionType = 0,
                location = "Chicago",
                profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
                phone = 5555555555,
                age = 18,
                statusAccount = 1
            ),
            User(
                id = 5456,
                name = "Alice Johnson",
                email = "alice.johnson@example.com",
                password = "password789",
                accountType = 2,
                suscriptionType = 0,
                location = "Chicago",
                profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
                phone = 5555555555,
                age = 18,
                statusAccount = 1
            ),        User(
                id = 6456,
                name = "Alice Johnson",
                email = "alice.johnson@example.com",
                password = "password789",
                accountType = 2,
                suscriptionType = 0,
                location = "Chicago",
                profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
                phone = 5555555555,
                age = 18,
                statusAccount = 1
            )
        )) }
    val productList by remember { mutableStateOf(mutableListOf(
        Product(
            id = 1456,
            name = "Royal Canin Mini Sterilised",
            description = "Nutritious dog food for sterilized small breed dogs",
            price = 599.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.zooplus.es/shop/tienda_perros/pienso_perros",
            bannerUrls =
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg"+";"+
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg;"

        ),
        Product(
            id = 2456,
            name = "Royal Canin Medium Sterilised",
            description = "Balanced dog food for sterilized medium breed dogs",
            price = 149.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.zooplus.es/shop/tienda_perros/pienso_perros",
            bannerUrls =
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg"+";"+
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg;"

        ),
        Product(
            id = 3456,
            name = "Royal Canin Maxi Sterilised",
            description = "Specially formulated dog food for sterilized large breed dogs",
            price = 79.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.zooplus.es/shop/tienda_perros/pienso_perros",
            bannerUrls =
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg"+";"+
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg;"

        ),
        Product(
            id = 446,
            name = "Royal Canin Mini Puppy",
            description = "Premium nutrition for small breed puppies",
            price = 79.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.zooplus.es/shop/tienda_perros/pienso_perros",
            bannerUrls =
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg"+";"+
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg;"

        ),
        Product(
            id = 546,
            name = "Royal Canin Maxi Puppy",
            description = "Complete and balanced nutrition for large breed puppies",
            price = 79.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.zooplus.es/shop/tienda_perros/pienso_perros",
            bannerUrls =
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg"+";"+
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg;"

        )
    ))}
    val serviceList by remember { mutableStateOf( mutableListOf(
        Service(
            id = 1456,
            name = "Paseos Diarios",
            type = 0,
            status = 0,
            publicationDate = "2024-04-21",
            description = "Paseos diarios para perros de todas las edades y razas. Incluye ejercicio moderado y socialización.",
            price = 25.0,
            uid = 3456,
            bannerUrl =
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
                    +";"+"https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"+";"

        ),
        Service(
            id = 2456,
            name = "Entrenamiento Básico",
            type = 0,
            status = 0,
            publicationDate = "2024-04-20",
            description = "Entrenamiento básico para cachorros y perros adultos. Enseñanza de órdenes básicas y comportamiento adecuado.",
            price = 50.0,
            uid = 2456,
            bannerUrl =
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
                    +";"+"https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"+";"

        ),
        Service(
            id = 3456,
            name = "Cuidado de Día",
            type = 0,
            status = 0,
            publicationDate = "2024-04-19",
            description = "Cuidado diurno para perros mientras los propietarios están fuera. Incluye tiempo de juego y supervisión.",
            price = 35.0,
            uid = 146,
            bannerUrl = "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
            +";"+"https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"+";"
        ),
        Service(
            id = 4456,
            name = "Adiestramiento Avanzado",
            type = 0,
            status = 0,
            publicationDate = "2024-04-18",
            description = "Adiestramiento avanzado para perros con necesidades especiales. Enseñanza de habilidades avanzadas y obediencia.",
            price = 70.0,
            uid = 3456,
            bannerUrl = "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
            +";"+"https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"+";"
        ),
        Service(
            id = 5456,
            name = "Guardería Nocturna",
            type = 0,
            status = 0,
            publicationDate = "2024-04-17",
            description = "Guardería nocturna para perros que necesitan alojamiento durante la noche. Ambiente seguro y cómodo para descansar.",
            price = 40.0,
            uid = 146,
            bannerUrl = "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
                    +";"+"https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"+";"
        ),
        Service(
            id = 6456,
            name = "Terapia Canina",
            type = 0,
            status = 0,
            publicationDate = "2024-04-16",
            description = "Terapia emocional para perros que sufren de ansiedad o estrés. Sesiones individuales y grupales disponibles.",
            price = 60.0,
            uid = 2456,
            bannerUrl =  "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
                    +";"+"https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"+";"
        )
    ))}
    val context = LocalContext.current

    val onLoadGetData: () -> Unit = {
        Log.d("pERO LLAAMA", userList.count().toString())

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val usersPetition = RetrofitInstance.api.getUsers()
                val productsPetition = RetrofitInstance.api.getProducts()
                val servicesPetition = RetrofitInstance.api.getServices()
                Log.d("ACOUNTTTTT", userList.count().toString())

                if (usersPetition.isSuccessful && productsPetition.isSuccessful && servicesPetition.isSuccessful) {
                    val services = servicesPetition.body()
                    val users = usersPetition.body()
                    val products = productsPetition.body()

                    if(services!=null) serviceList.addAll(services)
                    if(users!=null) userList.addAll(users)
                    if(products!=null) productList.addAll(products)
                    DataRepository.setUsers(userList)
                    DataRepository.setServices(serviceList)
                    DataRepository.setProducts(productList)
                    Log.d("ACOUNTTTTT", userList?.count().toString())
                    serviceList.shuffle()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción
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

    val list = shuffleUsersAndProducts(userList, productList)
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
            items(serviceList) { service ->
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
                                onClick = { /* Acción del primer botón */ },
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
                                fontSize = 18.sp,
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

    return shuffledList
}


