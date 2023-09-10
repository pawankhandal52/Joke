package com.unlimit.jokes.presentation.jokesscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unlimit.jokes.databinding.ItemJokesBinding

class JokeListAdapter(private val lifecycleOwner: LifecycleOwner):
    ListAdapter<JokeItem,JokeVH>(JokeItemDiffUtils) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJokesBinding.inflate(inflater, parent, false)
        return JokeVH(
            binding,
            lifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: JokeVH, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

class JokeVH(private val binding:ItemJokesBinding,
             private val lifecycleOwner: LifecycleOwner
): RecyclerView.ViewHolder(binding.root){
    fun bind(jokeItem: JokeItem){
        binding.executeAfter {
            lifecycleOwner = this@JokeVH.lifecycleOwner
            mtvJoke.text = jokeItem.joke
            tvTime.text = jokeItem.time
        }
    }
}

object JokeItemDiffUtils: DiffUtil.ItemCallback<JokeItem>(){
    override fun areItemsTheSame(oldItem: JokeItem, newItem: JokeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: JokeItem, newItem: JokeItem): Boolean {
        return oldItem.joke == newItem.joke &&
                oldItem.time == newItem.time
    }

}

data class JokeItem(
    var joke:String,
    var time:String
)

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}