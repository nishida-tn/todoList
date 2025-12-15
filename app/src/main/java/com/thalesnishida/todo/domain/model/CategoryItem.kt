package com.thalesnishida.todo.domain.model

import androidx.compose.ui.graphics.Color

data class CategoryItem(
    val categoryName: TodoCategoryIcon,
    val icon: Int,
    val backGroundColor: Color,
    val iconColor: Color
)
