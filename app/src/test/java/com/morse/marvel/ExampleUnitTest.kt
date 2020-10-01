package com.morse.marvel

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


open interface Root {

    public fun Kerollos (){
        println("In : Kerollos")
    }

}

open class Basic1  {

    public fun sayHi(){
        println("In : Parent1")
    }

}

open class Basic2 {

    public fun sayHi(){
        println("In : Parent2")
    }

}

class Vechile : Basic1() , Root {

    public fun VV () {
        println("In : vv")
        Kerollos()
    }

}

class Animal : Basic2() , Root {

    public fun AA () {
        println("In : vv")
        Kerollos()
    }


}

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var animal = Animal()
        animal?.AA()
    }
}