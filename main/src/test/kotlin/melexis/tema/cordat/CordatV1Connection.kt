package melexis.tema.cordat

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.Properties

typealias RawConnectionFactory = (String, Properties) -> Connection

class CordatV1Connection(
    url: String,
    user: String,
    password: String,
    connectionFactory: RawConnectionFactory = DriverManager::getConnection,
) : AutoCloseable {
    private val connection: Connection
    private val properties = Properties()

    init {
        properties.setProperty("user", user)
        properties.setProperty("password", password)
        properties.setProperty("preferQueryMode", "simple")

        connection = connectionFactory(url, properties)
    }

    @Throws(SQLException::class)
    fun executeBatchQuery(queries: Array<String>): IntArray {
        val statement: Statement = connection.createStatement()
        queries.forEach { statement.addBatch(it) }
        return statement.executeBatch()
    }

    @Throws(SQLException::class)
    fun executeQuery(query: String): ResultSet {
        val statement: Statement = connection.createStatement()
        return statement.executeQuery(query)
    }

    @Throws(SQLException::class)
    fun executeUpdate(query: String): Int {
        val statement: Statement = connection.createStatement()
        return statement.executeUpdate(query)
    }

    @Throws(SQLException::class)
    fun executeCreateGrant(query: String): Boolean {
        val statement: Statement = connection.createStatement()
        return statement.execute(query)
    }

    fun beginTransaction() {
        connection.autoCommit = false
    }

    fun commitTransaction() {
        if (connection.autoCommit) return

        connection.commit()
        connection.autoCommit = true
    }

    fun rollbackTransaction() {
        if (connection.autoCommit) return

        connection.rollback()
        connection.autoCommit = true
    }

    override fun close() {
        connection.close()
    }
}
