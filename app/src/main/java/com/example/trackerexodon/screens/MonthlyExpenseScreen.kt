import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import com.example.trackerexodon.R
import com.example.trackerexodon.components.*
import com.example.trackerexodon.utils.DateFormatter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyExpenseScreen(navController: NavHostController) {
    val viewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val backgroundColor = Color(0xFF21353C)

    // Date state
    val calendar = Calendar.getInstance()
    var selectedMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var selectedYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    val monthNames = listOf("January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December")

    // Custom date range state
    var showCustomDateRange by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(calendar.timeInMillis) }
    var endDate by remember { mutableStateOf(calendar.timeInMillis) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    // Format for displaying dates
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    // Filter selection state
    var selectedFilterOption by remember { mutableStateOf("Monthly") }
    var showFilterOptions by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = useDarkIcons
        )
    }

    // ViewModel State
    val state by viewModel.expenses.collectAsState(initial = emptyList())

    // Filter expenses based on selected option
    val filteredExpenses = when (selectedFilterOption) {
        "Monthly" -> {
            val startCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val endCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }

            state.filter {
                val expenseDate = it.date.toLong()
                expenseDate in startCalendar.timeInMillis..endCalendar.timeInMillis
            }
        }
        "Custom Range" -> {
            val endOfDay = Calendar.getInstance().apply {
                timeInMillis = endDate
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis

            val startOfDay = Calendar.getInstance().apply {
                timeInMillis = startDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            state.filter {
                val expenseDate = it.date.toLong()
                expenseDate in startOfDay..endOfDay
            }
        }
        else -> emptyList()
    }

    // Calculate totals
    val totalIncome = filteredExpenses
        .filter { it.type == "Income" }
        .sumOf { it.amount.toDouble() }

    val totalExpense = filteredExpenses
        .filter { it.type == "Expense" }
        .sumOf { it.amount.toDouble() }

    val balance = totalIncome - totalExpense

    Scaffold(
        topBar = { Header(true, about = false, navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(paddingValues)
        ) {
            item {
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
                            text = "Monthly Analysis",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF31434D))
                                    .clickable { showFilterOptions = true }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = selectedFilterOption,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = showFilterOptions,
                                onDismissRequest = { showFilterOptions = false },
                                modifier = Modifier.background(Color(0xFF31434D))
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Monthly", color = Color.White) },
                                    onClick = {
                                        selectedFilterOption = "Monthly"
                                        showFilterOptions = false
                                        showCustomDateRange = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Custom Range", color = Color.White) },
                                    onClick = {
                                        selectedFilterOption = "Custom Range"
                                        showFilterOptions = false
                                        showCustomDateRange = true
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Monthly filter UI
                    if (selectedFilterOption == "Monthly") {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF31434D)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                // Month navigation
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = {
                                            if (selectedMonth == 0) {
                                                selectedMonth = 11
                                                selectedYear--
                                            } else {
                                                selectedMonth--
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowLeft,
                                            contentDescription = "Previous Month",
                                            tint = Color.White
                                        )
                                    }

                                    Text(
                                        text = "${monthNames[selectedMonth]} $selectedYear",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )

                                    IconButton(
                                        onClick = {
                                            if (selectedMonth == 11) {
                                                selectedMonth = 0
                                                selectedYear++
                                            } else {
                                                selectedMonth++
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "Next Month",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Custom date range UI
                    if (showCustomDateRange) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF31434D)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Select Date Range",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Start date selector
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF21353C))
                                        .clickable { showStartDatePicker = true }
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "From: ",
                                        color = Color(0xFFB3C0C7),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = dateFormat.format(Date(startDate)),
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // End date selector
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF21353C))
                                        .clickable { showEndDatePicker = true }
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "To: ",
                                        color = Color(0xFFB3C0C7),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = dateFormat.format(Date(endDate)),
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Balance box
                    BalanceBox(
                        expenses = totalExpense.toString(),
                        income = totalIncome.toString(),
                        balance = balance.toString()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Transactions list text
                    Text(
                        text = "Transactions",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            if (filteredExpenses.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF31434D)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_not_found),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "No transactions found for this period",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            } else {
                items(filteredExpenses.sortedByDescending { it.date }) { item ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
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
                            onDelete = { },
                            onEdit = { }
                        )
                    }
                }
            }

        }

        // DatePicker dialogs
        if (showStartDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showStartDatePicker = false },
                confirmButton = {
                    Button(onClick = { showStartDatePicker = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showStartDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = rememberDatePickerState(initialSelectedDateMillis = startDate),
                    dateValidator = { timestamp ->
                        timestamp <= endDate
                    },
                    onDateSelected = { startDate = it }
                )
            }
        }

        if (showEndDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showEndDatePicker = false },
                confirmButton = {
                    Button(onClick = { showEndDatePicker = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEndDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = rememberDatePickerState(initialSelectedDateMillis = endDate),
                    dateValidator = { timestamp ->
                        timestamp >= startDate
                    },
                    onDateSelected = { endDate = it }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    state: DatePickerState,
    dateValidator: (Long) -> Boolean,
    onDateSelected: (Long) -> Unit
) {
    DatePicker(
        state = state,
        dateValidator = dateValidator
    )

    LaunchedEffect(state.selectedDateMillis) {
        state.selectedDateMillis?.let { onDateSelected(it) }
    }
}
