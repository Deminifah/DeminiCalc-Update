package com.deminifah.deminiccalc

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.deminifah.deminiccalc.screen.HistoryCompose
import com.deminifah.deminiccalc.screen.HistoryItem
import com.deminifah.deminiccalc.screen.HistorySerializer
import com.deminifah.deminiccalc.screen.Screen1
import com.deminifah.deminiccalc.screen.Screen2
import com.deminifah.deminiccalc.screen.Screen3
import com.deminifah.deminiccalc.screen.Screen4
import com.deminifah.deminiccalc.ui.theme.DeminiCalcTheme
import java.util.Locale


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
                    LaunchedEffect(model.updateHistory.value) {
                        if (model.updateHistory.value){
                            val calendar = Calendar.getInstance()
                            val dateFormat = SimpleDateFormat("MMMM dd", Locale.US)
                            val formattedDate = dateFormat.format(calendar.time)
                            val item = HistoryItem(formattedDate,model.historyLabel.value,model.displayExp.value)
                            model.addHistory(myDatastore,item)
                            model.updateHistory.value = false
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        if(model.showHistoryDialog.value){
                            Dialog(onDismissRequest = {model.showHistoryDialog.value=false}) {
                                LazyColumn(modifier = Modifier.fillMaxWidth() .fillMaxHeight(0.8f).background(color = Color.White), contentPadding = PaddingValues(16.dp)) {
                                    item {
                                        Text("History", fontStyle = FontStyle.Italic, fontWeight = FontWeight.ExtraBold)
                                    }
                                    items(history.history){item->
                                        HistoryCompose(item.date,item.label,item.result)
                                    }
                                }
                            }
                        }
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
                            Box(modifier = Modifier.fillMaxSize().clickable {model.destinationDialog.value=false}){
                            }
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