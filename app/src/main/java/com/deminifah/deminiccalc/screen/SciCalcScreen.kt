package com.deminifah.deminiccalc.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.deminifah.deminiccalc.R

@Composable

fun Screen1(){
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White))
    {
        ToolBars(leftIcon = painterResource(R.drawable.round_dehaze_24),
            rightIcon = painterResource(R.drawable.round_history_24),
            headerText = "Sci-Calc")
        Column(modifier = Modifier.weight(1f,fill = true)) {
            CalcScreen(Modifier.weight(1f, fill = true))
            LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
                item {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.MoreVert,contentDescription = "", modifier = Modifier.rotate(90f))
                    }
                }
                item {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.KeyboardArrowUp,contentDescription = "", modifier = Modifier.rotate(90f))
                    }
                }

            }
            LazyVerticalGrid(columns = GridCells.Fixed(4),
                modifier = Modifier.weight(2f, fill = true).background(color = Color.DarkGray),
                verticalArrangement = Arrangement.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)) {
                items(CalcBtnData){item->
                    CalcBtn(type = item.type, num = item.num, funcSymbol = item.funcSymbol)
                }
            }
        }
    }
}