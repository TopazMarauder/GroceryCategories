package com.example.groceryappb30.app

class Endpoints {

    companion object {

        const val URL_CATEGORY = "category"
        const val URL_SUB_CATEGORY = "subcategory/"
        const val URL_PRODUCT = "products/"
        const val URL_ADDRESS = "address/"
        const val URL_USER = "users/"
        const val URL_ORDER = "orders/"

        fun getCategory(): String {
            return "${Config.BASE_URL + URL_CATEGORY}"

        }

        fun getSubCategory(catId: Int): String {
            return "${Config.BASE_URL + URL_SUB_CATEGORY + catId}"

        }

        fun getProduct(subId: Int): String {
            return "${Config.BASE_URL + URL_PRODUCT + "sub/" + subId}"


        }

        fun getProductDirect(id: String): String {
            return "${Config.BASE_URL + URL_PRODUCT + id}"
        }

        fun postRegister(): String {
            return "${Config.BASE_URL}auth/register"

        }

        fun getLogin(): String {
            return "${Config.BASE_URL}auth/login"
        }

        fun putUser(id: String): String{
            return "${Config.BASE_URL + URL_USER + id}"
        }

        fun getAddress(id: String): String{
            return "${Config.BASE_URL + URL_ADDRESS + id}"
        }

        fun postAddress(): String{
            return "${Config.BASE_URL}address"
        }

        fun deleteAddress(id: String): String{
            return "${Config.BASE_URL + URL_ADDRESS + id}"

        }

        fun postOrder(): String{
            return "${Config.BASE_URL}orders"
        }
        //http://grocery-second-app.herokuapp.com/api/orders/5e53e8cde4d77e0017d85a96
        fun getOrder(id: String): String{
            return "${Config.BASE_URL + URL_ORDER + id}"
        }

    }
}