package com.melexis.wallbox.fses

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.Clock
import java.time.Instant

class JacksonSerializer(val mapper: ObjectMapper = jacksonObjectMapper(), val clock: Clock) : Serializer {

    override inline fun <T> deserialize(ser: Serialized): T {
        return mapper.readValue(ser.payload.inputStream(), object : TypeReference<T>() {})
    }

    override fun <T> serialize(event: T): Serialized {
        return Serialized(-1, Instant.now(clock), mapper.writeValueAsBytes(event))
    }

    override fun fileExtension(ser: Serialized): String {
        return "json"
    }
}