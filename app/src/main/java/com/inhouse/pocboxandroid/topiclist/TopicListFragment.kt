package com.inhouse.pocboxandroid.topiclist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.inhouse.pocboxandroid.BaseFragment
import com.inhouse.pocboxandroid.databinding.FragmentTopicListBinding
import com.inhouse.pocboxandroid.topiclist.adapter.TopicListAdapter
import com.inhouse.pocboxandroid.topiclist.model.Topic

class TopicListFragment : BaseFragment() {
    companion object {
        const val TAG = "TopicListFragment"
    }

    private val topicListViewModelFactory = TopicListViewModelFactory()
    private val topicListViewModel: TopicListViewModel by viewModels { topicListViewModelFactory }
    private lateinit var binding: FragmentTopicListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopicListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clickListener = object : TopicListAdapter.OnClickListener {
            override fun onClick(topic: Topic) {
                topicListViewModel.updateNavigationIdx(topic.idx)
            }
        }
        binding.rvTopicList.adapter = TopicListAdapter(getTopics(), clickListener)
        topicListViewModel.navigationIdx.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(TAG, "navigationIdx = $it")
                val action: NavDirections? =
                    when (it) {
                        0 -> TopicListFragmentDirections.actionTopicListFragmentToTopicImageSliderFragment()
                        1 -> TopicListFragmentDirections.actionTopicListFragmentToNotificationFragment()
                        2 -> TopicListFragmentDirections.actionTopicListFragmentToTestDownloadFragment()
                        else -> null
                    }
                action?.let { findNavController().navigate(action) }
                topicListViewModel.doneNavigation()
            }
        })
    }

    private fun getTopics(): List<Topic> {
        val topics: ArrayList<Topic> = ArrayList()
        topics.add(Topic("Image Slider", 0))
        topics.add(Topic("Notifications", 1))
        topics.add(Topic("Download Test", 2))
//        topics.add(Topic("Tone Generator", 2))
//        topics.add(Topic("Signature Pad", 3))
        return topics
    }
}