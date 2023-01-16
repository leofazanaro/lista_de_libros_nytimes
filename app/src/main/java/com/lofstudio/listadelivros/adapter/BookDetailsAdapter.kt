package com.lofstudio.listadelivros.adapter

import android.content.Context

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.cardview.widget.CardView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lofstudio.listadelivros.R
import com.lofstudio.listadelivros.domain.BookDetails
import com.lofstudio.minhascolecoes.interfaces.RecyclerViewButtonOnClickListener

import java.lang.Exception


class BookDetailsAdapter(

    context: Context,
    private var mlist: MutableList<BookDetails>) :

    RecyclerView.Adapter<BookDetailsAdapter.MyViewHolder>() {


    private val context: Context
    private val mLayoutInflater: LayoutInflater
    private var recyclerViewButtonOnClickListener: RecyclerViewButtonOnClickListener? = null

    init {

        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.context = context



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layout = R.layout.item_book_details
        val v = mLayoutInflater.inflate(layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {


        myViewHolder.txtTtle.text = "${mlist[position].title}"
        myViewHolder.txtAutor.text = "${mlist[position].author}"
        myViewHolder.txtPrice.text = "${mlist[position].price}"


        myViewHolder.card.setOnClickListener { view ->
            if (recyclerViewButtonOnClickListener != null) {

                recyclerViewButtonOnClickListener!!.onClickListener(view, position, mlist[position])
            }
        }


        myViewHolder.card.setOnLongClickListener(object : View.OnLongClickListener {

            override fun onLongClick(view: View): Boolean {
                if (recyclerViewButtonOnClickListener != null) {
                    recyclerViewButtonOnClickListener!!.onLongClickListener(view, position, mlist[position])
                }
                return true
            }
        })

    }


    override fun getItemCount(): Int {
        return mlist.size
    }

    fun addItem(item: BookDetails) {
        mlist.add(item)
        notifyDataSetChanged()
    }

    fun setRecyclerViewButtonOnClickListener(recyclerViewButtonOnClickListener: RecyclerViewButtonOnClickListener?) {
        this.recyclerViewButtonOnClickListener = recyclerViewButtonOnClickListener
    }

    fun submitList(it: MutableList<BookDetails>) {

        mlist = it
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var card: CardView
        var txtTtle: TextView
        var txtAutor: TextView
        var txtPrice: TextView



        init {

            txtTtle = itemView.findViewById<View>(R.id.txtTtle) as TextView
            txtAutor = itemView.findViewById<View>(R.id.txtAutor) as TextView
            txtPrice = itemView.findViewById<View>(R.id.txtPrice) as TextView
            card = itemView.findViewById<View>(R.id.card) as CardView
        }
    }
}