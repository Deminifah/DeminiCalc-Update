package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
    Column {
}
f
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

