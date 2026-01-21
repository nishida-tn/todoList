package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R

@Composable
fun Priority(
    priority: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(1.dp, Color(0xFF979797), RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painterResource(R.drawable.ic_flag),
            tint = Color.White,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = priority.toString(),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun PriorityPreview() {
    Priority(
        priority = 2
    )
}