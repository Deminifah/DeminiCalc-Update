package com.deminifah.deminiccalc.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deminifah.deminiccalc.obj.currencyList
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License

class AppModel:ViewModel() {
    init {
        License.iConfirmNonCommercialUse("deminifah")
    }
    val currencyPriceState = mutableStateListOf(currencyList)
    val eval = Expression()
    val exp = mutableStateOf("")
    val displayExp  = mutableStateOf("")
    val result = mutableStateOf("")
    fun handleCLick(pos:Int){
        when(pos){
            0->{
                displayExp.value = ""
                exp.value = ""
            }
            1->{
                displayExp.value += "("
                exp.value += '('
            }
            2->{displayExp.value += '%'
                exp.value += '%'
            }
            3->{displayExp.value += '÷'
                exp.value += '/'}
            4->{displayExp.value += '7'
                exp.value += '7'}
            5->{displayExp.value += '8'
                exp.value += '8'}
            6->{displayExp.value += '9'
                exp.value += '9'}
            7->{displayExp.value += '×'
                exp.value += '*'}
            8->{displayExp.value += '4'
                exp.value += '4'}
            9->{displayExp.value += '5'
                exp.value += '5'}
            10->{displayExp.value += '6'
                exp.value += '6'}
            11->{displayExp.value += '-'
                exp.value += '-'}
            12->{displayExp.value += '1'
                exp.value += '1'}
            13->{displayExp.value += '2'
                exp.value += '2'}
            14->{displayExp.value += '3'
                exp.value += '3'}
            15->{displayExp.value += '+'
                exp.value += '+'}
            16->{displayExp.value += '0'
                exp.value += '0'}
            17->{displayExp.value += '.'
                exp.value += '.'}
            18->{displayExp.value =displayExp.value.dropLast(1)
                exp.value = exp.value.dropLast(1)}
            19->{calculate()}

        }
    }
    fun calculate(){
        eval.expressionString = exp.value
        val result = eval.calculate()
        displayExp.value = result.toString()
    }

    fun updateCurrencyPrice(){
        viewModelScope.launch {  }
    }

}