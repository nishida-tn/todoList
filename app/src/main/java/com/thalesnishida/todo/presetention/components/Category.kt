package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R

@Composable
fun Category(
    title: String
) {
    val shape = RoundedCornerShape(4.dp)
    Row(
        modifier = Modifier
            .clip(shape)
            .background(color = colorResource(R.color.flag_periwinkle))
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            tint = colorResource(R.color.ic_launcher_background),
            modifier = Modifier.size(14.dp),
            painter = painterResource(id = R.drawable.ic_mortarboar),
            contentDescription = "Icon"
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun CategoryPreview() {
    Category("University")
}