package me.sf.movielibrary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {
    private val _index = MutableLiveData(0)
    val index: LiveData<Int>
        get() = _index

    fun changeValue(value: Int) {
        _index.value = value
    }
}
