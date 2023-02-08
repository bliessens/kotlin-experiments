package medium

interface Q3Base {
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val x: Int) : Q3Base {
    override fun printMessage() { print(x) }
    override fun printMessageLine() { println(x) }
}

class Q3Derived(b: Q3Base) : Q3Base by b {
    override fun printMessage() { print("abc") }
}

fun main() {
    val b = BaseImpl(10)
    Q3Derived(b).printMessage()
    Q3Derived(b).printMessageLine()
}
