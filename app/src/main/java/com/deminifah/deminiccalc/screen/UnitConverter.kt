package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deminifah.deminiccalc.R

//Unit Converter Screen
@Composable
fun Screen2(){
    Column(modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.app_bg)))
    {
        ToolBars(leftIcon = painterResource(R.drawable.round_dehaze_24),
            rightIcon = painterResource(R.drawable.round_history_24),
            headerText = "Sci-Calc")
        Column(modifier = Modifier.weight(1f,fill = true)) {
            LazyRow(modifier = Modifier.fillMaxWidth().requiredHeight(50.dp), contentPadding = PaddingValues(16.dp)) {
                item {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.MoreVert,contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.rotate(90f))
                    }
                }
                item {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp,contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.rotate(90f))
                    }
                }

            }
            LazyVerticalGrid(columns = GridCells.Fixed(4),
                modifier = Modifier.weight(2f, fill = true),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(CalcBtnData){index,item->
                    CalcBtn2(type = item.type, index = index,num = item.num, funcSymbol = item.funcSymbol)
                }
            }
        }
    }
}
