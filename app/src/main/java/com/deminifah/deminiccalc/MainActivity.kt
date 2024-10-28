package com.deminifah.deminiccalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deminifah.deminiccalc.screen.HealthInfoCard
import com.deminifah.deminiccalc.screen.HealthResultCardA
import com.deminifah.deminiccalc.screen.Screen1
import com.deminifah.deminiccalc.screen.UnitConverterCard
import com.deminifah.deminiccalc.ui.theme.DeminiCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeminiCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen1()
                    println(innerPadding)
                }
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




@Preview(showBackground = true, name = "test")
@Composable
fun GreetingPreview() {
    DeminiCalcTheme {
        Box(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray), contentAlignment = Alignment.Center){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                HealthInfoCard(modifier = Modifier.weight(1f, fill = true))
//                HealthInfoCard(modifier = Modifier.weight(1f, fill = true))
//                HealthResultCardA()
                UnitConverterCard()
            }

        }
    }
}