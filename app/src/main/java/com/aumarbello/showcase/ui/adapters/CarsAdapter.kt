package com.aumarbello.showcase.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aumarbello.showcase.R
import com.aumarbello.showcase.data.models.Car
import com.aumarbello.showcase.databinding.ItemCarBinding
import com.aumarbello.showcase.utils.formatPrice
import com.aumarbello.showcase.utils.formatRating
import com.aumarbello.showcase.utils.load
import com.aumarbello.showcase.utils.parseBrand

class CarsAdapter (
    private val listener: CarItemListener
): PagingDataAdapter<Car, CarsAdapter.CarsHolder>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_car, parent, false
        )

        return CarsHolder(view)
    }

    override fun onBindViewHolder(holder: CarsHolder, position: Int) {
        getItem(position)?.let { holder.bindItem(it) }
    }

    inner class CarsHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: ItemCarBinding = ItemCarBinding.bind(view)

        fun bindItem(item: Car) {
            binding.carImage.load(item.imageUrl)
            binding.title.text = item.title
            binding.price.text = item.marketPrice.formatPrice()

            binding.rating.isVisible = item.rating != null
            binding.rating.text = item.rating.formatRating()

            val brand = item.title.parseBrand()
            binding.brandBanner.text = brand
            binding.carBrand.text = brand

            binding.addToCart.setOnClickListener { listener.addToCart() }
            itemView.setOnClickListener { listener.openDetails(item.id) }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}