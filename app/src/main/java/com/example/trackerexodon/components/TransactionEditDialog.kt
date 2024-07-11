package com.example.trackerexodon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackerexodon.R
import com.example.trackerexodon.data.model.ExpenseEntity

@Composable
fun TransactionEditDialog(
    isOpen: Boolean,
    transaction: ExpenseEntity?,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: (ExpenseEntity) -> Unit
) {
    if (isOpen && transaction != null) {
        var title by remember { mutableStateOf(transaction.title) }
        var amount by remember { mutableStateOf(transaction.amount.toString()) }
        var category by remember { mutableStateOf(transaction.category) }

        val commonTextStyle = TextStyle(color = Color.White, fontSize = 14.sp)

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = "Edit Transaction",
                    color = Color.White
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Title
                    Column {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .background(color = Color(0xFF296054))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_form_title),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                            ) {
                                BasicTextField(
                                    value = title,
                                    onValueChange = { title = it },
                                    singleLine = true,
                                    textStyle = commonTextStyle,
                                    cursorBrush = SolidColor(Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) { innerTextField ->
                                    if (title.isEmpty()) {
                                        Text(
                                            text = "Expense Title",
                                            color = Color.Gray,
                                            style = commonTextStyle,
                                            modifier = Modifier.align(Alignment.CenterStart)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                        Divider(color = Color(0xFF414141), thickness = 1.dp)
                    }

                    // Amount
                    Column {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .background(color = Color(0xFF296054))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_form_title),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                            ) {
                                BasicTextField(
                                    value = amount,
                                    onValueChange = {
                                        if (it.all { char -> char.isDigit() }) {
                                            amount = it
                                        }
                                    },
                                    singleLine = true,
                                    textStyle = commonTextStyle,
                                    cursorBrush = SolidColor(Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) { innerTextField ->
                                    if (amount.isEmpty()) {
                                        Text(
                                            text = "Expense Amount",
                                            color = Color.Gray,
                                            style = commonTextStyle,
                                            modifier = Modifier.align(Alignment.CenterStart)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                        Divider(color = Color(0xFF414141), thickness = 1.dp)
                    }

                    // Category
                    Column {
                        Row(
                            modifier = Modifier.padding(vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(shape = RoundedCornerShape(4.dp))
                                    .background(color = Color(0xFF296054))
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_form_title),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                            ) {
                                BasicTextField(
                                    value = category,
                                    onValueChange = { category = it },
                                    singleLine = true,
                                    textStyle = commonTextStyle,
                                    cursorBrush = SolidColor(Color.White),
                                    modifier = Modifier.fillMaxWidth()
                                ) { innerTextField ->
                                    if (category.isEmpty()) {
                                        Text(
                                            text = "Category",
                                            color = Color.Gray,
                                            style = commonTextStyle,
                                            modifier = Modifier.align(Alignment.CenterStart)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                        Divider(color = Color(0xFF414141), thickness = 1.dp)
                    }
                }
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
                    onClick = {
                        onConfirmButtonClick(
                            transaction.copy(
                                title = title,
                                amount = amount ?: "0.0",
                                category = category
                            )
                        )
                    },
                ) {
                    Text(text = "Save", color = Color(0xFF3FDB9D))
                }
            },
            containerColor = Color(0xFF31434D),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        )
    }
}
