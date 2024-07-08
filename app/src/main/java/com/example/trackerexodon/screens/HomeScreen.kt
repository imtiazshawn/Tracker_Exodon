package com.example.trackerexodon.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.trackerexodon.components.BalanceBox
import com.example.trackerexodon.components.ExpenseHistory
import com.example.trackerexodon.components.Header
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = Color(0xFF21353C)

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    Scaffold(
        topBar = { Header() },
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
                BalanceBox()
            }

            Spacer(modifier = Modifier.height(28.dp))
            ExpenseHistory()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}