package com.deminifah.deminiccalc.utils

enum class BtnValue{
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ZERO,
    EXPONENT,
    EQUAL,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MOD,
    OPENBRACKET,
    CLOSEBRACKET,
    DOT,
    DELETE,
    CLEAR

}






sealed class Units(val factor: Double) {
    // Volume Units
    data object CubicMeter : Units(1.0)
    data object CubicCentimeter : Units(1000000.0)
    data object Liter : Units(1000.0)
    data object Milliliter : Units(1000000.0)
    data object CubicFoot : Units(35.3147) // TODO: Implement conversion factor
    data object CubicInch : Units(61023.7) // TODO: Implement conversion factor
    data object Gallon : Units(264.172) // TODO: Implement conversion factor
    data object Quart : Units(1056.69) // TODO: Implement conversion factor
    data object Pint : Units(2113.38) // TODO: Implement conversion factor
    data object OunceV : Units(33814.0) // TODO: Implement conversion factor

    // Mass Units
    data object Kilogram : Units(1.0)
    data object Gram : Units(1000.0)
    data object Milligram : Units(1000000.0)
    data object Pound : Units(2.20462) // TODO: Implement conversion factor
    data object Ounce : Units(35.274) // TODO: Implement conversion factor
    data object Ton : Units(0.001) // TODO: Implement conversion factor

    // Area Units
    data object SquareMeter : Units(1.0)
    data object SquareKilometer : Units(0.000001)
    data object SquareFoot : Units(10.7639) // TODO: Implement conversion factor
    data object SquareYard : Units(1.19599) // TODO: Implement conversion factor
    data object Acre : Units(0.000247105) // TODO: Implement conversion factor
    data object Hectare : Units(0.0001)
    data object SquareMile : Units(0.000000386102) // TODO: Implement conversion factor


    // Length Units
    data object Meter : Units(1.0)
    data object Kilometer : Units(0.001)
    data object Centimeter : Units(100.0)
    data object Millimeter : Units(1000.0)
    data object Inch : Units(39.37) // TODO: Implement conversion factor
    data object Foot : Units(3.28084) // TODO: Implement conversion factor
    data object Yard : Units(1.094) // TODO: Implement conversion factor
    data object Mile : Units(0.0006214) // TODO: Implement conversion factor
    data object Nautical: Units (0.00054)
    data object Nanometer:Units(1000000000.0)
    data object Micrometer:Units(1000000.0)
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
    val conversionResult = (to.factor / from.factor) * value
    return conversionResult
}





enum class Area{
    SquareMeter,
    SquareKilometer,
    SquareFoot,
    SquareYard,
    Acre,
    Hectare,
    SquareMile
}
enum class Temperature{
    Celcius,
    Fahrenheit,
    Kelvin
}
enum class Length{
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
enum class  Volume{
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
enum class Mass {
    Kilogram,
    Gram,
    Milligram,
    Pound,
    Ounce,
    Ton
}


