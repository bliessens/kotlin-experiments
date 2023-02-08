package kodein

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

interface Repository
class InMemoryRepository : Repository
class DIAwareService(override val di: DI) : DIAware {
    val repo: Repository by instance()
}

class KodeinAwareTest {

    val diModule = DI {
        bind<Repository> { singleton { InMemoryRepository() } }
    }

    @Test
    fun testKodeinCon() {
        val s = DIAwareService(diModule)

        assertThat(s.repo).isNotNull
    }
}
