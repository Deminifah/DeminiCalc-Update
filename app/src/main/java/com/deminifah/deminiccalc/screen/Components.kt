package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deminifah.deminiccalc.R

@Composable
fun ToolBars(leftIcon:Painter, rightIcon:Painter, headerText:String){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
           IconButton(onClick = {}) {
               Icon(painter = leftIcon, contentDescription = "")
           }
            Text(text = headerText)
        }
        IconButton(onClick = { /*TODO*/ }){
            Icon(painter = rightIcon, contentDescription = "")
        }
    }
}


@Composable
fun CalcBtn(type:CalcbtnType, num:String = "1", funcSymbol: FuncSymbol = FuncSymbol.Clear){
    Surface(shape = MaterialTheme.shapes.medium, modifier = Modifier.padding(8.dp)) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(10.dp).requiredSize(32.dp)) {
            when(type){
                CalcbtnType.Number -> {Text(text = num)}
                CalcbtnType.Function -> {
                    val painter = when(funcSymbol){
                        FuncSymbol.Clear -> {R.drawable.clear}
                        FuncSymbol.Equal -> {R.drawable.equal}
                        FuncSymbol.Multiply -> {R.drawable.multiply}
                        FuncSymbol.Minus ->{R.drawable.round_dehaze_24}
                        FuncSymbol.Plus -> {R.drawable.plus}
                        FuncSymbol.Divide -> {R.drawable.divide}
                        FuncSymbol.Mod -> R.drawable.mod
                        FuncSymbol.Bracket -> R.drawable.bracket
                        FuncSymbol.Del -> {R.drawable.round_dehaze_24}
                    }
                    Icon(painter = painterResource(id = painter), contentDescription = "")
                }
            }
        }
    }
}
enum class CalcbtnType{
    Number,
    Function
}
enum class FuncSymbol{
    Clear,
    Equal,
    Multiply,
    Minus,
    Plus,
    Divide,
    Mod,
    Bracket,
    Del
}

@Composable
fun  CalcScreen(modifier:Modifier){
    Surface(shape = MaterialTheme.shapes.medium, modifier = modifier, color = Color.White) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 24.dp)) {
            CustomCalcText(Modifier.padding(16.dp))
        }
    }
}

@Composable
fun CustomCalcText(modifier: Modifier){
    BasicTextField(value = "", onValueChange = {},modifier)
}


data class BtnData(val type:CalcbtnType, var num:String = "1", var funcSymbol: FuncSymbol = FuncSymbol.Clear)





val CalcBtnData = listOf(BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Clear),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Bracket),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Mod),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Divide),
    BtnData(type = CalcbtnType.Number, num = "7"),
    BtnData(type = CalcbtnType.Number, num = "8"),
    BtnData(type = CalcbtnType.Number, num = "9"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Multiply),
    BtnData(type = CalcbtnType.Number, num = "4"),
    BtnData(type = CalcbtnType.Number, num = "5"),
    BtnData(type = CalcbtnType.Number, num = "6"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Minus),
    BtnData(type = CalcbtnType.Number, num = "1"),
    BtnData(type = CalcbtnType.Number, num = "2"),
    BtnData(type = CalcbtnType.Number, num = "3"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Plus),
    BtnData(type = CalcbtnType.Number, num = "0"),
    BtnData(type = CalcbtnType.Number, num = "."),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Del),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Equal)
    )




//Health Screen  Component

@Composable
fun HealthInfoCard(title:String = "Age", value:String = "43",modifier:Modifier = Modifier){
    Surface(onClick = {}, shape = MaterialTheme.shapes.medium, tonalElevation = 32.dp, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = title, fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize )

            Text(text = value,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                fontSize = MaterialTheme.typography.bodySmall.fontSize)
        }
    }

}


@Composable
fun HealthResultCardA(title: String = "Body Mass Index",result:String = "Result", comments:String = "Comments"){
    Surface(shape = MaterialTheme.shapes.medium){
        Column (verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(4.dp, 16.dp)){
            Text(text = title, fontWeight = FontWeight.Medium, fontSize = MaterialTheme.typography.labelLarge.fontSize)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = result, fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                Text(":",fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                Text(text = comments,fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            }

        }
    }
}
@Composable
fun HealthResultCardB(){}


//Unit Converter Component


@Composable
fun UnitConverterCard(){
    Surface(shape = MaterialTheme.shapes.medium, tonalElevation = 32.dp, modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.padding(16.dp,24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Unit Converter", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.weight(1f))
            }
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(0.5f)){
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.round_compare_arrows_24),
                        modifier = Modifier.rotate(90f),
                        contentDescription = "")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Unit Converter", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.weight(1f))
            }
        }

    }
}