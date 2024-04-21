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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    fullStarIconResId: Int = R.drawable.star_solid,
    halfStarIconResId: Int = R.drawable.star_half_solid,
    emptyStarIconResId: Int = R.drawable.star_regular
) {
    Row {

        for (i in 1 until 6) {
            val iconResId = when {
                i <= rating -> fullStarIconResId
                i <= rating + 0.6 -> halfStarIconResId
                else -> emptyStarIconResId
            }
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier
                    .size(starSize)
                ,
            )
        }
    }
}