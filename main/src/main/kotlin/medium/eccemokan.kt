package medium

import java.io.File

/**
 * https://medium.com/@ecemokan/creational-design-patterns-with-kotlin-3cbc630402c2
 */
data class Address(
    var phone: String,
    var title: String,
    var no: String,
    var city: String
) {
    private constructor(builder: Builder) : this(
        builder.phone,
        builder.title,
        builder.no,
        builder.city
    )

    class Builder {
        lateinit var phone: String
        lateinit var title: String
        lateinit var no: String
        lateinit var city: String

        fun phone(init: Builder.() -> String) = apply { phone = init() }
        fun title(init: Builder.() -> String) = apply { title = init() }
        fun no(init: Builder.() -> String) = apply { no = init() }
        fun city(init: Builder.() -> String) = apply { city = init() }
    }

    companion object {
        fun build(init: Builder.() -> Unit) = Address(Builder().apply(init))
    }
}

fun main() {
    val address = Address.build {
        phone = "0 (555) 555 55 55"
        title = "New Address"
        no = "17A"
        city = "NewYork"
    }


    println(address)
    val file = File("src/main/kotlin/medium/eccemokan.kt")
    println("Extension of '${file}' is '${file.fileExtension()}'")

    println("334".length.times(3))

}


fun File.fileExtension(): String {
    return this.name.substring(this.name.lastIndexOf('.') + 1, this.name.length)
}

