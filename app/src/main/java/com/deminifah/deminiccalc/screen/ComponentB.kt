package com.deminifah.deminiccalc.screen

import android.widget.Toast
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.deminifah.deminiccalc.R
import com.deminifah.deminiccalc.model.AppModel
import kotlinx.coroutines.awaitCancellation


//currency converter
@Composable
fun Screen3(modifier: Modifier,model: AppModel){

    Column(modifier = modifier.fillMaxSize().background(color = colorResource(R.color.app_bg)))
    {
        ToolBars(leftIcon = painterResource(R.drawable.round_dehaze_24),
            rightIcon = painterResource(R.drawable.round_history_24),
            headerText = "Currency",model)
        Column(modifier = Modifier.weight(1f,fill = true)) {
            ConverterCard(Modifier.weight(1f).padding(horizontal = 10.dp),model)
            LazyVerticalGrid(columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1.5f, fill = true),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(BtnData2){ index, item->
                    CalcBtn3(type = item.type, index = index, model = model,num = item.num, funcSymbol = item.funcSymbol)
                }
            }
        }
    }
}


@Composable
fun CalcBtn3(type:CalcbtnType,index:Int, num:String = "1", funcSymbol: FuncSymbol = FuncSymbol.Clear,model: AppModel){

    Surface(shape = MaterialTheme.shapes.medium,
        onClick = {model.handleUnitBtnClick2(index)},
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
                CalcbtnType.Number -> {
                    Text(text = num, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                }
                CalcbtnType.Function -> {
                    val painter = when(funcSymbol){
                        FuncSymbol.Clear -> {
                            R.drawable.c}
                        FuncSymbol.Equal -> {
                            R.drawable.equals}
                        FuncSymbol.Multiply -> {
                            R.drawable.multiplys}
                        FuncSymbol.Minus ->{
                            R.drawable.ic_baseline_minus}
                        FuncSymbol.Plus -> {
                            R.drawable.ic_baseline_plus}
                        FuncSymbol.Divide -> {
                            R.drawable.ic_baseline_divide}
                        FuncSymbol.Mod -> R.drawable.iconoir_percentage
                        FuncSymbol.Bracket -> R.drawable.brack
                        FuncSymbol.Del -> {
                            R.drawable.mynaui_delete_solid}
                        FuncSymbol.ArrowUp -> {
                            R.drawable.down}
                        FuncSymbol.ArrowDown -> R.drawable.up
                    }
                    AsyncImage(ImageRequest.Builder(LocalContext.current).data(painter).placeholder(painter) .build(), contentDescription = "")
                }
            }
        }
    }
}
//Unit Converter Component
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConverterCard(modifier: Modifier,model: AppModel){
    val clipboard = LocalClipboardManager.current
    val toast = Toast.makeText(LocalContext.current,"Copied to Clip Tray", Toast.LENGTH_SHORT)
    LaunchedEffect(true) {
        if (model.fromHasFocus2.value){
            model.fromFocus2.requestFocus()
        }
    }
    if (model.showUnitDialog2.value){
        PickDialog2(model) { model.showUnitDialog2.value = false }
    }
    Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 32.dp, color = Color.White, modifier = modifier.fillMaxWidth()){
        Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(text = model._unitFrom2, textAlign = TextAlign.Center, color = colorResource(R.color.measure), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).clickable { model.showUnitDialog2.value=true;model.handleUnitChange2(true) })
                InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() }){
                    val textFieldValue = TextFieldValue(model.fromValue2.value, selection = TextRange(model.fromValue2.value.length, model.fromValue2.value.length) )
                    OutlinedTextField(value = textFieldValue, singleLine = true,trailingIcon = { IconButton(onClick = {clipboard.setText(
                        AnnotatedString(model.fromValue2.value)
                    );toast.show()}){ Icon(painterResource(R.drawable.baseline_content_copy_24),"") }  } ,textStyle = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black), onValueChange = {}, modifier = Modifier.weight(1f).focusRequester(model.fromFocus2).onFocusChanged { model.fromHasFocus2.value = it.isFocused   })
                }
            }
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(0.5f)){
                IconButton(onClick = {model.switchUnit2()}) {
                    AsyncImage(ImageRequest.Builder(LocalContext.current).data(R.drawable.convert).placeholder(R.drawable.convert) .build(), contentDescription = "", modifier = Modifier.requiredSize(32.dp))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(text = model._unitTo2, textAlign = TextAlign.Center, color = colorResource(R.color.measure), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).clickable {model.showUnitDialog2.value = true;model.handleUnitChange2(false)  })
                InterceptPlatformTextInput(interceptor = { _, _ -> awaitCancellation() }){
                    val textFieldValue2 = TextFieldValue(model.toValue2.value, selection = TextRange(model.toValue2.value.length, model.toValue2.value.length) )
                    OutlinedTextField(value = textFieldValue2, singleLine = true,trailingIcon = { IconButton(onClick = {clipboard.setText(
                        AnnotatedString(model.toValue2.value)
                    );toast.show()}){ Icon(painterResource(R.drawable.baseline_content_copy_24),"") } } ,textStyle = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black), onValueChange = {}, modifier = Modifier.weight(1f).focusRequester(model.toFocus2).onFocusChanged { model.toHasFocus2.value = it.isFocused })
                }
            }
        }

    }
}


@Composable
fun PickDialog2(model: AppModel, onDismiss: ()-> Unit){
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium, color = Color.White) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                itemsIndexed(model.currencyListState){index, value ->
                    DialogItem2(value.name,value.code,model,index)
                }
            }
        }
    }
}
@Composable
fun DialogItem2(item:String, label:String, model: AppModel, index: Int){
    Column(verticalArrangement = Arrangement.spacedBy(1.dp), modifier = Modifier.fillMaxSize().padding(16.dp) .clickable { model.showUnitDialog2.value = false;model.handleUniClick2(model.findCurrency(label,index)) }) {
        Text(text = item, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize, color = Color.Black)
        Text(text = label.toUpperCase(Locale.current), fontWeight = MaterialTheme.typography.labelMedium.fontWeight, color = Color.Black ,fontSize = MaterialTheme.typography.labelMedium.fontSize)
        HorizontalDivider()
    }
}