package melexis.tema.cordat

fun interface CordatV1ConnectionFactory {
    fun create(dbUrl: String, username: String, password: String): CordatV1Connection
}

const val TRANSACTION_BEGIN = "BEGIN"
const val TRANSACTION_COMMIT = "COMMIT"
const val TRANSACTION_ROLLBACK = "ROLLBACK"
const val DATABASE_NAME = "tpsqldb"
