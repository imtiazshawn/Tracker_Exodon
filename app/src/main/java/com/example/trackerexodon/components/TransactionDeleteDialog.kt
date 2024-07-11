package com.example.trackerexodon.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TransactionDeleteDialog(
    isOpen: Boolean,
    title: String,
    bodyText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = title,
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = bodyText,
                    color = Color.LightGray
                )
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(text = "Cancel", color = Color.White)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                ) {
                    Text(text = "Delete", color = Color.Red)
                }
            },
            containerColor = Color(0xFF31434D),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        )
    }
}
