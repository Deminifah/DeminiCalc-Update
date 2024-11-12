package com.deminifah.deminiccalc.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.deminifah.deminiccalc.R
import com.deminifah.deminiccalc.model.AppModel
import com.deminifah.deminiccalc.utils.Area
import com.deminifah.deminiccalc.utils.Length
import com.deminifah.deminiccalc.utils.Mass
import com.deminifah.deminiccalc.utils.Temperature
import com.deminifah.deminiccalc.utils.UnitType
import com.deminifah.deminiccalc.utils.Units
import com.deminifah.deminiccalc.utils.Volume
import kotlinx.coroutines.awaitCancellation

@Composable
fun ToolBars(leftIcon:Painter, rightIcon:Painter, headerText:String,model: AppModel){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
           IconButton(onClick = {model.destinationDialog.value=true}) {
               Icon(painter = leftIcon, contentDescription = "", tint = Color.White)
           }
            Text(text = headerText, color = Color.White, fontWeight = FontWeight.Bold)
        }
        IconButton(onClick = { model.showHistoryDialog.value=true }){
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
                CalcbtnType.Number -> {Text(text = num, color = Color.Black, fontWeight = FontWeight.Bold)}
                CalcbtnType.Function -> {
                    if(index == 0){
                        Text(text = "(", color = Color.White, fontWeight = FontWeight.ExtraBold)
                    }
                    else if(index == 1){
                        Text(text = ")", color = Color.White, fontWeight = FontWeight.ExtraBold)
                    }
                    else{
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



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomCalcText(modifier: Modifier, state:MutableState<String>,pad:Int=0){
    val textvalue = TextFieldValue(text = state.value, TextRange(state.value.length, state.value.length))
    val clipboard = LocalClipboardManager.current
    val toast = Toast.makeText(LocalContext.current,"Copied to Clip Tray",Toast.LENGTH_SHORT)

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }
    InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() })
    {
        Row(modifier = modifier,horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            BasicTextField(value = textvalue,
                textStyle = TextStyle(
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                cursorBrush = SolidColor(Color.DarkGray),
                modifier = Modifier.weight(1f).fillMaxHeight().focusRequester(focusRequester).padding(pad.dp),
                onValueChange = {})
            IconButton(onClick = {clipboard.setText(AnnotatedString(state.value));toast.show()}) {
                Icon(painterResource(R.drawable.baseline_content_copy_24),"", tint = Color.DarkGray)
            }
        }
//        TextField(value = textvalue,
//            colors = TextFieldDefaults.colors(cursorColor = Color.DarkGray, focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
//            modifier = modifier.focusRequester(focusRequester),
//            onValueChange = {})
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

data class BtnData(val type:CalcbtnType, var num:String = "", var funcSymbol: FuncSymbol = FuncSymbol.Clear)

val CalcBtnData = listOf(BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Bracket),
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
fun HealthInfoCard(modifier:Modifier = Modifier,title:String = "Age", value:String = "43",onClick:()->Unit){
    Surface(onClick = onClick, color = Color.White, shape = MaterialTheme.shapes.medium, tonalElevation = 32.dp, modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(14.dp)) {
            Text(text = title, fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize )

            Text(text = value,
                color = Color.Gray,

                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodySmall.fontSize)
        }
    }

}
@Composable
fun HealthResultCardA(title: String = "Body Mass Index",result:String = "Result", comments:String = "Comments"){
    Surface(shape = MaterialTheme.shapes.medium, color = Color.White){
        Column (verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp, 10.dp)){
            Text(text = title, fontWeight = FontWeight.ExtraBold, fontSize = MaterialTheme.typography.bodyLarge.fontSize, color = Color.Black)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = result, fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize,fontStyle = FontStyle.Italic, color = colorResource(R.color.measure))
                Text(text = comments,fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize, fontStyle = FontStyle.Italic, color = colorResource(R.color.measure))

            }

        }
    }
}







//Unit Converter Component
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterCard(modifier: Modifier,model: AppModel){
    val clipboard = LocalClipboardManager.current
    val toast = Toast.makeText(LocalContext.current,"Copied to Clip Tray",Toast.LENGTH_SHORT)
    LaunchedEffect(true) {
        if (model.fromHasFocus.value){
            model.fromFocus.requestFocus()
        }
    }
    if (model.showUnitDialog.value){
        PickDialog(model) { model.showUnitDialog.value = false }
    }
    Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 32.dp, color = Color.White, modifier = modifier.fillMaxWidth()){
        Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(text = model._unitFrom, color = colorResource(R.color.measure), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).clickable { model.showUnitDialog.value=true;model.handleUnitChange(true) })
                 InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() }){
                     val textFieldValue = TextFieldValue(model.fromValue.value, selection =TextRange(model.fromValue.value.length, model.fromValue.value.length) )
                     OutlinedTextField(value = textFieldValue, singleLine = true,trailingIcon = { IconButton(onClick = {clipboard.setText(AnnotatedString(model.fromValue.value));toast.show()}){ Icon(painterResource(R.drawable.baseline_content_copy_24),"") }  } ,textStyle = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black), onValueChange = {}, modifier = Modifier.weight(1f).focusRequester(model.fromFocus).onFocusChanged { model.fromHasFocus.value = it.isFocused   })
                }
            }
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(0.5f)){
                IconButton(onClick = {model.switchUnit()}) {
                    AsyncImage(ImageRequest.Builder(LocalContext.current).data(R.drawable.convert).placeholder(R.drawable.convert) .build(), contentDescription = "", modifier = Modifier.requiredSize(32.dp))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(text = model._unitTo, color = colorResource(R.color.measure), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).clickable {model.showUnitDialog.value = true;model.handleUnitChange(false)  })
                InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() }){
                    val textFieldValue2 = TextFieldValue(model.toValue.value, selection =TextRange(model.toValue.value.length, model.toValue.value.length) )
                    OutlinedTextField(value = textFieldValue2, singleLine = true,trailingIcon = { IconButton(onClick = {clipboard.setText(AnnotatedString(model.toValue.value));toast.show()}){ Icon(painterResource(R.drawable.baseline_content_copy_24),"") } } ,textStyle = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black), onValueChange = {}, modifier = Modifier.weight(1f).focusRequester(model.toFocus).onFocusChanged { model.toHasFocus.value = it.isFocused })
                }
            }
        }

    }
}
@Composable
fun CalcBtn2(type:CalcbtnType,index:Int, num:String = "1", funcSymbol: FuncSymbol = FuncSymbol.Clear,model: AppModel){

    Surface(shape = MaterialTheme.shapes.medium,
        onClick = {model.handleUnitBtnClick(index)},
        modifier = Modifier.padding(8.dp), color = when(type)
        {
            CalcbtnType.Number -> {
                Color.White
            }
            CalcbtnType.Function -> {
                colorResource(R.color.func_btn)
            }
        })
    {
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .padding(10.dp)
            .requiredSize(32.dp)) {
            when(type){
                CalcbtnType.Number -> {Text(text = num, fontWeight = FontWeight.ExtraBold, color = Color.Black)}
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
val BtnData2 = listOf(
    BtnData(type = CalcbtnType.Number, num = "7"),
    BtnData(type = CalcbtnType.Number, num = "8"),
    BtnData(type = CalcbtnType.Number, num = "9"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Clear),
    BtnData(type = CalcbtnType.Number, num = "4"),
    BtnData(type = CalcbtnType.Number, num = "5"),
    BtnData(type = CalcbtnType.Number, num = "6"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.ArrowUp),
    BtnData(type = CalcbtnType.Number, num = "1"),
    BtnData(type = CalcbtnType.Number, num = "2"),
    BtnData(type = CalcbtnType.Number, num = "3"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.ArrowDown),
    BtnData(type = CalcbtnType.Number, num = "0"),
    BtnData(type = CalcbtnType.Number, num = "."),
    BtnData(type = CalcbtnType.Number, num = "00"),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Del)
)


// other components


@Composable
fun VisibleBtn(options:List<String>,model: AppModel) {
    var selectedIndex by remember { mutableStateOf(0) }
    SingleChoiceSegmentedButtonRow (modifier = Modifier.requiredWidth((108 * 5).dp), space = 0.dp){
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier,
                border = BorderStroke(0.dp,Color.Transparent),
                colors = SegmentedButtonDefaults.colors(activeContainerColor = colorResource(R.color.func_btn), inactiveContentColor = colorResource(R.color.func_btn), inactiveContainerColor = Color.Transparent, activeContentColor = Color.White),
                onClick = { selectedIndex = index ;model.changeMeasurementUnit(index)},
                selected = index == selectedIndex
            ) {
                Text(label)
            }
        }
    }
}


@Composable
fun PickDialog(model: AppModel, onDismiss: ()-> Unit){
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium, color = Color.White) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(model.getCurrentUnits()){
                    DialogItem(it.item,it.label,model)
                }
            }
        }
    }
}
@Composable
fun DialogItem(item:String, label:String, model: AppModel){
    Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.clickable { model.showUnitDialog.value = false;model.handleUniClick(
        pickUnit(item,model.measurement[model.currentMeasurement.intValue])
    ) }) {
        Text(text = item, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize, color = Color.Black)
        Text(text = label, color = Color.Black ,fontWeight = MaterialTheme.typography.labelMedium.fontWeight, fontSize = MaterialTheme.typography.labelMedium.fontSize)
    }
}

data class DialogData(val item:String, val label:String)



fun pickUnit(unit:String,type:UnitType):Units{
    return when(type){
        UnitType.Area->{
            when(unit){
                "SquareMeter"->{Area.SquareMeter}
                "SquareKilometer"->{Area.SquareKilometer}
                "SquareFoot" ->{Area.SquareFoot}
                "SquareMile"->{Area.SquareMile}
                "SquareYard"->{Area.SquareYard}
                "Acre"->{Area.Acre}
                "Hectare"->{Area.Hectare}
                else ->{Area.SquareMeter}
            }
        }
        UnitType.Length->{
            when(unit){
                "Meter"->{ Length.Meter}
                "Kilometer"->{Length.Kilometer}
                "Centimeter"->{Length.Centimeter}
                "Millimeter"->{Length.Millimeter}
                "Inch"->{Length.Inch}
                "Foot"->{Length.Foot}
                "Yard"->{Length.Yard}
                "Mile"->{Length.Mile}
                "Nautical"->{Length.Nautical}
                "Nanometer"->{Length.Nanometer}
                "Micrometer"->{Length.Micrometer}
                else ->{Length.Meter}
            }
        }
        UnitType.Temperature->{
            when(unit) {
                "Celcius" -> {
                    Temperature.Celcius
                }

                "Fahrenheit" -> {
                    Temperature.Fahrenheit
                }

                "Kelvin" -> {
                    Temperature.Kelvin
                }

                else -> {
                    Temperature.Celcius
                }
            }
        }
        UnitType.Volume->{
            when(unit){
                "CubicMeter"->{Volume.CubicMeter}
                "CubicCentimeter"->{Volume.CubicCentimeter}
                "Liter"->{Volume.Liter}
                "Milliliter"->{Volume.Milliliter}
                "CubicFoot"->{Volume.CubicFoot}
                "CubicInch"->{Volume.CubicInch}
                "Gallon"->{Volume.Gallon}
                "Quart"->{Volume.Quart}
                "Pint"->{Volume.Pint}
                "Ounce"->{Volume.OunceV}
                else ->{Volume.CubicMeter}
            }
        }
        UnitType.Mass->{
            when(unit){
                "Kilogram"->{Mass.Kilogram}
                "Gram"->{Mass.Gram}
                "Milligram"->{Mass.Milligram}
                "Pound"->{Mass.Pound}
                "Ounce"->{Mass.Ounce}
                "Ton"->{Mass.Ton}
                else ->{Mass.Kilogram}
            }
        }
    }
}