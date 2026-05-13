package com.example.valentinesgarage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.valentinesgarage.ui.navigation.AppNavigation
import com.example.valentinesgarage.ui.theme.ValentinesGarageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValentinesGarageTheme {
                AppNavigation()
            }
        }
    }
}