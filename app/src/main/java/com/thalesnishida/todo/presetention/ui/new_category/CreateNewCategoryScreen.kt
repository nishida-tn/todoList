package com.thalesnishida.todo.presetention.ui.new_category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thalesnishida.todo.R
import com.thalesnishida.todo.presetention.components.ButtonDefault
import com.thalesnishida.todo.presetention.components.CircleSelectItem
import kotlin.collections.set
import kotlin.text.set

@Composable
fun CreateNewCategoryScreen(
    onCancel: () -> Unit,
    onCreateCategory: (String, Color?) -> Unit
) {
    val colorList = listOf(
        colorResource(R.color.flag_lime),
        colorResource(R.color.flag_peach_pink),
        colorResource(R.color.flag_cyan),
        colorResource(R.color.flag_mint),
        colorResource(R.color.flag_periwinkle),
        colorResource(R.color.flag_pink),
        colorResource(R.color.flag_magenta),
        colorResource(R.color.flag_seafoam),
        colorResource(R.color.flag_sky),
        colorResource(R.color.flag_light_orange),
        colorResource(R.color.flag_mint_light)
    )

    var selectedColor by remember { mutableStateOf<Color?>(null) }
    var categoryName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Create new category",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )
        Text(
            text = "Category Name:",
            color = Color.White,
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = {
                Text(
                    text = "Category Name",
                    fontSize = 18.sp
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White
            )
        )

        Text(
            text = "Category Icon:",
            color = Color.White,
            fontSize = 16.sp
        )

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = {},
            shape = RoundedCornerShape(
                4.dp
            )
        ) {
            Text("Choose Icon from library")
        }

        Text(
            text = "Category Color:",
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(colorList) { color ->
                CircleSelectItem(
                    onSelectColor = { selectedColor = it },
                    isColorSelected = (selectedColor == color),
                    color = color
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            ButtonDefault(
                onClick =  onCancel,
                modifier = Modifier.weight(1f),
                text = "Cancel",
                showBackgroundColor = false
            )
            ButtonDefault(
                onClick = { onCreateCategory(categoryName, selectedColor) },
                modifier = Modifier.weight(1f),
                text = "Create Category",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateNewCategoryScreenPreview() {
    CreateNewCategoryScreen(
        onCancel = {},
        onCreateCategory = { _, _ -> }
    )
}