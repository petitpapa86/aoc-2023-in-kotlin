package day2

import println

class InputParser {
    var inputs: List<String>;


    constructor(inputs: List<String>) {
        this.inputs = inputs;
    }

    fun parse(): List<Game> {
        return gameInputs();
    }

    private fun gameInputs(): List<Game> {
        return streamLine().stream().map { lines -> parseToGame(lines) }.toList();
    }

    private fun parseToGame(lines: Array<String>): Game {
        var gameTokens = lines[0].split(" ");
        var game = Game(gameTokens[1].toInt());
        splitToToken(lines[1]).forEach { token ->
            game.addToken(parseToken(token))
        }
        return game;
    }

    private fun parseToken(token: String): Token {

        var sets = token.trim().split(" ")
        val token1 = when (sets.get(1).trim()) {
            "blue" -> Token("blue", sets.get(0).toInt())
            "red" -> Token("red", sets.get(0).toInt())
            "green" -> Token("green", sets.get(0).toInt())
            else -> {
                throw IllegalArgumentException()
            }
        }
        return token1
    }

    private fun splitToToken(line: String): List<String> {
        return line.split(",", ";").onEach { element -> element.trim() }.stream().toList()
    }

    private fun streamLine(): List<Array<String>> {
        var list = mutableListOf<Array<String>>()
        this.inputs.forEach { line ->
            val s = line.split(":")
            list.add(arrayOf(s.get(0), s.get(1)));
        }
        return list
    }
    fun calculatePossibility(): Int{
        var sum = 0
        var eachPowergame = 1;
        parse().forEach { game  ->
            println(game)
            var possible = true
            if (game.tokens["red"]?.count ?: 0 > 12) {
                possible = possible && false
            }
            if(game.tokens["green"]?.count ?: 0 > 13){
                possible = possible && false
            }
            if(game.tokens["blue"]?.count ?: 0 > 14){
                possible = possible && false
            }
            if(possible){
                sum += game.id
            }

        }
        return sum
    }

    fun calculatePower(): Long{
        var sum: Long = 0
        parse().forEach { game  ->
            sum  = sum.plus(game.tokens["red"]?.count!! * game.tokens["blue"]?.count!! * game.tokens["green"]?.count!!)
        }
        return sum
    }
}