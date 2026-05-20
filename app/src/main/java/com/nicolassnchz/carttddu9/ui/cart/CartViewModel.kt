package com.nicolassnchz.carttddu9.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolassnchz.carttddu9.domain.analytics.AnalyticsService
import com.nicolassnchz.carttddu9.domain.repository.CartRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository,
    private val analytics: AnalyticsService
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun loadCart() {
        viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
            _uiState.value = CartUiState.Loading

            try {
                val items = repository.getItems()
                val total = items.sumOf { item -> item.price * item.qty }
                _uiState.value = CartUiState.Success(items, total)
                analytics.track("cart_loaded")
            } catch (e: Exception) {
                _uiState.value = CartUiState.Error(e.message ?: "Error")
                analytics.track("cart_error")
            }
        }
    }
}
