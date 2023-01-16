package com.lofstudio.listadelivros.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.lofstudio.listadelivros.R
import com.lofstudio.listadelivros.adapter.BookDetailsAdapter
import com.lofstudio.listadelivros.domain.BookDetails
import com.lofstudio.listadelivros.viewmodel.LivrosViewModel
import com.lofstudio.minhascolecoes.interfaces.RecyclerViewButtonOnClickListener
import java.util.*

class BuscarLivrosActivity : AppCompatActivity(),RecyclerViewButtonOnClickListener {


    var edtSearch: EditText? =null
    var lyRefresh: SwipeRefreshLayout? =null
    var lyNest: NestedScrollView? =null
    var lyData: LinearLayout? =null
    var lyEmpty: LinearLayout? =null
    var txtEmpty: TextView? =null


    var recyclerview: RecyclerView? = null


    var viewModel: LivrosViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_livros)


        viewModel = LivrosViewModel()




        edtSearch = findViewById(R.id.edtSearch)
        lyRefresh = findViewById(R.id.lyRefresh)
        lyNest = findViewById(R.id.lyNest)
        lyData = findViewById(R.id.lyData)
        lyEmpty= findViewById(R.id.lyEmpty)
        txtEmpty= findViewById(R.id.txtEmpty)

        recyclerview = findViewById(R.id.recyclerview)
        recyclerview!!.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)



        lyRefresh!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                loadData()


            }
        })



        viewModel!!.listBooks!!.observe(this, Observer {


            if(it.size > 0){

                lyData!!.visibility= View.VISIBLE
                lyEmpty!!.visibility= View.GONE

            }else{

                lyData!!.visibility= View.GONE
                lyEmpty!!.visibility= View.VISIBLE
            }


            val adapter = BookDetailsAdapter(this,it)
            adapter.setRecyclerViewButtonOnClickListener(this)


            adapter.submitList(it)

            recyclerview!!.adapter = adapter

            lyRefresh!!.isRefreshing = false

        })


        edtSearch!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                viewModel!!.search(s.toString())


            }
        })




        loadData()

    }


    fun loadData(){


        lyRefresh!!.isRefreshing = true


        viewModel!!.loadData()



    }


    override fun onResume() {
        super.onResume()



    }

    override fun onClickListener(view: View?, position: Int, `object`: Any?) {

        val item: BookDetails? = `object` as BookDetails?

        var bookIntent = Intent(baseContext,BookDetailsActivity::class.java)
        bookIntent.putExtra("jsonItem",Gson().toJson(item))
        startActivity(bookIntent)

    }

    override fun onLongClickListener(view: View?, position: Int, `object`: Any?) {


    }

    override fun onActionClick(view: View?, position: Int, `object`: Any?, button: Int) {


    }


}

