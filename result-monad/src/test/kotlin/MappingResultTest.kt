import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MappingResultTest {


    val error = runCatching<String> {
        throw RuntimeException("test case error")
    }

    @Test
    fun `map() is only invoked on Success`() {
        var mapInvoked = false

        val outcome = error.map {
            mapInvoked = true
            it.uppercase()
        }.recover {
            it.message
        }

        assertThat(mapInvoked).isFalse()
        assertThat(outcome.isSuccess).isTrue()
        assertThat(outcome.getOrNull()).isEqualTo("test case error")
    }

    @Test
    fun `recover() is only invoked on Failure`() {
        var recoverInvoked = false

        val outcome = error.map {
            it.uppercase()
        }.recover {
            recoverInvoked = true
            it.message
        }

        assertThat(recoverInvoked).isTrue()
        assertThat(outcome.isSuccess).isTrue()
        assertThat(outcome.getOrNull()).isEqualTo("test case error")
    }

    @Test
    fun `recoverCatching() is only invoked on Failure`() {
        var recoverInvoked = false

        val outcome = error.map {
            it.uppercase()
        }.recoverCatching {
            recoverInvoked = true
            throw IllegalStateException("recovery error")
        }

        assertThat(recoverInvoked).isTrue()
        assertThat(outcome.isFailure).isTrue()
        assertThat(outcome.getOrNull()).isNull()
        assertThat(outcome.exceptionOrNull()).isInstanceOf(IllegalStateException::class.java)
    }

}