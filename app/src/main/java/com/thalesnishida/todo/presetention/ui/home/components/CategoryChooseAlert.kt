package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.model.TodoCategoryIcon
import com.thalesnishida.todo.presetention.components.ButtonDefault
import com.thalesnishida.todo.presetention.components.CategoryTodo

@Composable
fun CategoryChooseAlert(
    onDismiss: () -> Unit,
    onCategorySelected: (TodoCategoryIcon) -> Unit,
    onCategoryConfirmed: (TodoCategoryIcon) -> Unit
) {
    var selectedCategory by rememberSaveable { mutableStateOf<TodoCategoryIcon?>(null) }

    data class UiCategory(
        val categoryName: TodoCategoryIcon,
        val icon: Int,
        val backGroundColor: Color,
        val iconColor: Color
    )

    val listCategoryDefault = remember {
        mutableListOf(
            UiCategory(
                categoryName = TodoCategoryIcon.GROCERY,
                icon = R.drawable.ic_bread,
                backGroundColor = Color(0xFFCCFF80),
                iconColor = Color(0xFF21A300)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.WORK,
                icon = R.drawable.ic_briefcase,
                backGroundColor = Color(0xFFFF9680),
                iconColor = Color(0xFFA31D00)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.SPORT,
                icon = R.drawable.ic_sport,
                backGroundColor = Color(0xFF80FFFF),
                iconColor = Color(0xFF00A32F)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.DESIGN,
                icon = R.drawable.ic_design,
                backGroundColor = Color(0xFF80FFD9),
                iconColor = Color(0xFF00A372)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.UNIVERSITY,
                icon = R.drawable.ic_mortarboar,
                backGroundColor = Color(0xFF809CFF),
                iconColor = Color(0xFF0055A3)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.SOCIAL,
                icon = R.drawable.ic_megaphone,
                backGroundColor = Color(0xFFFF80EB),
                iconColor = Color(0xFFA30089)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.MUSIC,
                icon = R.drawable.ic_music,
                backGroundColor = Color(0xFFFC80FF),
                iconColor = Color(0xFFA000A3)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.HEALTH,
                icon = R.drawable.ic_heartbeat,
                backGroundColor = Color(0xFF80FFA3),
                iconColor = Color(0xFF00A3A3)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.MOVIE,
                icon = R.drawable.ic_video_camera,
                backGroundColor = Color(0xFF80D1FF),
                iconColor = Color(0xFF0069A3)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.HOME,
                icon = R.drawable.ic_home,
                backGroundColor = Color(0xFFFFCC80),
                iconColor = Color(0xFFA36200)
            ),
            UiCategory(
                categoryName = TodoCategoryIcon.CREATE_NEW,
                icon = R.drawable.ic_add,
                backGroundColor = Color(0xFF80FFD1),
                iconColor = Color(0xFF00A369)
            )
        )
    }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(4.dp),
        title = {
            Column {
                Text(
                    "Choose Category",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
            }
        },
        text = {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 556.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                items(listCategoryDefault) { uiItem ->
                    CategoryTodo(
                        categoryName = uiItem.categoryName,
                        icon = uiItem.icon,
                        backgroundColor = uiItem.backGroundColor,
                        iconColor = uiItem.iconColor,
                        itemSelected = selectedCategory == uiItem.categoryName,
                        onSelectedItem = { clickedCategory ->
                            selectedCategory = clickedCategory
                        }
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ButtonDefault(
                    onClick = {
                        selectedCategory?.let { category ->
                            onCategorySelected(category)
                            onDismiss()
                        }
                    },
                    text = "Add Category",
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryChooseAlertPreview() {
    CategoryChooseAlert(
        onDismiss = {},
        onCategorySelected = {},
        onCategoryConfirmed = {}
    )
}