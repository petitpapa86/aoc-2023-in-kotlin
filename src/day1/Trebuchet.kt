package day1

class Trebuchet {
    var inputs: List<String>?
    var wordMap: Map<String, String> = HashMap();
    var leftStartLetterToWordLength: Map<Char, List<Int>> = HashMap();
    var rightStartLetterToWordLength: Map<Char, List<Int>> = HashMap();

    constructor(testInput: List<String>) {
        inputs = testInput;
        buildMapOfWordToDigits();
        buiildStartLetterToWord();
    }

    private fun buiildStartLetterToWord() {
        leftStartLetterToWordLength = mapOf('o' to listOf(3), 't' to listOf(3, 5), 'f' to listOf(4),
                's' to listOf(3, 5), 'e' to listOf(5), 'n' to listOf(4))
        rightStartLetterToWordLength = mapOf('e' to listOf(3, 5, 4), 'o' to listOf(3), 'r' to listOf(4),
                'x' to listOf(3), 'n' to listOf(5), 't' to listOf(5))
    }

    private fun buildMapOfWordToDigits() {
        wordMap = mapOf("one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6",
                "seven" to "7", "eight" to "8", "nine" to "9");
    }

    fun sum(): Long {
        var sum: Long = 0;

        inputs!!.forEach { line ->
            sum += parseText(line)
        }

        return sum;
    }

    private fun parseText(line: String): Int {
        val len = line.length
        var leftIndex = 0;
        var rightIndex = len - 1;
        var leftValue: String? = null;
        while (leftIndex < len) {
            val currentChar = line[leftIndex]
            if (wordCorrespondToMapDigit(currentChar, true)) {
                val word: String? = evaluateWord(leftIndex, line, true)
                if (word == null) {
                    leftIndex++;
                    continue
                }
                leftValue = word;
                break;
            } else if (currentChar.isDigit()) {
                leftValue = currentChar.toString()
                break
            } else {
                leftIndex++;
            }
        }
        var rightValue: String? = null;
        while (rightIndex >= 0) {
            val currentChar = line[rightIndex]
            if (wordCorrespondToMapDigit(currentChar, false)) {
                val word: String? = evaluateWord(rightIndex, line, false)
                if (word == null) {
                    rightIndex--
                    continue
                }
                rightValue = word;
                break;
            } else if (currentChar.isDigit()) {
                rightValue = currentChar.toString()
                break
            } else {
                rightIndex--;
            }
        }
        if (leftIndex > rightIndex) {
            return 0;
        }

        val sb = StringBuilder()
        sb.append(leftValue).append(rightValue)
        return sb.toString().toInt()
    }

    private fun evaluateWord(index: Int, line: String, isLeft: Boolean): String? {
        var lists: List<Int>;
        if (isLeft)
            lists = leftStartLetterToWordLength.get(line[index])!!;
        else
            lists = rightStartLetterToWordLength.get(line[index])!!
        for (num in lists!!) {

            var word = "";
            if (isLeft) {
                if (index + num + 1 > line.length) {
                    continue
                }
                word = line.substring(index, index + num )
            } else {
                if (index - num < 0) {
                    continue
                }
                word = line.substring(index - num+1, index + 1);
            }

            if (wordMap.containsKey(word)) {
                return wordMap.get(word);
            }
        }
        return null;
    }

    private fun wordCorrespondToMapDigit(currentChar: Char, isLeft: Boolean): Boolean {
        if (isLeft)
            return leftStartLetterToWordLength.containsKey(currentChar)
        else
            return rightStartLetterToWordLength.containsKey(currentChar)
    }
}
