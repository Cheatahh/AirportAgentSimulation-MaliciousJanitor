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

Uml: [PlantUML](https://www.plantuml.com/plantuml/svg/bLHDJzj04BtxLqpLIoY9L752gyY54dgeWeIKSAgejDWJPyHwrzfT1r3L_zxnse0tZYdu42lpyMRUU-izSrvQNvSg-UItM29KIf9ElAd06xWptfiIShAOUJ8wSVUaAsbb2Oeq-gSAmLY_DKcIAUaSN4b56PdQ_PIQlB7NgYv8mvy4u7WaEJaI4DAIH-oanoPmmmKmduFPeRMK8wHfyZT9I7kq6vaXTC1SWeySqrB1Qp9DUU8-vCvGpXXZcuxiE5cO-axX55RUaYwci3JxlrscUp-pCVexiJ8ZP4995JZSLFaRfBbm1ieK1LhHaLw6Vss6tSfuwCLMwW85v67Pa77AF2pCW_vHws2TW4cRFO9PsY_4nqpLEUPhDjC9M9Bpyuj6elcNiESCF2bSqWRNLF8CnQHVmgjkoXkf2sDKpkystH6IpbyRH-rYruQqPsiwPvuRBpSRp7pdInoSN9aTqxxKdlpJaOW8hocxRtYyx29jWLv0lsKuR4qAur4XDEbBViZyoU9LrEwez2NybgPix7lTVk_1CNg1Shmaq9QATXYqwFdY8XM35jIOla8_ZnLkdMV0eDUgGipFoqhHXj2AxgZtLFJIKzPg6tsJuKiSOy_zRVjvmMC-tXTPEcnlreliFPxz-dnwyfMVQRZbRcVxPqr0ZXlIwAPGIYqBTFz56DmZ0cg73i8jYRpF7WG_3D90d9uyW_o6M44HcEZSDTN9OC91WA1XhpCPkOhRveR4QtEN4a1iCPOCv99OcNvRBjqMyzjP6kilG8RIcEy7-eP43_L6u09S9H-q6oSxH3ZacCM277lrVg3XAZsGCtQWBjK_)