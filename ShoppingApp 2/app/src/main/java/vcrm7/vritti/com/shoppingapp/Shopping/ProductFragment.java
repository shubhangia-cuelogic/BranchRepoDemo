package vcrm7.vritti.com.shoppingapp.Shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vcrm7.vritti.com.shoppingapp.Adapter.ProductFragmentAdapter;
import vcrm7.vritti.com.shoppingapp.Bean.ProductBean;
import vcrm7.vritti.com.shoppingapp.R;

/**
 * Created by 300151 on 1/25/2017.
 */
public class ProductFragment extends Fragment {
    private View rootView;
    RecyclerView listview_item_list;
    ProgressDialog progressDialog;
    List<ProductBean> ls_ProductList;
    public static ProductFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product, container,
                false);
        InitView(rootView);
        ls_ProductList = new ArrayList<ProductBean>();
        if (isnet()) {
            new DownloadPoductListJSON().execute();
        } else {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    private void InitView(View rootView) {
        listview_item_list = (RecyclerView) rootView.findViewById(R.id.listview_item_list);
    }

    private boolean isnet() {
        // TODO Auto-generated method stub
        Context context = getContext();
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    class DownloadPoductListJSON extends AsyncTask<Integer, Void, Integer> {
        String res;
        List<String> EnvName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            String url = getResources().getString(R.string.Get_Productdata);

            try {
                res = MainActivity.OpenConnection(url);
                res = res.replaceAll("\\\\", "");
                JSONObject jResults = new JSONObject(res);
                JSONArray arrJson = jResults.getJSONArray("products");

                for (int i = 0; i < arrJson.length(); i++) {
                    JSONObject obj = arrJson.getJSONObject(i);
                    ProductBean bean = new ProductBean();
                    bean.setPhoneNumber(obj.getString("phoneNumber"));
                    bean.setPrice(obj.getString("price"));
                    bean.setProductImg(obj.getString("productImg"));
                    bean.setProductname(obj.getString("productname"));
                    bean.setVendoraddress(obj.getString("vendoraddress"));
                    bean.setVendorname(obj.getString("vendorname"));
                    ls_ProductList.add(bean);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (res != null) {
                progressDialog.dismiss();
                listview_item_list.setHasFixedSize(true);
                adapter = new ProductFragmentAdapter(ls_ProductList);
                listview_item_list.setAdapter(adapter);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                listview_item_list.setLayoutManager(mLayoutManager);
            } else {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
