package day2

class Game{
    val  tokens = HashMap<String, Token>()
    fun addToken(token: Token) {
        tokens.compute(token.type) { _, existingToken ->
            if (existingToken == null || token.count > existingToken.count) token else existingToken
        }
    }

    var id: Int;

    constructor(id: Int) {
        this.id = id;
    }

    override fun toString(): String {
        return tokens.toString()
    }
}
