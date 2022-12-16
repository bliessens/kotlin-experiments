package com.melexis.wallbox

import java.util.concurrent.ConcurrentHashMap

interface KeyValueStore<E, I> {

    fun load(id: I): List<E>

    fun persist(id: I, events: List<E>): Unit
}

class InMemoryKeyValueStore(
    private val eventsByAggregate: MutableMap<WallBoxId, List<AbstractWallBoxEvent>> = ConcurrentHashMap()
) : KeyValueStore<AbstractWallBoxEvent, WallBoxId> {

    override fun load(id: WallBoxId): List<AbstractWallBoxEvent> {
        return eventsByAggregate.getOrElse(id) { emptyList() }
    }

    override fun persist(id: WallBoxId, events: List<AbstractWallBoxEvent>) {
        if (events.isNotEmpty()) {
            eventsByAggregate[id] = events
        }
    }
}

interface EventStore<E, I> {
    val keyValueStore: KeyValueStore<E, I>

    fun mutate(id: I, stateMutation: (existingEvents: List<E>) -> List<E>) {
        val existingEvents = this.keyValueStore.load(id)
        val newEvents = stateMutation(existingEvents)
        this.keyValueStore.persist(id, newEvents)
    }
}

class KeyValueEventStore(
    override val keyValueStore: KeyValueStore<AbstractWallBoxEvent, WallBoxId> = InMemoryKeyValueStore()
) : EventStore<AbstractWallBoxEvent, WallBoxId>


