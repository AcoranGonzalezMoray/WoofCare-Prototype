package com.example.woofcareapp.screens.Home

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
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof

@Composable
fun HomeScreen(navController: NavController) {
    val userList = listOf(
        User(
            id = 1,
            name = "John Doe",
            email = "john.doe@example.com",
            password = "password123",
            accountType = 0,
            suscriptionType = 1,
            location = "New York",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 1234567890,
            statusAccount = 1
        ),
        User(
            id = 2,
            name = "Jane Smith",
            email = "jane.smith@example.com",
            password = "password456",
            accountType = 1,
            suscriptionType = 2,
            location = "Los Angeles",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 9876543210,
            statusAccount = 1
        ),
        User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            statusAccount = 1
        ),
        User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            statusAccount = 1
        ),
        User(
            id = 4,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            statusAccount = 1
        ),
        User(
            id = 5,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            statusAccount = 1
        ),        User(
            id = 6,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            statusAccount = 1
        )
    )
    val productList = listOf(
        Product(
            id = 1,
            name = "Royal Canin Mini Sterilised",
            description = "Nutritious dog food for sterilized small breed dogs",
            price = 599.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            bannerUrls = listOf(
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg",
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg"
            )
        ),
        Product(
            id = 2,
            name = "Royal Canin Medium Sterilised",
            description = "Balanced dog food for sterilized medium breed dogs",
            price = 149.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            bannerUrls = listOf(
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg",
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg"
            )
        ),
        Product(
            id = 3,
            name = "Royal Canin Maxi Sterilised",
            description = "Specially formulated dog food for sterilized large breed dogs",
            price = 79.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            bannerUrls = listOf(
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg",
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg"
            )
        ),
        Product(
            id = 4,
            name = "Royal Canin Mini Puppy",
            description = "Premium nutrition for small breed puppies",
            price = 79.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            bannerUrls = listOf(
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg",
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg"
            )
        ),
        Product(
            id = 5,
            name = "Royal Canin Maxi Puppy",
            description = "Complete and balanced nutrition for large breed puppies",
            price = 79.99,
            location = "Pet Supplies",
            companyName = "Royal Canin",
            status = 1,
            bannerUrls = listOf(
                "https://piensoymascotas.com/27460-large_default/royal-canin-mini-sterilised.jpg",
                "https://images.ecestaticos.com/XKJFrabFEJ1xTt_2Ox8dFkVyV0c=/0x0:2121x1414/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2Fd67%2Fa8d%2F860%2Fd67a8d8604edeac49386e96e2890fe7a.jpg"
            )
        )
    )

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
                        .padding(horizontal = 5.dp, vertical =  10.dp)
                        .height(100.dp)
                ) {
                    items(list) { item ->
                        Item(item = item, navController)
                    }
                }
            }
            items(userList) { user ->
                UserItemServices(user = user)
            }
        }
    }
}
@Composable
fun UserItemServices(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(vertical = 6.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Fondo de la imagen de usuario
            Image(
                painter = rememberImagePainter(user.profileUrl),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Capa superior con los botones y el nombre del usuario
            Surface(
                color = Color(0x44000000), // Color con alfa reducido
                modifier = Modifier.fillMaxSize()
            ) {
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
                            onClick = { /* Acción del primer botón */ },
                            backgroundColor = DarkButtonWoof, // Color naranja
                            modifier = Modifier.padding(8.dp).size(40.dp)
                        ) {
                            Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Add", tint = Color.White)
                        }
                        FloatingActionButton(
                            onClick = { /* Acción del segundo botón */ },
                            backgroundColor = DarkButtonWoof,
                            modifier = Modifier.padding(8.dp).size(40.dp)
                        ) {
                            Icon(Icons.Default.RemoveRedEye, contentDescription = "Edit", tint = Color.White)
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = user.name+", 34",
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(16.dp)
                    )
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
                    modifier = Modifier.size(100.dp)
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
                        data = item.bannerUrls[0],
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = item.name,
                    modifier = Modifier.size(100.dp)
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


