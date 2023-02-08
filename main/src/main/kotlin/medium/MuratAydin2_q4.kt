package medium

interface Q4Base {
    val message: String
    fun print()
}

class Q4BaseImpl(val x: Int) : Q4Base {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Q4Derived(b: Q4Base) : Q4Base by b {
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

fun main() {
    val b = Q4BaseImpl(10)
    val derived = Q4Derived(b)
    derived.print()
    println(derived.message)
}
