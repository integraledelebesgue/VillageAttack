package integraledelebesgue.ooplab.engine

data class Properties(
    val gold: Int,
    val width: Int,
    val height: Int
) {
    init {
        require(gold > 0) {"Gold must be positive, got $(gold)"}
        require(width > 0) {"Map width must be positive, got $(width)"}
        require(height > 0) {"Map height must be positive, got $(height)"}
    }
}
