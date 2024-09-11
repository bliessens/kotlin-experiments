package be.klogging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.testUtil.StringListAppender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val topLevelLogger = KLogger.getLogger { } as Logger

object Singleton {
    val objectLogger = KLogger.getLogger { } as Logger
}

@Disabled
class Slf4jBindingLoggerTest {
    val logger = KLogger.getLogger { } as Logger

    @Test
    fun testNameOfClassLoggerIsClassName() {
        assertThat(logger.name).isEqualTo(Slf4jBindingLoggerTest::class.qualifiedName)
    }

    @Test
    fun testNameOfTopLevelLoggerIsKotlinClassName() {
        assertThat(topLevelLogger.name).isEqualTo(Slf4jBindingLoggerTest::class.qualifiedName + "Kt")
    }

    @Test
    fun testNameOfMethodLoggerIsClassName() {
        val methodLogger = KLogger.getLogger { } as Logger

        assertThat(methodLogger.name).isEqualTo(Slf4jBindingLoggerTest::class.qualifiedName)
    }

    @Test
    fun testNameOfTaggedLogger() {
        val methodLogger = KLogger.getLogger(tag = "myTag") { } as Logger

        assertThat(methodLogger.name).isEqualTo(Slf4jBindingLoggerTest::class.qualifiedName + ".[myTag]")
    }

    @Test
    fun testNameOfObjectLoggerIsObjectName() {
        assertThat(Singleton.objectLogger.name).isEqualTo(Singleton::class.qualifiedName)
    }

    @Test
    fun testNameOfCompanionLoggerIsClassName() {
        assertThat(companionLogger.name).isEqualTo(Slf4jBindingLoggerTest::class.qualifiedName)
    }

    companion object {
        val companionLogger = KLogger.getLogger { } as Logger
    }

    @Test
    fun testParamValues() {
        val root = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as ch.qos.logback.classic.Logger
        val lc = LoggerFactory.getILoggerFactory() as LoggerContext

        val consoleAppender = ConsoleAppender<ILoggingEvent>().apply {
            encoder = PatternLayoutEncoder().apply {
                pattern = "%msg%n"
                context = lc
                start()
            }
            context = lc
            target = "System.out"
            name = "console"
            start()
        }
        val listAppender = StringListAppender<ILoggingEvent>().apply {
            layout = PatternLayout().apply {
                context = lc
                pattern = "%level %msg"
                start()
            }
            context = lc
            name = "lsitAppender"
            start()
        }
        root.level = Level.DEBUG
        root.addAppender(consoleAppender)
        root.addAppender(listAppender)

        val contextVar = "some value"
        (logger as KLogger).warn { "OK" }
        (logger as KLogger).warn { "with ${contextVar}" }
        (logger as KLogger).trace { "not visible ${contextVar}" }

        assertThat(listAppender.strList)
            .hasSize(2)
            .containsExactly("WARN OK", "WARN with some value")
    }
}

