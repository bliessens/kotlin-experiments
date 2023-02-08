package com.melexis.wallbox

class WallBoxService(val eventStore: EventStore<AbstractWallBoxEvent, WallBoxId>) {

    fun handle(command: AbstractWallBoxCommand) {
        eventStore.mutate(command.wallBox) { events -> WallBoxAggregate.hydrate(events).handleCommand(command) }
    }
}

class WallBoxAggregate(val events: MutableList<AbstractWallBoxEvent>) {

    companion object {

        fun hydrate(events: List<AbstractWallBoxEvent>): WallBoxAggregate {
            return WallBoxAggregate(events.toMutableList())
        }
    }

    fun handleCommand(command: AbstractWallBoxCommand): List<AbstractWallBoxEvent> {
        when (command) {
            is RegisterWallBoxCommand -> return this.events + WallBoxRegisteredEvent(command.wallBox)
        }
        return this.events
    }
}

data class WallBoxId(val value: String) {
    init {
        require(value.length in 6..12) { println("WallBox identifiers are 10 to 25 chars long") }
    }

    override fun toString(): String {
        return value
    }
}

abstract class AbstractWallBoxCommand(open val wallBox: WallBoxId)
data class RegisterWallBoxCommand(override val wallBox: WallBoxId) : AbstractWallBoxCommand(wallBox)

abstract class AbstractWallBoxEvent(open val wallBox: WallBoxId)
data class WallBoxRegisteredEvent(override val wallBox: WallBoxId) : AbstractWallBoxEvent(wallBox)
