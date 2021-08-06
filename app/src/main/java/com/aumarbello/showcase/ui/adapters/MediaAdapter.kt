package com.aumarbello.showcase.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aumarbello.showcase.R
import com.aumarbello.showcase.data.models.CarMedia
import com.aumarbello.showcase.databinding.ItemCarMediaBinding
import com.aumarbello.showcase.utils.isVideoUrl
import com.aumarbello.showcase.utils.load

class MediaAdapter (
    private val mediaSelected: (CarMedia) -> Unit
): ListAdapter<CarMedia, MediaAdapter.MediaHolder>(DIFF) {
    private var selectedItemId = -1L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_car_media,
            parent,
            false
        )
        return MediaHolder(view)
    }

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class MediaHolder (view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemCarMediaBinding.bind(view)

        fun bindItem(item: CarMedia) {
            if (item.url.isVideoUrl()) {
                binding.image.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.ic_video,
                        itemView.context.theme
                    )
                )
            } else {
                binding.image.load(item.url)
            }

            itemView.isSelected = item.id == selectedItemId
            itemView.setOnClickListener { updateSelection(item) }
        }

        private fun updateSelection(item: CarMedia) {
            val currentList = currentList
            val currentSelection = currentList.indexOfFirst { it.id == selectedItemId }
            val newSelection = currentList.indexOf(item)

            selectedItemId = item.id
            mediaSelected(item)

            if (currentSelection != -1) notifyItemChanged(currentSelection)
            if (newSelection != -1) notifyItemChanged(newSelection)
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<CarMedia>() {
            override fun areItemsTheSame(oldItem: CarMedia, newItem: CarMedia): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CarMedia, newItem: CarMedia): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}