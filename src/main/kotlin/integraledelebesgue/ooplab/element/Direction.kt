package integraledelebesgue.ooplab.element

enum class Direction(val unitVector: Vector2D) {
    NORTH(Vector2D(0, 1)),
    NORTHEAST(Vector2D(1, 1)),
    EAST(Vector2D(1, 0)),
    SOUTHEAST(Vector2D(1, -1)),
    SOUTH(Vector2D(0, -1)),
    SOUTHWEST(Vector2D(-1, -1)),
    WEST(Vector2D(-1, 0)),
    NORTHWEST(Vector2D(-1, 1));

    fun rotate(steps: Int): Direction {
        return values()[(this.ordinal + steps % 8 + 8) % 8]
    }

    companion object {
        fun randomDirection(): Direction {
            return values().random()
        }
    }
}
