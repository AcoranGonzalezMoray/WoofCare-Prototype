package com.example.woofcareapp.screens.Search

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.screens.Search.ItemDetails.previewItem
import com.example.woofcareapp.ui.theme.DarkButtonWoof
import com.example.woofcareapp.ui.theme.backWoof
import com.example.woofcareapp.ui.theme.prominentWoof

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

@Composable
fun SearchScreen(navController: NavController) {
    val searchValue = remember{ mutableStateOf("") }
    val (selectedType, setSelectedType) = remember { mutableStateOf(0) }
    val (selectedPriceMin, setSelectedPriceMin) = remember { mutableStateOf(0.0) }
    val (selectedPriceMax, setSelectedPriceMax) = remember { mutableStateOf(50.0) }

    val serviceList = listOf(
        Service(
            id = 1,
            name = "Paseos Diarios",
            type = 1,
            status = 0,
            publicationDate = "2024-04-21",
            description = "Paseos diarios para perros de todas las edades y razas. Incluye ejercicio moderado y socialización.",
            price = 25.0,
            uid = 1,
            reviewId = 1,
            bannerUrl = listOf(
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg",
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
            )
        ),
        Service(
            id = 2,
            name = "Entrenamiento Básico",
            type = 2,
            status = 0,
            publicationDate = "2024-04-20",
            description = "Entrenamiento básico para cachorros y perros adultos. Enseñanza de órdenes básicas y comportamiento adecuado.",
            price = 50.0,
            uid = 2,
            reviewId = 1,
            bannerUrl = listOf(
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg",
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
            )
        ),
        Service(
            id = 3,
            name = "Cuidado de Día",
            type = 1,
            status = 0,
            publicationDate = "2024-04-19",
            description = "Cuidado diurno para perros mientras los propietarios están fuera. Incluye tiempo de juego y supervisión.",
            price = 35.0,
            uid = 3,
            reviewId = 1,
            bannerUrl = listOf(
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg",
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
            )
        ),
        Service(
            id = 4,
            name = "Adiestramiento Avanzado",
            type = 1,
            status = 0,
            publicationDate = "2024-04-18",
            description = "Adiestramiento avanzado para perros con necesidades especiales. Enseñanza de habilidades avanzadas y obediencia.",
            price = 70.0,
            uid = 4,
            reviewId = 1,
            bannerUrl = listOf(
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg",
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
            )
        ),
        Service(
            id = 5,
            name = "Guardería Nocturna",
            type = 2,
            status = 0,
            publicationDate = "2024-04-17",
            description = "Guardería nocturna para perros que necesitan alojamiento durante la noche. Ambiente seguro y cómodo para descansar.",
            price = 40.0,
            uid = 5,
            reviewId = 1,
            bannerUrl = listOf(
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg",
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
            )
        ),
        Service(
            id = 6,
            name = "Terapia Canina",
            type = 2,
            status = 0,
            publicationDate = "2024-04-16",
            description = "Terapia emocional para perros que sufren de ansiedad o estrés. Sesiones individuales y grupales disponibles.",
            price = 60.0,
            uid = 6,
            reviewId = 1,
            bannerUrl = listOf(
                "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg",
                "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
            )
        )
    )
    var filteredList = serviceList.filter { service ->
        service.name.contains(searchValue.value, ignoreCase = true) &&
                (selectedType == 0 || service.type == selectedType) &&
                (service.price in selectedPriceMin..selectedPriceMax)
    }


    Column(
        modifier = Modifier.fillMaxSize().background(backWoof)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
        ) {
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
            )        }
        Filter(onPriceRangeChange = {}, onTypeChange = { setSelectedType(it) }, onMinPrice = {setSelectedPriceMin(it)},
            onMaxPrice = {setSelectedPriceMax(it)})
        LazyColumn(Modifier.padding(5.dp)) {
            filteredList.forEach {
                item { previewItem(service = it, navController = navController) }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filter(
    onPriceRangeChange: (Pair<Int, Int>) -> Unit, // Callback to handle price range changes
    currentPriceRange: Pair<Int, Int>? = null, // Initial price range (optional)
    onTypeChange: (Int) -> Unit,
    onMinPrice: (Double) -> Unit,
    onMaxPrice: (Double) -> Unit,
) {
    val checkedList = remember { mutableStateListOf<Int>() }
    val options = listOf("Trainer", "Pet Sitter")
    val icons = listOf(
        Icons.Default.TrendingUp,
        Icons.Default.Pets,
    )
    var minPrice by remember { mutableStateOf(currentPriceRange?.first ?: 0.0) }
    var maxPrice by remember { mutableStateOf(currentPriceRange?.second ?: 50.0) }
    val segmentedButtonColors = SegmentedButtonColors(
        activeContainerColor = DarkButtonWoof,
        activeContentColor = Color.White,
        activeBorderColor = prominentWoof,

        inactiveContainerColor = backWoof,
        inactiveContentColor = Color.Black,
        inactiveBorderColor = prominentWoof,

        disabledActiveContainerColor = Color.LightGray,
        disabledActiveContentColor = Color.Gray,
        disabledActiveBorderColor = Color.LightGray,
        disabledInactiveContainerColor = Color.LightGray,
        disabledInactiveContentColor = Color.Gray,
        disabledInactiveBorderColor = Color.LightGray
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),

                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    value = minPrice.toString(),
                    onValueChange = {
                        if(it.isNotEmpty())minPrice = it.toDouble()
                        if(minPrice.toString().isNotEmpty()) onMinPrice( minPrice.toDouble())
                    },
                    label = { Text("Min Price") },
                    modifier = Modifier.weight(1f) // Assign equal weight to both TextFields

                )
                Box(
                    modifier = Modifier.weight(0.2f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Remove, contentDescription = null)
                }
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    value = maxPrice.toString(),
                    onValueChange = {
                        if(it.isNotEmpty())maxPrice = it.toDouble()
                        if(maxPrice.toString().isNotEmpty()) onMaxPrice( maxPrice.toDouble())
                    },
                    label = { Text("Max price") },
                    modifier = Modifier.weight(1f) // Assign equal weight to both TextFields

                )

            }
            Spacer(modifier = Modifier.width(7.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            {
                MultiChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            colors =segmentedButtonColors,
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            icon = {
                                SegmentedButtonDefaults.Icon(active = index in checkedList) {
                                    Icon(
                                        imageVector = icons[index],
                                        contentDescription = null,
                                        modifier = Modifier.size(SegmentedButtonDefaults.IconSize)
                                    )
                                }
                            },
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    checkedList.clear()
                                    checkedList.add(index)
                                    var local = index +1
                                    onTypeChange(local)
                                } else {
                                    checkedList.remove(index)
                                    if (checkedList.isEmpty()) {
                                        onTypeChange(0)
                                    }
                                }
                            },
                            checked = index in checkedList
                        ) {
                            Text(label)
                        }
                    }
                }
            }
        }

    }
}
