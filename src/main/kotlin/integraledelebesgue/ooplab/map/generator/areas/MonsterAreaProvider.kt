package integraledelebesgue.ooplab.map.generator.areas

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.MonsterTerritoryFactory
import integraledelebesgue.ooplab.engine.GameProperties

sealed interface MonsterAreaProvider {

    fun generate()

}


object BoundaryAreaProvider: MonsterAreaProvider {

    override fun generate() {
        for(x in 0 until GameProperties.width)
            for(y in 0..2) {
                MonsterTerritoryFactory.create(Vector2D(x, y))
                MonsterTerritoryFactory.create(Vector2D(x, GameProperties.height - 1 - y))
            }

        for(x in 0..2)
            for(y in 0 until GameProperties.height) {
                MonsterTerritoryFactory.create(Vector2D(x, y))
                MonsterTerritoryFactory.create(Vector2D(GameProperties.width - 1 - x, y))
            }
    }

}