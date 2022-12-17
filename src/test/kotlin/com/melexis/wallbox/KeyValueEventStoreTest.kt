package com.melexis.wallbox

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify


class KeyValueEventStoreTest {

    val mock = mock(KeyValueDB::class.java) as KeyValueDB<AbstractWallBoxEvent, WallBoxId>
    val eventStore = KeyValueEventStore(mock)

    val captor = argumentCaptor<WallBoxId>()
    val eventsCaptor = argumentCaptor<List<AbstractWallBoxEvent>>()

    @Test
    fun name() {
        `when`(mock.load(any())).then { emptyList<AbstractWallBoxEvent>() }

        val wallBox = WallBoxId("21343565764")
        eventStore.mutate(wallBox) { events -> listOf(WallBoxRegisteredEvent(wallBox)) }

        verify(mock).persist(captor.capture(), eventsCaptor.capture())
        assertThat(captor.firstValue).isEqualTo(wallBox)
        assertThat(eventsCaptor.firstValue)
            .hasSize(1)
            .containsExactly(WallBoxRegisteredEvent(wallBox))

    }
}