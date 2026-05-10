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


//package com.example.valentinesgarage
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import com.example.valentinesgarage.ui.navigation.AppNavigation
//import com.example.valentinesgarage.ui.theme.ValentinesGarageTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ValentinesGarageTheme {
//                AppNavigation()
//            }
//        }
//    }
//}


//package com.example.valentinesgarage
//
//import android.content.Context
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import androidx.appcompat.app.AppCompatActivity
//import androidx.navigation.fragment.NavHostFragment
//import androidx.navigation.ui.setupActionBarWithNavController
//import com.example.valentinesgarage.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navHost = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        setupActionBarWithNavController(navHost.navController)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_logout -> {
//                // Clear saved user session
//                getSharedPreferences("garage_prefs", Context.MODE_PRIVATE)
//                    .edit().clear().apply()
//
//                // Navigate back to login
//                val navHost = supportFragmentManager
//                    .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//                navHost.navController.navigate(R.id.loginFragment)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navHost = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
//    }
//}