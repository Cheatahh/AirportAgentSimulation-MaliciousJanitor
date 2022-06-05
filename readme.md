### AirportAgentSimulation-MaliciousJanitor

A plugin providing a MaliciousJanitor for the AirportAgentSimulation of my university class.

The MaliciousJanitor Agent is classified as attacker.
It randomly wanders around the simulation world and has a specific chance to place down a SlowDownTile. For example, this could be seen as waxing the floor.
If an entity walks over a SlowDownTile it receives a temporary slowdown.

#### Custom config attributes

- **initialSpeed: Double** - The initial speed of this agent.
- **tilePlacingChance: String** - Chance to lay down a SlowDownTile in percent. Example values are "69" or "42%".
- **tileLifeTime: Long** - Total amount of ticks a SlowDownTile is considered alive. After this threshold is reached, the tile will get cleared.
- **tileSlowDownTime: Long** - Total amount of ticks a SlowDownTile can affect an entity.
- **tileSlowDownCoolDown: Long** - Total amount of ticks an entity cannot be affected by the same SlowDownTile, after it has been slowed down.
- **tileSlowDownFunction: String** Function to determine the actual speed amplifier at a given point in time. Possible values are "exponential", "exponentialInverted", "linear", "cubic", "cubicInverted" or any percentages like for example "42%".
- **excludedEntityTypes: String** A list of entity types (qualified names) to be excluded by the SlowDownTiles, delimited by whitespaces and/or commas.

The compiled jar can be found in `out/artifacts`.

Simulation: [Vincent200355/AirportAgentSimulation-Base](https://github.com/Vincent200355/AirportAgentSimulation-Base)
