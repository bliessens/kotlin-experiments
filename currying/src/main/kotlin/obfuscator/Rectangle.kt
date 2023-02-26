package obfuscator


val surfaceOfRectangle: (Int) -> (Int) -> Int = { x: Int, y: Int -> x * y }.curried()

val surfaceOfRectWidth2 = surfaceOfRectangle(2)

fun funSurfaceOfRectWidth2(x: Int): Int = surfaceOfRectangle(2).invoke(x)

fun squareSurface(x: Int): Int = surfaceOfRectangle(x).invoke(x)


fun main() {

    println(surfaceOfRectWidth2(2))
    println(funSurfaceOfRectWidth2(2))
    println(surfaceOfRectWidth2(9))
    println(funSurfaceOfRectWidth2(9))

    println(squareSurface(9))

}
