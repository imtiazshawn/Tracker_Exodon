package com.example.trackerexodon.screens

import ExpenseInsect
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.trackerexodon.R
import com.example.trackerexodon.components.BalanceBox
import com.example.trackerexodon.components.Header
import com.example.trackerexodon.components.TransactionItem
import com.example.trackerexodon.data.model.ExpenseEntity
import com.example.trackerexodon.utils.DateFormatter
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = Color(0xFF21353C)

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    // ViewModel State
    val state by viewModel.expenses.collectAsState(initial = emptyList())
    val balance = viewModel.getBalance(state)
    val expenses = viewModel.getTotalExpense(state)
    val income = viewModel.getTotalIncome(state)
    val categoryPercentages = viewModel.getCategoryPercentages(state)

    Scaffold(
        topBar = { Header(false, navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("ADD_EXPENSE") },
                containerColor = Color(0xFF296054)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color(0xFF3FDB9D)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF21353C))
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hey ",
                        color = Color(0xFFFFFFFF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "There!",
                        color = Color(0xFFFFFFFF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Explore Here for your Expenses History!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    BalanceBox(expenses.toString(), income.toString(), balance)
                }

                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Expenses Insect",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                ExpenseInsect(
                    rentPercentage = categoryPercentages["Rent"] ?: 0f,
                    foodPercentage = categoryPercentages["Food"] ?: 0f,
                    travelPercentage = categoryPercentages["Travel"] ?: 0f,
                    othersPercentage = categoryPercentages["Others"] ?: 0f
                )

                Spacer(modifier = Modifier.height(28.dp))
                ExpenseHomeHistory(state = state, navController = navController)
            }
        }
    }
}


@Composable
fun ExpenseHomeHistory(
    state: List<ExpenseEntity>,
    navController: NavHostController
) {
    val viewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val sortedList = state.sortedByDescending { it.date.toLong() }.take(4)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Expenses History",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "See More >>",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    navController.navigate("EXPENSE_HISTORY")
                }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (sortedList.isEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_not_found),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "There is no Transaction Item",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        } else {
            sortedList.forEach { item ->
                TransactionItem(
                    id = item.id,
                    title = item.title,
                    amount = item.amount.toString(),
                    date = DateFormatter.formatDateToHumanReadableForm(item.date.toLong()),
                    category = item.category,
                    type = item.type,
                    color = if (item.type == "Income") Color(0xFF3FDB9D) else Color(0xFFFC575D),
                    icon = viewModel.getItemIcon(item),
                    valueType = if (item.type == "Income") "+" else "-",
                    editable = remember { mutableStateOf(false) },
                    onDelete = {}
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "See All Expenses",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        navController.navigate("EXPENSE_HISTORY")
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
