plugins {
    id("com.melexis.java-conventions")
    id("java")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}