package builder

import builder.UserTest.assertUser
import org.junit.jupiter.api.Test

class UserKtTest {

    @Test
    fun testUser() {
        val user = user {
            firstName("benoit")
            lastName("liessens")
            email("benoit@liessens.be")
        }
        assertUser(user)
    }
}