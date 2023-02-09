package kodein

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

interface StatefulClient {
    fun doRequest(): String
}

class StatefulClientImpl : StatefulClient {
    init {
        instanceCounter++
    }

    companion object {
        var instanceCounter = 0
    }

    override fun doRequest(): String = "[StatefulClientImpl.$instanceCounter]"
}

class ServiceFacade(val client: StatefulClient) {
    fun callClient(): String {
        return client.doRequest()
    }
}

class ClientProxy(val di: DI) : StatefulClient {
    override fun doRequest(): String {
        val delegate by di.instance<StatefulClient>()
        return delegate.doRequest()
    }
}

class KodeinProxyTest {

    @BeforeEach
    fun setUp() {
        StatefulClientImpl.instanceCounter = 0
    }

    @Nested
    inner class SplitDIContext {
        val clientDI = DI {
            bind<StatefulClient> { provider { StatefulClientImpl() } }
        }

        val di = DI {
            bind { singleton { ClientProxy(clientDI) } }
            bind { singleton { ServiceFacade(instance()) } }
        }

        @Test
        fun testNewStatefulClientForEachRequest() {
            val iterations = 5
            val facade by di.instance<ServiceFacade>()

            repeat(iterations) { facade.callClient() }

            assertThat(StatefulClientImpl.instanceCounter).isEqualTo(iterations)
        }
    }

    @Nested
    inner class InjectDIContext {

        val unified = DI {
            bind<StatefulClient> { provider { StatefulClientImpl() } }
            bind<StatefulClient>(tag = "proxy") { singleton { ClientProxy(di) } }
            bind { singleton { ServiceFacade(instance(tag = "proxy")) } }
        }

        @Test
        fun testNewStatefulClientForEachRequest() {
            val iterations = 5
            val facade by unified.instance<ServiceFacade>()

            repeat(iterations) { facade.callClient() }

            assertThat(StatefulClientImpl.instanceCounter).isEqualTo(iterations)
        }

    }
}
