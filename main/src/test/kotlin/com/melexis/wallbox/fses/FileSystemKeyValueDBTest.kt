package com.melexis.wallbox

import com.melexis.wallbox.fses.DefaultFileNameGenerator
import com.melexis.wallbox.fses.FileSystemKeyValueDB
import com.melexis.wallbox.fses.Serialized
import com.melexis.wallbox.fses.Serializer
import com.melexis.wallbox.fses.XStreamSerializer
import com.melexis.wallbox.fses.sequenceNumber
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.time.Clock
import java.time.Instant

class FileSystemKeyValueDBTest {

    object ser : Serializer {
        override fun <T> deserialize(ser: Serialized): T {
            TODO("Intentionally not implemented")
        }

        override fun <T> serialize(event: T): Serialized {
            TODO("Intentionally not implemented")
        }

        override fun fileExtension(ser: Serialized): String {
            return "data"
        }
    }

    @Nested
    inner class FileNaming {

        @Test
        fun fileNameHasPaddedSequenceNumber() {
            assertThat(
                DefaultFileNameGenerator(
                    ser,
                    Serialized(1, Instant.now(), ByteArray(0)),
                ),
            ).isEqualTo("event-001.data")
            assertThat(
                DefaultFileNameGenerator(
                    ser,
                    Serialized(99, Instant.now(), ByteArray(0)),
                ),
            ).isEqualTo("event-099.data")
            assertThat(
                DefaultFileNameGenerator(
                    ser,
                    Serialized(123, Instant.now(), ByteArray(0)),
                ),
            ).isEqualTo("event-123.data")
        }

        @Test
        fun parseSequenceNumber() {
            assertThat(File("event-099.data").sequenceNumber()).isEqualTo(99)
            assertThat(File("event-001.data").sequenceNumber()).isEqualTo(1)
            assertThat(File("event-321.data").sequenceNumber()).isEqualTo(321)
        }
    }

    @Nested
    inner class Serialization {

        @Test
        fun testConversionOfFileModifiedDateToInstant(@TempDir tempFolder: File) {
            val newFile = File(tempFolder, "dfdsafsf")
            newFile.createNewFile()

            assertThat(Instant.ofEpochMilli(newFile.lastModified()))
                .isBeforeOrEqualTo(Instant.now())
                .isAfter(Instant.now().minusSeconds(3))
        }
    }

    @Nested
    inner class EventStore {

        val wallBox = WallBoxId("654321")

        @Test
        fun testWriteEvents(@TempDir tempFolder: File) {
            "".run { }
            val eventStore = FileSystemKeyValueDB(tempFolder, XStreamSerializer(clock = Clock.systemUTC()))
            val events = listOf(
                WallBoxRegisteredEvent(wallBox),
                WallBoxRegisteredEvent(wallBox),
                WallBoxRegisteredEvent(wallBox),
                WallBoxRegisteredEvent(wallBox),
                WallBoxRegisteredEvent(wallBox),
            )

            eventStore.persist(wallBox, events)

            val expectedStorageDir = File(tempFolder, wallBox.toString())
            assertThat(expectedStorageDir).exists()
            assertThat(expectedStorageDir.listFiles()).hasSize(events.size)
        }

        @Test
        fun readEvents() {
            val eventStore = FileSystemKeyValueDB(
                File("src/test/resources/eventStore"),
                XStreamSerializer(clock = Clock.systemUTC()),
            )

            val events = eventStore.load(wallBox)

            assertThat(events)
                .hasSize(5)
                .extracting<WallBoxId> { event -> event.wallBox }
                .allMatch { id -> id.value == wallBox.value }
        }
    }
}
