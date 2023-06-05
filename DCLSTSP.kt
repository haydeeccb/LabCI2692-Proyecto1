/* Aqui encontraremos los Algoritmos 1,2,3 y 4
    Con Sus respectivas funciones/procesos    
*/

fun divideAndConquerTSP(A: Array<Array<Int>>): Array<Array<Int>> {
    var n = A.size
    when (n) {
        1 -> return cicloUnaCiudad(A)
        2 -> return cicloDosCiudades(A)
        3 -> return cicloTresCiudades(A)
    } 
    var (p,q) = obtenerParticiones(A)
    var c1 = divideAndConquerTSP(p)
    var c2 = divideAndConquerTSP(q)
    return combinarCiclos(c1, c2)
}

fun cicloUnaCiudad() {

}

fun cicloDosCiudades() {

}

fun cicloTresCiudades() {
    
}
