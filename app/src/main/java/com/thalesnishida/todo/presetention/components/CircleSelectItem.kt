package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.R

@Composable
fun CircleSelectItem(
    onSelectColor: (Color) -> Unit,
    isColorSelected: (Boolean) = false,
    color: Color
){
    Box(
        modifier = androidx.compose.ui.Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color)
            .clickable {
                onSelectColor(color)
            },
        contentAlignment = Alignment.Center
    ) {
        if(isColorSelected) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                tint = Color.White,
                contentDescription = "Selected Color",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircleSelectItemPreview() {
    CircleSelectItem(onSelectColor = {}, color = Color.Red, isColorSelected = true)
}