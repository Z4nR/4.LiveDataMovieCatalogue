package com.zulham.filmntv.viewmodel

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