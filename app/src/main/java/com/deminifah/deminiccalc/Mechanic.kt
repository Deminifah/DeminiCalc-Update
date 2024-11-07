package com.deminifah.deminiccalc
import org.mariuszgromada.math.mxparser.*

fun tester(){
    License.iConfirmNonCommercialUse("deminifah")
    val eval = Expression()
    eval.expressionString = "56-sqrt(25)"
    println("Debugging begin ------>")

    println(eval.calculate())
    println(eval.errorMessage)


}

fun regulate(){
    val exp = ""
    val operator = "+-%^/"

}