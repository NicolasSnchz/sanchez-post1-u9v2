package com.nicolassnchz.carttddu9.domain.repository

import com.nicolassnchz.carttddu9.domain.model.CartItem

interface CartRepository {
    suspend fun getItems(): List<CartItem>
    suspend fun addItem(item: CartItem): Boolean
    suspend fun removeItem(id: String): Boolean
}
