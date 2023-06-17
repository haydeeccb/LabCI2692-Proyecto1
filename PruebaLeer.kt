import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
   var archivoEntrada = args[0]
   var nombreArchivoSalida = args[1]
   var datosEntrada = File(archivoEntrada).readText() // Leer el archivo de entrada y convertir todo su contenido en un String
   var datosTSP = obtenerDatosTSP(datosEntrada) // datosTSP es el equivalente a un args con las palabras del archivo de entrada en orden
}

fun obtenerDatosTSP(datosEntrada: String): Array<String> {
   var tamañoEntrada = datosEntrada.length // Número de caracteres en el archivo de entrada
   var tmp = Array(tamañoEntrada, {""}) // Arreglo auxiliar
   var saltoDeLinea = "\n"
   var espacio = " "
   var dosPuntos = ":"
   var k = 0
   var numeroDePalabras = 0
   for (i in 0 until tamañoEntrada) {
      // Se verifica caracter por caracter y se descartan los espacios, saltos de línea y el símbolo :
      if (datosEntrada[i] != saltoDeLinea[0] && datosEntrada[i] != espacio[0] && datosEntrada[i] != dosPuntos[0]) { 
         tmp[k] = tmp[k]+datosEntrada[i] // Concatenar la palabra
      } else {
         if (k >= 1) {
            if (tmp[k-1] == "") { // Verificar los espacios que quedan vacíos en el arreglo auxiliar
               tmp[k-1] = tmp[k-1]+"!" // Marcar los espacios que quedaron vacíos
               numeroDePalabras = numeroDePalabras - 1 // Ajustar el contador del número de palabras
            }
         }
         k = k + 1 // Para pasar a la siguiente palabra en el arreglo auxiliar
         numeroDePalabras = numeroDePalabras + 1
      }
   }
   var datos = Array(numeroDePalabras, {"!"}) // Inicializar un arreglo nuevo para guardar las palabras
   var h = 0
   for (i in 0 until k) {
      if (tmp[i] != "!") {
         datos[h] = tmp[i] // Almacenar las palabras en el nuevo arreglo
         println("Palabra numero ${h+1}: ${datos[h]}") // Esto es para verificar la lectura correcta, se debe borrar al final
         h = h + 1
      }
   }
   return datos 
}