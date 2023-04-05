package functional.programming

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MapMonoidTest {

    object ListConcatenationMonoid {

        operator fun invoke(acc: List<Int>, next: Int) = acc + listOf(next)

        fun identity(): List<Int> =
            emptyList()
    }

    @Test
    fun testMapAsFold() {
        val list = listOf(1, 2, 3)

        val actual: List<Int> =
            list.fold(
                ListConcatenationMonoid.identity(),
                ListConcatenationMonoid::invoke,
            )

        assertThat(actual).containsExactly(1, 4, 9)
    }
}
