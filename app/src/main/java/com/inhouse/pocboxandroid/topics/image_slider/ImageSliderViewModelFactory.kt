package com.inhouse.pocboxandroid.topics.image_slider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageSliderViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageSliderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageSliderViewModel() as T
        }
        throw IllegalArgumentException("ViewModel not known")
    }

}