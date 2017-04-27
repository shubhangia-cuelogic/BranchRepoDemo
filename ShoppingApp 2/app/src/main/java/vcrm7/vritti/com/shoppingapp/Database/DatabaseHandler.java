package vcrm7.vritti.com.shoppingapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vcrm7.vritti.com.shoppingapp.Bean.ProductBean;

/**
 * Created by 300151 on 1/26/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "ShoppingDatabase";
    public static final String TABLE_CART = "CartTable";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_CART = "CREATE TABLE "
                + TABLE_CART
                + "(CartId INTEGER PRIMARY KEY AUTOINCREMENT,productname TEXT,price TEXT,vendorname TEXT,vendoraddress TEXT,productImg TEXT,phoneNumber TEXT)";
        db.execSQL(CREATE_TABLE_CART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_CART);

    }

    public void AddCart(ProductBean bean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("productname", bean.getProductname());
        cv.put("price", bean.getPrice());
        cv.put("vendorname", bean.getVendorname());
        cv.put("vendoraddress", bean.getVendoraddress());
        cv.put("productImg", bean.getProductImg());
        cv.put("phoneNumber", bean.getPhoneNumber());
        long a = db.insert(TABLE_CART, null, cv);
    }

}
