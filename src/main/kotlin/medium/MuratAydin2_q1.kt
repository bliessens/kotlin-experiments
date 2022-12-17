package medium

/**
 * https://medium.com/@maydin/kotlin-quiz-refresh-your-kotlin-knowledge-2-1a9bf97cc019
 */
interface Printable {
    fun prettyPrint(): String
}

@JvmInline
value class Name(val s: String) : Printable {
    override fun prettyPrint(): String = "Let's $s!"
}

fun main() {
    val name = Name("Kotlin")
    println(name.prettyPrint())
}