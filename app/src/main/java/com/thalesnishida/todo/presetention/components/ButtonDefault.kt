package com.thalesnishida.todo.presetention.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonDefault(
    onClick: () -> Unit,
    text: String,
    showBackgroundColor: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(4.dp)
    val containerColor = if (showBackgroundColor) MaterialTheme.colorScheme.primary else Color.Transparent
    val contentColor = if (showBackgroundColor) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        modifier = modifier.clip(shape),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor),
        shape = shape
    ) {
        Text(text = text, color = contentColor)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonDefaultPreview() {
    ButtonDefault(onClick = {}, text = "Savar")
}