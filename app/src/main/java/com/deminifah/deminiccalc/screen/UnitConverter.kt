package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deminifah.deminiccalc.R
import com.deminifah.deminiccalc.model.AppModel

//Unit Converter Screen
@Composable
fun Screen2(modifier: Modifier,model: AppModel){
    Column(modifier = modifier.fillMaxSize().background(color = colorResource(R.color.app_bg)))
    {
        ToolBars(leftIcon = painterResource(R.drawable.round_dehaze_24),
            rightIcon = painterResource(R.drawable.round_history_24),
            headerText = "Unit Converter",model)
        Column(modifier = Modifier.weight(1f,fill = true)) {
            UnitConverterCard(Modifier.weight(1f).padding(horizontal = 10.dp),model)
            LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
                val options = listOf("Area", "Length", "Temperature", "Volume","Mass")
                item { VisibleBtn(options,model) }
            }
            LazyVerticalGrid(columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1.5f, fill = true),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(BtnData2){ index, item->
                    CalcBtn2(type = item.type, index = index, model = model,num = item.num, funcSymbol = item.funcSymbol)
                }
            }
        }
    }
}


@Composable
fun Test(){
    val state = remember { mutableStateOf("") }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(value = state.value, onValueChange = {state.value=it}, trailingIcon = { Icon(
            painterResource(R.drawable.baseline_content_copy_24),"") })
    }
}



fun sam(pos:Int){
    val toValue2 = mutableStateOf("")
    val fromValue2= mutableStateOf("")
    val toHasFocus2 = mutableStateOf(false)
    val fromHasFocus2= mutableStateOf(true)
    var value2 = "0"

}


