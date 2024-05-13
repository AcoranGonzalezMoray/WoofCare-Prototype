package com.example.woofcareapp.screens.Chat

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChatsScreen(navController: NavController) {
    var messages by remember {
        mutableStateOf(emptyList<Message>())
    }
    val context = LocalContext.current

    val myId = DataRepository.getUser()!!.id
    val chats = remember { mutableStateListOf<MutableList<Message>>() }
    var users = remember { mutableStateOf<MutableList<User>>(mutableListOf()) }

    val onLoadMessages: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val messagePetition = RetrofitInstance.api.getMessages()

                if (messagePetition.isSuccessful) {
                    messages = messagePetition.body()!!
                    messages.forEach { message ->
                        val existingChat = chats.find { chat ->
                            (chat.firstOrNull()?.uidSender == message.uidSender || chat.firstOrNull()?.uidReceiver == message.uidSender) &&
                                    (chat.firstOrNull()?.uidSender == message.uidReceiver || chat.firstOrNull()?.uidReceiver == message.uidReceiver)
                        }

                        if (existingChat != null) {
                            existingChat.add(message)
                            Log.d("asdasdadb", message.message)
                        } else {
                            val newChat = mutableListOf(message)
                            chats.add(newChat)
                            Log.d("asdasdadA", message.message)
                        }

                    }
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }

    LaunchedEffect(key1 = true) {
        onLoadMessages()
        try {
            val usersPetition = withContext(Dispatchers.IO) {
                RetrofitInstance.api.getUsers()
            }
            if (usersPetition.isSuccessful) {
                val userList = usersPetition.body() ?: emptyList()
                users.value = userList.toMutableList()
            }
        } catch (e: Exception) {
            Log.d("excepcionUserC", "${e}")
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
                Log.d("users", users.value.toString())

                // Obtener el usuario correspondiente
                val otherUser = users.value.find { user -> user.id == otherUserId }


                if (otherUser != null) {
                    Log.d("otherUser", otherUser.name)
                }
                if (otherUser != null) {
                    Log.d("2222222", chat[0].uidReceiver.toString())

                    ChatItem(chat = chat ,onClick = {
                        DataRepository.setMessagePlus(chat)
                        DataRepository.setUserPlus(otherUser)
                        navController.navigate("chat")
                    },otherUser, navController)
                }
                if (index < chats.size - 1) Spacer(modifier = Modifier.height(8.dp))
            }
        } else {
            // Maneja el caso cuando no hay chats disponibles
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No hay chats disponibles", fontSize = 20.sp)
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ChatItem(chat: List<Message>, onClick: () -> Unit, other: User, navController: NavController) {
    val lastMessage = chat.lastOrNull()
    val context = LocalContext.current
    val onDeleteChat: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                for (it in chat){
                    val messagePetition = RetrofitInstance.api.deleteMessage(it.id)
                    if (messagePetition.isSuccessful) { }
                }
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Chat successfully deleted", Toast.LENGTH_SHORT).show()
                    navController.navigate("chats")
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        elevation = 4.dp,
        backgroundColor = DarkButtonWoof
    ) {
        Box(){
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
                        text = "Chat with ${other.name}",
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
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lastMessage.sentDate.split(".").first(),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 5.dp)

                        )
                    }
                }
            }
            // Icono de "X" en la esquina superior derecha
            IconButton(
                onClick = {onDeleteChat() },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}