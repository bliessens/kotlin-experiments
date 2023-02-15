package pricing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pricing.dsl.Country
import pricing.dsl.Price
import pricing.dsl.add
import pricing.dsl.pricing
import pricing.dsl.set

internal class PricingConfigTest {

    @Test
    internal fun basePriceIsMandatory() {
        assertThrows<UninitializedPropertyAccessException> { (Country.DE.pricing { }.total()) }
    }

    @Test
    internal fun germanVATIs19Percent() {
        val price = Country.DE.pricing {
            set base 100
            add tax vat
        }

        assertEquals(Price(119), price.total())
    }

    @Test
    internal fun germanTourismTaxIs5Percent() {
        val price = Country.DE.pricing {
            set base 100
            add tax tourism
        }

        assertEquals(Price(105), price.total())
    }

    @Test
    internal fun germanTotalPrice() {
        val price = Country.DE.pricing {
            set base 100
            add tax vat
            add tax tourism
        }

        assertEquals(Price(124), price.total())
    }

    @Test
    internal fun spanishVATIs21Percent() {
        val price = Country.ES.pricing {
            set base 100
            add tax vat
        }

        assertEquals(Price(121), price.total())
    }

    @Test
    internal fun spainTourismTaxIsZeoPercent() {
        val price = Country.ES.pricing {
            set base 100
            add tax tourism
        }

        assertEquals(Price(100), price.total())
    }

    @Test
    internal fun spainTotalPrice() {
        val price = Country.ES.pricing {
            set base 100
            add tax vat
            add tax tourism
        }

        assertEquals(Price(121), price.total())
    }
}

