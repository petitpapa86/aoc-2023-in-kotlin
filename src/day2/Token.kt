package day2

class Token {
    lateinit var type: String;
     var count: Int
   constructor(type: String, count:Int){
       this.type = type
       this.count = count
   }

    override fun toString(): String {
        return "($type, $count)"
    }

}