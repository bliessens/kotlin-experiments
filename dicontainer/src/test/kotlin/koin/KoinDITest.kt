package koin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

interface Repository
class InMemoryRepository : Repository
class Service(val a: Repository)

class ApplicationUnderTest : KoinComponent {
    val service: Service by inject()
    override fun toString(): String {
        return "Application with $service"
    }
}

class KoinDITest {

    @Test
    fun testKoinAwareInjection() {
        startKoin {
            modules(
                module {
                    single<Repository> { InMemoryRepository() }
                    single { Service(get()) }
                },
            )
        }

        val inner = ApplicationUnderTest()
        println(inner)
        assertThat(inner.service).isNotNull
    }
}
