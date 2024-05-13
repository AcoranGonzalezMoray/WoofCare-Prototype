package com.example.woofcareapp.screens.Chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.OnlinePrediction
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.woofcareapp.R
import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.api.services.RetrofitInstance
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.screens.Home.UserItemServices
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ChatScreen(navController: NavController) {
    var messages by remember {
        mutableStateOf(DataRepository.getMessagePlus())
    }

    var other = DataRepository.getUserPlus()
    var me = DataRepository.getUser()
    val context = LocalContext.current
    var current by remember{ mutableStateOf(true) }

    val onLoadMessages: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            while (current){
                try {
                    val messagePetition = RetrofitInstance.api.getMessages()

                    if (messagePetition.isSuccessful) {
                        val messagesDB = messagePetition.body()!!
                        messagesDB.filter { (it.uidSender ==  me!!.id|| it.uidReceiver == other!!.id) &&
                                (it.uidSender == other!!.id || it.uidReceiver == me.id) }

                        val hasNewMessages = messagesDB.any { newMessage ->
                            !messages!!.any { oldMessage ->
                                newMessage.id == oldMessage.id
                            }
                        }
                        if (hasNewMessages) messages = messagesDB
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.d("excepcionUserC", "${e}")
                }

                delay(500)
            }

        }
    }

    LaunchedEffect(Unit){
        delay(1500)
        onLoadMessages()
    }

    Column(Modifier.fillMaxSize()) {
        if (other != null) {
            Header(other, navController, onCurrent= {
                current = false
                navController.navigate("chats")
            })
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(backWoof)
                .weight(0.8f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        )
        {
            item {
                if (other != null) {
                    ChatMessages(messages, other, navController)
                }
            }
        }
        Box(modifier = Modifier
            .weight(0.1f)
            .background(backWoof)
            .fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            if (other != null) {
                SendMessage(other)
            }
        }
    }
}

@Composable
fun Header(other: User, navController: NavController, onCurrent: () -> Unit) {
    TopAppBar(
        backgroundColor = DarkButtonWoof,
        title = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 12.dp)
                    .clickable {
                        DataRepository.setUserPlus(other)
                        navController.navigate("userInfo")
                    }
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                   Row(
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "menu", tint = Color.White,
                           modifier = Modifier.clickable {
                               onCurrent()
                           })
                       var urlImage = other.profileUrl

                       if (urlImage != null) {
                           // Si hay una URL de imagen disponible, muestra la imagen desde la URL
                           Box(
                               modifier = Modifier
                                   .size(45.dp)
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
                                   .size(45.dp)
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
                       Column {
                           Text(text = other.name, color = Color.White)
                           Row {
                               Icon(imageVector = Icons.Filled.OnlinePrediction, contentDescription = "menu", tint = Color.Green )
                               Text(text = "online", color = Color.Green)
                           }

                       }
                   }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(imageVector = Icons.Filled.Settings, contentDescription = "menu", tint = Color.White)

                }

            }
        },
    )
}

@Composable
fun ChatMessages(messages: List<Message>?, other: User, navController: NavController) {
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(System.currentTimeMillis())
    messages?.forEach {
        Message(it, other, navController)

    }
}

@Composable
fun Message(message: Message, other: User, navController: NavController) {
    val me = DataRepository.getUser()
    val user = if (message.uidSender == me?.id) me else other
    var services = remember { mutableStateOf<MutableList<Service>>(mutableListOf()) }

    LaunchedEffect(key1 = true) {
        try {
            val usersPetition = withContext(Dispatchers.IO) {
                RetrofitInstance.api.getServices()
            }
            if (usersPetition.isSuccessful) {
                val servicesLocal = usersPetition.body() ?: emptyList()
                services.value = servicesLocal.toMutableList()
            }
        } catch (e: Exception) {
            Log.d("excepcionUserC", "${e}")
        }
    }



    Surface(
        color = backWoof,
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(user.id == me?.id && message.serviceId==0){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    // Contenido de la primera columna
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(36.dp)) // Agrega esquinas redondeadas con radio de 16.dp

            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(if (user.id != me?.id) prominentWoof else DarkButtonWoof)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = if (user.id == me?.id) Alignment.End else Alignment.Start
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            if(message.serviceId!=0){
                                val service = services.value.filter { it.id == message.serviceId }
                                if(service.isNotEmpty()){
                                    UserItemServices(service[0], navController)
                                }
                            }
                            Text(
                                text = message.message,
                                color = Color.Black,
                                style = MaterialTheme.typography.body1,

                            )
                            Row(
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = message.sentDate.split(".").first(),
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.caption,
                                    textAlign = TextAlign.Start// Alinea el texto a la derecha si es tu mensaje, de lo contrario, alinea a la izquierda
                                )
                            }
                        }
                    }
                }
            }
            if(user.id != me?.id){
                Column(
                    modifier = Modifier
                        .weight((if (message.serviceId != 0) 0.1f else 1f) as Float)
                        .padding(4.dp)
                ) {
                    // Contenido de la primera columna
                }
            }
        }
    }
}

@Composable
fun SendMessage(other: User) {
    var text by remember { mutableStateOf(TextFieldValue()) } // Variable para almacenar el valor del texto
    val me = DataRepository.getUser()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val onSendMessage: () -> Unit = {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val formattedDateTime = currentDateTime.format(formatter)
                var message = Message(
                    id = 0,
                    uidSender = me!!.id,
                    uidReceiver = other.id,
                    message = text.text,
                    serviceId = 0,
                    sentDate = formattedDateTime,
                    type = "0"
                )
                val messagePetition = RetrofitInstance.api.createMessage(message)

                if (messagePetition.isSuccessful) {
                        text =  TextFieldValue("")
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("excepcionUserC", "${e}")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backWoof),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.8f)

                    .background(
                        color = DarkButtonWoof,
                        shape = RoundedCornerShape(16.dp) // Ajusta el radio según tus necesidades
                    )
            ) {
                 Row(
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     var urlImage = me?.profileUrl

                     if (urlImage != null) {
                         if (urlImage.isNotBlank()) {
                             // Si hay una URL de imagen disponible, muestra la imagen desde la URL
                             Box(
                                 modifier = Modifier
                                     .size(40.dp)
                                     .padding(start = 5.dp)
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
                                     .size(40.dp)
                                     .padding(start = 5.dp)
                                     .clip(CircleShape)
                             ) {
                                 Image(
                                     painter = painterResource(id = R.drawable.profile),
                                     contentDescription = "Imagen de perfil",
                                     contentScale = ContentScale.Crop,
                                     modifier = Modifier.fillMaxSize()
                                 )
                             }
                         }
                     }
                     Spacer(modifier = Modifier.width(4.dp))
                     Box(
                         modifier = Modifier
                             .weight(1f)
                             .fillMaxHeight()
                             .padding(vertical = 8.dp),
                         contentAlignment = Alignment.CenterStart
                     ) {
                         TextField(
                             value = text ,
                             onValueChange = {it -> text = it  },
                             modifier = Modifier
                                 .fillMaxSize()
                                 .padding(horizontal = 16.dp),
                             singleLine = true,
                             textStyle = MaterialTheme.typography.body1,
                             label = { Text(text = "Type your message") },
                             colors = TextFieldDefaults.textFieldColors(backgroundColor = DarkButtonWoof, focusedLabelColor = backWoof, cursorColor = prominentWoof,
                                 focusedIndicatorColor = prominentWoof),
                             shape = RoundedCornerShape(8.dp)
                         )
                     }
                 }
              }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    onSendMessage()

                    focusManager.clearFocus()
                          },
                modifier = Modifier.weight(0.1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkButtonWoof),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
    }
}
