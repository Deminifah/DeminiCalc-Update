package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.deminifah.deminiccalc.R
import com.deminifah.deminiccalc.model.AppModel
import kotlinx.coroutines.awaitCancellation

@Composable
fun ToolBars(leftIcon:Painter, rightIcon:Painter, headerText:String){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
           IconButton(onClick = {}) {
               Icon(painter = leftIcon, contentDescription = "", tint = Color.White)
           }
            Text(text = headerText, color = Color.White, fontWeight = FontWeight.Bold)
        }
        IconButton(onClick = { /*TODO*/ }){
            Icon(painter = rightIcon, contentDescription = "", tint = Color.White)
        }
    }
}
@Composable
fun CalcBtn(type:CalcbtnType,model:AppModel,index:Int, num:String = "1", funcSymbol: FuncSymbol = FuncSymbol.Clear){

    Surface(shape = MaterialTheme.shapes.medium,
        onClick = {model.handleCLick(index)},
        modifier = Modifier.padding(8.dp), color = when(type){
        CalcbtnType.Number -> {
            Color.White
        }
        CalcbtnType.Function -> {
            if (funcSymbol == FuncSymbol.Del){
                Color.White
            }else if (funcSymbol == FuncSymbol.Equal){
                colorResource(R.color.equal)
            }
            else{
                colorResource(R.color.func_btn)
            }
        }
    })
    {
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(10.dp)
            .requiredSize(32.dp)) {
            when(type){
                CalcbtnType.Number -> {Text(text = num)}
                CalcbtnType.Function -> {
                    val painter = when(funcSymbol){
                        FuncSymbol.Clear -> {R.drawable.c}
                        FuncSymbol.Equal -> {R.drawable.equals}
                        FuncSymbol.Multiply -> {R.drawable.multiplys}
                        FuncSymbol.Minus ->{R.drawable.ic_baseline_minus}
                        FuncSymbol.Plus -> {R.drawable.ic_baseline_plus}
                        FuncSymbol.Divide -> {R.drawable.ic_baseline_divide}
                        FuncSymbol.Mod -> R.drawable.iconoir_percentage
                        FuncSymbol.Bracket -> R.drawable.brack
                        FuncSymbol.Del -> {R.drawable.mynaui_delete_solid}
                        FuncSymbol.ArrowUp -> {R.drawable.down}
                        FuncSymbol.ArrowDown -> R.drawable.up
                    }
                    AsyncImage(ImageRequest.Builder(LocalContext.current).data(painter).placeholder(painter) .build(), contentDescription = "")
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
    Del,
    ArrowUp,
    ArrowDown
}

@Composable
fun  CalcScreen(modifier:Modifier){
    Surface(modifier = modifier, color = Color.White) {
//        CustomCalcText(Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomCalcText(modifier: Modifier, state:MutableState<String>){
    val textvalue = TextFieldValue(text = state.value, TextRange(state.value.length, state.value.length))

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }
    InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() })
    {

        TextField(value = textvalue,
            textStyle = TextStyle(
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            ),
            colors = TextFieldDefaults.colors(cursorColor = Color.DarkGray, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
            modifier = modifier.focusRequester(focusRequester),
            onValueChange = {})
    }

//    BasicTextField(
//        value = state.value,
//        readOnly = true,
//        modifier = modifier.focusRequester(focusRequester)
//            .onFocusChanged { keyboardController?.hide(); },
//        textStyle = TextStyle(
//            color = Color.DarkGray,
//            fontWeight = FontWeight.Bold,
//            fontSize = 32.sp
//        ),
//        cursorBrush = SolidColor(Color.DarkGray),
//        onValueChange = {},
//    )

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
@Composable
fun CalcBtn2(type:CalcbtnType,index:Int, num:String = "1", funcSymbol: FuncSymbol = FuncSymbol.Clear){

    Surface(shape = MaterialTheme.shapes.medium,
        onClick = {},
        modifier = Modifier.padding(8.dp), color = when(type){
            CalcbtnType.Number -> {
                Color.White
            }
            CalcbtnType.Function -> {
                if (funcSymbol == FuncSymbol.Del){
                    Color.White
                }else if (funcSymbol == FuncSymbol.Equal){
                    colorResource(R.color.equal)
                }
                else{
                    colorResource(R.color.func_btn)
                }
            }
        })
    {
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(10.dp)
            .requiredSize(32.dp)) {
            when(type){
                CalcbtnType.Number -> {Text(text = num)}
                CalcbtnType.Function -> {
                    val painter = when(funcSymbol){
                        FuncSymbol.Clear -> {R.drawable.c}
                        FuncSymbol.Equal -> {R.drawable.equals}
                        FuncSymbol.Multiply -> {R.drawable.multiplys}
                        FuncSymbol.Minus ->{R.drawable.ic_baseline_minus}
                        FuncSymbol.Plus -> {R.drawable.ic_baseline_plus}
                        FuncSymbol.Divide -> {R.drawable.ic_baseline_divide}
                        FuncSymbol.Mod -> R.drawable.iconoir_percentage
                        FuncSymbol.Bracket -> R.drawable.brack
                        FuncSymbol.Del -> {R.drawable.mynaui_delete_solid}
                        FuncSymbol.ArrowUp -> {R.drawable.down}
                        FuncSymbol.ArrowDown -> R.drawable.up
                    }
                    AsyncImage(ImageRequest.Builder(LocalContext.current).data(painter).placeholder(painter) .build(), contentDescription = "")
                }
            }
        }
    }
}



