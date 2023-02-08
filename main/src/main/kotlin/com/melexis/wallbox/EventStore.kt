package com.melexis.wallbox

interface EventStore<E, I> {
    val keyValueDB: KeyValueDB<E, I>

    fun mutate(id: I, stateMutation: (existingEvents: List<E>) -> List<E>) {
        val existingEvents = this.keyValueDB.load(id)
        val newEvents = stateMutation(existingEvents)
        this.keyValueDB.persist(id, newEvents)
    }
}

class KeyValueEventStore(
    override val keyValueDB: KeyValueDB<AbstractWallBoxEvent, WallBoxId> = InMemoryKeyValueDB(),
) : EventStore<AbstractWallBoxEvent, WallBoxId>
