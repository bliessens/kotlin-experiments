package melexis.tema.cordat

import java.sql.ResultSet
import java.sql.SQLException

class CordatV1Provider02(
    private val appConfig: AppConfig,
    private val connectionFactory: CordatV1ConnectionFactory = CordatV1ConnectionFactory(::CordatV1Connection),
) : CordatProvider {

    private lateinit var cordatV1Connection: CordatV1Connection

    override fun terminate() {
        if (this::cordatV1Connection.isInitialized) {
            cordatV1Connection.close()
        }
    }

    /**
     * Called specifying the database the client wants to use.
     *
     * @param database the database name.
     * @return true if the database exists and the current user can connect to
     * it.
     */
    fun createDbConnection(): Boolean {
        val connectionUrl = "jdbc:postgresql://${appConfig.cordatV1HostPort}/$DATABASE_NAME"
        return try {
            terminate()
            cordatV1Connection =
                connectionFactory.create(connectionUrl, "tpsqluser", appConfig.tpsqluserPassword.trim())
            true
        } catch (e: Exception) {
            println("Got error on connection creation")
            println(e.stackTraceToString())
            false
        }
    }

    private fun <T> ensureCordatV1Connection(block: (cordatV1Connection: CordatV1Connection) -> T) =
        (cordatV1Connection ?: throw IllegalStateException("No open connection")).let(block)

    override fun getResult(query: String): ResultSet = ensureCordatV1Connection {
        return@ensureCordatV1Connection try {
            TODO("omitted for brevity")
        } catch (e: SQLException) {
            val errorMessage = e.message ?: "Unknown error"
            TODO("omitted for brevity")
        }
    }

    override fun getResult(queries: Array<String>): List<ResultSet> {
        TODO("omitted for brevity")
    }

    override fun getTransactionLifeCycleEvent(transactionLifeCycle: String) =
        ensureCordatV1Connection { cordatV1Connection ->
            when (transactionLifeCycle.uppercase()) {
                TRANSACTION_BEGIN -> cordatV1Connection.beginTransaction()
                TRANSACTION_COMMIT -> cordatV1Connection.commitTransaction()
                TRANSACTION_ROLLBACK -> cordatV1Connection.rollbackTransaction()
                else -> println("Noop due to unknown transactionLifeCycle value: $transactionLifeCycle")
            }
        }

    private fun getQueryCommand(query: String): ResultSet {
        TODO("omitted for brevity")
    }
}
