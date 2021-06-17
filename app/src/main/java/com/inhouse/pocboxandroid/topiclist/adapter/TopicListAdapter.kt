package com.inhouse.pocboxandroid.topiclist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.pocboxandroid.databinding.TopicListItemBinding
import com.inhouse.pocboxandroid.topiclist.model.Topic

class TopicListAdapter(
    private val listTopics: List<Topic>,
    private val clickListener: OnClickListener
) :
    RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {

    class TopicViewHolder(private val binding: TopicListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic, clickListener: TopicListAdapter.OnClickListener) {
            binding.topic = topic
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder(
            TopicListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val strTopic = listTopics[position]
        holder.bind(strTopic, clickListener)
    }

    override fun getItemCount(): Int {
        return listTopics.size
    }

    interface OnClickListener {
        fun onClick(topic: Topic)
    }
}