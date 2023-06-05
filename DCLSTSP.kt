/* Aqui encontraremos los Algoritmos 1,2,3 y 4
    Con Sus respectivas funciones/procesos    
*/

fun divideAndConquerTSP(A: Array<Pair<Int, Int>>):  Array<Pair<Int, Int>> {
    var n = A.size
    when (n) {
        1 -> return cicloUnaCiudad(A)
        2 -> return cicloDosCiudades(A)
        3 -> return cicloTresCiudades(A)
    } 
    var (P,Q) = obtenerParticiones(A)
    var C1 = divideAndConquerTSP(P)
    var C2 = divideAndConquerTSP(Q)
    return combinarCiclos(C1, C2)
}

fun cicloUnaCiudad(A: Array<Pair<Int, Int>>):  Array<Pair<Int, Int>> {
    var B = arrayOf(A[0], A[0])
    return B
}

fun cicloDosCiudades(A: Array<Pair<Int, Int>>):  Array<Pair<Int, Int>> {
    return posibilidades(A)
}

fun cicloTresCiudades(A:  Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {
    return posibilidades(A)
}

fun permutaciones(A: Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {
    var A = arrayOf(Pair(1, 4),Pair(1, 2),Pair(3, 2))
    var B = Array(A.size){i -> i}
    var C: Array<Any> = Array(A.size+1){Pair(0,0)}
    for (i in 0 until A.size) {
        var x = (0 until A.size).random()
        while(B[x] == -1) {
            x = (0 until A.size).random()
    	}
        B[x] = -1
        C[i] = A[x]
    }
    C[A.size] = C[0]
    return C
}

fun combinarCiclos(A: Array<Pair<Int, Int>>, B: Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {
    when (A.size) {
        0 -> return B
    }
    when (B.size) {
        0 -> return A
    }
    var minG = 
}