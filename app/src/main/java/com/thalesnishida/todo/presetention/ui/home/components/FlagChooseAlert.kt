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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.presetention.components.ButtonDefault
import com.thalesnishida.todo.presetention.components.PriorityItem

@Composable
fun FlagChoose(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var selectedPriorityState by rememberSaveable { mutableIntStateOf(0) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(4.dp),
        title = {
            Column {
                Text(
                    "Task Priority",
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
                    .heightIn(max = 360.dp),
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                items(count = 10) { index ->
                    val number = index + 1
                    PriorityItem(
                        number = number,
                        itemSelected = selectedPriorityState == number,
                        onSelectedItem = {
                            selectedPriorityState =
                                if (selectedPriorityState == number) 0 else number
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
                    onClick = { onDismiss() },
                    text = "Cancel",
                    showBackgroundColor = false,
                    modifier = Modifier.weight(1f)
                )
                ButtonDefault(
                    onClick = {
                        if (selectedPriorityState != 0) {
                            onConfirm(selectedPriorityState)
                        }
                    },
                    text = "Confirm",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FlagChoosePreview() {
    FlagChoose(
        onDismiss = {},
        onConfirm = {}
    )
}