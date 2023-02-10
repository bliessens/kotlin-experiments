package builder


fun user(creator: UserBuilder.() -> Unit): User {
    return UserBuilder().apply(creator).build()
}

fun User.displayName() = "${this.firstName}, ${this.lastName}"

class UserBuilder {

    lateinit private var email: String
    lateinit private var firstName: String
    lateinit private var lastName: String

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

