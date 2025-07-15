package com.albertocamillo.onthisdaymvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.albertocamillo.onthisdaymvi.data.db.AppDatabase
import com.albertocamillo.onthisdaymvi.data.db.OnThisDayViewModelFactory
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel
import com.albertocamillo.onthisdaymvi.ui.screen.OnThisDayScreen
import com.albertocamillo.onthisdaymvi.ui.theme.OnThisDayMVITheme

// ============================
// MainActivity.kt
// ============================
/**
 * Entry point for the application. Sets up the ViewModel using a factory,
 * provides the Room database instance, and launches the Compose UI with [OnThisDayScreen].
 *
 * Responsible for wiring the app together without DI frameworks like Hilt.
 */


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set status bar background to black and icons to light
        window.statusBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        val db = AppDatabase.getDatabase(applicationContext)
        val factory = OnThisDayViewModelFactory(db.onThisDayDao())

        setContent {
            OnThisDayMVITheme {
                val view = LocalView.current
                val window = (view.context as ComponentActivity).window

                SideEffect {
                    // Let the system know we want dark icons (i.e. black on light background)
                    WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = true
                }

                val viewModel: OnThisDayViewModel = viewModel(factory = factory)
                OnThisDayScreen(viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OnThisDayMVITheme {
        Greeting("Android")
    }
}