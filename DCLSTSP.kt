// FUNCIONES NECESARIAS PARA EL ALGORITMO 2, LÍNEAS DE CÓDIGO 1 - 312

/* Función: obtenerMaximoArreglo
 * Descripción: Recibe un arreglo A: Array<Double> y retorna un Double que corresponde al elemento máximo de dicho arreglo
 * Precondición: A.size >= 1
 * Postondición: (\forall int i: 0 <= i < A.size, \result >= A[i]) && (\exists int i; 0 <= i && i < A.size; \result == A[i])
 */ 
fun obtenerMaximoArreglo(A: Array<Double>): Double {
	var n = A.size
	var maximo = A[0]
	for (i in 1 until n) {
		if (A[i] > maximo) {
			maximo = A[i]
		}
	}
	return maximo
}

/* Función: obtenerMinimoArreglo
 * Descripción: Recibe un A: Array<Double> y retorna un Double que corresponde al elemento mínimo de dicho arreglo
 * Precondición: A.size >= 1
 * Postondición: (\forall int i: 0 <= i < A.size, \result <= A[i]) && (\exists int i; 0 <= i && i < A.size; \result == A[i])
 */ 
fun obtenerMinimoArreglo(A: Array<Double>): Double {
	var n = A.size
	var minimo = A[0]
	for (i in 1 until n) {
		if (A[i] < minimo) {
			minimo = A[i]
		}
	}
	return minimo
}

/* Función: obtenerRectangulo
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un arreglo Array<Pair<Double,Double>> 
 * que muestra las coordenadas de un rectángulo que contiene todos los puntos del arreglo P. 
 * Dichas coordenadas se encuentran en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result[0].first <= P[i].first <= \result[2].first && \result[0].second <= P[i].second <= \result[2].second) 
 */ 
fun obtenerRectangulo (P: Array<Pair<Double,Double>>): Array<Pair<Double,Double>> {
	var n = P.size
	var coordenadasX = Array(n, {0.0})
	var coordenadasY = Array(n, {0.0})
	for (i in 0 until n) {
		coordenadasX[i] = P[i].first
		coordenadasY[i] = P[i].second
	}
	var minimoX = obtenerMinimoArreglo(coordenadasX)
	var maximoX = obtenerMaximoArreglo(coordenadasX)
	var minimoY = obtenerMinimoArreglo(coordenadasY)
	var maximoY = obtenerMaximoArreglo(coordenadasY)
	var coordenadas00 = Pair(minimoX,minimoY)
	var coordenadas10 = Pair(maximoX, minimoY)
	var coordenadas01 = Pair(minimoX, maximoY)
	var coordenadas11 = Pair(maximoX, maximoY)
	var coordenadasRectangulo = arrayOf(coordenadas00, coordenadas10, coordenadas11, coordenadas01)
	return coordenadasRectangulo
}

/* Función: obtenerDimensionesRectangulo
 * Descripción: Recibe un R: Array<Pair<Double,Double>> con las coordenadas cartesianas de un rectángulo y 
 * retorna un Pair<Double,Double> que contiene las dimensiones de sus lados paralelos al eje X y al eje Y, en ese orden. 
 * Las coordendas del rectángulo que se recibe deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 0 && P[0].first <= P[2].first && P[0].second <= P[2].second
 * Postondición: \result.first >= 0 && \result.second >= 0
 */ 
fun obtenerDimensionesRectangulo(R: Array<Pair<Double,Double>>) : Pair<Double,Double> {
	var dimensionesX = R[2].first - R[0].first
	var dimensionesY = R[2].second - R[0].second
	return Pair(dimensionesX, dimensionesY)
}

/* Función: swap
 * Descripción: Recibe un P: Array<Pair<Double,Double>> y dos i,j: Int. Esta función intercambia las posiciones A[i] y A[j]
 * Precondición: 0 <= i,j < P.size
 * Postondición: P[i] == \old(P[j]) && P[j] == \old(P[i])
 */ 
fun swap (P: Array<Pair<Double,Double>>, i: Int, j: Int) {
    var temp = P[i]
    P[i] = P[j]
    P[j] = temp
}

/* Función: quicksortTSP
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas, dos p,r: Int que identifican el subarreglo
 * P[p...r] que se desea ordenar, y un coordenada: String que identifica respecto a qué coordenada se debe ordenar el arreglo. 
 * Este procedimiento ordena los elementos de P de acuerdo a las coordenadas especificadas por el String coordenada, 
 * utilizando el algoritmo quicksort clásico
 * Precondición: true
 * Postondición: (\forall int i; 0 <= i && i < P.size-1; P[i].first <= P[i+1].first) || (\forall int i; 0 <= i && i < P.size-1; P[i].second <= P[i+1].second)
 */ 
fun quicksortTSP(P: Array<Pair<Double,Double>>, p: Int, r: Int, coordenada: String) {
	if (p < r) {
		if (coordenada == "X" || coordenada == "x") {
			var q = particionCoordenadaX(P,p,r)
			quicksortTSP(P, p, q-1, coordenada)
			quicksortTSP(P, q+1, r, coordenada)
		} else {
			var q = particionCoordenadaY(P,p,r)
			quicksortTSP(P, p, q-1, coordenada)
			quicksortTSP(P, q+1, r, coordenada)
		}
	}
}

/* Función: particionCoordenadaX
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas y dos p,r: Int que identifican el subarreglo
 * P[p...r] sobre el cual se producirá una partición. Esta función genera una partición del subarreglo P[p...r] 
 * tal que las coordenadas X de los elementos del subarreglo P[p...q-1] sean menores que P[q].first y las coordenadas X 
 * de los elementos del subarreglo P[q+1...r] sean mayores que P[q].first. La función retorna q: Int.
 * Precondición: 0 <= p < r < P.size
 * Postondición: p <= q <= r && (\forall int i; p <= i && i < q; P[i].first <= P[q].first) && (\forall int i; q+1 <= i && i <= r; P[i].first => P[q].first)
 */  
fun particionCoordenadaX(P: Array<Pair<Double,Double>>, p: Int, r: Int): Int {
	var x = P[r].first
	var i = p - 1
	for (j in p until r) {
		if (P[j].first <= x) {
			i = i + 1
			swap(P, i, j)
		}
	}
	swap(P, i+1, r)
	return i+1
}

/* Función: particionCoordenadaY
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas, y dos p,r: Int que identifican el subarreglo
 * P[p...r] sobre el cual se producirá una partición. Esta función genera una partición del subarreglo P[p...r] 
 * tal que las coordenadas Y de los elementos del subarreglo P[p...q-1] sean menores que P[q].second y las coordenadas Y
 * de los elementos del subarreglo P[q+1...r] sean mayores que P[q].second. La función retorna q: Int.
 * Precondición: 0 <= p < r < P.size
 * Postondición: p <= q <= r && (\forall int i; p <= i && i < q; P[i].second <= P[q].second) && (\forall int i; q+1 <= i && i <= r; P[i].second => P[q].second)
 */  
fun particionCoordenadaY(P: Array<Pair<Double,Double>>, p: Int, r: Int): Int {
	var x = P[r].second
	var i = p - 1
	for (j in p until r) {
		if (P[j].second <= x) {
			i = i + 1
			swap(P, i, j)
		}
	}
	swap(P, i+1, r)
	return i+1
}

/* Función: coindicenDosCoordenadasX
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna true si dos coordenadas X coindicen, 
 * y false en caso contrario
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun coincidenDosCoordenadasX(P: Array<Pair<Double,Double>>): Boolean {
	var n = P.size
	if(n == 1) {
		return false
	}
	for (i in 0 until n-1) {
		for (j in i+1 until n) {
			if (P[i].first == P[j].first) {
				return true
			}
		}
	}
	return false 
}

/* Función: coindicenDosCoordenadasY
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna true si dos coordenadas Y coindicen, 
 * y false en caso contrario
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun coincidenDosCoordenadasY(P: Array<Pair<Double,Double>>): Boolean {
	var n = P.size
	if(n == 1) {
		return false
	}
	for (i in 0 until n-1) {
		for (j in i+1 until n) {
			if (P[i].second == P[j].second) {
				return true
			}
		}
	}
	return false 
}

/* Función: obtenerPuntoDeCorte
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y un ejeDeCorte: String que identifica un eje de corte.
 * La función retorna un Pair<Double,Double> que corresponde al punto de corte para el arreglo P, de acuerdo al eje especificado
 * Precondición: P.size >= 1
 * Postondición: (\exists int i; 0 <= i && i < P.size; P[i] == \result)
 */ 
fun obtenerPuntoDeCorte(P: Array<Pair<Double,Double>>, ejeDeCorte: String): Pair<Double,Double> {
	var n = P.size
	var pos = n/2
	if(n%2 == 0) {
		pos = pos - 1
	}
	if (ejeDeCorte == "X" || ejeDeCorte == "x") {
		if (coincidenDosCoordenadasX(P) == true) {
			quicksortTSP(P,0,n-1,"Y")
		}
		else {
			quicksortTSP(P,0,n-1,"X")
		}
	} else {
		if (coincidenDosCoordenadasY(P) == true) {
			quicksortTSP(P,0,n-1,"X")
		}
		else {
			quicksortTSP(P,0,n-1,"Y")
		}
	}
	return P[pos]
}

/* Función: aplicarCorte
 * Descripción: Recibe un ejeDeCorte: String que especifica el eje de corte, un puntoDeCorte: pair que especifica el punto de corte 
 * y un rectangulo: Array<Pair<Double,Double>> que especifica las coordenadas de un rectángulo, en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un Pair<Array<Pair<Double,Double>>, Array<Pair<Double,Double>>> que contiene las coordenadas de dos rectángulos,
 * los cuales se obtienen a partir del rectángulo original y trazando una recta
 * perpendicular al eje de corte por el punto de corte. Dichas coordenadas se encuentran en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 1
 * Precondición: rectangulo[0].first <= puntoDeCorte.first <= rectangulo[2].first && rectangulo[0].second <= puntoDeCorte.second <= rectangulo[2].second)
 * Postondición: (\result.first).size == 4 && (\result.second).size == 4
 */ 
fun aplicarCorte(ejeDeCorte: String, puntoDeCorte: Pair<Double,Double>, rectangulo: Array<Pair<Double,Double>>): Pair<Array<Pair<Double,Double>>, Array<Pair<Double,Double>>> {
	if (ejeDeCorte == "X" || ejeDeCorte == "x") {
		var coordenadasIzq00 = rectangulo[0]
		var coordenadasIzq10 = puntoDeCorte
		var coordenadasIzq11 = Pair(puntoDeCorte.first, rectangulo[2].second)
		var coordenadasIzq01 = rectangulo[3]
		var coordenadasDer00 = puntoDeCorte
		var coordenadasDer10 = rectangulo[1]
		var coordenadasDer11 = rectangulo[2]
		var coordenadasDer01 = Pair(puntoDeCorte.first, rectangulo[2].second)
		var rectanguloIzq = arrayOf(coordenadasIzq00, coordenadasIzq10, coordenadasIzq11, coordenadasIzq01)
		var rectanguloDer = arrayOf(coordenadasDer00, coordenadasDer10, coordenadasDer11, coordenadasDer01)
		return Pair(rectanguloIzq, rectanguloDer)
	} else {
		var coordenadasIzq00 = rectangulo[0]
		var coordenadasIzq10 = rectangulo[1]
		var coordenadasIzq11 = Pair(rectangulo[1].first, puntoDeCorte.second)
		var coordenadasIzq01 = puntoDeCorte
		var coordenadasDer00 = puntoDeCorte
		var coordenadasDer10 = Pair(rectangulo[1].first, puntoDeCorte.second)
		var coordenadasDer11 = rectangulo[2]
		var coordenadasDer01 = rectangulo[3]
		var rectanguloIzq = arrayOf(coordenadasIzq00, coordenadasIzq10, coordenadasIzq11, coordenadasIzq01)
		var rectanguloDer = arrayOf(coordenadasDer00, coordenadasDer10, coordenadasDer11, coordenadasDer01)
		return Pair(rectanguloIzq, rectanguloDer)	
	}
}

/* Función: obtenerPuntosRectangulo
 * Descripción: Esta función recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas y un 
 * rectangulo: Array<Pair<Double,Double>> con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un arreglo Array<Pair<Double,Double>> que contiene aquellos puntos de P que se encuentran dentro del 
 * rectángulo
 * Precondición: P.size >= 1
 * Postondición: 0 <= \result.size <= P.size
 */ 
fun obtenerPuntosRectangulo(P: Array<Pair<Double,Double>>, rectangulo: Array<Pair<Double,Double>>): Array<Pair<Double,Double>>{
	var n = P.size
	var k = 0
	var tmp = Array(n, {0})
	for (i in 0 until n) {
		if (rectangulo[0].first <= P[i].first && P[i].first <= rectangulo[2].first) {
			if (rectangulo[0].second <= P[i].second && P[i].second <= rectangulo[2].second)
			tmp[i] = 1
			k = k+1 
		}
	}
	var particion = Array(k, {Pair(0.0, 0.0)})
	var j = 0
	for (i in 0 until n) {
		if(tmp[i] == 1) {
			particion[j] = P[i]
			j = j + 1
		}
	}
	return particion
}

/* Función: obtenerPuntoDeCorteMitad
 * Descripción: Recibe un rectangulo: Array<Pair<Double,Double>> con las coordenadas de un rectángulo y un ejeDeCorte: String,
 * el cual especifica respecto a cuál eje se debe obtener el punto de corte. Las coordenadas del rectángulo deben estar en el 
 * siguiente orden: esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un Pair<Double,Double>, que corresponde al punto medio del lado del rectángulo que es paralelo al 
 * eje de corte y se encuentra más cercano a dicho eje.
 * Precondición: rectangulo.size == 4
 * Postondición: rectangulo[0].first <= \result.first <= rectangulo[1].first && rectangulo[0].second <= \result.second <= rectangulo[3].second
 */ 
fun obtenerPuntoDeCorteMitad(rectangulo: Array<Pair<Double,Double>>, eje: String): Pair<Double,Double> {
	var xmin = rectangulo[0].first
	var ymin = rectangulo[0].second
	if (eje == "X" || eje == "x") {
		var xdim = rectangulo[1].first - rectangulo[0].first
		return Pair(xmin + xdim/2, ymin)
	} else {
		var ydim = rectangulo[3].second - rectangulo[0].second
		return Pair(xmin, ymin + ydim/2)
	}
}

// ALGORITMO 2

fun obtenerParticiones(P: Array<Pair<Double,Double>>): Pair< Array<Pair<Double,Double>>, Array<Pair<Double,Double>>> {
	var rectangulo = obtenerRectangulo(P)
	var (Xdim, Ydim) = obtenerDimensionesRectangulo(rectangulo)
	var ejeDeCorte: String
	var eje: String
	if(Xdim > Ydim) {
		ejeDeCorte = "X"
		eje = "X"
	} else {
		ejeDeCorte = "Y"
		eje = "Y"
	}
	var (Xc, Yc) = obtenerPuntoDeCorte(P, ejeDeCorte)
	var (rectanguloIzq, rectanguloDer) = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo)
	var particionIzq = obtenerPuntosRectangulo(P, rectanguloIzq)
	var particionDer = obtenerPuntosRectangulo(P, rectanguloDer)
	if ((particionIzq.size == 0 && particionDer.size > 3) || (particionDer.size == 0 && particionIzq.size > 3)) {
		if (ejeDeCorte == "X") {
			ejeDeCorte = "Y"
		} else {
			ejeDeCorte = "X"
		}
		Xc = obtenerPuntoDeCorte(P, ejeDeCorte).first
		Yc = obtenerPuntoDeCorte(P, ejeDeCorte).second
		rectanguloIzq = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).first
		rectanguloDer = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).second
		particionIzq = obtenerPuntosRectangulo(P, rectanguloIzq)
		particionDer = obtenerPuntosRectangulo(P, rectanguloDer)
		if ((particionIzq.size == 0 && particionDer.size > 3) || (particionDer.size == 0 && particionIzq.size > 3)) {
			Xc = obtenerPuntoDeCorteMitad(rectangulo, eje).first
			Yc = obtenerPuntoDeCorteMitad(rectangulo, eje).second
			rectanguloIzq = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).first
			rectanguloDer = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo).second
			particionIzq = obtenerPuntosRectangulo(P, rectanguloIzq)
			particionDer = obtenerPuntosRectangulo(P, rectanguloDer)
		}
	}
	return Pair(particionIzq, particionDer)
}

// PARA PROBAR LOS ALGORITMOS

/* Función:
 * Descripción: 
 * Precondición:
 * Postondición:
 */ 

fun imprimirArregloCoordenadas(A: Array<Pair<Double,Double>>) {
	var n = A.size 
	print("[ ")
	for (i in 0 until (n-1)) {
		var xi = A[i].first
		var yi = A[i].second
		print("( ${xi} , ${yi} ) , ")
	}
	var xi = A[n-1].first
	var yi = A[n-1].second
	println("( ${xi} , ${yi} ) ] ")
}

fun main(args: Array<String>) {
	var n = args[0].toInt() // Tamaño del arreglo de coordenadas
	var P = Array(n, {Pair(0.0,0.0)}) // Se inicializa el arreglo de coordenadas
	var k = 1
	// Con el siguiente for se asignan los valores del arreglo de coordenadas dados por el usuario
	for (i in 0 until n) { 
		P[i] = Pair(args[k].toDouble(), args[k+1].toDouble())
		k = k+2
	}
	println("El arreglo de coordenadas es")
	imprimirArregloCoordenadas(P)
	println("Y el rectángulo que las contiene vienen dado por las siguientes coordenadas")
	var rectangulo = obtenerRectangulo(P)
	imprimirArregloCoordenadas(rectangulo)
	println("El punto de corte del rectángulo, para el ejeDeCorte X, es")
	var ejeDeCorte = "X"
	var (Xc, Yc) = obtenerPuntoDeCorte(P, ejeDeCorte)
	println("( ${Xc} , ${Yc} )")
	println("Luego del corte se tiene los siguientes rectangulos")
	var (rectanguloIzq, rectanguloDer) = aplicarCorte(ejeDeCorte, Pair(Xc, Yc), rectangulo)
	println("El izquierdo")
	imprimirArregloCoordenadas(rectanguloIzq)
	println("Y el derecho")
	imprimirArregloCoordenadas(rectanguloDer)
	println("Y así obtenemos las particiones")
	var (particionIzq, particionDer) = obtenerParticiones(P)
	println("La izquierda")
	imprimirArregloCoordenadas(particionIzq)
	println("Y la derecha")
	imprimirArregloCoordenadas(particionDer)
}

// EXTRAS  

/* Función: quicksortCoordenadaYTSP
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> de coordenadas, y dos enteros p y r que identifican el subarreglo
 * P[p...r] que se desea ordenar. Esta función ordena los elementos de P de acuerdo a las coordenadas Y, 
 * utilizando el algoritmo quicksort clásico
 * Precondición: true
 * Postondición: (\forall int i; 0 <= i && i < P.size-1; P[i].second <= P[i+1].second)
 */ 
fun quicksortCoordenadaYTSP(P: Array<Pair<Double,Double>>, p: Int, r: Int) {
	if (p < r) {
		var q = particionCoordenadaY(P,p,r)
		quicksortCoordenadaYTSP(P, p, q-1)
		quicksortCoordenadaYTSP(P, q+1, r)
	}
} 
