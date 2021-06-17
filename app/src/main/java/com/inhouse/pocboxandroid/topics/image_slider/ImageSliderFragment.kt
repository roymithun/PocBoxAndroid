package com.inhouse.pocboxandroid.topics.image_slider

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inhouse.pocboxandroid.BaseFragment
import com.inhouse.pocboxandroid.databinding.FragmentImageSliderBinding
import com.inhouse.pocboxandroid.topics.image_slider.adapter.ImageSliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class ImageSliderFragment : BaseFragment() {
    companion object {
        const val TAG = "ImageSliderFragment"
    }

    private val imageSliderViewModelFactory = ImageSliderViewModelFactory()
    private val imageSliderViewModel: ImageSliderViewModel by viewModels { imageSliderViewModelFactory }

    private lateinit var binding: FragmentImageSliderBinding
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageSliderBinding.inflate(layoutInflater)
        binding.imageSliderViewModel = imageSliderViewModel
        binding.lifecycleOwner = this

        configureSliderView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSliderViewModel.renewedSliderItems.observe(viewLifecycleOwner, {
            it?.let {
                imageSliderAdapter.renewItems(it)
                imageSliderViewModel.resetRenew()
            }
        })
        imageSliderViewModel.removeLastItem.observe(viewLifecycleOwner, {
            it?.let {
                if (it && imageSliderAdapter.count - 1 >= 0) {
                    imageSliderAdapter.removeItemAt(imageSliderAdapter.count - 1)
                    imageSliderViewModel.resetRemoveLast()
                }
            }
        })
        imageSliderViewModel.addNewSliderItem.observe(viewLifecycleOwner, {
            it?.let {
                imageSliderAdapter.addItem(it)
                imageSliderViewModel.resetNewItem()
            }
        })
    }

    private fun configureSliderView() {
        imageSliderAdapter = ImageSliderAdapter()
        val sliderView = binding.sliderViewImage
        sliderView.setSliderAdapter(imageSliderAdapter)

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        binding.sliderViewImage.setOnIndicatorClickListener {
            Log.d(TAG, "onIndicatorClicked: ${sliderView.currentPagePosition} :: $it")
            sliderView.currentPagePosition = it
        }
    }
}