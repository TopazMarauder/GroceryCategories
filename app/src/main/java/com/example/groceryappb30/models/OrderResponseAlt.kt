package com.example.groceryappb30.models

import java.io.Serializable

data class OrderResponseAlt(
    val count: Int,
    val `data`: ArrayList<Orders>,
    val error: Boolean
)

data class Orders(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderSummary: OrderSummaryAlt,
    val payment: PaymentAlt,
    val products: ArrayList<ProductAlt>,
    val shippingAddress: ShippingAddressAlt,
    val user: UserAlt,
    val userId: String
) : Serializable{
    companion object{
        val KEY_ORDERS = "orders"
        val KEY_DATE = "date"
        val KEY_SUMMARY = "summary"
        val KEY_PRODUCTS = "products"
        val KEY_ADDRESS = "shipping"
        val KEY_NAMES = "names"
        val KEY_IMAGES = "images"
    }

}

data class OrderSummaryAlt(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
): Serializable

data class PaymentAlt(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
): Serializable

data class ProductAlt(
    val _id: String,
    val image: String,
    val mrp: Int,
    val price: Int,
    val productName: String,
    val quantity: Int
): Serializable

data class ShippingAddressAlt(
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
): Serializable

data class UserAlt(
    val _id: String,
    val email: String,
    val mobile: String
): Serializable