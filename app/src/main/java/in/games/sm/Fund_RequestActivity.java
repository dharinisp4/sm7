package in.games.sm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.games.sm.Adapter.Request_HistoryAdapter;
import in.games.sm.Objects.Fund_Request_HistoryObject;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.setSessionTimeOut;

public class Fund_RequestActivity extends MyBaseActivity {

    private RecyclerView recyclerView;
    ArrayList<Fund_Request_HistoryObject> list;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    TextView txtBack;
    private Request_HistoryAdapter request_historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund__request);

        progressDialog=new ProgressDialog(Fund_RequestActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        list=new ArrayList();

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setSessionTimeOut(Fund_RequestActivity.this);

        txtBack=(TextView)findViewById(R.id.txtBack);
        request_historyAdapter=new Request_HistoryAdapter(this,list);
        //matakListViewAdapter=new MatakListViewAdapter(this,matkaList);
        recyclerView.setAdapter(request_historyAdapter);

        String User_id= Prevalent.currentOnlineuser.getId();
       getRequestData(User_id);


       txtBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    private void getRequestData(final String user_id) {

        progressDialog.show();

        list.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_req_history, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("empty"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(Fund_RequestActivity.this,"empty",Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());

                }
                else
                {
                    try
                    {
                        JSONArray jsonArray=new JSONArray(response);
                        //progressDialog.dismiss();
                        for(int i=0; i<=jsonArray.length()-1;i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Fund_Request_HistoryObject matkasObjects = new Fund_Request_HistoryObject();
                            matkasObjects.setRequest_id(jsonObject.getString("request_id"));
                            matkasObjects.setRequest_points(jsonObject.getString("request_points"));
                            matkasObjects.setTime(jsonObject.getString("time"));
                            matkasObjects.setRequest_status(jsonObject.getString("request_status"));
                            matkasObjects.setUser_id(jsonObject.getString("user_id"));


                            list.add(matkasObjects);


                        }
                        request_historyAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Fund_RequestActivity.this,"There is no history for this game",Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }
                }


                //  Toast.makeText(BidActivity.this,response,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Fund_RequestActivity.this,"Error"+error.toString(),Toast.LENGTH_LONG).show();
//                        Log.e("Volley",error.toString());
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("user_id",user_id);



                // params.put("phonepay",phonepaynumber);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
