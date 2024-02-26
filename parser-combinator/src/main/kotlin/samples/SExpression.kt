@file:Suppress("PackageDirectoryMismatch", "MemberVisibilityCanBePrivate")

package samples

import me.alllex.parsus.parser.Grammar
import me.alllex.parsus.parser.Parser
import me.alllex.parsus.parser.map
import me.alllex.parsus.parser.or
import me.alllex.parsus.parser.parser
import me.alllex.parsus.parser.repeatZeroOrMore
import me.alllex.parsus.parser.times
import me.alllex.parsus.parser.unaryMinus
import me.alllex.parsus.token.literalToken
import me.alllex.parsus.token.regexToken


sealed class SExpression {
    data class Int(val value: Long) : SExpression()
    data class Num(val value: Double) : SExpression()
    data class Str(val value: String) : SExpression()
    data class Sym(val value: String) : SExpression()
    data class Lst(val value: List<SExpression>) : SExpression() {
        constructor(vararg value: SExpression) : this(value.toList())
    }
}

object SExpressionGrammar : Grammar<SExpression>() {
    init {
        regexToken("\\s+", ignored = true)
    }

    val lpar by literalToken("(")
    val rpar by literalToken(")")
    val num by regexToken("-?\\d+\\.\\d+") map { SExpression.Num(it.text.toDouble()) }
    val int by regexToken("-?\\d+") map { SExpression.Int(it.text.toLong()) }
    val str by regexToken("\"(\\\\.|[^\\\\\"])*\"") map { SExpression.Str(it.text.run { substring(1, lastIndex) }) }
    val sym by regexToken("[^\\s()]+") map { SExpression.Sym(it.text) }

    val atom by num or int or str or sym
    val listItems by parser { repeatZeroOrMore(sexpr) }
    val list by -lpar * listItems * -rpar map { SExpression.Lst(it) }
    val sexpr: Parser<SExpression> by list or atom

    override val root by sexpr
}

fun main() {
    println(SExpressionGrammar.parseOrThrow("123.8"))
}