package com.melexis.wallbox

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WallBoxServiceTest {

    private val id: WallBoxId = WallBoxId("12345678900")

    val storageMap = mutableMapOf<WallBoxId, List<AbstractWallBoxEvent>>()
    val keyValueStore = InMemoryKeyValueDB(storageMap)
    val eventStore = KeyValueEventStore(keyValueStore)
    val wallBoxService = WallBoxService(eventStore)

    @Test
    fun name() {
        wallBoxService.handle(RegisterWallBoxCommand(id))

        assertThat(storageMap).containsKey(id).matches({ map -> map[id]!!.size == 1 }, "")
    }
}
