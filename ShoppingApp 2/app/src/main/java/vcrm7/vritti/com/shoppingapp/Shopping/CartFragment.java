package vcrm7.vritti.com.shoppingapp.Shopping;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vcrm7.vritti.com.shoppingapp.Adapter.CartFragmentAdapter;
import vcrm7.vritti.com.shoppingapp.Bean.ProductBean;
import vcrm7.vritti.com.shoppingapp.Database.DatabaseHandler;
import vcrm7.vritti.com.shoppingapp.R;

/**
 * Created by 300151 on 1/25/2017.
 */
public class CartFragment extends Fragment {
    private View rootView;
    public static RecyclerView listview_cart_item_list;
    public static List<ProductBean> ls_CartItemList;
    public static DatabaseHandler db;
    public static SQLiteDatabase sql;
    public static CartFragmentAdapter adapter;


    @Override
    public void onResume() {
        super.onResume();
        ls_CartItemList = getCartItemList();
        adapter = new CartFragmentAdapter(ls_CartItemList);
        listview_cart_item_list.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listview_cart_item_list.setLayoutManager(mLayoutManager);
        adapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_cart, container,
                false);
        InitView(rootView);
        ls_CartItemList = getCartItemList();
        listview_cart_item_list.setHasFixedSize(true);
        adapter = new CartFragmentAdapter(ls_CartItemList);
        listview_cart_item_list.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listview_cart_item_list.setLayoutManager(mLayoutManager);

        return rootView;
    }

    private void InitView(View rootView) {
        ls_CartItemList = new ArrayList<ProductBean>();
        db = new DatabaseHandler(getActivity());
        sql = db.getWritableDatabase();
        listview_cart_item_list = (RecyclerView) rootView.findViewById(R.id.listview_cart_item_list);
    }


    private List<ProductBean> getCartItemList() {
        List<ProductBean> CartList = new ArrayList<ProductBean>();
        String que = "SELECT * FROM " + db.TABLE_CART;
        Cursor cur = sql.rawQuery(que, null);
        if (cur.getCount() > 0) {
            cur.moveToFirst();
            do {
                ProductBean bean = new ProductBean();

                bean.setCartid(cur.getString(cur.getColumnIndex("CartId")));
                bean.setVendorname(cur.getString(cur.getColumnIndex("vendorname")));
                bean.setVendoraddress(cur.getString(cur.getColumnIndex("vendoraddress")));
                bean.setProductname(cur.getString(cur.getColumnIndex("productname")));
                bean.setPhoneNumber(cur.getString(cur.getColumnIndex("phoneNumber")));
                bean.setPrice(cur.getString(cur.getColumnIndex("price")));
                bean.setProductImg(cur.getString(cur.getColumnIndex("productImg")));
                CartList.add(bean);
            } while (cur.moveToNext());
        }
        return CartList;
    }
}
