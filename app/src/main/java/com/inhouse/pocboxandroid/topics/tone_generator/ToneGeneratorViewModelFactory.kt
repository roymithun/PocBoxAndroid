package com.inhouse.pocboxandroid.topics.tone_generator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToneGeneratorViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToneGeneratorViewModel::class.java)) {
            return ToneGeneratorViewModel() as T
        }
        throw IllegalArgumentException("ViewModel unknown")
    }
}