package com.example.trackerexodon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.trackerexodon.R

@Composable
fun BalanceBox() {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(color = Color(0xFF31434D))
            .padding(vertical = 20.dp, horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(shape = RoundedCornerShape(42.dp))
                    .background(color = Color(0xFF21353C))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_weight_care),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center).size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Total Balance", color = Color.White, fontSize = 16.sp)
                Text(text = "1,00,980 BDT", color = Color.White, fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(shape = RoundedCornerShape(42.dp))
                        .background(color = Color(0xFF21353C))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_weight_care),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Income", color = Color.White, fontSize = 16.sp)
                    Text(text = "92,800 BDT", color = Color.White, fontSize = 16.sp)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(shape = RoundedCornerShape(42.dp))
                        .background(color = Color(0xFF21353C))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_weight_care),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center).size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Income", color = Color.White, fontSize = 16.sp)
                    Text(text = "92,800 BDT", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}