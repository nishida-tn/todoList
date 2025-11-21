package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R

@Composable
fun CategoryTodo() {
    val shape = RoundedCornerShape(4.dp)
    Row(
        modifier = Modifier
            .clip(shape)
            .background(Color.Blue)
            .padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp)
            .border(
                2.dp, Color.Blue,
                shape
            )
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painterResource(R.drawable.ic_send),
            contentDescription = "Category Icon"
        )

        Text(
            text = "Home",
            fontSize = 12.sp,
            fontStyle = FontStyle.Normal,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTodoPreview() {
    CategoryTodo()
}