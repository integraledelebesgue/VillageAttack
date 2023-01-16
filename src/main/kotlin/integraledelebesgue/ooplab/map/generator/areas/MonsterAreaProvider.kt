package integraledelebesgue.ooplab.map.generator.areas

sealed interface MonsterAreaProvider {

    fun generate()

}


object BoundaryAreaProvider: MonsterAreaProvider {

    override fun generate() {

    }

}