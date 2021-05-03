package com.example.groceryappb30.models


data class OrderResponse(
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: ArrayList<OrderProduct>,
    val shippingAddress: ShippingAddress,
    val user: OrderUser,
    val userId: String
)

data class OrderSummary(
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment(
    val paymentMode: String,
    val paymentStatus: String
)

data class OrderProduct(
    val image: String,
    val mrp: Int,
    val price: Int,
    val productName: String,
    val quantity: Int
)

data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)

data class OrderUser(
    val email: String,
    val mobile: String
)