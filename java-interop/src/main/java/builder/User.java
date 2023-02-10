package builder;

public class User {

    private final String firstName;
    private final String lastName;
    private final String email;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static EMailBuilder builder() {
        return new AdvancedBuilder();
    }

    public static class AdvancedBuilder implements EMailBuilder, FirstNameBuilder, LastNameBuilder {
        private String email;
        private String firstName;
        private String lastName;

        private AdvancedBuilder() {
        }

        @Override
        public FirstNameBuilder email(String email) {
            this.email = email;
            return this;
        }

        @Override
        public LastNameBuilder firstName(String name) {
            this.firstName = name;
            return this;
        }

        @Override
        public AdvancedBuilder lastName(String name) {
            this.lastName = name;
            return this;
        }

        public User build() {
            return new User(firstName, lastName, email);
        }
    }

    public interface EMailBuilder {
        FirstNameBuilder email(String email);
    }

    public interface FirstNameBuilder {
        LastNameBuilder firstName(String name);
    }

    public interface LastNameBuilder {
        AdvancedBuilder lastName(String name);
    }
}
