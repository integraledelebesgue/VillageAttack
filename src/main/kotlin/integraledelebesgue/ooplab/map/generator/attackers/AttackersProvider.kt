package integraledelebesgue.ooplab.map.generator.attackers

import integraledelebesgue.ooplab.element.Vector2D
import integraledelebesgue.ooplab.element.creature.Creature
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

sealed interface AttackersProvider {

    fun generate(creatureClass: KClass<out Creature>, position: Vector2D)

}


object InteractiveAttackersProvider: AttackersProvider {

    @Synchronized
    override fun generate(creatureClass: KClass<out Creature>, position: Vector2D) {
        Creature.factory[creatureClass]!!.create(position)
    }

}