package com.nicolassnchz.carttddu9.ui.cart

import com.nicolassnchz.carttddu9.domain.model.CartItem

sealed class CartUiState {
    object Loading : CartUiState()

    data class Success(
        val items: List<CartItem>,
        val total: Double
    ) : CartUiState()

    data class Error(
        val message: String
    ) : CartUiState()
}
