package containers.tree

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BinTreeTest {
    @Test
    fun addElementsToTree() {
        val tree = bintree(5)

        assertThat(tree.add(5)).isFalse()
        assertThat(tree.add(4)).isTrue()
        assertThat(tree.add(6)).isTrue()
        assertThat(tree.add(3)).isTrue()
        assertThat(tree.add(2)).isTrue()
    }

    @Test
    fun traverseInOrder() {
        val tree = bintree(5, 4, 6, 3, 7, 2, 8, 9)

        val list = buildList<Int> {
            tree.traverse() { this.add(it) }
        }

        assertThat(list)
            .containsExactly(2, 3, 4, 5, 6, 7, 8, 9)
    }

    @Test
    fun testIterator() {
        val tree = bintree(2, 3, 4, 5, 6, 7, 8, 9)

        assertThat(
            tree
                .map { it * 2 }
                .all { it % 2 == 0 }
        ).isTrue()

        assertThat(tree.fold(0) { acc, item -> acc + item }).isEqualTo(44)
    }
}
