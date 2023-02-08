import kotlin.random.Random

fun randomNumbers(
    max: Int = Int.MAX_VALUE,
    seed: Long = System.currentTimeMillis(),
) = sequence {
    val random = Random(seed)
    while (true) {
        yield(random.nextInt(max))
    }
}

fun randomString(
    length: Int,
    seed: Long = System.currentTimeMillis(),
) = sequence {
    val random = Random(seed)
    val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    while (true) {
        (1..length)
            .map { random.nextInt(charPool.size) }
            .map(charPool::get)
            .joinToString(separator = "")
            .also {
                yield(it)
            }
    }
}.distinct()
