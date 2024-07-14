import ExpenseViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.trackerexodon.components.*
import com.example.trackerexodon.data.model.ExpenseEntity
import com.example.trackerexodon.utils.DateFormatter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*

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
    var sortingOption by remember { mutableStateOf("Normal") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val sortedList = when (sortingOption) {
        "This Day" -> state.filter { Date(it.date.toLong()).isToday() }
        "This Week" -> state.filter { Date(it.date.toLong()).isThisWeek() }
        "This Month" -> state.filter { Date(it.date.toLong()).isThisMonth() }
        else -> state.sortedByDescending { it.date.toLong() }
    }

    val deleteModalOpen = expenseViewModel.deleteDialogOpen.value
    val editModalOpen = expenseViewModel.editDialogOpen.value
    val itemToDelete = remember { mutableStateOf<Int?>(null) }
    val itemToEdit = remember { mutableStateOf<ExpenseEntity?>(null) }

    Scaffold(
        topBar = { Header(true, about = false, navController) },
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
                    Row {
                        Box {
                            Image(
                                painter = painterResource(R.drawable.ic_sorting_02),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { dropdownExpanded = true }
                            )
                            DropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false },
                                modifier = Modifier
                                    .background(color = Color(0xFF31434D))
                                    .padding(horizontal = 12.dp)
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Normal", color = Color.White) },
                                    onClick = {
                                        sortingOption = "Normal"
                                        dropdownExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "This Day", color = Color.White) },
                                    onClick = {
                                        sortingOption = "This Day"
                                        dropdownExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "This Week", color = Color.White) },
                                    onClick = {
                                        sortingOption = "This Week"
                                        dropdownExpanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "This Month", color = Color.White) },
                                    onClick = {
                                        sortingOption = "This Month"
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(20.dp))
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

fun Date.isToday(): Boolean {
    val today = Calendar.getInstance()
    val date = Calendar.getInstance()
    date.time = this
    return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)
}

fun Date.isThisWeek(): Boolean {
    val today = Calendar.getInstance()
    val date = Calendar.getInstance()
    date.time = this
    return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.WEEK_OF_YEAR) == date.get(Calendar.WEEK_OF_YEAR)
}

fun Date.isThisMonth(): Boolean {
    val today = Calendar.getInstance()
    val date = Calendar.getInstance()
    date.time = this
    return today.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
            today.get(Calendar.MONTH) == date.get(Calendar.MONTH)
}
