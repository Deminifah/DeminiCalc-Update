package com.deminifah.deminiccalc
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License
import org.mariuszgromada.math.mxparser.mXparser
import java.util.Locale

fun tester(){
    License.iConfirmNonCommercialUse("deminifah")
    println( mXparser.checkIfUnicodeBuiltinKeyWordsMode())
    mXparser.enableUnicodeBuiltinKeyWordsMode()
    val eval = Expression()
    eval.expressionString = "²√8"
    println("Debugging begin ------>")
    println(eval.checkSyntax())
    println(".....")
    println(eval.checkLexSyntax())

    println(eval.calculate())
    println(eval.errorMessage)


}


fun unicodeReplace(symbol:String): String{
    var solution = symbol.replace("²", "^2")
    return solution

}

fun stringFormat(value: String): String{
    return String.format(Locale.ENGLISH,"%,.2f",value.toDouble())
}

