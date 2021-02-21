package com.zulham.filmntv.viewmodel

import com.zulham.filmntv.R
import com.zulham.filmntv.model.DataModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class TVViewModelTest {

    private lateinit var tvViewModel: TVViewModel

    @Before
    fun setUp(){
        tvViewModel = TVViewModel()
    }

    @Test
    fun getDetail() {
        val tvDetail = tvViewModel.getData()
        assertNotNull(tvDetail)
    }
}