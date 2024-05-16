import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Database(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(sqliteDatabase: SQLiteDatabase) {
        var query:String = "create table users(userName text,password text)"
        sqliteDatabase.execSQL(query)

        val query2:String = "create table cart(username text,product text,price float,otype text)"
        sqliteDatabase.execSQL(query2)

        val query3:String = "create table orderplace(username text,fullname text,address text,contactno text,pincode int,date text,time text,amount float,otype text)"
        sqliteDatabase.execSQL(query3)

    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Your implementation here
    }

    fun register(userName:String,pass:String){
        var cv:ContentValues = ContentValues()
        cv.put("userName",userName)
        cv.put("password",pass)
        val sql:SQLiteDatabase = writableDatabase
        sql.insert("users",null,cv)
        sql.close()
    }

    fun login(userName: String, pass: String): Int {
        var result:Int=0
        val sqldatabase = readableDatabase
        val str = arrayOf(userName, pass)
        val c = sqldatabase.rawQuery("select * from users where userName = ? and password = ?", str)
        if (c.moveToFirst()) {
            result=1
        }
        return result
    }

    fun addCart(userName: String, product: String,price:Float,otype:String) {
        val cv:ContentValues = ContentValues()
        cv.put("username",userName)
        cv.put("product",product)
        cv.put("price",price)
        cv.put("otype",otype)
        val sql:SQLiteDatabase = writableDatabase
        sql.insert("cart",null,cv)
        sql.close()
    }

    fun checkCart(userName: String, product: String): Int {
        var result:Int= 0

        val str = arrayOf(userName, product)
        val db = getReadableDatabase()
        val c = db.rawQuery("select * from cart where username = ? and product = ?", str)
        if (c.moveToFirst()) {
            result = 1
        }
        db.close()
        return result
    }

    fun removeCart(userName: String,otype:String) {
        val str = arrayOf(userName,otype)
        val sql:SQLiteDatabase = writableDatabase
        sql.delete("cart","username=? and otype=?",str)
        sql.close()
    }

    fun getCartData(userName: String,otype:String):ArrayList<String> {
        var arr:ArrayList<String> = ArrayList()
        val db:SQLiteDatabase = readableDatabase
        val str= arrayOf(userName,otype)
        val c:Cursor = db.rawQuery("select * from cart where username = ? and otype = ?",str)
        if(c.moveToFirst()){
            do {
                var product:String = c.getString(1)
                var price:String = c.getString(2)
                arr.add(product+"$"+price)
            }while (c.moveToNext())
        }
        db.close()
        return arr
    }

    fun addOrder(userName: String,otype:String,fullName:String,address:String,contactno:String,pincode:Int,date:String,time:String,price:Float){
        var arr:ArrayList<String> = ArrayList()
        val db:SQLiteDatabase = readableDatabase
        val cv:ContentValues = ContentValues()
        cv.put("username",userName)
        cv.put("fullname",fullName)
        cv.put("address",address)
        cv.put("contactno",contactno)
        cv.put("pincode",pincode)
        cv.put("date",date)
        cv.put("time",time)
        cv.put("amount",price)
        cv.put("otype",otype)
        db.insert("orderplace",null,cv)
        db.close()
    }

    fun getOrderData(userName: String):ArrayList<String>{
        var arr:ArrayList<String> = ArrayList()
        val db:SQLiteDatabase = readableDatabase
        var str = arrayOf(userName)
        val c:Cursor = db.rawQuery("select * from orderplace where username = ? ",str)
        if(c.moveToFirst()){
            do {
                arr.add(c.getString(1)+"$"+c.getString(2)+"$"+c.getString(3)+"$"+c.getString(4)+"$"+c.getString(5)+"$"+c.getString(6)+"$"+c.getString(7)+"$"+c.getString(8))
            }while (c.moveToNext())
        }
        db.close()
        return arr
    }

    fun checkAppointmentExists(userName: String,fullName: String,contact: String,address: String,date: String,time: String,):Int{
       var arr = 0
        var str:Array<String> = arrayOf(userName,fullName,address,contact,date,time)
        val db:SQLiteDatabase = readableDatabase
        val c:Cursor = db.rawQuery("select * from orderplace where username = ? and fullname = ? and address = ? and contactno = ? and date = ? and time = ?",str)
        if(c.moveToFirst()){
            arr = 1
        }
        db.close()
        return arr
    }

}
