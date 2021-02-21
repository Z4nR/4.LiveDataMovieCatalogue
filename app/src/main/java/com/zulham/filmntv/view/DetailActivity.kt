package com.zulham.filmntv.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zulham.filmntv.model.DataModel
import com.zulham.filmntv.R
import com.zulham.filmntv.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<DataModel>("film")

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        movie?.let { detailViewModel.setDetail(it) }

        detailViewModel.getDetail().observe(this, {
            movie?.let { showDetail(it) }
        })


    }

    private fun showDetail(mov: DataModel){
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