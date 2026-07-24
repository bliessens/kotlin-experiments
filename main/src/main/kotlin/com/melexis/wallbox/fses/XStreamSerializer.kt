package com.melexis.wallbox.fses

import com.thoughtworks.xstream.XStream
import java.time.Clock
import java.time.Instant

class XStreamSerializer(
    val xstream: XStream = XStream(),
    val clock: Clock,
) : Serializer {
    init {
        xstream.allowTypesByWildcard(arrayOf("com.melexis.wallbox.**"))
    }

    override fun <T> deserialize(ser: Serialized): T = xstream.fromXML(ser.payload.inputStream()) as T

    override fun <T> serialize(event: T): Serialized =
        Serialized(-1, Instant.now(clock), xstream.toXML(event).toByteArray(charset = Charsets.UTF_8))

    override fun fileExtension(ser: Serialized): String = "xml"
}
