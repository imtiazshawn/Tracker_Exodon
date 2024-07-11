package com.example.trackerexodon.components

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackerexodon.R
import com.example.trackerexodon.data.model.ExpenseEntity
import com.example.trackerexodon.utils.DateFormatter
import java.util.*

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
        var date by remember { mutableStateOf(transaction.date) }
        var type by remember { mutableStateOf(transaction.type) }
        val commonTextStyle = TextStyle(color = Color.White, fontSize = 14.sp)

        val categories = listOf("Salary", "Food", "Rent", "Travel", "Others")
        var categoryExpand by remember { mutableStateOf(false) }

        val types = listOf("Income", "Expense")
        var typeExpand by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                date = calendar.timeInMillis.toString()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

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
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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

                    // Date
                    Column(
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(shape = RoundedCornerShape(4.dp))
                                        .background(color = Color(0xFF296054))
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_calender),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                BasicTextField(
                                    value = if (date.isEmpty()) "" else DateFormatter.formatDateToHumanReadableForm(date.toLong()),
                                    onValueChange = { },
                                    enabled = false
                                ) {
                                    Text(
                                        text = if (date.isEmpty()) "Date" else DateFormatter.formatDateToHumanReadableForm(date.toLong()),
                                        color = if (date.isEmpty()) Color.Gray else Color.White,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFFCCCCCC),  )
                        }
                        Divider(color = Color(0xFF414141), thickness = 1.dp)
                    }

                    // Category
                    Column(
                        modifier = Modifier.clickable { categoryExpand = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(shape = RoundedCornerShape(4.dp))
                                        .background(color = Color(0xFF296054))
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_sorting),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                BasicTextField(
                                    value = if (category.isEmpty()) "" else category,
                                    onValueChange = { },
                                    enabled = false
                                ) {
                                    Text(
                                        text = if (category.isEmpty()) "Category" else category,
                                        color = if (category.isEmpty()) Color.Gray else Color.White,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = categoryExpand,
                                    onDismissRequest = { categoryExpand = false },
                                    modifier = Modifier
                                        .background(color = Color(0xFF31434D))
                                        .padding(horizontal = 12.dp)
                                ) {
                                    categories.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item, color = Color.White) },
                                            onClick = {
                                                category = item
                                                categoryExpand = false
                                            }
                                        )
                                    }
                                }
                            }
                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFFCCCCCC),  )
                        }
                        Divider(color = Color(0xFF414141), thickness = 1.dp)
                    }

                    // Type
                    Column(
                        modifier = Modifier.clickable { typeExpand = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment =                         Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(shape = RoundedCornerShape(4.dp))
                                        .background(color = Color(0xFF296054))
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_sorting),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                BasicTextField(
                                    value = if (type.isEmpty()) "" else type,
                                    onValueChange = { },
                                    enabled = false
                                ) {
                                    Text(
                                        text = if (type.isEmpty()) "Type" else type,
                                        color = if (type.isEmpty()) Color.Gray else Color.White,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                                DropdownMenu(
                                    expanded = typeExpand,
                                    onDismissRequest = { typeExpand = false },
                                    modifier = Modifier
                                        .background(color = Color(0xFF31434D))
                                        .padding(horizontal = 12.dp)
                                ) {
                                    types.forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(text = item, color = Color.White) },
                                            onClick = {
                                                type = item
                                                typeExpand = false
                                            }
                                        )
                                    }
                                }
                            }
                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFFCCCCCC),  )
                        }
                        Divider(color = Color(0xFF414141), thickness = 1.dp)
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val updatedTransaction = ExpenseEntity(
                            id = transaction.id,
                            title = title,
                            amount = amount,
                            date = date,
                            category = category,
                            type = type
                        )
                        onConfirmButtonClick(updatedTransaction)
                    }
                ) {
                    Text(text = "Confirm", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel", color = Color.White)
                }
            },
            containerColor = Color(0xFF31434D),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        )
    }
}
