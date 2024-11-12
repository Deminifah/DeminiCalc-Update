package com.deminifah.deminiccalc
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License

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


