import org.junit.Test

class JavaInvariant {


    @Test
    fun kotlinIsCovariant() {
        val stringNames: List<String> = listOf("benoit", "erle", "anae")
        val charNames = stringNames
            .map { name: String? -> name as CharSequence }
            .toList()

        collectionCall(stringNames)
        collectionCall(charNames)

    }

    private fun collectionCall(col: Collection<CharSequence>) {

    }

    @Test
    fun test1() {
        val stringNames: List<String> = listOf("benoit", "erle", "anae")
        val charNames = stringNames
            .map { name: String? -> name as CharSequence }
            .toList()

        genericCollectionCall(stringNames)
        genericCollectionCall(charNames)

    }

    fun <T : CharSequence> genericCollectionCall(col: Collection<T>) {

    }

    class TextPrinter< /*in*/ /*out*/ T> {

        fun doWith(values: Collection<T>) {

        }

        fun other(): List<T> {
            return emptyList()
        }

    }
}