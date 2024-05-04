package com.example.woofcareapp.screens.Notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Info.Product.ExpandableItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun NotificactionsScreen() {
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
            ratingId = 1,
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
            profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
            phone = 9876543210,
            ratingId = 1,
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
            ratingId = 1,
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
            profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
            phone = 5555555555,
            ratingId = 1,
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
            ratingId = 1,
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
            profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        ), User(
            id = 6,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        )
    )
    val emptyList = emptyList<User>()
    val expandedToday = remember { mutableStateOf(false) }
    if (userList.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No hay notificaciones")

        }
    } else {
        Column {
            Box(modifier = Modifier.padding(10.dp)) {
                ExpandableItem(title = "Today", content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        userList.forEach {
                            item {
                                ListItem(
                                    leadingContent = {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = it.profileUrl,
                                                builder = {
                                                    crossfade(true)
                                                }
                                            ),
                                            contentDescription = it.name,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clickable {
                                                    DataRepository.setUserPlus(it)
                                                    //navController.navigate("userInfo")
                                                },
                                            contentScale = ContentScale.Crop
                                        )
                                    },
                                    headlineContent = { Text(text = it.name) }
                                )
                                Divider(thickness = 2.dp, color = Color.Gray)
                            }
                        }
                    }
                }, expanded = expandedToday, onClick = { expandedToday.value = false })

            }
            Divider(Modifier.height(5.dp))

            Box(modifier = Modifier.padding(10.dp)) {
                ExpandableItem(title = "Last Week", content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        userList.forEach {
                            item {
                                ListItem(
                                    leadingContent = {
                                        Image(
                                            painter = rememberImagePainter(
                                                data = it.profileUrl,
                                                builder = {
                                                    crossfade(true)
                                                }
                                            ),
                                            contentDescription = it.name,
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clickable {
                                                    DataRepository.setUserPlus(it)
                                                    //navController.navigate("userInfo")
                                                },
                                            contentScale = ContentScale.Crop
                                        )
                                    },
                                    headlineContent = { Text(text = it.name) }
                                )
                                Divider(thickness = 2.dp, color = Color.Gray)
                            }
                        }
                    }
                }, expanded = expandedToday, onClick = { expandedToday.value = false })

            }
        }


    }
}