package com.aumarbello.showcase.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aumarbello.showcase.R
import com.aumarbello.showcase.data.models.Brand
import com.aumarbello.showcase.databinding.ItemBrandBinding
import com.aumarbello.showcase.utils.load

class BrandsAdapter (
    private val onBrandSelected: (String) -> Unit
) : PagingDataAdapter<Brand, BrandsAdapter.BrandHolder>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_brand, parent, false
        )
        return BrandHolder(view)
    }

    override fun onBindViewHolder(holder: BrandHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BrandHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: ItemBrandBinding = ItemBrandBinding.bind(view)

        fun bind(item: Brand) {
            binding.brandIcon.load(item.imageUrl)
            binding.brandName.text = item.name

            itemView.setOnClickListener {
                onBrandSelected(item.name)
            }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Brand>() {
            override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}