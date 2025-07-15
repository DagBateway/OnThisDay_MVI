package com.albertocamillo.onthisdaymvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.albertocamillo.onthisdaymvi.data.db.AppDatabase
import com.albertocamillo.onthisdaymvi.data.db.OnThisDayViewModelFactory
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel
import com.albertocamillo.onthisdaymvi.ui.screen.OnThisDayScreen
import com.albertocamillo.onthisdaymvi.ui.theme.OnThisDayMVITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(applicationContext)
        val factory = OnThisDayViewModelFactory(db.onThisDayDao())

        setContent {
            val viewModel: OnThisDayViewModel = viewModel(factory = factory)
            OnThisDayScreen(viewModel)
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