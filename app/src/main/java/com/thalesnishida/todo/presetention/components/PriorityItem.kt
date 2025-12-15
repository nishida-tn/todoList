package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.R

@Composable
fun PriorityItem(
    onSelectedItem: (Int) -> Unit = {},
    number: Int,
    itemSelected: Boolean = false
) {
    val shape = RoundedCornerShape(4.dp)
    Column(
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .clip(shape)
            .background(if (itemSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer)
            .clickable {
                onSelectedItem(number)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_flag),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "Priority Icon"
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = number.toString(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PriorityItemPreview() {
    PriorityItem(number = 1)
}