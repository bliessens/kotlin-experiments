package melexis.tema.cordat

import java.sql.ResultSet
import java.sql.SQLException

class CordatV1Provider03(
    private val appConfig: AppConfig,
    private val connectionFactory: CordatV1ConnectionFactory = CordatV1ConnectionFactory(::CordatV1Connection),
) : CordatProvider {

    private val connectionUrl = "jdbc:postgresql://${appConfig.cordatV1HostPort}/$DATABASE_NAME"

    private val cordatV1Connection: CordatV1Connection by lazy {
        connectionFactory.create(connectionUrl, "tpsqluser", appConfig.tpsqluserPassword.trim())
    }

    override fun terminate() {
        cordatV1Connection.close()
    }

    private fun <T> ensureCordatV1Connection(block: (cordatV1Connection: CordatV1Connection) -> T) =
        cordatV1Connection.let(block)

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
