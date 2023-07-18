package com.example.jogodavelha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.jogodavelha.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turno
    {
        Circulo,
        X
    }
    private var primeiroTurno = Turno.X
    private var seguinteTurno = Turno.Circulo

    private var placarX = 0
    private var placarCirculo = 0

    private var listaTabuleiro = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTabuleiro()
    }

    private fun initTabuleiro()
    {
        listaTabuleiro.add(binding.a1)
        listaTabuleiro.add(binding.a2)
        listaTabuleiro.add(binding.a3)
        listaTabuleiro.add(binding.b1)
        listaTabuleiro.add(binding.b2)
        listaTabuleiro.add(binding.b3)
        listaTabuleiro.add(binding.c1)
        listaTabuleiro.add(binding.c2)
        listaTabuleiro.add(binding.c3)
    }

    fun cliqueRealizado(view: View)
    {
        if(view !is Button)
            return
        addToTabuleiro(view)

        if(checandoVitoria (Circulo))
        {
            placarCirculo++
            resultado("Circulo Venceu")
        }

        if(checandoVitoria (X))
        {
            placarX++
            resultado("X Venceu")
        }

        if(tabuleiroCheio ())
        {
            resultado ("Draw")
        }
    }

    private fun checandoVitoria(s: String): Boolean {
        if(jogo(binding.a1,s)&& jogo(binding.a2,s)&& jogo(binding.a3,s))
            return true
        if(jogo(binding.b1,s)&& jogo(binding.b2,s)&& jogo(binding.b3,s))
            return true
        if(jogo(binding.c1,s)&& jogo(binding.c2,s)&& jogo(binding.c3,s))
            return true
        if(jogo(binding.a1,s)&& jogo(binding.b1,s)&& jogo(binding.c1,s))
            return true
        if(jogo(binding.a2,s)&& jogo(binding.b2,s)&& jogo(binding.c2,s))
            return true
        if(jogo(binding.a3,s)&& jogo(binding.b3,s)&& jogo(binding.c3,s))
            return true
        if(jogo(binding.a1,s)&& jogo(binding.b2,s)&& jogo(binding.c3,s))
            return true
        if(jogo(binding.a3,s)&& jogo(binding.b2,s)&& jogo(binding.c1,s))
            return true
        return false
    }

    private fun jogo (button: Button, symbol: String): Boolean = button.text == symbol

    private fun resultado(s: String) {
        val mensagem ="\nVitórias do O  - $placarCirculo\n\nVitórias do X  - $placarX"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(mensagem)
            .setPositiveButton("Jogar outra vez")
            {_,_ ->
                resetTabuleiro ()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetTabuleiro() {
        for (button in listaTabuleiro){
            button.text = ""
        }
        if (primeiroTurno == Turno.Circulo)
            primeiroTurno = Turno.X
        else if (primeiroTurno == Turno.X)
            primeiroTurno = Turno.Circulo

        seguinteTurno = primeiroTurno
        setEtiquetaTurno()
    }

    private fun tabuleiroCheio(): Boolean {
        for (button in listaTabuleiro){
            if (button.text == "")
                return false
        }
        return true
    }

    private fun addToTabuleiro (button: Button)
    {
        if(button.text != "")
            return

        if(seguinteTurno == Turno.Circulo)
        {
            button.text = Circulo
            seguinteTurno = Turno.X
        }
        else if(seguinteTurno == Turno.X)
        {
            button.text = X
            seguinteTurno = Turno.Circulo
        }
        setEtiquetaTurno ()


    }

    private fun setEtiquetaTurno()
    {
        var textoTurno = ""
        if (seguinteTurno == Turno.X)
            textoTurno = "Vez do $X"
        else if (seguinteTurno == Turno.Circulo)
            textoTurno = "Vez do $Circulo"

        binding.turnoTV.text = textoTurno
    }

    companion object {
        const val X = "X"
        const val Circulo = "O"
    }

}