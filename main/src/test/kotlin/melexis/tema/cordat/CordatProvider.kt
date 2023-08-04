package melexis.tema.cordat

import java.sql.ResultSet

interface CordatProvider /*: AutoCloseable*/ {
    fun getResult(query: String): ResultSet
    fun getResult(queries: Array<String>): List<ResultSet>
    fun getTransactionLifeCycleEvent(transactionLifeCycle: String)
    fun terminate()

//    override fun close() {
//        terminate()
//    }
}
