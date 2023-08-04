package melexis.tema.cordat

import org.junit.jupiter.api.Test
import java.sql.DriverManager

class AutoCloseableTest {

    @Test
    fun try_with_resources() {
        val sqlConnection by lazy { DriverManager.getConnection("jdbc:psql://localhost") }

        val result = sqlConnection.use {
            it.prepareStatement("SELECT * FROM FancyTable").executeQuery()
        }
    }

//    @Test
//    fun try_with_resources_with_CordatProvider() {
//        lateinit var provider: CordatProvider
//
//        val result = provider.use {
//
//        }
//    }
}
