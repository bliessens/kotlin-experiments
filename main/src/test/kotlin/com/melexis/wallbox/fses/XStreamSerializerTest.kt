package com.melexis.wallbox.fses

import com.melexis.wallbox.WallBoxId
import com.melexis.wallbox.WallBoxRegisteredEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class XStreamSerializerTest {

    val ser = XStreamSerializer(clock = Clock.fixed(Instant.now(), ZoneId.systemDefault()))

    val xml = """
    <com.melexis.wallbox.WallBoxRegisteredEvent>
      <wallBox defined-in="com.melexis.wallbox.AbstractWallBoxEvent">
        <value>123456</value>
      </wallBox>
      <wallBox reference="../wallBox"/>
    </com.melexis.wallbox.WallBoxRegisteredEvent>
    """.trimIndent()

    @Test
    fun toXML() {
        val actual: Serialized = ser.serialize(WallBoxRegisteredEvent(WallBoxId("123456")))

        assertThat(actual.payload).isEqualTo(xml.toByteArray(Charsets.UTF_8))
    }

    @Test
    fun toEvent() {
        val actual =
            ser.deserialize<WallBoxRegisteredEvent>(Serialized(0, Instant.now(), xml.toByteArray(Charsets.UTF_8)))

        assertThat(actual.wallBox).isEqualTo(WallBoxId("123456"))
    }
}
