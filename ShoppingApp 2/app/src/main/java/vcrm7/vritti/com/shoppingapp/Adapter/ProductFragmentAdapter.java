package vcrm7.vritti.com.shoppingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import vcrm7.vritti.com.shoppingapp.Bean.ProductBean;
import vcrm7.vritti.com.shoppingapp.Database.DatabaseHandler;
import vcrm7.vritti.com.shoppingapp.R;

/**
 * Created by 300151 on 1/26/2017.
 */
public class ProductFragmentAdapter extends RecyclerView.Adapter<ProductFragmentAdapter.MyViewHolder> {
    List<ProductBean> ProductList;
    static Context context;
    DatabaseHandler db;

    public ProductFragmentAdapter(List<ProductBean> ProductList) {
        this.ProductList = ProductList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageview_product_logo;
        public TextView txt_ProductName, txt_Add_Cart, txt_ProductPrice, txt_VendorName, txt_VendorAddress;

        public MyViewHolder(View view) {
            super(view);
            imageview_product_logo = (ImageView) view.findViewById(R.id.imageview_product_logo);
            txt_ProductName = (TextView) view.findViewById(R.id.txt_ProductName);
            txt_Add_Cart = (TextView) view.findViewById(R.id.txt_Add_Cart);
            txt_ProductPrice = (TextView) view.findViewById(R.id.txt_ProductPrice);
            txt_VendorName = (TextView) view.findViewById(R.id.txt_VendorName);
            txt_VendorAddress = (TextView) view.findViewById(R.id.txt_VendorAddress);
            context = view.getContext();
            db = new DatabaseHandler(context);
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
    public ProductFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_activity, parent, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;


    }

    @Override
    public void onBindViewHolder(ProductFragmentAdapter.MyViewHolder holder, final int position) {
        holder.txt_ProductName.setText(ProductList.get(position).getProductname());
        holder.txt_ProductPrice.setText(ProductList.get(position).getPrice());
        holder.txt_VendorName.setText(ProductList.get(position).getVendorname());
        holder.txt_VendorAddress.setText(ProductList.get(position).getVendoraddress());
        Picasso.with(context)
                .load(ProductList.get(position).getProductImg())
                .placeholder(R.drawable.default_img).error(R.drawable.error)
                .resize(60, 60)
                .into(holder.imageview_product_logo);
        holder.txt_Add_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductBean bean = new ProductBean();
                bean.setVendorname(ProductList.get(position).getVendorname());
                bean.setProductname(ProductList.get(position).getProductname());
                bean.setPrice(ProductList.get(position).getPrice());
                bean.setVendoraddress(ProductList.get(position).getVendoraddress());
                bean.setProductImg(ProductList.get(position).getProductImg());
                bean.setPhoneNumber(ProductList.get(position).getPhoneNumber());
                db.AddCart(bean);
                Toast.makeText(context, "Item added to cart successfully", Toast.LENGTH_LONG).show();

                notifyDataSetChanged();
            }
        });
    }
}
