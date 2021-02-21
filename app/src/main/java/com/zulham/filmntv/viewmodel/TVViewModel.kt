package com.zulham.filmntv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zulham.filmntv.model.DataModel
import com.zulham.filmntv.utils.dummy_data.DataTV

class TVViewModel: ViewModel() {
    private val listTV = MutableLiveData<ArrayList<DataModel>>()

    fun setData(){
        listTV.postValue(DataTV.list)
    }

    fun getData(): LiveData<ArrayList<DataModel>> {
        return listTV
    }
}