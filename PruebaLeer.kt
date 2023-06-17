import kotlin.math.roundToInt
import java.io.File
import java.io.Reader
import java.io.InputStream
import java.io.BufferedReader

fun main(A: Array<String>){
    instancia2(A[0])
}

fun instancia2(A: String) {
    var i = 0
    var j = 0
    var m = 0
    var centinela1: Int
    var centinela2: Int
    // Se cuenta la cantidad de líneas
    File(A).forEachLine {i++}
    var numeroDeLineas = i
    // Se crea arreglo con tamaño igual al número de líneas
    var B = Array(numeroDeLineas){""}
    println("Número de líneas: ${numeroDeLineas}") // BORRAR ESTO DESPUÉS
    // Rellenamos cada elemento de B con las líneas del texto
    i = 0
    File(A).forEachLine {line -> B[i++] = line}
    print("Penúltima linea:") // BORRAR ESTO DESPUÉS
    println(B[numeroDeLineas-1]) // BORRAR ESTO DESPUÉS
    // Se cuenta la cantidad de líneas que contienen coordenadas
    var lineasDeCoordeadas = numeroDeLineas - 7
    var finalB = numeroDeLineas - 1
    if (B[numeroDeLineas-1] != "EOF") {
      lineasDeCoordeadas = lineasDeCoordeadas - 1
      finalB = finalB - 1
    }
    // Se crea un arreglo con tamaño igual a la cantidad de líneas que contienen coordenadas 
    var C = Array(lineasDeCoordeadas){Pair("", "")}
    // revisamos cada elemento de B
    for (k in 6 until finalB) {
        centinela1 = 0
        centinela2 = 0
        m = 0
        // Verificamos cada caracter del string que se encuentra dentro del arreglo B
        for (p in 1 until B[k].length) {
            // Caso en donde haya espacios al inicio
            if (B[k][0] == ' ') {
                if (B[k][p] != ' ' && centinela1 == 0 && B[k][p-1] == ' ' && m != 0) {
                    centinela1 = p-1
                } else if (B[k][p] != ' ' && centinela1 != 0 && B[k][p-1] == ' ' && m != 0) {
                    centinela2 = p-1
                } else if (B[k][p] != ' ' &&  B[k][p-1] == ' ') {
                    m++
                } 
            } else {
                // Caso en donde no hay espacio al principio
                if (B[k][p] != ' ' && centinela1 == 0 && B[k][p-1] == ' ' && m == 0) {
                    centinela1 = p-1
                    m++
                } else if (B[k][p] != ' ' && centinela1 != 0 && B[k][p-1] == ' ' && m != 0) {
                    centinela2 = p-1
                } 
            }         
        }
        // Creación de un elemento par que contiene la primera división y la segunda división del string usando substring
        var par = Pair(B[k].substring(centinela1, centinela2), B[k].substring(centinela2, B[k].length))
        // Incluimos el elemento
        C[j] = par
        j++
    }
    println(C.contentToString())
}