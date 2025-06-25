package com.deminifah.deminiccalc

import androidx.compose.ui.util.fastJoinToString
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleAreasTest {
    @Test
    fun addition_isCorrect() {
        //assert(unicodeReplace("2²*5²+45²-55²") == "2^2*5^2+45^2-55^2")
        //println(stringFormat("1000"))
        //assert(stringFormat("5400.6512345") == "5,400.651235")
        println("12345".filterIndexed { index,value-> index != 0 })
        println("12345".removeRange(0,1))
        println("Test Passed...")
        println("12345".toMutableList().apply { add(2,'/') }.joinToString())
        println(".....")
//        println(unicodeReplace("2²*5²+45²-55²"))

    }
}