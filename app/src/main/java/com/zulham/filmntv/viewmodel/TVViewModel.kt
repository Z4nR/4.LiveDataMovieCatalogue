package com.zulham.filmntv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.filmntv.model.DataModel
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TVViewModel: ViewModel() {
    private val listTV = MutableLiveData<ArrayList<DataModel>>()

    private val isError = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<String>()

    fun setData(){
        val listMovie = ArrayList<DataModel>()

        val client = AsyncHttpClient()

        val url = "https://api.themoviedb.org/3/discover/tv?api_key=9ca2038021928e3fd76667cb05f853e0&page=1"

        client.addHeader("Authorization :", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Y2EyMDM4MDIxOTI4ZTNmZDc2NjY3Y2IwNWY4NTNlMCIsInN1YiI6IjYwMzI1NGZlMGE1MTdjMDA0MTk0NWMxNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WRCFHSb3ezZV33wFOgAnaQMvs0nz3uZjq_ADpgKNpxs")
        client.addHeader("Content-Type:", "application/json;charset=utf-8")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {

                val result = String(responseBody)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("results")

                    for (i in 0 until items.length()){
                        val jsonObj = items.getJSONObject(i)
                        listMovie.add(
                            DataModel(
                                id = jsonObj.getInt("id"),
                                title = jsonObj.getString("name"),
                                releaseDate = jsonObj.getString("first_air_date"),
                                vote = jsonObj.getString("vote_average"),
                                img = "https://image.tmdb.org/t/p/w500/" + jsonObj.getString("poster_path")
                            )
                        )

                    }

                    listTV.value = listMovie


                } catch (e: Exception) {
                    e.message?.let { setError(true, it) }
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {

                val errorMessages =
                    when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error.message}"
                    }
                isError.value = true
                errorMessage.value = errorMessages
            }
        })
    }

    fun getData(): LiveData<ArrayList<DataModel>> {
        return listTV
    }

    fun setError(error: Boolean, message: String) {
        isError.value = error
        errorMessage.value = message
    }

    fun getIsError(): LiveData<Boolean> {
        return isError
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }
}