package com.inhouse.pocboxandroid.topiclist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TopicListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopicListViewModel() as T
        }
        throw IllegalArgumentException("ViewModel unknown")
    }
}