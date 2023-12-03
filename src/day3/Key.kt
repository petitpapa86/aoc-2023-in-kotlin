package day3

class Key {
    var row = -1
    var col = -1
    var num = -1

    constructor(row: Int, col: Int, num: Int) {
        this.row = row
        this.col = col
        this.num = num
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Key) return false

        return (this.row == other.row && this.col == other.col)
    }

    override fun hashCode(): Int {
        return (row * 31 + col)
    }

}