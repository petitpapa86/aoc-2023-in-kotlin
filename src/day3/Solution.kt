package day3

class Solution {
    val mapStar = mutableMapOf<Key, ArrayList<Int>>()
    fun solve(inputs: List<String>): Long{
        val  board = buildBoard(inputs)
        return calculateSum(board);
    }

    private fun calculateSum(board: Array<Array<Char>>): Long {

        var sum: Long =0;
        for ( row in 0..<board.size){
            var col = 0
            while (col < board[0].size) {
                val currentChar = board[row][col]
                if (currentChar == '.' || isSymbols(currentChar)) {
                    col++
                    continue
                }

                if (currentChar.isDigit()) {
                    val numString = getNumbers(col, board[row])
                    if (numsContainsNeighborSymbols(numString, row, col, board)) {

                        sum += numString.toInt()
                    }
                    col += numString.length
                    continue
                }
                col++;
            }
        }
        return sum;
    }

    private fun numsContainsNeighborSymbols(numString: String, row: Int, col: Int, board: Array<Array<Char>>): Boolean {
        for (i in numString.indices){
           if( lookDiagonal(row, col+i, board, numString)) {
               return true
           }
        }
        return  false
    }

    private fun lookDiagonal(row: Int, col: Int, board: Array<Array<Char>>, numString: String): Boolean {
        for (r in -1..1) {
            for (c in -1..1) {
                if (isOutofBound(row + r, col + c, board)) continue
                if (isSymbols(board[row + r][col + c])) {
                    if(board[row + r][col + c] == '*'){
                        addNum(numString, row+r, col+c)
                    }
                    return true
                }
            }
        }
        return false
    }

    private fun addNum(numString: String, row: Int, col: Int) {
        val key = Key(row, col, numString.toInt())
        mapStar.getOrPut(key) { ArrayList() }.add(numString.toInt())
    }

    private fun isOutofBound(r: Int, c: Int, board: Array<Array<Char>>): Boolean {
        return r < 0 || r >= board.size || c < 0 || c >= board[0].size
    }

    private fun getNumbers(col: Int, chars: Array<Char>): String {
        return chars.drop(col) //creates a new list that excludes the first col elements, effectively starting from index col.
                .takeWhile { it.isDigit() }
                .joinToString(separator = "")
    }
    /*
    private fun getNumbers(col: Int, chars: Array<Char>): String {
        var nums = ""
        for (i in col..<chars.size){
            if(chars[i].isDigit()){
                nums += chars[i]
            }else break
        }
        return nums
    }
*/
    private fun isSymbols(currentChar: Char): Boolean {
        return currentChar == '*' || currentChar == '#' || currentChar == '$' || currentChar == '+'
                || currentChar == '%' || currentChar == '/' || currentChar == '=' || currentChar == '@'
                || currentChar == '&' || currentChar == '-'
    }

    private fun buildBoard(inputs: List<String>): Array<Array<Char>> {
        val board: Array<Array<Char>>  = Array(inputs.size) { CharArray(inputs[0].length).map { it.toChar() }.toTypedArray()}

        for (i in inputs.indices) {
            for (j in 0..<inputs[0].length) {
                board[i][j] = inputs[i][j]
            }
        }
        return board
    }

    fun calculateGift(input: List<String>): Long {
        var sum: Long = 0
        solve(input)
        for(entry in mapStar){
            val values = entry.value;
            if(values.size > 2 || values.size <= 1){
                continue
            }
            sum += values[0] * values[1]
        }
        return sum;
    }
}