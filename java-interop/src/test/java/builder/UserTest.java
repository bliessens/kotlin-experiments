package builder;

import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void advancedBuilder() {
        User user = User.builder()
                .email("benoit@liessens.be")
                .firstName("benoit")
                .lastName("liessens")
                .build();

        assertUser(user);
    }

    public static void assertUser(User user) {
        assertEquals("benoit", user.getFirstName());
        assertEquals("liessens", user.getLastName());
        assertEquals("benoit@liessens.be", user.getEmail());
    }

    @Test
    void kotlinMethods() {
        User user = newSampleUser();

        assertUser(user);
    }

    @NotNull
    private static User newSampleUser() {
        return UserBuilderKt.user(userBuilder -> {
            userBuilder.firstName("benoit");
            userBuilder.lastName("liessens");
            userBuilder.email("benoit@liessens.be");
            return Unit.INSTANCE;
        });
    }

    @Test
    void testExtensionMethod() {
        User user = newSampleUser();

        assertThat(UserBuilderKt.displayName(user)).isEqualTo("benoit, liessens");
    }
}