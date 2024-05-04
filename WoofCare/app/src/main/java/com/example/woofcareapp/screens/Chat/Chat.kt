package com.example.woofcareapp.screens.Chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.woofcareapp.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen() {
    Column(Modifier.fillMaxSize()) {
        Header(userName = "Chat con Alice")
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(0.8f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        )
        {
            item {
                ChatMessages()
            }
        }
        Box(modifier = Modifier.weight(0.1f).fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            SendMessage()
        }
    }
}

@Composable
fun Header(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        IconButton(onClick = { /* Handle back button click */ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.width(16.dp))
        ProfileImage(imageResource = R.drawable.alice)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = userName, style = MaterialTheme.typography.h6)
    }
}

@Composable
fun ChatMessages() {
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(System.currentTimeMillis())

    Message("Alice", "Hi there!", R.drawable.alice, currentTime)
    Message("Bob", "Hey, how are you?", R.drawable.profile, currentTime)
    Message("Alice", "I'm good, thanks! How about you?", R.drawable.alice, currentTime)
    Message("Bob", "I'm doing great, thanks for asking!", R.drawable.profile, currentTime)
    Message("Alice", "That's good to hear.", R.drawable.alice, currentTime)
    // Add more messages as needed
}

@Composable
fun Message(sender: String, text: String, profileImageResource: Int, timestamp: String) {
    Surface(
        color = Color.LightGray,
        modifier = Modifier.padding(4.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            ProfileImage(imageResource = profileImageResource)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = sender, color = Color.Black, style = MaterialTheme.typography.subtitle2)
                Text(text = text, color = Color.Black, style = MaterialTheme.typography.body1)
                Text(
                    text = timestamp,
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun SendMessage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                TextField(
                    value = TextFieldValue(),
                    onValueChange = { /* Handle message text change */ },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1,
                    label = { Text(text = "Enviar Mensaje") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Handle send button click */ },
                modifier = Modifier.size(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
    }
}
@Composable
fun ProfileImage(imageResource: Int) {
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = null,
        modifier = Modifier.size(40.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}