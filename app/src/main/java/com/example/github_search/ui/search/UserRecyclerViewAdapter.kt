package com.example.github_search.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github_search.app.data.model.UserDto
import com.example.github_search.databinding.ItemUserBinding

class UserRecyclerViewAdapter(
    private val onItemClick: (UserDto) -> Unit
) : ListAdapter<UserDto, UserRecyclerViewAdapter.ViewHolder>(
    DiffUtilCallback()
) {
    private class DiffUtilCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<UserDto>() {
        override fun areItemsTheSame(oldItem: UserDto, newItem: UserDto): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UserDto, newItem: UserDto): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.root.setOnClickListener { onItemClick(item) }
        holder.bind(item, position)
    }

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserDto, position: Int) {
            binding.itemNumber.text = (position + 1).toString()
            binding.tvContent.text = item.login
        }
    }
}
