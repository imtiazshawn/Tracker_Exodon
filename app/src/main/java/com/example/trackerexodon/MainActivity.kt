package com.example.trackerexodon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.trackerexodon.navigation.NavHostScreen
import com.example.trackerexodon.ui.theme.TrackerExodonTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackerExodonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF21353C)
                ) {
                    NavHostScreen()
                }
            }
        }
    }
}