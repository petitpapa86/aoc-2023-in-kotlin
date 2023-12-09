package day8


class HauntedWasteland {
    private lateinit var direction: String
    private var maps = mutableMapOf<String, Array<String>>()
    var nodeEndingWithA = mutableListOf<String>()


    private fun getDirection(c: Char): Int {
        return if (c == 'L') 0 else 1
    }

    fun solve(inputs: List<String>): Int {
        direction = inputs[0]
        val count: Int

        for (index in 2 until inputs.size) {
            val tokens = inputs[index].split(" = (")
            val leftTokens = tokens[1].split(", ")
            maps[tokens[0]] = arrayOf(leftTokens[0], leftTokens[1].substring(0, leftTokens[1].length - 1))
        }

        var source = "AAA"
        var index = 0
        val len = direction.length

        if (len <= 0) {
            return 0
        }

        count = generateSequence { index++ % len }
            .takeWhile { source != "ZZZ" }
            .onEach { source = maps[source]?.get(getDirection(direction[it])).toString() }
            .count()

        return count
    }

    fun solvePart2(inputs: List<String>): Long {
        direction = inputs[0]


        for (index in 2 until inputs.size) {
            val tokens = inputs[index].split(" = (")
            val leftTokens = tokens[1].split(", ")
            maps[tokens[0]] = arrayOf(leftTokens[0], leftTokens[1].substring(0, leftTokens[1].length - 1))
            if (tokens[0][2] == 'A') {
                nodeEndingWithA.add(tokens[0])
            }
        }

        val len = direction.length

        if (len <= 0) {
            return 0
        }
        var counts = mutableListOf<Long>()

        nodeEndingWithA.parallelStream().forEach { s ->
            var step = 0
            var source = s
            while (source[2] != 'Z') {
                source = maps[source]?.get(getDirection(direction[(step++ % len)])).toString()
            }
            counts.add(step.toLong())
        }
        return  counts.fold(1) { acc, num -> (acc * num) / calculateGCD(acc, num) }
    }

    private fun calculateGCD(a: Long, b: Long): Long {
        return if (b == 0L) a else calculateGCD(b, a % b)
    }


}
