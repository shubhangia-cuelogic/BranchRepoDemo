package vcrm7.vritti.com.shoppingapp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vcrm7.vritti.com.shoppingapp.Bean.ProductBean;
import vcrm7.vritti.com.shoppingapp.Database.DatabaseHandler;
import vcrm7.vritti.com.shoppingapp.R;
import vcrm7.vritti.com.shoppingapp.Shopping.CartFragment;
import vcrm7.vritti.com.shoppingapp.Shopping.MainActivity;

public class CartFragmentAdapter extends RecyclerView.Adapter<CartFragmentAdapter.MyViewHolder> {
    List<ProductBean> ProductList;
    static Context context;
    DatabaseHandler db;
    SQLiteDatabase sql;
    Activity call;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    public CartFragmentAdapter(List<ProductBean> ProductList) {
        this.ProductList = ProductList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_CartProduct;
        public TextView txt_ProductName, txt_VendorName, txt_VendorAddress, txt_Price;
        Button btn_CallVendor, btn_RemoveCart;

        public MyViewHolder(View view) {
            super(view);
            img_CartProduct = (ImageView) view.findViewById(R.id.img_CartProduct);
            txt_ProductName = (TextView) view.findViewById(R.id.txt_ProductName);
            txt_VendorName = (TextView) view.findViewById(R.id.txt_VendorName);
            txt_VendorAddress = (TextView) view.findViewById(R.id.txt_VendorAddress);
            txt_Price = (TextView) view.findViewById(R.id.txt_Price);
            btn_CallVendor = (Button) view.findViewById(R.id.btn_CallVendor);
            btn_RemoveCart = (Button) view.findViewById(R.id.btn_RemoveCart);
            context = view.getContext();
            db = new DatabaseHandler(context);
            sql = db.getWritableDatabase();


        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    @Override
    public CartFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_activity, parent, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;


    }

    @Override
    public void onBindViewHolder(CartFragmentAdapter.MyViewHolder holder, final int position) {
        holder.txt_ProductName.setText(ProductList.get(position).getProductname());
        holder.txt_Price.setText(ProductList.get(position).getPrice());
        holder.txt_VendorName.setText(ProductList.get(position).getVendorname());
        holder.txt_VendorAddress.setText(ProductList.get(position).getVendoraddress());
        Picasso.with(context)
                .load(ProductList.get(position).getProductImg())
                .placeholder(R.drawable.default_img).error(R.drawable.error)
                .resize(60, 60)
                .into(holder.img_CartProduct);
        holder.btn_RemoveCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String que = "";
                String CartId = ProductList.get(position).getCartid();
                ProductList.remove(position);
                notifyDataSetChanged();
                sql.delete(db.TABLE_CART, "CartId=?",
                        new String[]{CartId});
            }
        });
        holder.btn_CallVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        //requestPermissions(MainActivity.class, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:123456789"));
                        context.startActivity(intent);
                    }



                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
