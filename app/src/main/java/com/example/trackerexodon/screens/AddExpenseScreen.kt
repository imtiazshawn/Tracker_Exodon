package com.example.trackerexodon.screens

import ExpenseViewModel
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.viewmodel.AddExpenseViewModel
import com.example.expensetracker.viewmodel.AddExpenseViewModelFactory
import com.example.trackerexodon.components.DataForm
import com.example.trackerexodon.components.Header
import com.example.trackerexodon.data.model.ExpenseEntity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(navController: NavHostController) {
    val expenseViewModel: ExpenseViewModel = viewModel()
    val viewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = Color(0xFF21353C)

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = color, darkIcons = useDarkIcons
        )
    }

    val title = expenseViewModel.title.value
    val amount = expenseViewModel.amount.value
    val date = expenseViewModel.date.value
    val category = expenseViewModel.category.value
    val type = expenseViewModel.type.value
    val model = ExpenseEntity(
        null, title, amount, date, category, type
    )


    Scaffold(topBar = { Header(false, about = false, navController) }, floatingActionButton = {
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
                onClick = {
                    coroutineScope.launch {
                        if (title.isNotEmpty() && amount.isNotEmpty() && date.isNotEmpty() && category.isNotEmpty() && type.isNotEmpty()) {
                            if (viewModel.addExpense(model)) {
                                navController.popBackStack()
                            }
                        } else {
                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
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
    }) { paddingValues ->
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
            DataForm(
                viewModel = expenseViewModel
            )
        }
    }
}


@Preview
@Composable
fun AddExpenseScreenPreview() {
    AddExpenseScreen(rememberNavController())
}