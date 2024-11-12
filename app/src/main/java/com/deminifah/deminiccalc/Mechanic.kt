package com.deminifah.deminiccalc
import com.deminifah.deminiccalc.utils.Units
import org.mariuszgromada.math.mxparser.*

fun tester(){
    License.iConfirmNonCommercialUse("deminifah")
    val eval = Expression()
    eval.expressionString = "3/0"
    println("Debugging begin ------>")
    println(eval.checkSyntax())
    println(".....")
    println(eval.checkLexSyntax())

    println(eval.calculate())
    println(eval.errorMessage)


}


fun regulate(){
    val exp = ""
    val operator = "+-%^/"

}