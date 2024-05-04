package com.example.woofcareapp.screens.Chat

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.R
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.ui.theme.DarkButtonWoof

@Composable
fun ChatsScreen(navController: NavController) {
    val messages = listOf(
        Message(
            id = 1,
            uidReceiver = 123,
            uidSender = 456,
            type = "text",
            message = "¡Hola! ¿Estoy interesao en este servicio?",
            sentDate = "2024-05-04 09:15:00",
            serviceId = 1
        ),
        Message(
            id = 1,
            uidReceiver = 123,
            uidSender = 234,
            type = "text",
            message = "¡Hola! ¿Cómo estás?",
            sentDate = "2024-05-04 09:15:00",
            serviceId = 789
        ),
        Message(
            id = 2,
            uidReceiver = 456,
            uidSender = 123,
            type = "text",
            message = "Hola, estoy bien. ¿Y tú?",
            sentDate = "2024-05-04 09:17:00",
            serviceId = 789
        ),
        Message(
            id = 3,
            uidReceiver = 123,
            uidSender = 456,
            type = "text",
            message = "Genial, gracias por preguntar.",
            sentDate = "2024-05-04 09:20:00",
            serviceId = 789
        ),
        Message(
            id = 4,
            uidReceiver = 456,
            uidSender = 123,
            type = "text",
            message = "¿Tienes planes para hoy?",
            sentDate = "2024-05-04 09:22:00",
            serviceId = 789
        ),
        Message(
            id = 5,
            uidReceiver = 123,
            uidSender = 456,
            type = "text",
            message = "No realmente, solo descansar en casa. ¿Y tú?",
            sentDate = "2024-05-04 09:25:00",
            serviceId = 789
        ),
        Message(
            id = 6,
            uidReceiver = 456,
            uidSender = 123,
            type = "text",
            message = "Pensaba en salir a caminar más tarde.",
            sentDate = "2024-05-04 09:28:00",
            serviceId = 789
        ),
        Message(
            id = 7,
            uidReceiver = 123,
            uidSender = 456,
            type = "text",
            message = "Suena bien. El clima parece perfecto para eso.",
            sentDate = "2024-05-04 09:30:00",
            serviceId = 789
        ),
        Message(
            id = 8,
            uidReceiver = 456,
            uidSender = 123,
            type = "text",
            message = "Sí, espero que no llueva.",
            sentDate = "2024-05-04 09:32:00",
            serviceId = 789
        ),
        Message(
            id = 9,
            uidReceiver = 123,
            uidSender = 456,
            type = "text",
            message = "Deberías revisar el pronóstico del tiempo antes de salir.",
            sentDate = "2024-05-04 09:35:00",
            serviceId = 789
        ),
        Message(
            id = 10,
            uidReceiver = 456,
            uidSender = 123,
            type = "text",
            message = "Sí, lo haré. Gracias por el recordatorio.",
            sentDate = "2024-05-04 09:37:00",
            serviceId = 789
        )
    )
    val userList = listOf(
        User(
            id = 456,
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
            id = 123,
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
        ),        User(
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


    val myId = 123 //DataRepository
    val chats = remember { mutableStateListOf<MutableList<Message>>() }

    LaunchedEffect(myId) {
        messages.forEach { message ->
            val existingChat = chats.find { chat ->
                (chat.firstOrNull()?.uidSender == message.uidSender || chat.firstOrNull()?.uidReceiver == message.uidSender) &&
                        (chat.firstOrNull()?.uidSender == message.uidReceiver || chat.firstOrNull()?.uidReceiver == message.uidReceiver)
            }

            if (existingChat != null) {
                existingChat.add(message)
            } else {
                val newChat = mutableListOf(message)
                chats.add(newChat)
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(backWoof)) {
        // Aquí puedes mostrar la lista de chats usando 'chats'
        if (chats.isNotEmpty()) {
            chats.forEachIndexed { index, chat ->
                // Filtrar el otro usuario
                val otherUserId = if (chat[0].uidReceiver == myId) {
                    chat[0].uidSender
                } else {
                    chat[0].uidReceiver
                }

                // Obtener el usuario correspondiente
                val otherUser = userList.firstOrNull { user -> user.id == otherUserId }


                Log.d("asdasdad", chat[0].uidReceiver.toString())
                if (otherUser != null) {
                    ChatItem(chat = chat ,onClick = {
                        DataRepository.setMessagePlus(chat)
                        DataRepository.setUserPlus(otherUser)
                        navController.navigate("chat")
                    },otherUser)
                }
                if (index < chats.size - 1) Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            // Maneja el caso cuando no hay chats disponibles
            Text(text = "No hay chats disponibles")
        }
    }
}

@Composable
fun ChatItem(chat: List<Message>, onClick: () -> Unit, other: User) {
    val lastMessage = chat.lastOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        elevation = 4.dp,
        backgroundColor = DarkButtonWoof
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column {
                var urlImage = other.profileUrl

                if (urlImage != null) {
                    // Si hay una URL de imagen disponible, muestra la imagen desde la URL
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = rememberImagePainter(urlImage),
                            contentDescription = "Imagen de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    // Si no hay URL de imagen disponible, muestra una imagen predeterminada desde los recursos
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alice),
                            contentDescription = "Imagen de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            Column {
                Text(
                    text = "Chat con ${other.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (lastMessage != null) {
                    Text(
                        text = lastMessage.message,
                        fontSize = 14.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = lastMessage.sentDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}