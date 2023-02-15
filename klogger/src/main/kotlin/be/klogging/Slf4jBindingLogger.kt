package be.klogging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface KLogger {
    fun error(msg: () -> String)
    fun error(exception: Throwable, msg: () -> String)
    fun warn(msg: () -> String)
    fun warn(exception: Throwable, msg: () -> String)
    fun info(msg: () -> String)
    fun debug(msg: () -> String)
    fun trace(msg: () -> String)

    companion object {
        fun getLogger(implicitContext: () -> Unit): KLogger {
            when {
                isSlf4j() -> return Slf4jBindingLogger(LoggerFactory.getLogger(implicitContext::class.java.enclosingClass.name))
                else -> error("Class org.slf4j.LoggerFactory not found")
            }
        }
    }
}

private fun isSlf4j(): Boolean = runCatching {
    ClassLoader.getSystemClassLoader().loadClass("org.slf4j.LoggerFactory")
    Result.success(true)
}.isSuccess

private class Slf4jBindingLogger constructor(private val slf4jlog: Logger) : Logger by slf4jlog, KLogger {

    override fun error(msg: () -> String) {
        if (slf4jlog.isErrorEnabled) slf4jlog.error(msg())
    }

    override fun error(exception: Throwable, msg: () -> String) {
        if (slf4jlog.isErrorEnabled) slf4jlog.error(msg(), exception)
    }

    override fun warn(msg: () -> String) {
        if (slf4jlog.isWarnEnabled) slf4jlog.warn(msg())
    }

    override fun warn(exception: Throwable, msg: () -> String) {
        if (slf4jlog.isWarnEnabled) slf4jlog.warn(msg(), exception)
    }

    override fun info(msg: () -> String) {
        if (slf4jlog.isInfoEnabled) slf4jlog.info(msg())
    }

    override fun debug(msg: () -> String) {
        if (slf4jlog.isDebugEnabled) slf4jlog.debug(msg())
    }

    override fun trace(msg: () -> String) {
        if (slf4jlog.isTraceEnabled) slf4jlog.trace(msg())
    }

}
