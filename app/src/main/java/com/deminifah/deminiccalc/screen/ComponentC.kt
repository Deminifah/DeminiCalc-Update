package com.deminifah.deminiccalc.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.deminifah.deminiccalc.R
import com.deminifah.deminiccalc.model.AppModel


@Composable
fun HealthDialog(model: AppModel, title:String){
    Dialog(onDismissRequest = {model.healthDialogState.value=false}) {
        Surface(modifier = Modifier.fillMaxHeight(0.85f), color = colorResource(R.color.app_bg), shape = MaterialTheme.shapes.medium) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(title, color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.SemiBold)
                    IconButton(onClick = {model.healthDialogState.value=false}) {
                        Icon(Icons.Rounded.Clear,"", tint = Color.White)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    CustomCalcText(modifier = Modifier.weight(1f) .clip(MaterialTheme.shapes.medium) .background(color = Color.White),model.healthDialogText, pad = 8)
                    DialogBtn3(
                        modifier = Modifier.weight(0.4f),
                        type = CalcbtnType.Function,
                        num = "equal",
                        funcSymbol = FuncSymbol.Equal,
                        model = model
                    )
                }
                LazyVerticalGrid(columns = GridCells.Fixed(3),
                    modifier = Modifier.weight(1.5f, fill = true),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(healthDialogBtn){ item->
                        DialogBtn3(
                            type = item.type,
                            num = item.num,
                            funcSymbol = item.funcSymbol,
                            model = model
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun HealthDialog2(model: AppModel){
    Dialog(onDismissRequest = {model.healthDialogState.value=false})
    {
        Surface(shape = MaterialTheme.shapes.medium, color = colorResource(R.color.app_bg))
        {
            Column(modifier = Modifier.padding(4.dp)) {
                Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Gender", fontWeight = FontWeight.Bold, color = Color.White,fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    IconButton(onClick = {model.healthDialogState.value=false}) {
                        Icon(Icons.Rounded.Clear,"", tint = Color.White)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(shape = MaterialTheme.shapes.small, color = Color.White, onClick = {model.gender.value="Male";model.healthDialogState.value=false}, modifier = Modifier.weight(1f)) {
                        Text("Male", modifier = Modifier.fillMaxWidth().padding(10.dp), textAlign = TextAlign.Center ,color = Color.Black,fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    }
                    Surface(shape = MaterialTheme.shapes.small, color = Color.White, onClick = {model.gender.value="Female";model.healthDialogState.value=false}, modifier = Modifier.weight(1f)) {
                        Text("Female",modifier = Modifier.fillMaxWidth().padding(10.dp), textAlign = TextAlign.Center, color = Color.Black,fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    }
                }
            }
        }
    }
}


val healthDialogBtn = listOf(
    BtnData(type = CalcbtnType.Number, num = "7"),
    BtnData(type = CalcbtnType.Number, num = "8"),
    BtnData(type = CalcbtnType.Number, num = "9"),
    BtnData(type = CalcbtnType.Number, num = "4"),
    BtnData(type = CalcbtnType.Number, num = "5"),
    BtnData(type = CalcbtnType.Number, num = "6"),
    BtnData(type = CalcbtnType.Number, num = "1"),
    BtnData(type = CalcbtnType.Number, num = "2"),
    BtnData(type = CalcbtnType.Number, num = "3"),
    BtnData(type = CalcbtnType.Number, num = "0"),
    BtnData(type = CalcbtnType.Number, num = "."),
    BtnData(type = CalcbtnType.Function, funcSymbol = FuncSymbol.Del)
)



@Composable
fun DialogBtn3(
    modifier: Modifier = Modifier,
    type: CalcbtnType,
    num: String = "del",
    funcSymbol: FuncSymbol = FuncSymbol.Clear,
    model: AppModel
){

    Surface(shape = MaterialTheme.shapes.medium,
        onClick = {model.handleDialogPick(num)},
        modifier = modifier.padding(8.dp), color = when(type)
        {
            CalcbtnType.Number -> {
                Color.White
            }
            CalcbtnType.Function -> {
                if(funcSymbol != FuncSymbol.Equal) Color.White else colorResource(R.color.equal)
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





@Composable
fun Screen4(modifier: Modifier,model: AppModel){

    LazyColumn(horizontalAlignment = Alignment.End,modifier = modifier.fillMaxSize() .background(color = colorResource(R.color.app_bg))){
        item {
            if(model.healthDialogState.value){
                println("We reache here now line 174")
                when(model.healthDialogType.value){
                    HealthDialogType.Age -> HealthDialog(model,"Age")
                    HealthDialogType.Weight -> HealthDialog(model,"Weight (Kg)")
                    HealthDialogType.Height -> HealthDialog(model,"Height (Cm)")
                    HealthDialogType.Gender -> HealthDialog2(model)
                }
            }
        }
        item {ToolBars(leftIcon = painterResource(R.drawable.round_dehaze_24),
            rightIcon = painterResource(R.drawable.round_history_24),
            headerText = "Health",model)  }
        item {   Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                HealthInfoCard(modifier = Modifier.weight(1f), title = "Age", value = model.age.value) {
                    model.healthDialogType.value =
                        HealthDialogType.Age;model.healthDialogState.value = true
                }
                HealthInfoCard(modifier = Modifier.weight(1f), title = "Gender", value = model.gender.value){
                    model.healthDialogType.value =
                        HealthDialogType.Gender;model.healthDialogState.value = true
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                HealthInfoCard(modifier = Modifier.weight(1f), title = "Height (cm) ", value = model.height.value){
                    println("Dialog No show ooo")
                    model.healthDialogType.value =
                        HealthDialogType.Height;
                    model.healthDialogState.value = true
                    println(model.healthDialogState.value)
                    println(".....")
                }
                HealthInfoCard(modifier = Modifier.weight(1f), title = "Weight (kg)", value = model.weight.value){
                    model.healthDialogType.value =
                        HealthDialogType.Weight;model.healthDialogState.value = true
                }
            }
        }}
        item {Spacer(Modifier.height(16.dp))  }
        item { Text("Health Report", color = Color.White, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
        item {Column (modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            HealthResultCardA(title = "Body Mass Index (BMI)", result = model.bmiResult.value, comments = model.bmiComment.value)
            HealthResultCardA(title = "Basal Metabolic Rate (BMR)", result = model.bmrResult.value,model.bmrComment.value)
        } }
        item {
            val ctx = LocalContext.current
            FloatingActionButton(onClick = {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,"THIS IS JUST A TESTING")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent,"Bmi Result")
            ctx.startActivity(shareIntent)
        }, modifier=Modifier.padding(8.dp),containerColor = colorResource(R.color.action)) {
            Icon(Icons.Rounded.Share,"")
        }}
    }
}

enum class HealthDialogType{
    Age,
    Gender,
    Height,
    Weight
}