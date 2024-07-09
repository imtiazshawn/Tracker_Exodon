package com.example.trackerexodon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.trackerexodon.utils.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataForm(dateDialogVisibility: MutableState<Boolean>, date: MutableState<Long>) {

    val commonTextStyle = TextStyle(color = Color.White, fontSize = 16.sp, lineHeight = 20.sp)

    val title = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val category = remember {
        mutableStateOf("")
    }
    val type = remember {
        mutableStateOf("")
    }

    val categories = listOf("Netflix", "Paypal", "Starbucks", "Salary", "Upwork")
    var categoryExpand by remember { mutableStateOf(false) }

    val types = listOf("Income", "Expense")
    var typeExpand by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

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
                        value = title.value,
                        onValueChange = { title.value = it },
                        singleLine = true,
                        textStyle = commonTextStyle,
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { innerTextField ->
                        if (title.value.isEmpty()) {
                            Text(
                                text = "Expense Title",
                                color = Color.Gray,
                                style = commonTextStyle,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
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
                        value = amount.value,
                        onValueChange = { amount.value = it },
                        singleLine = true,
                        textStyle = commonTextStyle,
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { innerTextField ->
                        if (amount.value.isEmpty()) {
                            Text(
                                text = "Expense Amount",
                                color = Color.Gray,
                                style = commonTextStyle,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
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
            modifier = Modifier.clickable { dateDialogVisibility.value = true }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
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
                        value = if (date.value == 0L) "" else DateFormatter.formatDateToHumanReadableForm(date.value),
                        onValueChange = { },
                        enabled = false
                    ) {
                        Text(
                            text = if (date.value == 0L) "Date" else "${DateFormatter.formatDateToHumanReadableForm(date.value)}",
                            color = if (date.value == 0L) Color.Gray else Color.White,
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
                        value = if (category.value.isEmpty()) "" else category.value,
                        onValueChange = { },
                        enabled = false
                    ) {
                        Text(
                            text = if (category.value.isEmpty()) "Category" else category.value,
                            color = if (category.value.isEmpty()) Color.Gray else Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = categoryExpand,
                        onDismissRequest = { categoryExpand = false },
                        modifier = Modifier.background(color = Color(0xFF31434D)).padding(horizontal = 12.dp)
                    ) {
                        categories.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item, color = Color.White) },
                                onClick = {
                                    category.value = item
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

        // Category
        Column(
            modifier = Modifier.clickable { typeExpand = true }
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
                        value = if (type.value.isEmpty()) "" else type.value,
                        onValueChange = { },
                        enabled = false
                    ) {
                        Text(
                            text = if (type.value.isEmpty()) "Type" else type.value,
                            color = if (type.value.isEmpty()) Color.Gray else Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = typeExpand,
                        onDismissRequest = { typeExpand = false },
                        modifier = Modifier.background(color = Color(0xFF31434D)).padding(horizontal = 12.dp)
                    ) {
                        types.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item, color = Color.White) },
                                onClick = {
                                    type.value = item
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
}

