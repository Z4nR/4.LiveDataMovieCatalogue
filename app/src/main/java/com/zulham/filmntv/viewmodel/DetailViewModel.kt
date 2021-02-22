package com.zulham.filmntv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.filmntv.model.DataModelDetail
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel: ViewModel() {
    private val detailShow = MutableLiveData<DataModelDetail>()

    private val isError: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<String>()

    fun setDetail(id: String?){

        val client = AsyncHttpClient()

        val url = "https://api.themoviedb.org/3/movies/$id?api_key=9ca2038021928e3fd76667cb05f853e0&page=1"

        client.addHeader("Authorization :", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5Y2EyMDM4MDIxOTI4ZTNmZDc2NjY3Y2IwNWY4NTNlMCIsInN1YiI6IjYwMzI1NGZlMGE1MTdjMDA0MTk0NWMxNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WRCFHSb3ezZV33wFOgAnaQMvs0nz3uZjq_ADpgKNpxs")
        client.addHeader("Content-Type:", "application/json;charset=utf-8")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {

                try {
                    val result = responseBody?.let { String(it) }
                    val res = result?.let { JSONObject(result) }

                    if (res != null) {
                        val detail = DataModelDetail(
                            img = "https://image.tmdb.org/t/p/w500/" + res.getString("backdrop_path"),
                            title = res.getString("title"),
                            desc = checkNullToString(res, "overview"),
                            releaseDate = checkNullToString(res, "release_date"),
                            genre = res.getString("genres"),
                            production = res.getString("production_companies"),
                            id = res.getInt("id")
                        )

                        detailShow.value = detail

                    } else {

                    }


                } catch (e: Exception) {
                    e.message?.let { setError(true, it) }
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessages =
                    when (statusCode) {
                        401 -> "$statusCode : Bad Request"
                        403 -> "$statusCode : Forbidden"
                        404 -> "$statusCode : Not Found"
                        else -> "$statusCode : ${error?.message}"
                    }
                isError.value = true
                errorMessage.value = errorMessages
            }
        })
    }

    fun checkNullToString(res:JSONObject, key: String): String {
        return if (res.isNull(key)) "Kosong" else res.getString(key)
    }

    fun getDetail(): LiveData<DataModelDetail> {
        return detailShow
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