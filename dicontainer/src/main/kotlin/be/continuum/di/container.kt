package be.continuum.di

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

object DIContext {

    var singletons: Map<TypeInfo, Any> by Delegates.notNull()

    fun init(singletons: Map<TypeInfo, Any>) {
        DIContext.singletons = singletons.toMap()
    }

    inline fun <reified T> get(): T {
        return singletons[TypeInfo.typeOf(T::class)] as T
    }
}

sealed interface TypeInfo {
    companion object {
        fun typeOf(type: KClass<*>): TypeInfo {
            return UnNamedTypeInfo(type)
        }

        fun typeOf(type: KClass<*>, name: String): TypeInfo {
            when (name.isBlank()) {
                true -> return UnNamedTypeInfo(type)
                false -> return NamedTypeInfo(type, name)
            }
        }
    }
}

data class NamedTypeInfo(val type: KClass<*>, val name: String = "") : TypeInfo
data class UnNamedTypeInfo(val type: KClass<*>) : TypeInfo

inline fun <reified T> injectObject() =
    object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return DIContext.singletons[TypeInfo.typeOf(T::class)] as T
        }
    }

inline fun <reified T> injectObject(name: String = "") =
    object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return DIContext.singletons[TypeInfo.typeOf(T::class, name)] as T
        }
    }

fun context(init: DIContextBuilder.() -> Unit): DIContext {
    val cb = DIContextBuilder()
    cb.init()
    return cb.build()
}


class DIContextBuilder {

    var singletons = mutableMapOf<TypeInfo, Any>()

    inline fun <reified T> singletonOf(name: String = "", supplier: () -> T) {
        singletons[TypeInfo.typeOf(T::class, name)] = supplier() as Any
    }

    inline fun <reified T> get(): T {
        return singletons[TypeInfo.typeOf(T::class)] as T
    }

    fun build(): DIContext {
        DIContext.init(singletons.toMap())
        return DIContext
    }
}

