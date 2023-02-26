package obfuscator

inline fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R {
    return { p1 -> { p2 -> this(p1, p2) } }
}

inline fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried(): (P1) -> (P2) -> (P3) -> R {
    return { p1 -> { p2 -> { p3 -> this(p1, p2, p3) } } }
}

inline fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).curried(): (P1) -> (P2) -> (P3) -> (P4) -> R {
    return { p1 -> { p2 -> { p3 -> { p4 -> this(p1, p2, p3, p4) } } } }
}