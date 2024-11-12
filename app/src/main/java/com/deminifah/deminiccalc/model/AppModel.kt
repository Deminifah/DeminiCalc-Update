package com.deminifah.deminiccalc.model

import android.icu.text.DecimalFormat
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.core.text.isDigitsOnly
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deminifah.deminiccalc.ScreenState
import com.deminifah.deminiccalc.obj.CurrencyPrice
import com.deminifah.deminiccalc.obj.currencyList
import com.deminifah.deminiccalc.obj.currencyPriceList
import com.deminifah.deminiccalc.screen.DialogData
import com.deminifah.deminiccalc.screen.HealthDialogType
import com.deminifah.deminiccalc.screen.History
import com.deminifah.deminiccalc.screen.HistoryItem
import com.deminifah.deminiccalc.screen.Trig
import com.deminifah.deminiccalc.utils.Area
import com.deminifah.deminiccalc.utils.Areas
import com.deminifah.deminiccalc.utils.Length
import com.deminifah.deminiccalc.utils.Lengths
import com.deminifah.deminiccalc.utils.Mass
import com.deminifah.deminiccalc.utils.Masses
import com.deminifah.deminiccalc.utils.Temperature
import com.deminifah.deminiccalc.utils.Temperatures
import com.deminifah.deminiccalc.utils.UnitType
import com.deminifah.deminiccalc.utils.Units
import com.deminifah.deminiccalc.utils.Volume
import com.deminifah.deminiccalc.utils.Volumes
import com.deminifah.deminiccalc.utils.unitConversion
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License
import org.mariuszgromada.math.mxparser.mXparser
import java.util.Locale

class AppModel:ViewModel() {
    val calculationError = mutableStateOf(false)
    val calculationErrMsg = mutableStateOf("")
    val appScreenState = mutableStateOf(ScreenState.SciCalc)
    val destinationDialog = mutableStateOf(false)


    private val formats = DecimalFormat("#.####")
    init {
        License.iConfirmNonCommercialUse("deminifah")
    }
    private val nav = ArrayDeque<ScreenState>()
    val backEnabled = mutableStateOf(false)
    private val eval = Expression()
    val trig = mutableStateOf(Trig.Deg)
    val exp = mutableStateOf("")
    val displayExp  = mutableStateOf("")
    val result = mutableStateOf("")
    var selectedIndex =  mutableIntStateOf(0)
    fun handleCLick(pos:Int){
        when(pos){
            0->{
                displayExp.value += "("
                exp.value += "("
            }
            1->{
                displayExp.value += ")"
                exp.value += ')'
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
    private fun calculate(){

        when(trig.value){
            Trig.Deg -> mXparser.setDegreesMode()
            Trig.Rad -> mXparser.setRadiansMode()
        }
        eval.expressionString = exp.value
        if(!eval.checkSyntax()){
            calculationErrMsg.value="Syntax Error"
            calculationError.value = true
            return
        }
        val result = eval.calculate()
        if(eval.errorMessage == "No errors detected." && result.toString()=="NaN" ) {
            calculationErrMsg.value="Math Error"
            calculationError.value = true
            return
        }else if (result.toString()=="NaN"){
            calculationErrMsg.value="Syntax Error"
            calculationError.value = true
            return
        }
        displayExp.value = result.toString().replace("E","×10^")
        exp.value = displayExp.value
    }
    fun specialFunc(func:String){
        if(func == "π" || func == "e"){
            displayExp.value += func
            exp.value += func
            return
        }
        displayExp.value += "$func("
        exp.value += "$func("
    }
    fun navigate(next:ScreenState){
        destinationDialog.value = false
        try {
            backEnabled.value = true
            nav.add(appScreenState.value)
            appScreenState.value=next
        }catch (_:Exception){}

    }
    fun goBack(){
        if (nav.isNotEmpty()){
            appScreenState.value = nav.last()
            nav.removeLast()
            if (nav.isEmpty()){
                backEnabled.value = false
            }
        }else{
            backEnabled.value = false
        }
    }



    //unit converter
    val showUnitDialog = mutableStateOf(false)
    val toFocus = FocusRequester()
    val fromFocus = FocusRequester()
    private val from = mutableStateOf(true)
    val toValue = mutableStateOf("")
    val fromValue= mutableStateOf("")
    val toHasFocus = mutableStateOf(false)
    val fromHasFocus= mutableStateOf(true)
    private var value = "0"
    val measurement= UnitType.entries
    val currentMeasurement = mutableIntStateOf(0)
    private val unitTo = mutableStateOf<Units>(Area.Acre)
    val _unitTo
        get() = unitTo.value.name
    private val unitFrom = mutableStateOf<Units>(Area.Acre)
    val _unitFrom
        get() = unitFrom.value.name

    fun switchUnit(){
        val temp = unitTo.value
        unitTo.value  = unitFrom.value
        unitFrom.value = temp
        handleConversion()
    }
    private fun handleConversion(){
        if (value.isBlank()){value = "0"}

        val result = if(fromHasFocus.value)unitConversion(unitFrom.value,unitTo.value,value.toDouble())else if (toHasFocus.value) unitConversion(unitTo.value,unitFrom.value,value.toDouble()) else unitConversion(unitFrom.value,unitTo.value,value.toDouble())
        val formated =formats.format(result)
        if (fromHasFocus.value){
            toValue.value = formated.toString()
        }else if (toHasFocus.value){
            fromValue.value = formated.toString()
        }
        else{
            toValue.value = formated.toString()
        }
    }
    fun getCurrentUnits():List<DialogData>{
        when(currentMeasurement.intValue){
            0->{
                return Areas.entries.map { area-> DialogData(item = area.name,"")}
            }
            1->{
                return Lengths.entries.map { DialogData(item = it.name,"")}
            }
            2->{
                return Temperatures.entries.map { DialogData(item = it.name,"")}
            }
            3->{
                return Volumes.entries.map { DialogData(item = it.name,"")}
            }
            4->{
                return Masses.entries.map { DialogData(item = it.name,"")}
            }
            else->{return emptyList()
            }

        }

    }
    fun handleUnitChange(pos :Boolean){
        from.value = pos
    }
    fun handleUniClick(unit:Units){
        if(from.value){
            unitFrom.value = unit
        }
        else{
            unitTo.value = unit
        }
        handleConversion()
    }
    fun handleUnitBtnClick(pos:Int){
        when(pos){
            0->{
                if (fromHasFocus.value){
                    fromValue.value += "7"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "7"
                    value = toValue.value
                }
                else{
                    fromValue.value += "7"
                    value = fromValue.value
                }
            }
            1->{
                if (fromHasFocus.value){
                    fromValue.value += "8"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "8"
                    value = toValue.value
                }
                else{
                    fromValue.value += "8"
                    value = fromValue.value
                }
            }
            2->{
                if (fromHasFocus.value){
                    fromValue.value += "9"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "9"
                    value = toValue.value
                }
                else{
                    fromValue.value += "9"
                    value = fromValue.value
                }
            }
            3->{
                if (fromHasFocus.value){
                    fromValue.value = ""
                    value = "0"
                }
                else if (toHasFocus.value){
                    toValue.value = ""
                    value = "0"
                }
                else{
                    fromValue.value = ""
                    toValue.value = ""
                    value = "0"
                }
            }
            4->{
                if (fromHasFocus.value){
                    fromValue.value += "4"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "4"
                    value = toValue.value
                }
                else{
                    fromValue.value += "4"
                    value = fromValue.value
                }
            }
            5->{
                if (fromHasFocus.value){
                    fromValue.value += "5"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "5"
                    value = toValue.value
                }
                else{
                    fromValue.value += "5"
                    value = fromValue.value
                }
            }
            6->{
                if (fromHasFocus.value){
                    fromValue.value += "6"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "6"
                    value += toValue.value
                }
                else{
                    fromValue.value += "6"
                    value = fromValue.value
                }
            }
            7->{
                fromFocus.requestFocus()
                return
            }
            8->{
                if (fromHasFocus.value){
                    fromValue.value += "1"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "1"
                    value = toValue.value
                }
                else{
                    fromValue.value += "1"
                    value = fromValue.value
                }
            }
            9->{
                if (fromHasFocus.value){
                    fromValue.value += "2"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "2"
                    value = toValue.value
                }
                else{
                    fromValue.value += "2"
                    value = fromValue.value
                }
            }
            10->{
                if (fromHasFocus.value){
                    fromValue.value += "3"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "3"
                    value = toValue.value
                }
                else{
                    fromValue.value += "3"
                    value = fromValue.value
                }
            }
            11->{
                toFocus.requestFocus()
                return
            }
            12->{
                if (fromHasFocus.value){
                    fromValue.value += "0"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "0"
                    value = toValue.value
                }
                else{
                    fromValue.value += "0"
                    value = fromValue.value
                }
            }
            13->{
                if (fromHasFocus.value && !fromValue.value.contains('.')){
                    fromValue.value += "."
                    value = fromValue.value
                }
                else if (toHasFocus.value && !toValue.value.contains('.')){
                    toValue.value += "."
                    value = toValue.value
                }
            }
            14->{
                if (fromHasFocus.value){
                    fromValue.value += "00"
                    value = fromValue.value
                }
                else if (toHasFocus.value){
                    toValue.value += "00"
                    value = toValue.value
                }
                else{
                    fromValue.value += "00"
                    value = fromValue.value
                }
            }
            15->{
                if (fromHasFocus.value && fromValue.value.isNotEmpty()){
                    fromValue.value = fromValue.value.dropLast(1)
                    value = fromValue.value
                }
                else if (toHasFocus.value && toValue.value.isNotEmpty()){
                    toValue.value = toValue.value.dropLast(1)
                    value = toValue.value
                }
            }
        }
        handleConversion()
    }

    fun changeMeasurementUnit(index:Int){
        currentMeasurement.intValue = index
        when(measurement[index]){
            UnitType.Area -> {
                unitTo.value = Area.SquareMeter
                unitFrom.value = Area.SquareMeter
            }
            UnitType.Temperature -> {
                unitTo.value = Temperature.Kelvin
                unitFrom.value = Temperature.Celcius
            }
            UnitType.Length -> {
                unitTo.value = Length.Kilometer
                unitFrom.value = Length.Meter
            }
            UnitType.Volume -> {
                unitTo.value = Volume.Liter
                unitFrom.value = Volume.Gallon
            }
            UnitType.Mass -> {
                unitTo.value=Mass.Kilogram
                unitFrom.value=Mass.Gram
            }
        }
        handleConversion()
    }


    //currency converter
    private val currencyPriceState = currencyPriceList.toMutableStateList()
    val currencyListState = currencyList.toMutableStateList()
    val showUnitDialog2 = mutableStateOf(false)
    val toFocus2 = FocusRequester()
    val fromFocus2 = FocusRequester()
    val toValue2 = mutableStateOf("")
    val fromValue2= mutableStateOf("")
    val toHasFocus2 = mutableStateOf(false)
    val fromHasFocus2= mutableStateOf(true)
    private var value2 = "0"
    private val unitTo2 = mutableStateOf(currencyPriceState[295])
    val _unitTo2
        get() = unitTo2.value.code.uppercase(Locale.UK)
    private val unitFrom2 = mutableStateOf(currencyPriceState[215])
    val _unitFrom2
        get() = unitFrom2.value.code.uppercase(Locale.UK)

    fun updateCurrencyPrice(){
        viewModelScope.launch {  }
    }
    fun switchUnit2(){
        val temp = unitTo2.value
        unitTo2.value  = unitFrom2.value
        unitFrom2.value = temp
        handleConversion2()
    }
    private fun handleConversion2(){
        if (value2.isBlank()){value2 = "0"}

        val result = if(fromHasFocus2.value)unitConversion(unitFrom2.value,unitTo2.value,value2.toDouble())else if (toHasFocus2.value) unitConversion(unitTo2.value,unitFrom2.value,value2.toDouble()) else unitConversion(unitFrom2.value,unitTo2.value,value2.toDouble())
        val formated =formats.format(result)
        if (fromHasFocus2.value){
            toValue2.value = formated.toString()
        }else if (toHasFocus2.value){
            fromValue2.value = formated.toString()
        }
        else{
            toValue2.value = formated.toString()
        }
    }
    fun handleUnitChange2(pos :Boolean){
        from.value = pos
    }
    fun handleUniClick2(unit:CurrencyPrice){
        if(from.value){
            unitFrom2.value = unit
        }
        else{
            unitTo2.value = unit
        }
        handleConversion2()

    }
    fun handleUnitBtnClick2(pos:Int){
        when(pos){

            0->{

                if (fromHasFocus2.value){
                    fromValue2.value += "7"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "7"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "7"
                    value2 = fromValue2.value
                }
            }
            1->{
                if (fromHasFocus2.value){
                    fromValue2.value += "8"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "8"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "8"
                    value2 = fromValue2.value
                }
            }
            2->{
                if (fromHasFocus2.value){
                    fromValue2.value += "9"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "9"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "9"
                    value2 = fromValue2.value
                }
            }
            3->{
                if (fromHasFocus2.value){
                    fromValue2.value = ""
                    value2 = "0"
                }
                else if (toHasFocus2.value){
                    toValue2.value = ""
                    value2 = "0"
                }
                else{
                    fromValue2.value = ""
                    toValue2.value = ""
                    value2 = "0"
                }
            }
            4->{
                if (fromHasFocus2.value){
                    fromValue2.value += "4"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "4"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "4"
                    value2 = fromValue2.value
                }
            }
            5->{
                if (fromHasFocus2.value){
                    fromValue2.value += "5"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "5"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "5"
                    value2 = fromValue2.value
                }
            }
            6->{
                if (fromHasFocus2.value){
                    fromValue2.value += "6"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "6"
                    value2 += toValue2.value
                }
                else{
                    fromValue2.value += "6"
                    value2 = fromValue2.value
                }
            }
            7->{
                fromFocus2.requestFocus()
                return
            }
            8->{
                if (fromHasFocus2.value){
                    fromValue2.value += "1"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "1"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "1"
                    value2 = fromValue2.value
                }
            }
            9->{
                if (fromHasFocus2.value){
                    fromValue2.value += "2"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "2"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "2"
                    value2 = fromValue2.value
                }
            }
            10->{
                if (fromHasFocus2.value){
                    fromValue2.value += "3"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "3"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "3"
                    value2 = fromValue2.value
                }
            }
            11->{
                toFocus2.requestFocus()
                return
            }
            12->{
                if (fromHasFocus2.value){
                    fromValue2.value += "0"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "0"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "0"
                    value2 = fromValue2.value
                }
            }
            13->{
                if (fromHasFocus2.value && !fromValue2.value.contains('.')){
                    fromValue2.value += "."
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value && !toValue2.value.contains('.')){
                    toValue2.value += "."
                    value2 = toValue2.value
                }
            }
            14->{
                if (fromHasFocus2.value){
                    fromValue2.value += "00"
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value){
                    toValue2.value += "00"
                    value2 = toValue2.value
                }
                else{
                    fromValue2.value += "00"
                    value2 = fromValue2.value
                }
            }
            15->{
                if (fromHasFocus2.value && fromValue2.value.isNotEmpty()){
                    fromValue2.value = fromValue2.value.dropLast(1)
                    value2 = fromValue2.value
                }
                else if (toHasFocus2.value && toValue2.value.isNotEmpty()){
                    toValue2.value = toValue2.value.dropLast(1)
                    value2 = toValue2.value
                }
            }
        }
        handleConversion2()
    }

    fun findCurrency(code:String,index: Int):CurrencyPrice{
        return currencyPriceState.find { it.code == code } ?:  currencyPriceState[index]
    }



    //health

    val age = mutableStateOf("Set")
    val gender = mutableStateOf("Set")
    val weight = mutableStateOf("Set")
    val height = mutableStateOf("Set")
    val healthDialogState = mutableStateOf(false)
    val healthDialogText = mutableStateOf("")
    val healthDialogType = mutableStateOf(HealthDialogType.Age)
    val bmiResult = mutableStateOf("Input data")
    val bmrResult = mutableStateOf("Input data")
    val bmiComment = mutableStateOf("")
    val bmrComment = mutableStateOf("")
    private fun healthCalc(){
        try {
            bmiResult.value = formats.format(calculateBMI(weight.value.toDouble(),height.value.toDouble()))
            bmrResult.value = formats.format(calculateBMR(weight.value.toDouble(),height.value.toDouble(),age.value.toInt(),gender.value=="Female"))
        }catch (e:Exception){
            println(e.message)
            println(e.printStackTrace())
        }
    }

    //FUNCTION TO CALCULATE BMI
    private fun calculateBMI(weightKg: Double, heightM: Double): Double {
        val height = heightM /100
        return weightKg / (height * height)
    }

    // Function to calculate BMR
    private fun calculateBMR(weightKg: Double, heightCm: Double, age: Int, isFemale: Boolean): Double {
        return if (isFemale) {
            // BMR calculation for females (using the Mifflin-St Jeor Equation)
            ((10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161) * 1.3

        } else {
            ((10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5) * 1.3
            // BMR calculation for males (using the Mifflin-St Jeor Equation)
        }
    }
    fun handleDialogPick(num:String){
        println(num)
        println("What is going on")

        if (num.isNotBlank() && (num.isDigitsOnly() || num ==".")){
            healthDialogText.value += num
        }else if(num == "equal" && healthDialogText.value.isNotBlank()){
            healthDialogState.value=false
            when(healthDialogType.value){
                HealthDialogType.Age -> age.value = healthDialogText.value
                HealthDialogType.Gender -> gender.value = healthDialogText.value
                HealthDialogType.Height -> height.value = healthDialogText.value
                HealthDialogType.Weight -> weight.value = healthDialogText.value
            }
            healthDialogText.value=""
            healthCalc()
        }
        else{
            healthDialogText.value =healthDialogText.value.dropLast(1)
        }
    }

    fun addHistory(data:DataStore<History>,item:HistoryItem){
        viewModelScope.launch {
            data.updateData { history ->
                history.copy(history = history.history.toMutableList().also { it.add(item)}.toList())
            }
        }
    }
}