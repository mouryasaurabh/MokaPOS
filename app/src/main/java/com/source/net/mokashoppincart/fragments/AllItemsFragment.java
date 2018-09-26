package com.source.net.mokashoppincart.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.source.net.mokashoppincart.FragmentToActivityInterface;
import com.source.net.mokashoppincart.R;
import com.source.net.mokashoppincart.ShoppingCartActivity;
import com.source.net.mokashoppincart.models.AllItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AllItemsFragment extends Fragment {
    private FragmentToActivityInterface mListener;

    private RequestQueue mRequestQueue;
    private  NetworkTask mNetworkTask;
    private ArrayList<AllItemModel> mAllItemModelList=new ArrayList<>();
    ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    public AllItemsFragment() {
        // Required empty public constructor
    }

    public static AllItemsFragment newInstance() {
        AllItemsFragment fragment = new AllItemsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_all_items, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mProgressBar=view.findViewById(R.id.progress);
        mRecyclerView=view.findViewById(R.id.recycer_view_items);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentToActivityInterface) {
            mListener = (FragmentToActivityInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mNetworkTask=new NetworkTask();
        mNetworkTask.execute();
    }

    public class NetworkTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            JsonArrayRequest request = new JsonArrayRequest("https://jsonplaceholder.typicode.com/photos",
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {
                            for(int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    AllItemModel allItemModel=new AllItemModel();
                                    allItemModel.setAlbumId(jsonObject.getInt("albumId"));
                                    allItemModel.setId(jsonObject.getInt("id"));
                                    allItemModel.setTitle(jsonObject.getString("title"));
                                    allItemModel.setUrl(jsonObject.getString("url"));
                                    allItemModel.setThumbUrl(jsonObject.getString("thumbnailUrl"));
                                    mAllItemModelList.add(allItemModel);
                                }
                                catch(JSONException e) {
                                }
                            }
                            mProgressBar.setVisibility(View.GONE);
                            initializeRV(mAllItemModelList);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getActivity(),getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show();
                        }
                    });
            mRequestQueue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }

    private void initializeRV(ArrayList<AllItemModel> mAllItemModel) {
        ItemAdapter itemAdapter=new ItemAdapter(getActivity(), mAllItemModel, new ItemAdapter.ItemClick() {
            @Override
            public void onItemClick(AllItemModel model, String amount) {
                mListener.onItemClick(model, amount);
            }
        });
        mRecyclerView.setAdapter(itemAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mNetworkTask != null && mNetworkTask.equals(AsyncTask.Status.RUNNING))
            mNetworkTask.cancel(true);
    }

}
