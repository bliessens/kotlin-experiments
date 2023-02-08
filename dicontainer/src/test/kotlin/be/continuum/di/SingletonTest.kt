package be.continuum.di

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

interface Service
class ServiceImpl(val name: String = "") : Service

class Controller(val service: Service)

class SingletonTest {

    @Nested
    inner class SingletonTests {
        val app = context {
            singletonOf<Service> { ServiceImpl() }
        }

        @Test
        fun retrieveSingleton() {
            val service = DIContext.get<Service>()

            assertThat(service).isNotNull
        }

        @Test
        fun injectSingleton() {
            val service: Service by injectObject()

            assertThat(service).isNotNull
        }
    }

    @Nested
    inner class DependencyInjectionTests {

        val app = context {
            singletonOf<Service> { ServiceImpl() }
            singletonOf { Controller(get()) }
        }

        @Test
        fun injectDependency() {
            val controller: Controller by injectObject()

            assertThat(controller).isNotNull
                .hasNoNullFieldsOrProperties()
        }
    }

    @Nested
    inner class NamingTests {
        val app = context {
            singletonOf<Service>(name = "default") { ServiceImpl("default") }
            singletonOf<Service>(name = "secondary") { ServiceImpl("secondary") }
        }

        @Test
        fun injectSingletonWithQualifiedName() {
            val service: Service by injectObject(name = "default")
            val secondary: Service by injectObject(name = "secondary")

            assertThat(service).isNotNull
                .hasFieldOrPropertyWithValue("name", "default")

            assertThat(secondary).isNotNull
                .hasFieldOrPropertyWithValue("name", "secondary")
        }

        @Disabled
        @Test
        fun injectWithoutQualifier() {
            val service: Service by injectObject()

            assertThat(service).isNotNull
        }
    }
}
