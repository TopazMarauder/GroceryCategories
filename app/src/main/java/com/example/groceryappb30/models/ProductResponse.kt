package com.example.groceryappb30.models

import java.io.Serializable

data class ProductResponse(
    val count: Int,
    val `data`: ArrayList<Product>,
    val error: Boolean
)

data class Product(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Int,
    val position: Int,
    val price: Int,
    val productName: String,
    var quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
): Serializable {
    companion object {

        var KEY_PRODUCT = "main prod"

    }
}