package com.example.expensetracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDatabase
import com.example.trackerexodon.data.dao.ExpenseDao
import com.example.trackerexodon.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import com.example.trackerexodon.R

class HomeViewModel(dao: ExpenseDao) : ViewModel() {
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

    fun getTotalExpense(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.type == "Expense") {
                val amount = it.amount.toDoubleOrNull() ?: 0.0
                total += amount
            }
        }
        return "$total"
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.type == "Income") {
                val amount = it.amount.toDoubleOrNull() ?: 0.0
                total += amount
            }
        }
        return "$total"
    }

    fun getItemIcon(item: ExpenseEntity): Int {
        if (item.category == "Salary") {
            return R.drawable.ic_expenses
        } else if (item.category == "Food") {
            return R.drawable.ic_food
        } else if (item.category == "Rent") {
            return R.drawable.ic_rent
        } else if (item.category == "Travel") {
            return R.drawable.ic_travel
        } else {
            return R.drawable.ic_others
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
