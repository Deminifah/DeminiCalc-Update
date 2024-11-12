package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.core.Serializer
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.deminifah.deminiccalc.R
import com.deminifah.deminiccalc.ScreenState
import com.deminifah.deminiccalc.model.AppModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


enum class Trig{
    Deg,
    Rad
}

//calculator screen
@Composable
fun Screen1(modifier: Modifier,model: AppModel){
    Column(modifier = modifier.fillMaxSize().background(color = colorResource(R.color.app_bg)))
    {
        ToolBars(leftIcon = painterResource(R.drawable.round_dehaze_24),
            rightIcon = painterResource(R.drawable.round_history_24),
            headerText = "Sci-Calc",model)
        Column(modifier = Modifier.weight(1f,fill = true)) {
            CustomCalcText(Modifier.weight(1f, fill = true).background(color = Color.White).fillMaxWidth(),model.displayExp)
            LazyRow(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically ,horizontalArrangement = Arrangement.spacedBy(16.dp),contentPadding = PaddingValues(16.dp)) {
                item {
                    IconButton(onClick = {model.displayExp.value="";model.exp.value=""}) {
                        Icon(painter = painterResource(R.drawable.c),contentDescription = "",
                            tint = Color.Red)
                    }
                }
                item {
                    val options = listOf(Trig.Deg,Trig.Rad)

                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier,
                                border = BorderStroke(0.dp,Color.Transparent),
                                colors = SegmentedButtonDefaults.colors(activeContainerColor = colorResource(R.color.func_btn), inactiveContentColor = colorResource(R.color.func_btn), inactiveContainerColor = Color.Transparent, activeContentColor = Color.White),
                                onClick = { model.selectedIndex.intValue = index;model.trig.value=options[index] },
                                selected = index == model.selectedIndex.intValue
                            ) {
                                Text(label.name)
                            }
                        }
                    }
                }
                items(specialFunc){item->
                    Box(modifier = Modifier.clickable { model.specialFunc(item) } .background(color = colorResource(R.color.app_bg))){
                        Text(item, color = Color.White, fontStyle = FontStyle.Italic, fontWeight = FontWeight.ExtraBold)
                    }
                }

            }
            LazyVerticalGrid(columns = GridCells.Fixed(4),
                modifier = Modifier.weight(2f, fill = true),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)) {
                itemsIndexed(CalcBtnData){index,item->
                    CalcBtn(type = item.type, model = model, index = index,num = item.num, funcSymbol = item.funcSymbol)
                }
            }
        }
    }
}



@Composable
fun HistoryCompose(date: String,label: String,result: String){
    Surface(color = Color.White) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(date, color = Color.Black, fontSize = MaterialTheme.typography.titleMedium.fontSize, fontWeight = FontWeight.SemiBold)
            SelectionContainer {
                Column(horizontalAlignment = Alignment.End) {
                    Text(label,color = Color.Black, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.ExtraBold)
                    Text(result,color = Color.Black, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.ExtraBold)
                }
            }
            HorizontalDivider()
        }
    }
}

val specialFunc = listOf(
    "√",
    "e",
    "π",
    "sin",
    "cos",
    "tan",
    "asin",
    "acos",
    "atan",
    "ln",
    "log10",
    "sec",
    "cot"
    )


@Serializable
data class History(val history:List<HistoryItem> = emptyList())


@Serializable
data class HistoryItem(val date:String,val label:String,val result:String)

object HistorySerializer:Serializer<History>{
    override val defaultValue: History
        get() = History()

    override suspend fun readFrom(input: InputStream): History {
        return try {
            Json.decodeFromString(
                deserializer = History.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e:Exception){
            defaultValue
        }
    }

    override suspend fun writeTo(t: History, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = History.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}

@Composable
fun AppDestinationCard(modifier: Modifier,model: AppModel){
    Surface(shadowElevation = 8.dp,tonalElevation = 32.dp,color = Color.White,modifier = modifier.fillMaxWidth(0.7f),shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 16.dp, bottomStart = 16.dp)) {
        Column {
            Row (modifier = Modifier.clickable {model.navigate(ScreenState.SciCalc) } ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)){
                IconButton(onClick = {model.navigate(ScreenState.SciCalc)}) {
                    Icon(painterResource(R.drawable.sci),"", tint = colorResource(R.color.measure))
                }
                Text("Scientific", color = colorResource(R.color.measure))
            }
            Row (modifier = Modifier.fillMaxWidth() .clickable {model.navigate(ScreenState.UnitConverter) },verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)){
                IconButton(onClick = {model.navigate(ScreenState.UnitConverter)}) {
                    Icon(painterResource(R.drawable.round_compare_arrows_24),"", tint = colorResource(R.color.measure))
                }
                Text("Unit Converter", color = colorResource(R.color.measure))
            }
            Row (modifier = Modifier.fillMaxWidth() .clickable {model.navigate(ScreenState.Currency) },verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)){
                IconButton(onClick = {model.navigate(ScreenState.Currency)}) {
                    AsyncImage(ImageRequest.Builder(LocalContext.current).data(R.drawable.currency).build(),"")
                }
                Text("Currency", color = colorResource(R.color.measure))
            }
            Row (modifier = Modifier.fillMaxWidth() .clickable {model.navigate(ScreenState.Health) },verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)){
                IconButton(onClick = {model.navigate(ScreenState.Health)}) {
                    Icon(painterResource(R.drawable.health),"", tint = colorResource(R.color.measure))
                }
                Text("Health", color = colorResource(R.color.measure))
            }
            }
    }
}
