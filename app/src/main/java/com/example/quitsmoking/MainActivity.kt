package com.example.quitsmoking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quitsmoking.ui.QuitScreen
import com.example.quitsmoking.ui.QuitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val vm: QuitViewModel = viewModel()
                QuitScreen(vm)
            }
        }
    }
}
