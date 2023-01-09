package patterns_in_kotlin

// Chess game
// load state of game

val notation = listOf("pa3", "qc5")

fun main() {
    val gameState = notation.map { Piece.fromNotation(it) }

    println(gameState)
    println(loadGame(notation))
}

sealed class Piece(open val position: String) {
    companion object {
        fun fromNotation(piece: String): Piece {
            val pieceType = piece[0]
            val position = piece.drop(1)
            return when (pieceType) {
                'p' -> Pawn(position)
                'q' -> Queen(position)
                else -> error("Unknown piece!")
            }
        }
    }
}

data class Pawn(override val position: String) : Piece(position)
data class Queen(override val position: String) : Piece(position)

fun loadGame(notation: List<String>): List<Piece> {
    return notation.map { piece ->
        Piece.fromNotation(piece)
    }
}

//@JvmName("loadGameExtension")
//fun List<String>.loadGame(): List<Piece> {
//    return  loadGame(this)
//}