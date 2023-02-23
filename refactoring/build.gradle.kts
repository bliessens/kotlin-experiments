plugins {
    id("com.melexis.java-conventions")
}

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.springframework:spring-core:5.3.23")
    implementation("org.springframework.boot:spring-boot-actuator:2.7.5") {
        isTransitive = false
    }
    implementation("org.slf4j:slf4j-api:2.0.4")
}