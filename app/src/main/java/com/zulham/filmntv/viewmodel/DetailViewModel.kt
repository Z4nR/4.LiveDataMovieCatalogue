package com.zulham.filmntv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.zulham.filmntv.model.DataModelDetail
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Integer.parseInt

class DetailViewModel: ViewModel() {
    private val detailShow = MutableLiveData<DataModelDetail>()

    private val isError: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private val errorMessage = MutableLiveData<String>()

    fun setFilmDetail(id: String?){

        val client = AsyncHttpClient()

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val url = "https://api.themoviedb.org/3/movie/${parseInt(id)}?api_key=9ca2038021928e3fd76667cb05f853e0"

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

                        //get genre
                        val gen = res.getJSONArray("genres")
                        var genString = ""
                        for (i in 0 until gen.length()){
                            genString += gen.getJSONObject(i).getString("name") + " "
                        }

                        //get company
                        val com = res.getJSONArray("production_companies")
                        var comString = ""
                        for (i in 0 until com.length()){
                            comString += com.getJSONObject(i).getString("name") + "\n"
                        }

                        isError.value = false
                        val detail = DataModelDetail(
                            img = "https://image.tmdb.org/t/p/w500/" + res.getString("poster_path"),
                            title = res.getString("title"),
                            desc = checkNullToString(res, "overview"),
                            releaseDate = checkNullToString(res, "release_date"),
                            genre = genString,
                            production = comString,
                            id = res.getInt("id")
                        )

                        detailShow.value = detail

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

    fun getFilmDetail(): LiveData<DataModelDetail> {
        return detailShow
    }

    fun setTVDetail(id: String?){

        val client = AsyncHttpClient()

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val url = "https://api.themoviedb.org/3/tv/${parseInt(id)}?api_key=9ca2038021928e3fd76667cb05f853e0"

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

                        //get genre
                        val gen = res.getJSONArray("genres")
                        var genString = ""
                        for (i in 0 until gen.length()){
                            genString += gen.getJSONObject(i).getString("name") + " "
                        }

                        //get company
                        val com = res.getJSONArray("production_companies")
                        var comString = ""
                        for (i in 0 until com.length()){
                            comString += com.getJSONObject(i).getString("name") + "\n"
                        }

                        isError.value = false
                        val detail = DataModelDetail(
                                img = "https://image.tmdb.org/t/p/w500/" + res.getString("poster_path"),
                                title = res.getString("name"),
                                desc = checkNullToString(res, "overview"),
                                releaseDate = checkNullToString(res, "first_air_date"),
                                genre = genString,
                                production = comString,
                                id = res.getInt("id")
                        )

                        detailShow.value = detail

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

    fun getTVDetail(): LiveData<DataModelDetail> {
        return detailShow
    }

    fun checkNullToString(res:JSONObject, key: String): String {
        return if (res.isNull(key)) "Kosong" else res.getString(key)
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