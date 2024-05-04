package com.example.woofcareapp.screens.FAQ

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.woofcareapp.screens.Info.Product.ExpandableItem

@Composable
fun FaqView(navController: NavHostController) {
    val firstExpanded = remember { mutableStateOf(true) }
    val searchValue = remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "We're here you with anything and everything on WoofCare",
                style = typography.h3
            )
        }

        OutlinedTextField(
            maxLines = 1,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            value = searchValue.value, onValueChange = {
                searchValue.value = it
            },
            singleLine = true,
            label = { Text(text = "Search") },
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            }
        )

        Text(
            text = "FAQ",
            style = typography.h6,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {

            //Recorrer en al back end
            item {
                ExpandableItem(
                    title = "What is WoofCare?",
                    content = { Text(text = "Ni idea de que es WoofCare") },
                    expanded = firstExpanded,
                    onClick = {firstExpanded.value = false}
                )
            }

            //Recorrer en al back end
            item {
                ExpandableItem(
                    title = "What is WoofCare?",
                    content = { Text(text = "Ni idea de que es WoofCare") },
                    expanded = firstExpanded,
                    onClick = {firstExpanded.value = false}
                )
            }

            //Recorrer en al back end
            item {
                ExpandableItem(
                    title = "What is WoofCare?",
                    content = { Text(text = "Ni idea de que es WoofCare") },
                    expanded = firstExpanded,
                    onClick = {firstExpanded.value = false}
                )
            }
        }

    }
}