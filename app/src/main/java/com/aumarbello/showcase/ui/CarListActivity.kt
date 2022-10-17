package com.aumarbello.showcase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.aumarbello.showcase.BuildConfig
import com.aumarbello.showcase.databinding.ActivityCarListBinding
import com.aumarbello.showcase.ui.adapters.BrandsAdapter
import com.aumarbello.showcase.ui.adapters.CarItemListener
import com.aumarbello.showcase.ui.adapters.CarsAdapter
import com.aumarbello.showcase.utils.closeKeyboard
import com.aumarbello.showcase.utils.showSnackBar
import com.aumarbello.showcase.viewmodels.CarsListVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarListActivity : AppCompatActivity(), CarItemListener {
    private lateinit var binding: ActivityCarListBinding
    private val viewModel by viewModels<CarsListVM>()

    private val brandsAdapter = BrandsAdapter(this::onBrandSelected)
    private val carsAdapter = CarsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.brandsList.adapter = brandsAdapter
        binding.carsList.adapter = carsAdapter

        binding.explore.text = BuildConfig.SAMPLE

        setObservers()
        setListeners()
    }

    override fun addToCart() {
        viewModel.addToCart()
    }

    override fun openDetails(id: String) {
        val intent = CarDetailsActivity.getStartIntent(this, id)
        startActivity(intent)
    }

    private fun onBrandSelected(brand: String) {
        val currentQuery = binding.query.text.toString()
        if (currentQuery != brand) {
            binding.query.setText(brand)
            binding.query.setSelection(brand.length)

            setCarsData(brand)
        }
    }

    private fun setListeners() {
        binding.filter.setOnClickListener {
            val query = binding.query.text.toString()
            if (query.isNotEmpty()) {
                setCarsData(query)
            }
            closeKeyboard()
        }

        brandsAdapter.addLoadStateListener { loadState ->
            handleError(loadState)
        }

        carsAdapter.addLoadStateListener { loadState ->
            binding.carsList.isVisible = loadState.refresh is
                    LoadState.NotLoading
            binding.carsLoader.isVisible = loadState.refresh is
                LoadState.Loading

            handleError(loadState)
        }
    }

    private fun setObservers() {
        setCarsData()
        lifecycleScope.launch {
            viewModel.getCarBrands().collectLatest {
                brandsAdapter.submitData(it)
            }
        }

        viewModel.getCartInfo().observe(this) {
            binding.cart.count.text = it.toString()
        }
    }

    private fun setCarsData(query: String = "") {
        lifecycleScope.launch {
            viewModel.searchCars(query).collectLatest {
                carsAdapter.submitData(it)
            }
        }
    }

    private fun handleError(state: CombinedLoadStates) {
        val errorState = state.source.append as? LoadState.Error
            ?: state.source.prepend as? LoadState.Error
            ?: state.append as? LoadState.Error
            ?: state.prepend as? LoadState.Error

        errorState?.let { showSnackBar(it.error.message ?: "An error occurred") }
    }
}