package com.inhouse.pocboxandroid.topiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopicListViewModel : ViewModel() {
    private val _navigationIdx = MutableLiveData<Int?>()
    val navigationIdx: LiveData<Int?>
        get() = _navigationIdx

    fun updateNavigationIdx(idx: Int) {
        _navigationIdx.value = idx
    }

    fun doneNavigation() {
        _navigationIdx.value = null
    }
}