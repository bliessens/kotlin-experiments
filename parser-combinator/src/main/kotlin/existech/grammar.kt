package existech

import me.alllex.parsus.parser.Grammar
import me.alllex.parsus.parser.Parser
import me.alllex.parsus.parser.map
import me.alllex.parsus.parser.maybe
import me.alllex.parsus.parser.times
import me.alllex.parsus.parser.unaryMinus
import me.alllex.parsus.parser.zeroOrMore
import me.alllex.parsus.token.literalToken
import me.alllex.parsus.token.regexToken

object STAGrammar : Grammar<STASection>() {

//    init {
//        regexToken("\\s+", ignored = true)
//    }

    val marker = regexToken("=+")

    //    val whitespace = regexToken("\\s+")
    val sectionName = regexToken("\\s+([\\w\\.]+\\s?)+")

    val header by -marker * sectionName * -marker map { Header(it.text.trim()) }

    val colon by literalToken(":")
    val fieldName by regexToken("[\\w\\s]+")
    val value by regexToken("\\.*$")

    val keyValuePair by fieldName * -colon * value map { Field(it.t1.text.trim(), it.t2.text.trim()) }

    val section by header * zeroOrMore(keyValuePair) map { it ->

        Statistics(/*it.first.name,*/ it.second)
    }
    val ssection by header * maybe(keyValuePair) map { it ->
        Header(it.first.name, it.second?.let { listOf(it) } ?: emptyList())
    }


    override val root: Parser<STASection> by section
}


sealed class STASection

data class Header(
    val name: String,
    val fields: List<Field> = emptyList(),
)

data class Field(
    val name: String,
    val value: String,
)

data class Statistics(
    val fields: List<Field>,
) : STASection()