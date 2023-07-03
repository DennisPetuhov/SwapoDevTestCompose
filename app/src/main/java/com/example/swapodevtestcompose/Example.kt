fun main() {
    var string = "https://swapi.dev/api/films/2/"
    var result = string.filter { it.isDigit() }
    println(result)
}