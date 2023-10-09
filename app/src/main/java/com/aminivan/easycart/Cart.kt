package com.aminivan.easycart


data class Cart(
    val id : Int,
    val idbarang: String,
    val name: String,
    var qty: Int,
    val price: Int,
    var subtotal: Int,
    val image: String,
)
