package com.example.woofcareapp.screens.Preference

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.woofcareapp.navigation.repository.DataRepository
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof

@Composable
fun PreferenceScreen(navController: NavController) {
    var isDarkMode by remember { mutableStateOf(false) }
    var fontSize by remember { mutableStateOf(16.sp) }
    var sliderPosition by remember { mutableFloatStateOf(16f) } // Ajusta el valor inicial dentro del rango permitido
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Inglés") }
    val languages = listOf("Inglés", "Español", "Francés", "Alemán")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            )
            {

                Text("Dark Mode", fontSize = DataRepository.getSizeOfText())
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { isDarkMode = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = DarkButtonWoof, uncheckedThumbColor = DarkButtonWoof, uncheckedTrackColor = prominentWoof)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(text = "Font Size", Modifier.padding(end = 5.dp), fontSize = DataRepository.getSizeOfText())
                Slider(
                    modifier = Modifier.width(150.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = DarkButtonWoof,
                        activeTrackColor = DarkButtonWoof,
                        inactiveTrackColor = prominentWoof,

                    ),
                    valueRange = 12f..30f,
                    value = sliderPosition,
                    onValueChange = { position ->
                        sliderPosition = position
                        DataRepository.setSizeOfText(position.sp)
                    },
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add a payment method", fontSize = DataRepository.getSizeOfText())
                Icon(Icons.Default.Add, contentDescription = null)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Language:", fontSize = DataRepository.getSizeOfText())
                Column {
                    OutlinedButton(onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = backWoof)) {
                        Text(text = selectedLanguage)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        modifier = Modifier.background(backWoof),
                        onDismissRequest = { expanded = false }
                    ) {
                        languages.forEach{languages ->
                            DropdownMenuItem(onClick = {
                                selectedLanguage = languages
                            }) {
                                Text(text = languages)
                            }
                        }
                    }
                }

            }


        }
    }
}