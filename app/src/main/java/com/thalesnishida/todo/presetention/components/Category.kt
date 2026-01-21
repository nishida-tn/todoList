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
import androidx.compose.ui.Alignment
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
    title: String,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, iconRes) = when (title.lowercase()) {
        "university" -> colorResource(R.color.flag_periwinkle) to R.drawable.ic_mortarboar
        "home" -> colorResource(R.color.flag_peach_pink) to R.drawable.ic_home
        "work" -> colorResource(R.color.flag_light_orange) to R.drawable.ic_briefcase
        else -> colorResource(R.color.flag_cyan) to R.drawable.ic_tag
    }

    val shape = RoundedCornerShape(4.dp)
    Row(
        modifier = modifier
            .clip(shape)
            .background(color = backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            tint = Color.White,
            modifier = Modifier.size(14.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

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