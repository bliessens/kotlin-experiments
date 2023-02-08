package patterns_in_kotlin

data class Request(val endpoint: String)
data class Response(val body: String)

object Logger {
    fun log(s: String): Unit = println("[LOGGER]: $s")
}

object Cache {
    private val cache = mutableMapOf<Request, Response>()

    fun put(request: Request, response: Response) {
        cache += request to response
    }

    fun get(request: Request): Response? {
        println("Cache hit!")
        return cache[request]
    }
}

interface Processor {
    fun process(request: Request): Response
}

/**
 * this is a Decorator because it does not change behaviour
 */
class LoggingProcessor(val logger: Logger, val processor: Processor) : Processor {
    override fun process(request: Request): Response {
        logger.log(request.toString())
        return processor.process(request)
    }

    companion object {
        fun forProcessor(processor: Processor): LoggingProcessor {
            return LoggingProcessor(Logger, processor)
        }
    }
}

/**
 * this is a Proxy because behaviour changes (delegating processor is NOT always invoked) depending on cache hit or mis-hit
 */
class CacheProcessor(val cache: Cache, val processor: Processor) : Processor {
    override fun process(request: Request): Response {
        return cache.get(request) ?: run {
            val response = processor.process(request)
            cache.put(request, response)
            response
        }
    }
}

class RequestProcessor : Processor {
    override fun process(request: Request): Response {
        return Response("you called ${request.endpoint}")
    }
}

fun main() {
    val request = Request("https://example.com")
    val processor: Processor = CacheProcessor(
        Cache,
        LoggingProcessor.forProcessor(
            RequestProcessor(),
        ),
    )
    println(processor.process(request))
    println(processor.process(request))
}
