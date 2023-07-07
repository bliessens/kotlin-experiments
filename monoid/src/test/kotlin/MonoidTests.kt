
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MonoidTests {

    // DataSet          "a" , "dfg" ""
    // Binary Operation    "aa" + "v" = "aav"
    //  associative?        ("a" + "b") + "c" = "a" + ( "b" + "c" )
    //  identity?           ""

    @Test
    fun `string concat`() {
        val list = listOf("abc", "def", "ghi")

        /*
         * ----
         * identity e:   e + "abc" = "abc" + e = "abc"
        *
        * */

        val folded = list.fold("", { acc: String, next: String -> acc + next })

        assertEquals("abcdefghi", folded)
    }

//    DataSet [] ,["a"] ,[""] , ["s","dd"]
//    BinOps          ["a"] + ["b"] = ["a", "b"]
//    Associative?    (["a"] + ["b"]) + ["c"] = ["a"] + ( ["b"] + ["c"])
//    Identity        []

    @Test
    fun `list concatenation`() {
        val list = listOf(listOf("abc"), listOf("def"), listOf("ghi"))

        val folded = list.fold(mutableListOf<String>(), { acc, next ->
            acc.addAll(next)
            acc
        })

        assertEquals(listOf("abc", "def", "ghi"), folded)
    }

    @Test
    fun `list concatenation - variant 1`() {
        val list = listOf("abc", "def", "ghi")

        val folded = list.fold(mutableListOf<Int>(), { acc, next ->
            acc.add(stringToInt(next))
            acc
        })

        assertEquals(listOf(3, 3, 3), folded)
    }

    private fun stringToInt(s: String): Int {
        return s.length
    }

    @Test
    fun `list concatenation - variant 2`() {
        val list = listOf("", "def", "ghi")

        val folded = list.fold(mutableListOf<String>(), { acc, next ->
            if (hasLength(next)) {
                acc.add(next)
            }
            acc
        })

        assertEquals(listOf("def", "ghi"), folded)
    }

    private fun hasLength(s: String): Boolean {
        return s.isNotEmpty()
    }

    // DataSet              True and False
    // Binary Operation     && (AND operator)
    //      associative ?   (true && false) && false == true && (false && false)
    //      identity ?      true

    @Test
    fun `boolean conjunction - boolean AND `() {
        val list = listOf(true, true, false)

//        val folded = list.fold(true, { acc, next -> acc && predicate(next) })

//        assertFalse(folded)
    }

    private fun predicate(s: String): Boolean {
        return s.isBlank()
    }

    // DataSet              True and False
    // Binary Operation     || (OR operator)
    //      associative ?   (true || false) || false == true || (false || false)
    //      identity ?

    @Test
    fun `boolean disjunction - boolean OR`() {
        val list = listOf(false, false, true)

//        val result = list.fold(/*identify*/) { acc, item -> /*operation*/ }
    }
}
