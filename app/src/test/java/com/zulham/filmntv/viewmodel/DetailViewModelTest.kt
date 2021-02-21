package com.zulham.filmntv.viewmodel

import com.zulham.filmntv.R
import com.zulham.filmntv.utils.dummy_data.DataFilm
import com.zulham.filmntv.model.DataModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock

class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private val dummyData = DataModel(title = "Jumanji",
            desc = "Set in 1869, two children receive a mysterious game after their father goes missing in the jungles of Africa. They unravel both the secret of their father’s disappearance and the origin of Jumanji. See how it all began.",
            production = "Anchors",
            releaseDate = "January, 20 2021",
            img = R.drawable.jumanji,
            genre = "Adventure, Comedy")

    @Before
    fun setUp(){
        detailViewModel = DetailViewModel()
    }

    @Test
    fun getDetail() {
        val filmDetail = DataFilm.list[0]
        val movieDetail = detailViewModel.getDetail()
        assertNotNull(movieDetail)
        assertEquals(filmDetail, dummyData)
    }
}