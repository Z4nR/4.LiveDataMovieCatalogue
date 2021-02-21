package com.zulham.filmntv.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.zulham.filmntv.R
import com.zulham.filmntv.adapter.FilmAdapter.ViewHolder
import com.zulham.filmntv.utils.dummy_data.DataFilm
import com.zulham.filmntv.utils.dummy_data.DataTV
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test

class HomeTest{

    @get : Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ITEM_IN_TEST = 4
    val MOVIE_IN_TEST = DataFilm.list
    val MOVIE_DETAIL = DataFilm.list.get(4)
    val TV_DETAIL = DataTV.list.get(4)
    val TV_IN_TEST = DataTV.list

    @Test
    fun test_isListFragment_onLaunch() {
        onView(withId(R.id.tab_layout_main)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager_main)).perform(ViewPagerActions.scrollRight(true))
    }

    @Test
    fun loadFilm(){
        onView(withId(R.id.tab_layout_main)).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(), withId(R.id.recyclerV)))
                .perform(scrollToPosition<ViewHolder>(MOVIE_IN_TEST.size))
    }

    @Test
    fun loadTV(){
        onView(withId(R.id.tab_layout_main)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager_main)).perform(ViewPagerActions.scrollRight(true))
        onView(allOf(isDisplayed(), withId(R.id.recyclerV)))
                .perform(scrollToPosition<ViewHolder>(TV_IN_TEST.size))
    }

    @Test
    fun test_selectItem_DetailVisibleFilm() {
        onView(allOf(isDisplayed(), withId(R.id.recyclerV)))
                .perform(actionOnItemAtPosition<ViewHolder>(LIST_ITEM_IN_TEST, click()))
        onView(withId(R.id.title_detail))
                .check(matches(withText(MOVIE_DETAIL.title)))
        onView(withId(R.id.genre_detail))
                .check(matches(withText(MOVIE_DETAIL.genre)))
        onView(withId(R.id.release_detail))
                .check(matches(withText(MOVIE_DETAIL.releaseDate)))
        onView(withId(R.id.desc_detail))
                .check(matches(withText(MOVIE_DETAIL.desc)))
        onView(withId(R.id.ph_detail))
                .check(matches(withText(MOVIE_DETAIL.production)))

    }

    @Test
    fun test_selectItem_DetailVisibleTV() {
        onView(withId(R.id.view_pager_main)).perform(ViewPagerActions.scrollRight(true))
        onView(allOf(isDisplayed(), withId(R.id.recyclerV)))
                .perform(actionOnItemAtPosition<ViewHolder>(LIST_ITEM_IN_TEST, click()))
        onView(withId(R.id.title_detail))
                .check(matches(withText(TV_DETAIL.title)))
        onView(withId(R.id.genre_detail))
                .check(matches(withText(TV_DETAIL.genre)))
        onView(withId(R.id.release_detail))
                .check(matches(withText(TV_DETAIL.releaseDate)))
        onView(withId(R.id.desc_detail))
                .check(matches(withText(TV_DETAIL.desc)))
        onView(withId(R.id.ph_detail))
                .check(matches(withText(TV_DETAIL.production)))

    }
}