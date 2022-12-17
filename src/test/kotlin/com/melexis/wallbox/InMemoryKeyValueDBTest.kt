package com.melexis.wallbox

import org.assertj.core.api.KotlinAssertions.assertThat
import org.assertj.core.api.iterable.Extractor
import org.junit.jupiter.api.Test

class InMemoryKeyValueDBTest {

    val eventStore = InMemoryKeyValueDB()

    val wallBoxId = WallBoxId("123455678899")

    @Test
    fun shouldReturnEmptyListForNewId() {
        assertThat(eventStore.load(wallBoxId)).isEmpty()
    }

    @Test
    fun shouldReturnPersistedEvents() {
        eventStore.persist(wallBoxId, listOf(WallBoxRegisteredEvent(wallBoxId)))

        assertThat(eventStore.load(wallBoxId))
            .hasSize(1)
            .extracting(Extractor { event -> event.wallBox })
            .containsExactly(wallBoxId)
    }

}