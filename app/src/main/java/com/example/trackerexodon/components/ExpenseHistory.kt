package com.example.trackerexodon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackerexodon.R
import com.example.trackerexodon.data.model.ExpenseEntity

@Composable
fun ExpenseHistory(list: List<ExpenseEntity>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Expenses History",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(list) { item ->
                TransactionItem(
                    id = item.id,
                    title = item.title,
                    amount = item.amount.toString(),
                    date = item.date.toString(),
                    category = item.category,
                    type = item.type,
                    color = if (item.type == "Income") Color(0xFF3FDB9D) else Color(0xFFFC575D)
                )
            }
        }
    }
}

@Composable
fun TransactionItem(
    id: Int?,
    title: String,
    amount: String,
    date: String,
    category: String,
    type: String,
    color: Color
) {
    Row(modifier = Modifier
        .clip(shape = RoundedCornerShape(12.dp))
        .background(color = Color(0xFF31434D))
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_expenses),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$title", color = Color.White, fontSize = 16.sp )
        }
        Text(text = "+ $amount", color = color, fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))
}
