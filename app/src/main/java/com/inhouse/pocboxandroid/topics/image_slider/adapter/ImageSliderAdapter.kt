package com.inhouse.pocboxandroid.topics.image_slider.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.inhouse.pocboxandroid.databinding.ImageSliderLayoutItemBinding
import com.inhouse.pocboxandroid.topics.image_slider.model.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter

class ImageSliderAdapter : SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder>() {
    private var sliderItems = ArrayList<SliderItem>()
    fun addItem(sliderItem: SliderItem) {
        sliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int) {
        sliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun renewItems(sliderItems: ArrayList<SliderItem>) {
        this.sliderItems = sliderItems
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return sliderItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ImageSliderViewHolder {
        val binding = ImageSliderLayoutItemBinding.inflate(LayoutInflater.from(parent!!.context))
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ImageSliderViewHolder?, position: Int) {
//        val (description, imageUrl) = sliderItems[position] // tuple like in Rust language
        viewHolder!!.bind(sliderItems[position])
    }

    class ImageSliderViewHolder(private val itemBinding: ImageSliderLayoutItemBinding) :
        SliderViewAdapter.ViewHolder(itemBinding.root) {
        fun bind(sliderItem: SliderItem) {
            itemBinding.tvAutoImageSlider.text = sliderItem.description
            itemBinding.tvAutoImageSlider.textSize = 16f
            itemBinding.tvAutoImageSlider.setTextColor(Color.WHITE)
            Glide.with(itemView)
                .load(sliderItem.imageUrl)
                .fitCenter()
                .into(itemBinding.ivAutoImageSlider)
        }
    }
}