package builder

fun user(creator: UserBuilder.() -> Unit): User = UserBuilder().apply(creator).build()

fun User.displayName() = "${this.firstName}, ${this.lastName}"

class UserBuilder {
    private lateinit var email: String
    private lateinit var firstName: String
    private lateinit var lastName: String

    fun email(value: String) {
        this.email = value
    }

    fun firstName(value: String) {
        this.firstName = value
    }

    fun lastName(value: String) {
        this.lastName = value
    }

    fun build(): User {
        requireNotNull(email)
        requireNotNull(firstName)
        requireNotNull(lastName)
        return User(firstName, lastName, email)
    }
}
