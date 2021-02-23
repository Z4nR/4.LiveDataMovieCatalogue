package com.zulham.filmntv.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.filmntv.R
import com.zulham.filmntv.model.DataModelDetail
import com.zulham.filmntv.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.log

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "Detail"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getStringExtra("film")

        Log.d(movie, "ANJAY")

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        movie?.let { detailViewModel.setDetail(it) }

        detailViewModel.getIsError().observe(this, {
            when (it) {
                true -> showErrorMessage()
                else -> showDetail()
            }
        })


    }

    private fun showErrorMessage() {
        detailViewModel.getErrorMessage().observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showDetail(){

        detailViewModel.getDetail().observe(this, {
            detail(it)
        })

    }

    private fun detail(mov: DataModelDetail){
        Glide.with(this)
            .load(mov.img)
            .apply(RequestOptions().override(1000, 1000))
            .into(img_poster_detail)
        title_detail.text = mov.title
        genre_detail.text = mov.genre
        release_detail.text = mov.releaseDate
        ph_detail.text = mov.production
        desc_detail.text = mov.desc
    }
}