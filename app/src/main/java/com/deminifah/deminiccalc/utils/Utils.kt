package com.deminifah.deminiccalc.utils

import com.deminifah.deminiccalc.obj.CurrencyPrice







sealed interface Units{
    val factor: Double
    val name:String
}


sealed class Length(override val factor: Double, override val name:String):Units{
    data object Meter : Length(1.0,"Meter")
    data object Kilometer : Length(0.001,"Kilometer")
    data object Centimeter : Length(100.0,"Centimeter")
    data object Millimeter : Length(1000.0,"Millimeter")
    data object Inch : Length(39.37,"Inch")
    data object Foot : Length(3.28084,"Foot")
    data object Yard : Length(1.094,"Yard")
    data object Mile : Length(0.0006214,"Mile")
    data object Nautical: Length (0.00054,"Nautical")
    data object Nanometer:Length(1000000000.0,"Nanometer")
    data object Micrometer:Length(1000000.0,"Micrometer")
}
sealed class Temperature(override val factor: Double, override val name:String):Units{
    // Temperature Units
    data object Celcius : Temperature(1.0,"Celcius")
    data object Fahrenheit : Temperature(1.0,"Fahrenheit")
    data object Kelvin : Temperature(1.0,"Kelvin")
}
sealed class Mass(override val factor: Double, override val name:String):Units{
    // Mass Units
    data object Kilogram : Mass(1.0,"Kilogram")
    data object Gram : Mass(1000.0,"Gram")
    data object Milligram : Mass(1000000.0,"Milligram")
    data object Pound : Mass(2.20462,"Pound")
    data object Ounce : Mass(35.274,"Ounce")
    data object Ton : Mass(0.001,"Ton")
}
sealed class Volume(override val factor: Double, override val name:String):Units{
    // Volume Units
    data object CubicMeter : Volume(1.0,"CubicMeter")
    data object CubicCentimeter : Volume(1000000.0,"CubicCentimeter")
    data object Liter : Volume(1000.0,"Liter")
    data object Milliliter : Volume(1000000.0,"Milliliter")
    data object CubicFoot : Volume(35.3147,"CubicFoot")
    data object CubicInch : Volume(61023.7,"CUbicInch")
    data object Gallon : Volume(264.172,"Gallon")
    data object Quart : Volume(1056.69,"Quart")
    data object Pint : Volume(2113.38,"Pint")
    data object OunceV : Volume(33814.0,"Ounce")

}



sealed class Area(override val factor: Double, override val name:String):Units {
    // Area Units
    data object SquareMeter : Area(1.0,"SquareMeter")
    data object SquareKilometer : Area(0.000001,"SquareKilometer")
    data object SquareFoot : Area(10.7639,"SquareFoot")
    data object SquareYard : Area(1.19599,"SquareYard")
    data object Acre : Area(0.000247105,"Acre")
    data object Hectare : Area(0.0001,"Hectare")
    data object SquareMile : Area(0.000000386102,"SquareMile")

}








// temperature func conversions
fun celsiusToFahrenheit(celsius: Double): Double {
    return (celsius * 1.8) + 32
}

fun celsiusToKelvin(celsius: Double): Double {
    return celsius + 273.15
}

fun fahrenheitToCelsius(fahrenheit: Double): Double {
    return (fahrenheit - 32) * 5.0 / 9.0
}

fun fahrenheitToKelvin(fahrenheit: Double): Double {
    return (fahrenheit + 459.67) * 5.0 / 9.0
}

fun kelvinToCelsius(kelvin: Double): Double {
    return kelvin - 273.15
}

fun kelvinToFahrenheit(kelvin: Double): Double {
    return (kelvin * 9.0 / 5.0) - 459.67
}

fun unitConversion(from: Units, to: Units, value: Double):Double{
    if (from is Temperature && to is Temperature){
        when{
            from == Temperature.Celcius && to == Temperature.Fahrenheit ->{
                return celsiusToFahrenheit(value)
            }
            from == Temperature.Celcius && to == Temperature.Kelvin ->{
                return celsiusToKelvin(value)
            }
            from == Temperature.Fahrenheit && to == Temperature.Celcius->{
                return fahrenheitToCelsius(value)
            }
            from == Temperature.Fahrenheit && to == Temperature.Kelvin->{
                return fahrenheitToKelvin(value)
            }
            from == Temperature.Kelvin && to == Temperature.Celcius->{
                return kelvinToCelsius(value)
            }
            from == Temperature.Kelvin && to == Temperature.Fahrenheit->{
                return kelvinToFahrenheit(value)
            }
        }
    }
    val conversionResult = (to.factor / from.factor) * value
    return conversionResult
}
fun unitConversion(from: CurrencyPrice, to: CurrencyPrice, value: Double):Double{
    val conversionResult = (to.price.toDouble() / from.price.toDouble()) * value
    return conversionResult
}








enum class Areas{
    SquareMeter,
    SquareKilometer,
    SquareFoot,
    SquareYard,
    Acre,
    Hectare,
    SquareMile
}
enum class Temperatures{
    Celcius,
    Fahrenheit,
    Kelvin
}
enum class Lengths{
    Meter,
    Kilometer,
    Centimeter,
    Millimeter,
    Inch,
    Foot,
    Yard,
    Mile,
    Nautical,
    Nanometer,
    Micrometer

}
enum class  Volumes{
    CubicMeter,
    CubicCentimeter,
    Liter,
    Milliliter,
    CubicFoot,
    CubicInch,
    Gallon,
    Quart,
    Pint,
    Ounce
}
enum class Masses {
    Kilogram,
    Gram,
    Milligram,
    Pound,
    Ounce,
    Ton
}

enum class UnitType{
    Area,
    Length,
    Temperature,
    Volume,
    Mass
}