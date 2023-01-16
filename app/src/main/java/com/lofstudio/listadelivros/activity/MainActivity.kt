package com.lofstudio.listadelivros.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lofstudio.listadelivros.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //COSTUMO DEIZAR A MAIN PARA CHECAR ALGUMA COISA NO SERVIDOR, LOGIN DE USUARIO ETC.
        //COMO NAO É O CASO DEIXEI VAZIA

        startActivity(Intent(baseContext,BuscarLivrosActivity::class.java))
        finish()
    }
}