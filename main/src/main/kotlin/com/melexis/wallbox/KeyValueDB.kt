package com.melexis.wallbox

import java.util.concurrent.ConcurrentHashMap

interface KeyValueDB<E, I> {

    fun load(id: I): List<E>

    fun persist(id: I, events: List<E>)
}

class InMemoryKeyValueDB(
    private val eventsByAggregate: MutableMap<WallBoxId, List<AbstractWallBoxEvent>> = ConcurrentHashMap(),
) : KeyValueDB<AbstractWallBoxEvent, WallBoxId> {

    override fun load(id: WallBoxId): List<AbstractWallBoxEvent> {
        return eventsByAggregate.getOrElse(id) { emptyList() }
    }

    override fun persist(id: WallBoxId, events: List<AbstractWallBoxEvent>) {
        if (events.isNotEmpty()) {
            eventsByAggregate[id] = events
        }
    }
}
