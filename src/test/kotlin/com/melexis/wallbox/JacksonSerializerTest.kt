package com.melexis.wallbox

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class JacksonSerializerTest {

    val ser = JacksonSerializer(clock = Clock.fixed(Instant.now(), ZoneId.systemDefault()))

    val json = "{\"wallBox\":{\"value\":\"123456\"}}"

    @Test
    fun toJSON() {
        val actual: Serialized = ser.serialize(WallBoxRegisteredEvent(WallBoxId("123456")))

        assertThat(actual.payload).isEqualTo(json.toByteArray(Charsets.UTF_8))
    }

    @Test
    fun toEvent() {
        val actual =
            ser.deserialize<WallBoxRegisteredEvent>(Serialized(0, Instant.now(), json.toByteArray(Charsets.UTF_8)))

        assertThat(actual.wallBox).isEqualTo(WallBoxId("123456"))
    }
}