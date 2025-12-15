package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.model.TodoCategoryIcon

@Composable
fun CategoryTodo(
    categoryName: TodoCategoryIcon,
    icon: Int,
    backgroundColor: Color = Color.DarkGray,
    iconColor: Color = Color.White,
    onSelectedItem: (TodoCategoryIcon) -> Unit = {},
    itemSelected: Boolean = false
) {
    val shape = RoundedCornerShape(4.dp)
    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(shape)
                .border(if (itemSelected) 4.dp else 0.dp, iconColor, shape)
                .background(backgroundColor)
                .clickable {
                    onSelectedItem(categoryName)
                }
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
                tint = iconColor,
                painter = painterResource(icon),
                contentDescription = "Category Icon"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = categoryName.name,
            fontSize = 12.sp,
            fontStyle = FontStyle.Normal,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTodoPreview() {
    CategoryTodo(
        icon = R.drawable.ic_briefcase,
        backgroundColor = Color(0xFFFF9680),
        iconColor = Color(0xFFA31D00),
        categoryName = TodoCategoryIcon.WORK
    )
}