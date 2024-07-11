package com.example.trackerexodon.screens

import ExpenseViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.trackerexodon.R
import com.example.trackerexodon.components.Header
import com.example.trackerexodon.components.TransactionDeleteDialog
import com.example.trackerexodon.components.TransactionEditDialog
import com.example.trackerexodon.components.TransactionItem
import com.example.trackerexodon.data.model.ExpenseEntity
import com.example.trackerexodon.utils.DateFormatter
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ExpenseHistoryScreen(navController: NavHostController) {
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
    val editable = remember { mutableStateOf(false) }
    val expenseViewModel: ExpenseViewModel = viewModel()
    val state by viewModel.expenses.collectAsState(initial = emptyList())
    val sortedList = state.sortedByDescending { it.date.toLong() }
    val deleteModalOpen = expenseViewModel.deleteDialogOpen.value
    val editModalOpen = expenseViewModel.editDialogOpen.value
    val itemToDelete = remember { mutableStateOf<Int?>(null) }
    val itemToEdit = remember { mutableStateOf<ExpenseEntity?>(null) }

    Scaffold(
        topBar = { Header(true, navController) },
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
        Column(
            modifier = Modifier
                .background(color = Color(0xFF21353C))
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
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
                    Image(
                        painter = painterResource(id = if (editable.value) R.drawable.ic_close_secondary else R.drawable.ic_edit_primary),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                editable.value = !editable.value
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
                    LazyColumn {
                        items(sortedList) { item ->
                            TransactionItem(
                                id = item.id,
                                title = item.title,
                                amount = item.amount.toString(),
                                date = DateFormatter.formatDateToHumanReadableForm(item.date.toLong()),
                                category = item.category,
                                type = item.type,
                                color = if (item.type == "Income") Color(0xFF3FDB9D) else Color(
                                    0xFFFC575D
                                ),
                                icon = viewModel.getItemIcon(item),
                                valueType = if (item.type == "Income") "+" else "-",
                                editable = editable,
                                onDelete = {
                                    itemToDelete.value = item.id
                                    expenseViewModel.deleteDialogOpen.value = true
                                },
                                onEdit = {
                                    itemToEdit.value = item
                                    expenseViewModel.editDialogOpen.value = true
                                }
                            )
                        }
                    }
                }
            }
            TransactionDeleteDialog(
                isOpen = deleteModalOpen,
                title = "Delete Transaction",
                bodyText = "Do you want to Delete the Transaction. It will never be undone",
                onDismissRequest = { expenseViewModel.deleteDialogOpen.value = false },
                onConfirmButtonClick = {
                    itemToDelete.value?.let { id ->
                        viewModel.deleteExpense(id)
                        expenseViewModel.deleteDialogOpen.value = false
                    }
                }
            )
            TransactionEditDialog(
                isOpen = editModalOpen,
                transaction = itemToEdit.value,
                onDismissRequest = { expenseViewModel.editDialogOpen.value = false },
                onConfirmButtonClick = { updatedTransaction ->
                    viewModel.updateExpense(updatedTransaction)
                    expenseViewModel.editDialogOpen.value = false
                }
            )
        }
    }
}