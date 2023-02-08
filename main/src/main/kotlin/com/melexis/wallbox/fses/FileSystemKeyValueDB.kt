package com.melexis.wallbox.fses

import com.melexis.wallbox.AbstractWallBoxEvent
import com.melexis.wallbox.KeyValueDB
import com.melexis.wallbox.WallBoxId
import java.io.File
import java.time.Instant

object DefaultFileNameGenerator : (Serializer, Serialized) -> String {
    override fun invoke(p1: Serializer, p2: Serialized): String {
        return String.format("event-%03d.%s", p2.seqNr, p1.fileExtension(p2))
    }
}

class FileSystemKeyValueDB(
    val rootDir: File,
    val serializer: Serializer,
    val fileNameGenerator: (Serializer, Serialized) -> String = DefaultFileNameGenerator,
) : KeyValueDB<AbstractWallBoxEvent, WallBoxId> {

    override fun load(id: WallBoxId): List<AbstractWallBoxEvent> {
        val eventDir = File(rootDir, id.toString())
        return eventDir.listFiles()
            .map { file -> Serialized(file.sequenceNumber(), file.lastModified(), file.inputStream().readBytes()) }
            .sortedBy { ser -> ser.seqNr }
            .map { ser -> serializer.deserialize<AbstractWallBoxEvent>(ser) }
            .toList()
    }

    override fun persist(id: WallBoxId, events: List<AbstractWallBoxEvent>) {
        val eventDir = File(rootDir, id.toString()).also { if (!it.exists()) it.mkdir() }
        events.map { event -> serializer.serialize(event) }
            .mapIndexed { index, serialized -> serialized.copy(seqNr = index) }
            .forEach { serialized ->
                File(eventDir, fileNameGenerator(serializer, serialized)).appendBytes(serialized.payload)
            }
    }
}

fun File.sequenceNumber(): Int {
    return this.name.substring(6, 9).toInt()
}

interface Serializer {
    fun <T> deserialize(ser: Serialized): T
    fun <T> serialize(event: T): Serialized
    fun fileExtension(ser: Serialized): String = "data"
}

data class Serialized constructor(
    val seqNr: Int,
    val timestamp: Instant,
    val payload: ByteArray,
) :
    Comparable<Serialized> {

    constructor(seqNr: Int, timestamp: Long, payload: ByteArray) :
        this(seqNr, Instant.ofEpochMilli(timestamp), payload)

    override fun compareTo(other: Serialized): Int {
        return seqNr.compareTo(other.seqNr)
    }
}
