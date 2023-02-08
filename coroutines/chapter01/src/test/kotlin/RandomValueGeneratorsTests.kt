import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RandomValuesTest {

    @Test
    fun testRandomInts() {
        val values = randomNumbers(10).take(10)

        for (number in values) {
            println(number)
        }
    }

    @Test
    fun testRandomIntsUpperBound() {
        val values = randomNumbers(10).take(200)

        assertThat(values.max()).isLessThanOrEqualTo(10)
    }

    @Test
    fun testRandomStrings() {
        val values = randomString(5).take(10)
        for (value in values) {
            assertThat(value).hasSize(5)
        }
    }

    @Test
    fun testRandomStringsAreDistinct() {
        val values = randomString(2).take(200)

        assertThat(values.distinct().count())
            .isEqualTo(values.count())
    }
}
