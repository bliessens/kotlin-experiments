package com.melexis.wallbox

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.function.Function

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
            .extracting(Function { event -> event.wallBox })
            .containsExactly(wallBoxId)
    }
}
