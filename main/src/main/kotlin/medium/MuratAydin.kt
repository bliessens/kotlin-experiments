package medium

/**
 * https://medium.com/@maydin/kotlin-quiz-refresh-your-kotlin-knowledge-1-2269065fd457
 */
open class Base

class Derived : Base()

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")//1
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo()   // call the extension function
    }
}

class DerivedCaller: BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

fun main() {
    BaseCaller().call(Base())
    DerivedCaller().call(Base())
    DerivedCaller().call(Derived())
}