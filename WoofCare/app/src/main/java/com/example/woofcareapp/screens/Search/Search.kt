package com.example.woofcareapp.screens.Search

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
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.woofcareapp.api.models.User
import com.example.woofcareapp.screens.Search.ItemDetails.previewItem

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

@Composable
fun SearchScreen(navController: NavController) {
    val searchValue = remember{ mutableStateOf("") }
    val userList = listOf(
        User(
            id = 1,
            name = "John Doe",
            email = "john.doe@example.com",
            password = "password123",
            accountType = 0,
            suscriptionType = 1,
            location = "New York",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 1234567890,
            ratingId = 1,
            statusAccount = 1
        ),
        User(
            id = 2,
            name = "Jane Smith",
            email = "jane.smith@example.com",
            password = "password456",
            accountType = 1,
            suscriptionType = 2,
            location = "Los Angeles",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 9876543210,
            ratingId = 3,
            statusAccount = 1
        ),
        User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        ),
        User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        ),
        User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        ),
        User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        ), User(
            id = 3,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            ratingId = 1,
            statusAccount = 1
        )
    )
    Column {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            ) {
                SearchBar(searchValue)
            }
            Filter(onPriceRangeChange = {})
            LazyColumn(Modifier.padding(5.dp)) {
                userList.forEach {
                    item { previewItem(user = it, navController = navController) }
                }
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchValue: MutableState<String>) {

    OutlinedTextField(
        maxLines = 1,
        modifier = Modifier
            .padding(10.dp) // Ajusta el padding según tus preferencias
            .fillMaxWidth(),
        value = searchValue.value, onValueChange = {searchValue.value = it},
        singleLine = true,
        label = { Text(text = "Search") },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filter(
    onPriceRangeChange: (Pair<Int, Int>) -> Unit, // Callback to handle price range changes
    currentPriceRange: Pair<Int, Int>? = null, // Initial price range (optional)
) {
    val checkedList = remember { mutableStateListOf<Int>() }
    val options = listOf("Trainer", "Pet Sitter")
    val icons = listOf(
        Icons.Default.TrendingUp,
        Icons.Default.Pets,
    )
    var minPrice by remember { mutableStateOf(currentPriceRange?.first ?: 0) }
    var maxPrice by remember { mutableStateOf(currentPriceRange?.second ?: 5000) }

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
                        minPrice = it.toInt()
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
                        maxPrice = it.toInt()
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
                            onCheckedChange = {
                                if (index in checkedList) {
                                    checkedList.remove(index)
                                } else {
                                    checkedList.clear()
                                    checkedList.add(index)
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


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onPriceRangeChange(Pair(minPrice, maxPrice)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Filter")
        }
    }
}
