package functional.programming

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StreamOpsWithFold {

    private val isEven: (Int) -> Boolean = { it.mod(2) == 0 }

    @Test
    fun sumIntegersWithFold() {
        val list = listOf(1, 2, 3)

        val actual = list.fold(0, { acc: Int, item: Int -> acc + item })
        // fold(0)  { acc=0, item=1 -> 1 }
        // fold( )  { acc=1, item=2 -> 3 }
        // fold( )  { acc=3, item=3 -> 6 }
        // fold(X)  { acc=X, item=3 -> X }

        assertThat(actual).isEqualTo(6)
    }

    /**
     * DataSet = Integer/Double
     * identity = 0.0
     * BinOps = +
     */
    @Test
    fun testTypeSwitch() {
        val list = listOf(1, 2, 3)

        val actual = list.fold(0.toDouble()) { acc: Double, item: Int -> acc + item }
        // list.fold( 0.0 ) { acc=0.0, item=1 -> 1.0 }
        // list.fold(     ) { acc=1.0, item=2 -> 3.0 }
        // list.fold(     ) { acc=3.0, item=3 -> 6.0 }

        assertThat(actual).isEqualTo(6.toDouble())
    }

    // DataSet: List of Ints
    // BinOps: concat lists
    // identity = empty list of Ints
    @Test
    fun testMapAsFold() {
        val list = listOf(2, 3, 4)

        val actual = list.fold(listOf<Int>()) { acc, item -> acc + listOf(item * item) }

        assertThat(actual).containsExactly(4, 9, 16)
    }

    @Test
    fun testFilterAsMap() {
        val list = listOf(1, 2, 3)

        val actual = list.fold(listOf<Int>()) { acc, item -> if (isEven(item)) acc + listOf(item) else acc }

        assertThat(actual).containsExactly(2)
    }


    @Test
    fun testAllMatch() {
        val list = listOf(1, 3, 5, 7, 9)

        val actual = list.fold(true) { acc, item -> acc && !isEven(item) }

        assertThat(actual).isTrue()
    }


    @Test
    fun testAnyMatch() {
        val list = listOf(2, 4, 5, 6, 8)
        val isOdd: (Int) -> Boolean = { it.mod(2) != 0 }

        val actual: Boolean = list.fold(false) { acc, item -> acc || isOdd(item) }

        assertThat(actual).isTrue()
    }

    @Test
    fun testListLength() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        val actual = list.fold(0) { acc, next -> acc + 1 }

        assertThat(actual).isEqualTo(9)
    }


    @Test
    fun testTakeFirstN() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        val actual = list.fold(listOf<Int>()) { acc, next -> if (acc.size < 5) acc + listOf(next) else acc }

        assertThat(actual).containsExactly(1, 2, 3, 4, 5)
    }

    @Test
    fun testTakeDropN() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        val actual = list.fold(listOf<Int>()) { acc, next -> if (acc.size + 1 == 5) listOf() else acc + listOf(next) }

        assertThat(actual).containsExactly(6, 7, 8, 9)
    }
}