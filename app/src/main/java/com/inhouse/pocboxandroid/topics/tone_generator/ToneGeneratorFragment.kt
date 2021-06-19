package com.inhouse.pocboxandroid.topics.tone_generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.inhouse.pocboxandroid.BaseFragment
import com.inhouse.pocboxandroid.databinding.FragmentToneGeneratorBinding

class ToneGeneratorFragment : BaseFragment() {
    private val toneGeneratorViewModelFactory = ToneGeneratorViewModelFactory()
    private val toneGeneratorViewModel: ToneGeneratorViewModel by viewModels { toneGeneratorViewModelFactory }
    private lateinit var binding: FragmentToneGeneratorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToneGeneratorBinding.inflate(layoutInflater)
        return binding.root
    }
}