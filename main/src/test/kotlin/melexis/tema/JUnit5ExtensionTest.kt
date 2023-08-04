package melexis.tema

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.RegisterExtension

class JUnit5ExtensionTest {

//    companion object {
//        @JvmField
//        @RegisterExtension
//        val rule = SampleClassRule()
//    }

    @JvmField
    @RegisterExtension
    val rule = SampleRule()

    @Test
    fun unitTest() {
        println("Unit test")
    }

    @Test
    fun unitTest2() {
        println("Unit test2")
    }

    class SampleRule : BeforeEachCallback, AfterEachCallback {
        override fun beforeEach(context: ExtensionContext?) {
            println("Starting Rule")
        }

        override fun afterEach(context: ExtensionContext?) {
            println("Stopping rule")
        }
    }

    class SampleClassRule : BeforeAllCallback, AfterAllCallback {
        override fun beforeAll(context: ExtensionContext?) {
            println("Starting class Rule")
        }

        override fun afterAll(context: ExtensionContext?) {
            println("Stopping class Rule")
        }
    }
}