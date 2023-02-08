package medium

class C {
    private fun getObject() = object {
        val x: String = "x"
    }

    fun printX() {
        println(getObject().x)
    }
}

fun main() {
    val c = C()
    c.printX()
}
