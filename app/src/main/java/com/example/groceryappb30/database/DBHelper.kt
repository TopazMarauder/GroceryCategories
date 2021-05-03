package com.example.groceryappb30.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.groceryappb30.models.OrderProduct
import com.example.groceryappb30.models.Product

class DBHelper(var mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {


    var sqldatabase: SQLiteDatabase = writableDatabase

    companion object {
        const val DATABASE_NAME = "mydb"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "product"
        const val COLUMN_PRODUCT_NAME = "product_name"  //string
        const val COLUMN_ID = "id"  //string
        const val COLUMN_MRP = "mrp"  //int
        const val COLUMN_PRICE = "price"  //int
        const val COLUMN_UNIT = "unit"  //string
        const val COLUMN_IMAGE = "image"  //string
        const val COLUMN_QUANTITY = "quantity"  //Int

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "create table $TABLE_NAME ($COLUMN_PRODUCT_NAME varchar(50), $COLUMN_ID varchar(50), $COLUMN_MRP integer,  $COLUMN_PRICE integer, $COLUMN_UNIT varchar(50), $COLUMN_IMAGE varchar(200), $COLUMN_QUANTITY integer)"
        db?.execSQL(createTable)
        Log.d("abc", "onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("abc", "onUpgrade")
        val dropTable = "drop table product"
        db?.execSQL(dropTable)
        onCreate(db)

    }


    // insert into product (id, name, email) values (1, 'mark', 'm@gmail.com')
    fun addProduct(product: Product) {
        var contentValues = ContentValues()
        contentValues.put(COLUMN_PRODUCT_NAME, product.productName)
        contentValues.put(COLUMN_ID, product._id)
        contentValues.put(COLUMN_MRP, product.mrp)
        contentValues.put(COLUMN_PRICE, product.price)
        contentValues.put(COLUMN_UNIT, product.unit)
        contentValues.put(COLUMN_IMAGE, product.image)
        contentValues.put(COLUMN_QUANTITY, 1)

        sqldatabase.insert(TABLE_NAME, null, contentValues)
        Log.d("abc", "add_root_ide_package_.com.example.groceryappb30.models.Product")
    }


    // update product set name="" email= "" where id= 1
    fun incrementProduct(product: Product): Int {


        var quantity = product.quantity + 1

        var contentValues = ContentValues()
        contentValues.put(COLUMN_QUANTITY, quantity)
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)


        return sqldatabase.update(TABLE_NAME, contentValues, whereClause, whereArgs)


    }

    fun decrementProduct(product: Product): Pair<Int, Boolean> {

        var quantity = product.quantity - 1


        if (quantity <= 0) {
            return Pair(deleteProduct(product._id), false)
        } else {
            var contentValues = ContentValues()
            contentValues.put(COLUMN_QUANTITY, quantity)
            var whereClause = "$COLUMN_ID = ?"
            var whereArgs = arrayOf(product._id)


            return Pair(sqldatabase.update(TABLE_NAME, contentValues, whereClause, whereArgs), true)
        }


    }

    // delete from product where id = 1
    fun deleteProduct(id: String): Int {
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(id)



        return sqldatabase.delete(TABLE_NAME, whereClause, whereArgs)

    }

    fun deleteAll(): Int{
        return sqldatabase.delete(TABLE_NAME, null, null)
    }

    // select * from product
    // select id, name, email from products
    fun getAllProducts(): ArrayList<Product> {
        var list: ArrayList<Product> = ArrayList()

        var columns = arrayOf(
            COLUMN_PRODUCT_NAME,
            COLUMN_ID,
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_UNIT,
            COLUMN_IMAGE,
            COLUMN_QUANTITY
        )
        var cursor = sqldatabase.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var mrp = cursor.getInt(cursor.getColumnIndex(COLUMN_MRP))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var unit = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))

                var product = Product(
                    1,
                    id,
                    1,
                    "",
                    "",
                    image,
                    mrp,
                    1,
                    price,
                    productName,
                    quantity,
                    true,
                    1,
                    unit
                )
                list.add(product)


            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }

    // select * from product where id = 1
    fun getQuantityById(id: String): Int {
        var query = "select * from $TABLE_NAME where $COLUMN_ID = ?"
        var cursor = sqldatabase.rawQuery(query, arrayOf(id))
        var quantity = 0

        if (cursor != null) {

            quantity = cursor.getInt(6)

        }
        return quantity
    }


    fun getPrices(): Triple<Pair<Int, Int>, String, Int>{


        var mrpSum = 0
        var discount = 0
        var priceSum = 0
        var deliveryFee = "10"
        var total = 0


        var columns = arrayOf(
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_QUANTITY
        )
        var cursor = sqldatabase.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var mrp = cursor.getInt(cursor.getColumnIndex(COLUMN_MRP))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))

                mrpSum += mrp*quantity
                priceSum += price*quantity



            } while (cursor.moveToNext())
        }

        cursor.close()


        discount = mrpSum- priceSum
        if(priceSum>= 300){
            deliveryFee = "free"
            total = priceSum
        }
        else{
            total = priceSum+10
        }


        return Triple(Pair(mrpSum, discount), deliveryFee, total)
    }




    fun checkSize(): Boolean {
        var cursor = sqldatabase.rawQuery("select * from $TABLE_NAME", null)

        return cursor.moveToFirst()
    }



    fun getOrderProducts(): ArrayList<OrderProduct> {
        var list: ArrayList<OrderProduct> = ArrayList()

        var columns = arrayOf(
            COLUMN_PRODUCT_NAME,
            COLUMN_ID,
            COLUMN_MRP,
            COLUMN_PRICE,
            COLUMN_UNIT,
            COLUMN_IMAGE,
            COLUMN_QUANTITY
        )
        var cursor = sqldatabase.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                var mrp = cursor.getInt(cursor.getColumnIndex(COLUMN_MRP))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))

                var product = OrderProduct(
                    image,
                    mrp,
                    price,
                    productName,
                    quantity
                )
                list.add(product)


            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }


}