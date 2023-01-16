package com.lofstudio.listadelivros.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lofstudio.listadelivros.api.Endpoint
import com.lofstudio.listadelivros.domain.BookDetails
import com.lofstudio.listadelivros.util.NetworkUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


open class LivrosViewModel {


    private var fullList: MutableList<BookDetails>? =null

    private val _listBooks= MutableLiveData<MutableList<BookDetails>>()

    val listBooks: LiveData<MutableList<BookDetails>>
        get() = _listBooks

    private val _error = MutableLiveData<Boolean>(true)
    val error = Transformations.map(_error) { !it }


    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading = Transformations.map(_isLoading) { !it }


    private var _errorText = MutableLiveData<String>()

    val errorText: MutableLiveData<String>
        get() = _errorText




    fun manageJson(data: JsonObject) {


        Log.w("lof_json","manageJson")


        val status = data!!.get("status")
        val num_results =  Integer.parseInt(data!!.get("num_results").asString)

        val resultArray = data.getAsJsonArray("results")



        if(status.toString().contains("OK")){



            if(num_results >  0){



                var auxBookList = ArrayList<BookDetails>()

                if (resultArray != null) {

                    for (i in 0 until resultArray.size()) {


                        val auxItemResultJSONObject = JSONObject(resultArray.get(i).toString())

                        val book_details_array = auxItemResultJSONObject.getJSONArray("book_details")

                        for (i in 0 until book_details_array.length()) {

                            val auxBookDetailsJSONObject = JSONObject(book_details_array.get(i).toString())

                            val bookDetails = Gson().fromJson(auxBookDetailsJSONObject.toString(), BookDetails::class.java)

                            auxBookList.add(bookDetails)


                        }


                    }


                    //ORDENANDO A LISTA
                    var sortedList = auxBookList.sortedWith(compareBy({ it.title }))


                    //GUARDA A LISTA PARA BUSCA LOCAL
                    fullList = sortedList as MutableList<BookDetails>


                    _error.postValue(false)
                    _errorText.postValue("")
                    _isLoading.postValue(false)
                    _listBooks.postValue(sortedList as MutableList<BookDetails>)




                    Log.w("lof_json", "books size ${auxBookList.size}")
                }else{


                    _error.postValue(true)
                    _errorText.postValue("Erro ao carregar lista")


                }


                _error.postValue(false)
                _errorText.postValue("")


            }else{



                _error.postValue(true)
                _errorText.postValue("Lista vazia!")

            }


        }else{



            //ERRO
            _error.postValue(true)
            _errorText.postValue("Erro ao carregar a lista.\n\nTente novamente mais tarde")

        }


    }

    fun loadData() {


        val WS_URL_BASE = "https://api.nytimes.com/"

        val retrofitClient = NetworkUtil.getRetrofitInstance(WS_URL_BASE)
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getJson().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {


                val data = response.body()

                if(data != null){

                    manageJson(data!!)


                }else{

                    //ERRO
                    _error.postValue(true)
                    _errorText.postValue("Erro ao carregar a lista.\n\nTente novamente mais tarde")

                }


            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                //ERRO
                _error.postValue(true)
                _errorText.postValue("Erro ao carregar a lista.\n\nTente novamente mais tarde")

            }

        })


    }


    fun search(src: String) {

        if (src.isEmpty()) {


            loadData()

        } else {




            var fiterList = fullList!!.filter {

                it.title!!.lowercase(Locale.ROOT).contains(src.lowercase(Locale.ROOT)) ||
                        it.author!!.lowercase(Locale.ROOT).contains(src.lowercase(Locale.ROOT))
            }

            _listBooks.postValue(fiterList as MutableList<BookDetails>)


        }
    }

}