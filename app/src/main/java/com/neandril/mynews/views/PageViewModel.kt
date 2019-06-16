package com.neandril.mynews.views

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    // val text: LiveData<String> = Transformations.map(_index) {
    //     "Hello world from section: $it"
    // }

    fun setIndex(index: Int) {
        _index.value = index
    }
}