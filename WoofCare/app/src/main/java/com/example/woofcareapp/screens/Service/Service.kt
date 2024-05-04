package com.example.woofcareapp.screens.Service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof

@Composable
fun ServiceScreen(navController: NavController) {
    Box( modifier = Modifier.fillMaxSize().background(backWoof)){
        Column(
            modifier = Modifier.fillMaxSize().background(backWoof)
        ) {

        }
        // Botón flotante
        FloatingActionButton(
            onClick = { navController.navigate("addService")},
            modifier = Modifier.padding(16.dp).padding(bottom = 80.dp).align(Alignment.BottomEnd),
            backgroundColor = DarkButtonWoof
        ) {
            // Contenido del botón flotante (por ejemplo, un icono)
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                tint = Color.White
            )
        }
    }
}