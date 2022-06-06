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

Uml: [PlantUML](https://www.plantuml.com/plantuml/svg/bLH1Jniz4BtxLyodxn9A8bMSa1MbK48FLP2G0gUAIcPtiXdXjLVsR01L_U-TE0ki0qlf7hAAPzuRl_UyFebi0hUrLUe_NcEDq5X3Jh5lW3tSUcPVGqa12oRlLBmZrvXWQh3aa1yR11zuxPKgh8aHBeobWdmRlnj7xCEbRIjoy4i170uKHmSQKjd8Y8rXt2vSImDCf-0t60AL2BEP-gqKESQmCWL2Homj-21hpbXuBagpJDodt7WgXMDefmDx7CrzUsjn34iEvAenBFp-RrVftazglFjAuio0cDJI90tNJVcEQ6cSWFLLXK5teXVftwuYQHKoz6njN8KQohJPL8dMty_zlVlMkXITXj6kUW2JMRyH7mhRbbXUIPXHmu8YJy-s5aq_oVnj1vF51QtmYcgPOKNqy_Aoc_9EwSnxMygxAwUwqFEbZxJRsAKdnn9DbymJy7ormeAxNFB5qONVYEnpnyIF1peJV4N5tLR7yrvCg90rz27Pi0vbCH-LbaPzozyAVpH_CRKxAdqBlync4ko7q_xWmV5kZcQu9SasbZQOlEZb4ZCNanU4qh14dkOExv8NmkJNia4iJ-l6qeemwEwezrnamrJilCs-oVGb3gaN_0v-MicOVyTbaRw6IvlAuZsS_FZ_-EYpFEDqozrCzi-QXX9Nv32EeJREL1Z_ol3g7j7GHeoGRaba37iKyWZ9bkBuw8da9-GEwgJ4bH7aKViJtYP87lQGQk0gte6t8bx0NKa3ISHO2r5KkJDzM6dY6ikRoIptNuD9hNjwFuvxBVGjR4Qk8JRctaMbJaHqMrlr1m00)