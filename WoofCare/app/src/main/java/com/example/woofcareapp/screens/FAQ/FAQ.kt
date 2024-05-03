package com.example.woofcareapp.screens.FAQ

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woofcareapp.screens.Info.Product.ExpandableItem
import com.example.woofcareapp.screens.Search.SearchBar

@Composable
@Preview(showBackground = true)
fun FaqView() {
    val firstExpanded = remember { mutableStateOf(true) }

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

        SearchBar()

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