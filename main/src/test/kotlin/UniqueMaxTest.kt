import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UniqueMaxTest {

    val foos = listOf(
        Foo("one", 1), Foo("two", 2), Foo("one", 2),
        Foo("two", 3), Foo("three", 1)
    )

    @Test
    fun shouldHaveUniqueSetByBarWithMaxBaz() {
        val result: Collection<Foo> by lazy {
            foos.groupBy { it.bar }
                .map { entry -> entry.key to entry.value.maxBy { it.baz } }
                .map { it: Pair<String, Foo> -> it.second }
                .toList()
        }

        assertThat(result)
            .containsExactly(
                Foo("one", 2),
                Foo("two", 3),
                Foo("three", 1)
            )
    }
}


data class Foo(val bar: String, val baz: Int)