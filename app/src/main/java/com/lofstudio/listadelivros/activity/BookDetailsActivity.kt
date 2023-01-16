package com.lofstudio.listadelivros.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.lofstudio.listadelivros.R
import com.lofstudio.listadelivros.domain.BookDetails

class BookDetailsActivity : AppCompatActivity() {

    var txtTtle: TextView? = null
    var txtAutor: TextView? = null
    var txtPrice: TextView? = null
    var txtDesc : TextView? = null
    var toolbar: Toolbar? = null
    var appBarLayout: AppBarLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)


        txtTtle = findViewById<View>(R.id.txtTtle) as TextView
        txtDesc = findViewById<View>(R.id.txtDesc) as TextView
        txtAutor = findViewById<View>(R.id.txtAutor) as TextView
        txtPrice = findViewById<View>(R.id.txtPrice) as TextView


        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar!!.setTitleTextColor(getResources().getColor(R.color.white))

        appBarLayout = findViewById(R.id.appBarLayout) as AppBarLayout
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })

        var drawable: Drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.arrow_left, null)!!

        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, resources.getColor(R.color.white))
        toolbar!!.setNavigationIcon(drawable)




        try {

            val bundle = intent.extras

            val jsonItem = bundle!!.getString("jsonItem")

            val bookDetails = Gson().fromJson(jsonItem,BookDetails::class.java)


            if(bookDetails != null){

                loadData(bookDetails)
            }



        }catch (e: Exception){


        }
    }

    fun loadData(bookDetails: BookDetails){

        txtTtle!!.text = "${bookDetails.title}"
        txtDesc!!.text = "${bookDetails.description}"
        txtAutor!!.text = "${bookDetails.author}"
        txtPrice!!.text = "${bookDetails.price}"


    }
}