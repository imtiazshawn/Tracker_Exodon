package com.example.trackerexodon.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trackerexodon.components.DataForm
import com.example.trackerexodon.components.ExpenseDatePicker
import com.example.trackerexodon.components.Header
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AddExpenseScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = Color(0xFF21353C)

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    val dateDialogVisibility = remember {
        mutableStateOf(false)
    }
    val date = remember {
        mutableStateOf(0L)
    }


    Scaffold(
        topBar = { Header() },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = { navController.popBackStack() },
                    containerColor = Color(0xFF296054),
                    modifier = Modifier.weight(0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFF3FDB9D)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                FloatingActionButton(
                    onClick = { },
                    containerColor = Color(0xFF3FDB9D),
                    modifier = Modifier.weight(0.75f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Add Expense",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF21353C))
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add Income/Expense",
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DataForm(dateDialogVisibility, date)
        }
        if (dateDialogVisibility.value) {
            ExpenseDatePicker(
                onDateSelected = {
                    date.value = it
                    dateDialogVisibility.value = false
                },
                onDismiss = { }
            )
        }
    }
}


@Preview
@Composable
fun AddExpenseScreenPreview() {
    AddExpenseScreen(rememberNavController())
}