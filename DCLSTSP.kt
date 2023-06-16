// Librerías

import kotlin.math.roundToInt

// FUNCIONES NECESARIAS PARA EL ALGORITMO 2

/* Función: obtenerMaximoCoordenadasX
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un Double que corresponde al elemento 
 * máximo de las coordenadas X de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result >= P[i].first) && (\exists int i; 0 <= i && i < P.size; \result == P[i].first)
 */ 
fun obtenerMaximoCoordenadasX(P: Array<Pair<Double,Double>>): Double {
	var n = P.size
	var maximoX = P[0].first
	for (i in 1 until n) {
		if (P[i].first > maximoX) {
			maximoX = P[i].first
		}
	}
	return maximoX
}

/* Función: obtenerMinimoCoordenadasX
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un Double que corresponde al elemento 
 * mínimo de las coordenadas X de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result <= P[i].first) && (\exists int i; 0 <= i && i < P.size; \result == P[i].first)
 */ 
fun obtenerMinimoCoordenadasX(P: Array<Pair<Double,Double>>): Double {
	var n = P.size
	var minimoX = P[0].first
	for (i in 1 until n) {
		if (P[i].first < minimoX) {
			minimoX = P[i].first
		}
	}
	return minimoX
}

/* Función: obtenerMaximoCoordenadasY
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un Double que corresponde al elemento 
 * máximo de las coordenadas Y de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result >= P[i].second) && (\exists int i; 0 <= i && i < P.size; \result == P[i].second)
 */ 
fun obtenerMaximoCoordenadasY(P: Array<Pair<Double,Double>>): Double {
	var n = P.size
	var maximoY = P[0].second
	for (i in 1 until n) {
		if (P[i].second > maximoY) {
			maximoY = P[i].second
		}
	}
	return maximoY
}

/* Función: obtenerMinimoCoordenadasY
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un Double que corresponde al elemento 
 * mínimo de las coordenadas Y de dicho arreglo.
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result <= P[i].second) && (\exists int i; 0 <= i && i < P.size; \result == P[i].second)
 */ 
fun obtenerMinimoCoordenadasY(P: Array<Pair<Double,Double>>): Double {
	var n = P.size
	var minimoY = P[0].second
	for (i in 1 until n) {
		if (P[i].second < minimoY) {
			minimoY = P[i].second
		}
	}
	return minimoY
}

/* Función: obtenerRectangulo
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un arreglo Array<Pair<Double,Double>> 
 * que muestra las coordenadas de un rectángulo que contiene todos los puntos del arreglo P. 
 * Dichas coordenadas se encuentran en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda
 * Precondición: P.size >= 1
 * Postondición: (\forall int i: 0 <= i < P.size, \result[0].first <= P[i].first <= \result[2].first && \result[0].second <= P[i].second <= \result[2].second) 
 */ 
fun obtenerRectangulo(P: Array<Pair<Double,Double>>): Array<Pair<Double,Double>> {
	var minimoX = obtenerMinimoCoordenadasX(P)
	var maximoX = obtenerMaximoCoordenadasX(P)
	var minimoY = obtenerMinimoCoordenadasY(P)
	var maximoY = obtenerMaximoCoordenadasY(P)
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
		var coordenadasIzq10 = Pair(puntoDeCorte.first,rectangulo[0].second)
		var coordenadasIzq11 = Pair(puntoDeCorte.first, rectangulo[2].second)
		var coordenadasIzq01 = rectangulo[3]
		var coordenadasDer00 = Pair(puntoDeCorte.first,rectangulo[0].second)
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
		var coordenadasIzq01 = Pair(rectangulo[3].first, puntoDeCorte.second)
		var coordenadasDer00 = Pair(rectangulo[3].first, puntoDeCorte.second)
		var coordenadasDer10 = Pair(rectangulo[1].first, puntoDeCorte.second)
		var coordenadasDer11 = rectangulo[2]
		var coordenadasDer01 = rectangulo[3]
		var rectanguloIzq = arrayOf(coordenadasIzq00, coordenadasIzq10, coordenadasIzq11, coordenadasIzq01)
		var rectanguloDer = arrayOf(coordenadasDer00, coordenadasDer10, coordenadasDer11, coordenadasDer01)
		return Pair(rectanguloIzq, rectanguloDer)	
	}
}

/* Función: esElPrimerRectangulo
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * Esta función verifica si el rectángulo dado es el primer rectángulo que se obtiene luego de aplicar el corte al rectángulo
 * que contiene a P (el primer rectángulo corresponde al izquierdo si el corte es vertical y al inferior si es horizontal)
 * Precondición: P.size >= 1
 * Postondición: true || false
 */ 
fun esElPrimerRectangulo(P: Array<Pair<Double,Double>>, rectangulo: Array<Pair<Double,Double>>): Boolean {
	var minimoX = obtenerMinimoCoordenadasX(P)
	var minimoY = obtenerMinimoCoordenadasY(P)
	var rec00 = Pair(minimoX, minimoY)
	if (rec00 == rectangulo[0]) {
		return true
	} else {
		return false
	}
}

/* Función: obtenerPuntosPrimerRectangulo
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * Esta función retorna los valores de P que están contenidos en el rectángulo dado, en un Array<Pair<Double,Double>>. 
 * Se incluyen aquellas ciudades de P que están sobre el lado correspondiente al corte 
 * (dichas ciudades se asignan a la partición del primer rectángulo por diseño)
 * Precondición: P.size >= 1
 * Postondición: \result.size >= 0
 */ 
fun obtenerPuntosPrimerRectangulo(P: Array<Pair<Double,Double>>, rectangulo: Array<Pair<Double,Double>>): Array<Pair<Double,Double>> {
	var n = P.size
	var k = 0
	var tmp = Array(n, {0})
	for (i in 0 until n) {
		if (rectangulo[0].first <= P[i].first && P[i].first <= rectangulo[2].first) {
			if (rectangulo[0].second <= P[i].second && P[i].second <= rectangulo[2].second){
				tmp[i] = 1
				k = k + 1 
			}	
		}
	}
	var particion = Array(k, {Pair(0.0, 0.0)})
	var j = 0
	for (i in 0 until n) {
		if (tmp[i] == 1) {
			particion[j] = P[i]
			j = j + 1
		} 
	}
	return particion
} 

/* Función: obtenerPuntosSegundoRectangulo
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * Esta función retorna los valores de P que están contenidos en el rectángulo dado, en un Array<Pair<Double,Double>>. 
 * No se incluyen aquellas ciudades de P que están sobre el lado correspondiente al corte 
 * (dichas ciudades se asignan a la partición del primer rectángulo por diseño)
 * Precondición: P.size >= 1
 * Postondición: \result.size >= 0
 */ 
fun obtenerPuntosSegundoRectangulo(P: Array<Pair<Double,Double>>, rectangulo: Array<Pair<Double,Double>>) : Array<Pair<Double,Double>> {
	var n = P.size
	var k = 0
	var tmp = Array(n, {0})
	var maximoX = obtenerMaximoCoordenadasX(P)
	var maximoY = obtenerMaximoCoordenadasY(P)
	if (rectangulo[3] == Pair(maximoX, maximoY)) {
		for (i in 0 until n) {
			if (rectangulo[0].first <= P[i].first && P[i].first <= rectangulo[2].first) {
				if (rectangulo[0].second < P[i].second && P[i].second <= rectangulo[2].second){
					tmp[i] = 1
					k = k + 1 
				}
			}
		}
	} else {
		for (i in 0 until n) {
			if (rectangulo[0].first < P[i].first && P[i].first <= rectangulo[2].first) {
				if (rectangulo[0].second <= P[i].second && P[i].second <= rectangulo[2].second){
					tmp[i] = 1
					k = k + 1
				} 
			}
		}
	}
	var particion = Array(k, {Pair(0.0, 0.0)})
	var j = 0
	for (i in 0 until n) {
		if (tmp[i] == 1) {
			particion[j] = P[i]
			j = j + 1
		} 
	}
	return particion
}

/* Función: obtenerPuntosRectangulo
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y un rectangulo: Array<Pair<Double,Double>> 
 * con las coordenadas de un rectángulo, las cuales deben estar en el siguiente orden:
 * esquina inferior izquierda, esquina inferior derecha, esquina superior derecha, esquina superior izquierda.
 * La función retorna un arreglo Array<Pair<Double,Double>> que contiene aquellos puntos de P que están contenidos en el 
 * rectángulo dado. Se incluyen los lados del corte en caso de que el rectángulo dado sea el primero y no se incluye en caso
 * contrario (por diseño)
 * Precondición: P.size >= 1
 * Postondición: 0 <= \result.size <= P.size
 */ 
fun obtenerPuntosRectangulo(P: Array<Pair<Double,Double>>, rectangulo: Array<Pair<Double,Double>>): Array<Pair<Double,Double>>{
	if (esElPrimerRectangulo(P, rectangulo) == true) {
		return obtenerPuntosPrimerRectangulo(P,rectangulo)
	} else {
		return obtenerPuntosSegundoRectangulo(P,rectangulo)
	}
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

/* Función: obtenerParticiones
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y retorna un 
 * Pair< Array<Pair<Double,Double>>, Array<Pair<Double,Double>>> que contiene dos particiones entre las cuales se reparten
 * las ciudades dadas en P. Este algoritmo cumple el papel de "Divide" en el esquema "Divide-and-Conquer" empleado para 
 * resolver el TSP
 * Precondición: P.size >= 1
 * Postondición: (\result.first).size >= 1 && (\result.second).size >= 1
 */ 
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


// FUNCIONES NECESARIAS PARA EL ALGORITMO 4 (en particular para el 2-opt)

/* Función: distanciaDosPuntos
 * Descripción: Recibe dos coordenadas de puntos y retorna la distancia euclidiana entre dichos puntos, redondeada a un entero
 * Precondición: true
 * Postondición: \result = (sqrt((Punto2.first - Punto1.first)*(Punto2.first - Punto1.first) + (Punto2.second - Punto1.second)*(Punto2.second - Punto1.second))).roundToInt()
 */ 
fun distanciaDosPuntos(Punto1: Pair<Double,Double>, Punto2: Pair<Double,Double>): Int {
	var xd = Punto2.first - Punto1.first
	var yd = Punto2.second - Punto1.second
	var d = Math.sqrt(xd*xd + yd*yd)
	return d.roundToInt()
}

/* Función: obtenerDistanciaTour
 * Descripción: Recibe un P: Array<Pair<Double,Double>> de coordenadas y un tour: Array<Int>, y retorna la distancia del tour
 * Precondición: (\forall int i; 0 <= i && i < tour.size; 1 <= tour[i] && tour[i] <= P.size)
 * Postondición: \result >= 0 
 */ 
fun obtenerDistanciaTour(tour: Array<Pair<Double,Double>>): Int {
	var n = tour.size
	var distancia = 0
	for (i in 0 until n-1) {
		distancia = distancia + distanciaDosPuntos(tour[i], tour[i+1])
	}
	return distancia
}

/* Función: swap2OPT
 * Descripción: Recibe un tour: Array<Pair<Double,Double>> y dos enteros p,q que indican dos posiciones en el tour
 * Este procedimiento invierte el subarreglo tour[p..q]
 * Precondición: 0 <= p <= q < tour.size
 * Postondición: (\forall int; 0 <= i && i < p: \result[i] == tour[i]) && (\forall int: q < i && i < tour.size; \result[i] == tour[i]) && (\forall int i ; p <= i && i <= q: \result[i] == tour[q+p-i])
 */ 
fun swap2OPT(tour: Array<Pair<Double,Double>>, p: Int, q: Int): Array<Pair<Double,Double>> {
	var n = q-p+1
	var tmp = Array(n, {Pair(0.0, 0.0)})
	var k = q
	for (i in 0 until n) {
		tmp[i] = tour[k]
		k = k-1
	}
	k = 0
	for (i in p until q+1) {
		tour[i] = tmp[k]
		k = k+1
	}
	return tour
}

/* Función: cambioDistanciaTour
 * Descripción: Recibe un tour: Array<Pair<Double,Double>> y dos enteros i,j. Esta función calcula el cambio en la distancia luego
 * de retirar los lados tour[i-1],tour[i] y tour[j],tour[j+1] reemplazándolos con los lados tour[i-1],tour[j] y tour[i],tour[j+1]
 * Precondición: 0 < i <= j < tour.size-1
 * Postondición: true
 */ 
fun cambioDistanciaTour(tour: Array<Pair<Double,Double>>, i: Int, j: Int): Int {
	var dNew1 = distanciaDosPuntos(tour[i-1], tour[j])
	var dNew2 = distanciaDosPuntos(tour[i], tour[j+1])
	var dOld1 = distanciaDosPuntos(tour[i-1], tour[i])
	var dOld2 = distanciaDosPuntos(tour[j], tour[j+1])
	var cambio = dNew1 + dNew2 - dOld1 - dOld2
	return cambio
}

/* Función: busquedaLocalCon2OPT
 * Descripción: Recibe un tour: Array<Pair<Double,Double>> y retorna un tour: Array<Pair<Double,Double>>. Esta función emplea el 
 * operador 2opt para mejorar una solución del TSP 
 * Precondición: tour.size >= 1
 * Postondición: \result.size >= 1
 */ 
fun busquedaLocalCon2OPT(tour: Array<Pair<Double,Double>>) : Array<Pair<Double,Double>> {
	var tourActual = tour
	var n = tourActual.size
	for (i in 1 until n-2) {
		for (j in i+1 until n-1) {
			var cambioDistancia = cambioDistanciaTour(tourActual, i, j)
			if(cambioDistancia < 0) {
				tourActual = swap2OPT(tourActual, i, j)
			}
		}
	}
	return tourActual
}

// ALGORITMO 4

/* Función: divideAndConquerAndLocalSearchTSP
 * Descripción: Recibe un arreglo P: Array<Pair<Double,Double>> con coordenadas de ciudades y retornar un tour que resuelve el
 * problema del TSP para dicho arreglo. El problema lo resuelve por medio de la técnica de Divide-And-Conquer y luego lo mejora
 * empleando el algoritmo 2-opt
 * Precondición: P.size >= 1
 * Postondición: \result.size >= 2 && \result[0] == \result[\result.size-1]
 */ 
/*fun divideAndConquerAndLocalSearchTSP(P: Array<Pair<Double,Double>>) : Array<Pair<Double,Double>> {
	var c1 = divideAndConquerTSP(P)
	return busquedaLocalCon2OPT(c1)
}*/

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

fun imprimirArreglo(A: Array<Int>) {
	for(i in 0 until A.size) {
		print("${A[i]} ")
	}
	println("")
}

// Para generar tours aleatorios
fun permutaciones(A: Array<Pair<Double, Double>>): Array<Pair<Double, Double>> {
    var B = Array(A.size){i -> i}
    var C: Array<Pair<Double, Double>> = Array(A.size+1){Pair(0.0,0.0)}
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

fun main(args: Array<String>) {
	
	// Generando un arreglo de coordenadas a partir de los datos dados por el usuario
	
	var n = args[0].toInt() // Tamaño
	var P = Array(n, {Pair(0.0,0.0)}) // Se inicializa
	var k = 1
	// Se asignan los valores
	for (i in 0 until n) { 
		P[i] = Pair(args[k].toDouble(), args[k+1].toDouble())
		k = k+2
	}
	println("El arreglo de coordenadas es")
	imprimirArregloCoordenadas(P)


	// Probando el algoritmo 2
	
	var particionIzq = obtenerParticiones(P).first // Obtener la partición izquierda (o inferior)
	var particionDer = obtenerParticiones(P).second  // Obtener la partición derecha (o superior)
	println("Se tienen las siguientes particiones de P")
	imprimirArregloCoordenadas(particionIzq)
	imprimirArregloCoordenadas(particionDer)


	// Probando el algoritmo 4 (el 2-opt en realidad)
	
	var tourAleatorio = permutaciones(P)  // Generar un tour aleatorio
	println("El tour original es es")
	imprimirArregloCoordenadas(tourAleatorio)
	var distancia = obtenerDistanciaTour(tourAleatorio)
	println("Y su distancia es ${distancia}")
	var tourAleatorio2 = busquedaLocalCon2OPT(tourAleatorio) // Mejorar el tour aleatorio con el 2-opt
	distancia = obtenerDistanciaTour(tourAleatorio2)
	println("El nuevo tour es")
	imprimirArregloCoordenadas(tourAleatorio2)
	println("Y su distancia es ${distancia}")
}

