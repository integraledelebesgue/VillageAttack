package integraledelebesgue.ooplab.map.generator.areas

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.physicalobject.MonsterAreaFactory
import integraledelebesgue.ooplab.engine.GameProperties

sealed interface MonsterAreaProvider {

    fun generate()

}


object BoundaryAreaProvider: MonsterAreaProvider {

    override fun generate() {
        for(x in 0 until GameProperties.width)
            for(y in 0..2) {
                MonsterAreaFactory.create(Vector2D(x, y))
                MonsterAreaFactory.create(Vector2D(x, GameProperties.height - 1 - y))
            }

        for(x in 0..2)
            for(y in 0 until GameProperties.height) {
                MonsterAreaFactory.create(Vector2D(x, y))
                MonsterAreaFactory.create(Vector2D(GameProperties.width - 1 - x, y))
            }
    }

}