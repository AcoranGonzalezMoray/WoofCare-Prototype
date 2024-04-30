package com.example.woofcareapp.screens.Search.ItemDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.woofcareapp.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)

@Composable
fun ItemDetailsScreen(navController: NavController) {
    Box(modifier = Modifier.width(300.dp)) {
        previewItem()
    }
}

@Composable
@Preview
fun realPreviewInBox(){
    Box(modifier = Modifier.width(300.dp)) {
        previewItem()
    }
}

@Composable
@Preview
fun previewItem() {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Image(
                painterResource(R.drawable.defaultprofile),
                contentDescription = "Car Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Ana")
                Text(text = "City: Las Palmas de G.C")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Trainer")

            RatingBar(rating = 3.4)
        }
    }
}

@Composable
fun RatingBar(
    rating: Double,
    starSize: Dp = 24.dp,
) {
    Row {
        for (i in 1 until 6) {
            val icon = if (i <= rating) Icons.Filled.Star else if (i - 0.5 <= rating) Icons.Filled.StarHalf else null
            icon?.let {
                Icon(
                    it,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(starSize)
                )
            }
        }
    }
}