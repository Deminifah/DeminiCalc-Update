package com.deminifah.deminiccalc

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deminifah.deminiccalc.model.AppModel
import com.deminifah.deminiccalc.screen.AppDestinationCard
import com.deminifah.deminiccalc.screen.History
import com.deminifah.deminiccalc.screen.HistorySerializer
import com.deminifah.deminiccalc.screen.Screen1
import com.deminifah.deminiccalc.screen.Screen2
import com.deminifah.deminiccalc.screen.Screen3
import com.deminifah.deminiccalc.screen.Screen4
import com.deminifah.deminiccalc.ui.theme.DeminiCalcTheme


val Context.myDatastore by dataStore("history.json", HistorySerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // At the top level of your kotlin file:
        enableEdgeToEdge()
        setContent {

            DeminiCalcTheme {
                val history = myDatastore.data.collectAsState(History()).value
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val model:AppModel = viewModel()
                    BackHandler(enabled = model.backEnabled.value) {model.goBack()}
                    Box(modifier = Modifier.fillMaxSize()) {
                        if(model.calculationError.value){
                            Dialog(onDismissRequest = {model.calculationError.value=false}) {
                                Surface(shape = MaterialTheme.shapes.medium) {
                                    Text(model.calculationErrMsg.value, color = Color.Red, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, modifier = Modifier.padding(32.dp))
                                }
                            }
                        }
                        when(model.appScreenState.value){
                            ScreenState.SciCalc -> Screen1(Modifier.padding(innerPadding),model)
                            ScreenState.UnitConverter -> Screen2(Modifier.padding(innerPadding),model)
                            ScreenState.Currency -> Screen3(Modifier.padding(innerPadding),model)
                            ScreenState.Health -> Screen4(Modifier.padding(innerPadding),model)

                        }
                        if (model.destinationDialog.value){
                            AppDestinationCard(Modifier.padding(innerPadding),model)
                        }
                    }
                }
            }
        }
    }
}

enum class ScreenState{
    SciCalc,
    UnitConverter,
    Currency,
    Health
}


@Preview(showBackground = true, name = "test")
@Composable
fun GreetingPreview() {

    DeminiCalcTheme {

        //Test()
       //Screen2()
        Box(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray), contentAlignment = Alignment.Center){
            //HealthDialog2()
        }
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
////                HealthInfoCard(modifier = Modifier.weight(1f, fill = true))
////                HealthInfoCard(modifier = Modifier.weight(1f, fill = true))
////                HealthResultCardA()
////                UnitConverterCard()
//
    }
}