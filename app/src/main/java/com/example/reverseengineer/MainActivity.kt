package com.example.reverseengineer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.reverseengineer.ui.DailymotionScreen
import com.example.reverseengineer.ui.theme.ReverseEngineerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            ReverseEngineerTheme {
                @OptIn(ExperimentalMaterial3Api::class)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DailymotionScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailymotionScreenPreview() {
    ReverseEngineerTheme {
        DailymotionScreen()
    }
}