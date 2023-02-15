package pricing.dsl

enum class Country {
    ES, DE
}

fun pricingOf(country: Country, init: PricingConfig.() -> Unit): PricingConfig {
    return PricingConfig.forCountry(country).apply(init)
}

fun Country.pricing(init: PricingConfig.() -> Unit): PricingConfig {
    return PricingConfig.forCountry(this).apply(init)
}

object add
object set

abstract class PricingConfig {

    lateinit var basePrice: Price
    val taxes = mutableListOf<Tax>()

    abstract val vat: Tax
    open val tourism = Tax(0.0)

    fun total(): Price {
        return taxes.map { tax -> basePrice * tax }
            .fold(basePrice, { intermediateSum, tax -> intermediateSum + tax })
    }

    infix fun set.base(price: Int) = base(price.toDouble())
    infix fun set.base(price: Double) = Price(price).also { basePrice = it }

    infix fun add.tax(tax: Tax) {
        taxes.add(tax)
    }

    companion object {
        fun forCountry(country: Country): PricingConfig {
            when (country) {
                Country.ES -> return Spain()
                Country.DE -> return Germany()
            }
        }
    }
}

data class Tax(val percentage: Double)
data class Price(val price: Double) {

    constructor(price: Int) : this(price.toDouble())

    operator fun times(tax: Tax): Price {
        return Price(price * tax.percentage)
    }

    operator fun plus(price: Price): Price {
        return Price(this.price + price.price)
    }

}

class Spain : PricingConfig() {
    override val vat: Tax
        get() = Tax(0.21)
}

class Germany : PricingConfig() {
    override val vat: Tax
        get() = Tax(0.19)

    override val tourism: Tax
        get() = Tax(0.05)
}
