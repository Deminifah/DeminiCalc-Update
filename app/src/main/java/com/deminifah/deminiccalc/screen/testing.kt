package com.deminifah.deminiccalc.screen

import android.content.Context
import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


//
//BasicTextField(value = textvalue,
//textStyle = TextStyle(
//color = Color.DarkGray,
//fontWeight = FontWeight.Bold,
//fontSize = 32.sp
//),
//cursorBrush = SolidColor(Color.DarkGray),
//modifier = Modifier.weight(1f).fillMaxHeight().focusRequester(focusRequester).padding(pad.dp)


@Composable
fun CustomTv(modifier: Modifier, textState: MutableState<String>,cursorPosition: MutableIntState){

    AndroidView(factory = {context->
        ImprovedEditText(context).apply {
            setSelection(textState.value.length)
            textSize = 32.sp.value
            background = null
            showSoftInputOnFocus = false
            println("Samu Alajo")
            onCursorChange = {start -> cursorPosition.intValue = start}
        }

    },modifier = modifier ,update = {update->
        update.setText(textState.value)
        update.setSelection(textState.value.length)

    })
}



class ImprovedEditText(context: Context): EditText(context) {
    var onCursorChange:((index:Int)->Unit)? = null
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        onCursorChange?.invoke(selStart)
    }
}



@Composable
@Preview

fun TestingReview(){
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        //CustomTv(Modifier.padding(32.dp).fillMaxWidth(0.8f))
    }
}