package existech

import me.alllex.parsus.parser.getOrThrow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParserTest {


    @Test
    fun parseHeaderName() {
        val marker = STAGrammar.parse(
            """
                ======
                  Section Name
                =====
                """.trimIndent()
        ).getOrThrow()
        assertThat(marker).isEqualTo(Header("Section Name"))
    }

    @Test
    fun parseSection() {
        val header = STAGrammar.parse(
            """
====================================================================
                      Statistic 2023_04_19_215256 Auto Summary.STA
====================================================================
Machine ID:9002327
            """.trimIndent().replace("\n", "")
        ).getOrThrow()

        assertThat(header).isEqualTo(Header("Statistic 2023_04_19_215256 Auto Summary.STA"))
    }
}