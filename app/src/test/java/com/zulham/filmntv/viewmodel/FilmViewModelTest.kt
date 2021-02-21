package com.zulham.filmntv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zulham.filmntv.model.DataFilm
import com.zulham.filmntv.model.DataModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class FilmViewModelTest {

    private lateinit var filmViewModel: FilmViewModel

    @Before
    fun setUp(){
        filmViewModel = FilmViewModel()
    }

    @Test
    fun getDetail() {
        val filmDetail = filmViewModel.getData()
        assertNotNull(filmDetail)
    }
}