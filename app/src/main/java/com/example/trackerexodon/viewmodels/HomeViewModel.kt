package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.ExpenseDatabase
import com.example.trackerexodon.data.dao.ExpenseDao
import com.example.trackerexodon.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import com.example.trackerexodon.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val dao: ExpenseDao) : ViewModel() {

    val expenses: Flow<List<ExpenseEntity>> = dao.getAllExpense()

    fun getBalance(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            val amount = it.amount.toDoubleOrNull() ?: 0.0
            if (it.type == "Income") {
                total += amount
            } else {
                total -= amount
            }
        }
        return "$total"
    }

    fun getTotalExpense(list: List<ExpenseEntity>): Double {
        var total = 0.0
        list.forEach {
            if (it.type == "Expense") {
                val amount = it.amount.toDoubleOrNull() ?: 0.0
                total += amount
            }
        }
        return total
    }

    fun getTotalIncome(list: List<ExpenseEntity>): Double {
        var total = 0.0
        list.forEach {
            if (it.type == "Income") {
                val amount = it.amount.toDoubleOrNull() ?: 0.0
                total += amount
            }
        }
        return total
    }

    private fun getCategoryExpense(list: List<ExpenseEntity>, category: String): Double {
        var total = 0.0
        list.forEach {
            if (it.type == "Expense" && it.category == category) {
                val amount = it.amount.toDoubleOrNull() ?: 0.0
                total += amount
            }
        }
        return total
    }

    fun getCategoryPercentages(list: List<ExpenseEntity>): Map<String, Float> {
        val totalExpense = getTotalExpense(list)
        if (totalExpense == 0.0) return mapOf()

        val rentExpense = getCategoryExpense(list, "Rent")
        val foodExpense = getCategoryExpense(list, "Food")
        val clothesExpense = getCategoryExpense(list, "Clothes")
        val travelExpense = getCategoryExpense(list, "Travel")
        val accessoriesExpense = getCategoryExpense(list, "Accessories")
        val gadgetsExpense = getCategoryExpense(list, "Gadgets")
        val educationExpense = getCategoryExpense(list, "Education")
        val familyExpense = getCategoryExpense(list, "Family")
        val donationsExpense = getCategoryExpense(list, "Donations")
        val othersExpense = getCategoryExpense(list, "Others")

        return mapOf(
            "Rent" to (rentExpense / totalExpense).toFloat(),
            "Food" to (foodExpense / totalExpense).toFloat(),
            "Clothes" to (clothesExpense / totalExpense).toFloat(),
            "Travel" to (travelExpense / totalExpense).toFloat(),
            "Accessories" to (accessoriesExpense / totalExpense).toFloat(),
            "Gadgets" to (gadgetsExpense / totalExpense).toFloat(),
            "Education" to (educationExpense / totalExpense).toFloat(),
            "Family" to (familyExpense / totalExpense).toFloat(),
            "Donations" to (donationsExpense / totalExpense).toFloat(),
            "Others" to (othersExpense / totalExpense).toFloat()
        )
    }

    fun getItemIcon(item: ExpenseEntity): Int {
        return when (item.category) {
            "Salary" -> R.drawable.ic_expenses
            "Food" -> R.drawable.ic_food
            "Clothes" -> R.drawable.ic_clothes
            "Education" -> R.drawable.ic_books
            "Rent" -> R.drawable.ic_rent
            "Travel" -> R.drawable.ic_travel
            "Accessories" -> R.drawable.ic_accessories
            "Gadgets" -> R.drawable.ic_gadget
            "Family" -> R.drawable.ic_family
            "Donations" -> R.drawable.ic_donations
            else -> R.drawable.ic_others
        }
    }

    fun updateExpense(expenseEntity: ExpenseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateExpense(expenseEntity)
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val expense = dao.getExpenseById(expenseId)
            if (expense != null) {
                dao.deleteExpense(expense)
            }
        }
    }
}

class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
