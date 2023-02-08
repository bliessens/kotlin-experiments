package kodein

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.delegate
import org.kodein.di.factory
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.provider
import org.kodein.di.singleton
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

interface IComp
class CompA : IComp
class CompB(val a: CompA) {
    override fun toString(): String {
        return "B references $a"
    }
}

class KodeinDITest {

    @Test
    fun bindImplementationToInterfaceType() {
        val di = DI {
            bindSingleton<IComp> { CompA() }
        }

        val comp by di.instance<IComp>()

        assertThat(comp).isNotNull
    }

    @Test
    fun cannotResolveUnboundInterface() {
        val di = DI {
            singleton { CompA() }
        }

        assertThrows<DI.NotFoundException> {
            val comp by di.instance<IComp>()
            comp.hashCode()
        }
    }

    @Nested
    inner class LazyCreation {
        private val created = AtomicBoolean(false)

        inner class Flag {
            init {
                created.set(true)
            }
        }

        @Test
        fun testEagerSingletonCreation() {
            val di = DI {
                bindSingleton { Flag() }
            }

            assertThat(created).isFalse
            val flag by di.instance<Flag>()
            flag.hashCode()
            assertThat(created).isTrue
        }
    }

    interface Builder
    inner class ABuilder : Builder

    @Nested
    inner class NonSingletons {

        val di = DI {
            bindProvider<Builder> { ABuilder() }
        }

        @Test
        fun name() {
            val b1 = di.instance<Builder>()
            val b2 = di.instance<Builder>()
            assertThat(b1).isNotSameAs(b2)
        }
    }

    interface DataSource

    class SimpleDS(val name: String) : DataSource {
        override fun toString(): String = "DataSource($name)"
    }

    @Nested
    inner class QualifiedObjects {
        val di = DI {
            bindSingleton<DataSource>(tag = "local") { SimpleDS("local") }
            bindSingleton<DataSource>(tag = "remote") { SimpleDS("remote") }
        }

        @Test
        fun shouldResolveLocalDataSource() {
            val ds by di.instance<DataSource>("local")

            assertThat(ds).asString().isEqualTo("DataSource(local)")
        }
    }

    interface Inner

    class Outer(val inner: Inner) {
        override fun toString(): String {
            return "Outer with $inner"
        }
    }

    class Outer2(val inner: Inner) {
        override fun toString(): String {
            return "Outer() with $inner"
        }
    }

    class InnerImpl : Inner

    @Nested
    inner class ClashingScopes {
        val di = DI {
            bind<Inner> { provider { InnerImpl() } }
            bind { singleton { Outer(instance()) } }
            bind<Outer2> { provider { Outer2(instance()) } }
        }

        @Test
        fun singletonWithInjectedProvider() {
            val outer by di.instance<Outer2>()

            println(outer)
            println(outer)
        }

        @Test
        fun providerWithInjectedProvider() {
            val outer by di.instance<Outer2>()
            val outer2 by di.instance<Outer2>()

            println(outer)
            println(outer2)
        }
    }

    @Nested
    inner class ModuleVsDi {
        val module = DI.Module(name = "base", prefix = "a") {
            bindSingleton { CompA() }
            delegate<IComp>().to<CompA>()
        }
        val context = DI {
            import(module)
            bindSingleton { CompB(instance()) }
        }

        @Test
        fun injectFromModule() {
            val comp by context.instance<IComp>()

            assertThat(comp).isNotNull
        }

        @Test
        fun injectComponent() {
            for ((type, bind) in context.container.tree.bindings) {
                println("$type => ${bind.map { it.binding.description }.joinToString()}")
            }
        }
    }

    class RandomDice(val nrOfSides: Int) {
        val value: Int by lazy { random.nextInt(nrOfSides) }

        companion object {
            val random = Random(System.currentTimeMillis())
        }

        override fun toString(): String {
            return "Dice($value/$nrOfSides)"
        }
    }

    @Nested
    inner class FactoryTest {
        val di = DI {
            bind<RandomDice> { factory { sides: Int -> RandomDice(sides) } }
        }

        @Test
        fun testInjectFactory() {
            val diceFactory: (Int) -> RandomDice by di.factory()

            val dice = List(600) { diceFactory(4) }

            dice.foldRight(setOf<RandomDice>()) { currentDice, acc ->
                assertThat(acc).doesNotContain(currentDice)
                acc + setOf(currentDice)
            }
        }
    }

    @Nested
    inner class MultitonTest {
        val di = DI {
            bind<RandomDice> { multiton { sides: Int -> RandomDice(sides) } }
        }

        @Test
        fun testMultitonBinding() {
            val diceFactory: (Int) -> RandomDice by di.factory()

            val dice5 = diceFactory(5)
            val alsoDice5 = diceFactory(5)
            val otherDice = diceFactory(2)

            assertThat(dice5)
                .isSameAs(alsoDice5)
                .isNotSameAs(otherDice)
        }
    }
}
