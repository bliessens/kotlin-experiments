package containers.tree

sealed interface Item<T>
class None<T> : Item<T>
data class Node<T>(
    var left: Item<T> = None(),
    val value: T,
    var right: Item<T> = None(),
) : Item<T>

class BinTree<T : Comparable<T>>(value: T) : Iterable<T> {
    private var root: Node<T>

    init {
        root = Node(value = value)
    }

    fun add(value: T): Boolean {
        return add(root, value)
    }

    fun add(root: Node<T>, value: T): Boolean {
        return if (value < root.value) {
            return addLeft(root, value)
        } else if (value > root.value) {
            return addRight(root, value)
        } else {
            // item already in tree
            return false
        }
    }

    private fun addLeft(node: Node<T>, value: T): Boolean {
        return if (node.left is None<T>) {
            node.left = Node(value = value)
            true
        } else {
            return add(node.left as Node<T>, value)
        }
    }

    private fun addRight(node: Node<T>, value: T): Boolean {
        return if (node.right is None<T>) {
            node.right = Node(value = value)
            true
        } else {
            return addRight(node.right as Node<T>, value)
        }
    }

    fun traverse(block: (T) -> Unit) {
        traverse(root, block)
    }

    override fun iterator(): Iterator<T> {
        return buildList<T> {
            traverse(root) { this.add(it) }
        }.iterator()
    }

    private fun <R> traverse(root: Node<T>, block: (T) -> R) {
        if (root.left is Node<T>) {
            traverse(root.left as Node<T>, block)
        }

        block(root.value)

        if (root.right is Node<T>) {
            traverse(root.right as Node<T>, block)
        }
    }

}

fun <T : Comparable<T>> bintree(value: T) = BinTree<T>(value)

fun <T : Comparable<T>> bintree(vararg values: T): BinTree<T> {
    return BinTree(values.first()).apply {
        values.drop(1).forEach { add(it) }
    }
}
