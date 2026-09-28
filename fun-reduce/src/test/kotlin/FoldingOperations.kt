import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoldingOperations {
    @Test
    fun filter() {
        val source = listOf(1, 2, 3, 4, 5)
        val isEven: (Int) -> Boolean = { it % 2 == 0 }

        assertThat(
            source.fold(listOf<Int>()) { acc, i ->
                if (isEven(i)) {
                    acc + listOf(i)
                } else {
                    acc
                }
            },
        ).isEqualTo(source.filter(isEven))
    }

    @Test
    fun map() {
        val source = listOf(1, 2, 3, 4, 5)
        val square: (Int) -> Int = { it * it }

        assertThat(
            source.fold(listOf<Int>()) { acc, i ->

                acc + listOf(square(i))
            },
        ).isEqualTo(source.map(square))
    }
}
