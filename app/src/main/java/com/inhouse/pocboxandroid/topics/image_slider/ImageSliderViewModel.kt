package com.inhouse.pocboxandroid.topics.image_slider

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inhouse.pocboxandroid.topics.image_slider.model.SliderItem

class ImageSliderViewModel : ViewModel() {
    private val _renewedSliderItems = MutableLiveData<ArrayList<SliderItem>?>()
    val renewedSliderItems: LiveData<ArrayList<SliderItem>?>
        get() = _renewedSliderItems

    private val _removeLastItem = MutableLiveData<Boolean?>()
    val removeLastItem: LiveData<Boolean?>
        get() = _removeLastItem

    private val _newSliderItem = MutableLiveData<SliderItem?>()
    val addNewSliderItem: LiveData<SliderItem?>
        get() = _newSliderItem

    fun onRenewItems() {
        val sliderItemList = ArrayList<SliderItem>()
        for (i in 1..5) {
            val imageUrl = "https://picsum.photos/id/${i * getRandomInt()}/1260/750"
            Log.d(ImageSliderFragment.TAG, "renewItems -> imageUrl = $imageUrl")
            sliderItemList.add(SliderItem("Slider Item $i", imageUrl))
        }
        _renewedSliderItems.value = sliderItemList
    }

    fun resetRenew() {
        _renewedSliderItems.value = null
    }

    fun onRemoveLastItem() {
        _removeLastItem.value = true
    }

    fun resetRemoveLast() {
        _removeLastItem.value = null
    }

    fun onAddNewItem() {
        val sliderItem = SliderItem(
            "Slider Item Added Manually",
            "https://picsum.photos/id/${getRandomInt()}/1260/750"
        )
        _newSliderItem.value = sliderItem
    }

    fun resetNewItem() {
        _newSliderItem.value = null
    }

    private fun getRandomInt(): Int {
        return (30..50).shuffled().first()
    }
}