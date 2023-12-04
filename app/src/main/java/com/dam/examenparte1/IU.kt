package com.dam.examenparte1

import android.util.Log

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Clase que contendra todo el programa,El juego consiste en darle al 'START', que empieza la cuenta atrás. Se van mostrando frases, que pueden ser verdaderas o falsas, segun vayan apareciendo el jugador le dará al botón de verdadero o falso y si coincide le suma uno a su puntuación, que se muestra en el cuadro de texto puntuación.
 *
 * El juego termina cuando la cuenta atrás llega a cero. Se le puede volver a iniciar con 'START'
 * Un botón de 'START' que empezará el juego y lanzará una cuenta atrás de 20 sg
 * Un cuadro de texto para mostrar la cuenta atrás
 * Un cuadro de texto para frases aleatorias, que estarán guardadas en una "mutableList"
 * Un botón de "V" verdadero y un botón "F" de falso
 * Un cuadro de texto que mostrará la puntuación, cantidad de aciertos
 */
class IU : ViewModel() {
    /**
     * variable global
     */
// para crear los objetos de las frases
    data class Frase(var texto: String, var verdadero: Boolean)
    // lista para almacenar las frase
    var frases: MutableList<Frase> = mutableListOf()
    // la frase actual
    var fraseActual: MutableState<Frase> = mutableStateOf(Frase("-",true))
    // El tiempo actual
    var tiempoActual: MutableState<Int> = mutableStateOf(0)
    // La puntuación
    var puntuacion: MutableState<Int> = mutableStateOf(0)


    /**
     * utilización dentro de un función composable cualquiera
     */
    @Composable
    fun aux(){
        // introducir frases en la lista
        frases.add(Frase("el torneo de rugby cinco naciones, ahora es seis naciones",true))
        frases.add(Frase("en el cielo hay cinco estrellas",false))
        frases.add(Frase("el dia cinco de diciembre del 2023 es martes",true))
        frases.add(Frase("cinco más cinco son diez",true))
        frases.add(Frase("dos mas dos son cinco",false))
        frases.add(Frase("los elefantes tienen cinco patas",false))
        frases.add(Frase("las estaciones climáticas son cinco",false))
        frases.add(Frase("tenemos cinco dedos los humanos",true))
        frases.add(Frase("cinco días tiene la semana sin el Domingo y el Sábado",true))
        frases.add(Frase("una gallina pesa menos que cinco toneladas",true))

        // asignar una frase aleatoria
        fraseActual.value = frases.random()

    }

    /**
     * Función que se encarga de mostrar el juego
     */

    /**
     * Boton que se usa para empezar el juego y comenzar la cuenta atras
     */
    @Composable
    fun botonStart() {
        Button(
            onClick = {
                // Comenzara un contador que indicara el tiempo que queda para que se acabe el juego
                // Se le asigna el valor de 20 a la variable tiempo
                tiempoActual.value = 20
                // Reduce el tiempo en 1 cada segundo usando un scope
                viewModelScope.launch {
                    while (tiempoActual.value > 0) {
                        tiempoActual.value--
                        delay(1000)
                    }
                }
                fraseActual.value = frases.random()
                // La puntuacion se reinicia
                puntuacion.value = 0
            },
            modifier = Modifier
                .height(35.dp)
                .width((350 / 2).dp)
                .padding(horizontal = 30.dp, vertical = 0.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = "Start")
        }
    }

    /**
     * Un cuadro de texto para mostrar la cuenta atrás
     */
    @Composable
    fun cuadroTextoCuentaAtras() {
        Text(text = tiempoActual.value.toString())
    }
    /**
     * Un cuadro de texto para frases aleatorias, que estarán guardadas en una "mutableList"
     */
    @Composable
    fun cuadroTextoFrases() {
        Text(text = fraseActual.value.texto)
    }
    /**
     * Un botón de "V" verdadero
     */
    @Composable
    fun botonVerdadero() {
        Button(
            onClick = {
                // si la frase es verdadera
                if (fraseActual.value.verdadero) {
                    // se suma uno a la puntuacion
                    puntuacion.value++
                    // se cambia la frase
                    fraseActual.value = frases.random()
                }
            },
            modifier = Modifier
                .height(35.dp)
                .width((350 / 2).dp)
                .padding(horizontal = 30.dp, vertical = 0.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = "V")
        }
    }
    /**
     * Un botón de "F" falso
     */
    @Composable
    fun botonFalso() {
        Button(
            onClick = {
                // si la frase es falsa
                if (!fraseActual.value.verdadero) {
                    // se suma uno a la puntuacion
                    puntuacion.value++
                    // se cambia la frase
                    fraseActual.value = frases.random()
                }
            },
            modifier = Modifier
                .height(35.dp)
                .width((350 / 2).dp)
                .padding(horizontal = 30.dp, vertical = 0.dp),
            colors = ButtonDefaults.buttonColors(Color.Black)
        ) {
            Text(text = "F")
        }
    }
    /**
     * Un cuadro de texto que mostrará la puntuación, cantidad de aciertos, en el centro de la pantalla
     */
    @Composable
    fun cuadroTextoPuntuacion() {
        //Debe de mostrarse en la mitad de la pantalla
        Text(text = puntuacion.value.toString())
    }
    /**
     * Como se muestra el juego
     */
    @Composable
    fun juego(){

        Column{
            Row {
                botonStart()
                cuadroTextoCuentaAtras()
            }
            // debe de estar en el centro de la pantalla
            Row {
                cuadroTextoFrases()
            }
            Row {
                botonVerdadero()
                botonFalso()
            }
            Row {
                cuadroTextoPuntuacion()
            }

        }





    }


















}