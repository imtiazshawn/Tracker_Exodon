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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionItem(
    id: Int?,
    title: String,
    amount: String,
    date: String,
    category: String,
    type: String,
    color: Color,
    icon: Int,
    valueType: String
) {
    Row(
        modifier = Modifier
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
            Column(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = Color(0xFF21353C)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = category,
                    fontSize = 8.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "$title", color = Color.White, fontSize = 16.sp)
                Text(text = "$date", color = Color(0xFFCCCCCC), fontSize = 12.sp)
            }
        }
        Text(text = "$valueType $amount", color = color, fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(12.dp))
}