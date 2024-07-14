package com.example.trackerexodon.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Header(backButton: Boolean, about: Boolean, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (backButton) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Tracker",
                color = Color(0xFF3FDB9D),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Exodon",
                color = Color(0xFFFFFFFF),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        if (about) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = null,
                tint = Color(0xFFFFFFFF),
                modifier = Modifier.size(24.dp).clickable {
                    navController.navigate("HOME_SCREEN")
                }
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = Color(0xFFFFFFFF),
                modifier = Modifier.size(24.dp).clickable {
                    navController.navigate("ABOUT_SCREEN")
                }
            )
        }
    }
}