package com.example.valentinesgarage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.valentinesgarage.ui.navigation.AppNavigation
import com.example.valentinesgarage.ui.theme.ValentinesGarageTheme
import com.example.valentinesgarage.ui.utils.SoundManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        SoundManager.playStartup(this)
        setContent {
            ValentinesGarageTheme {
                AppNavigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }
}