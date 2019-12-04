package in.games.sm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestActivity extends MyBaseActivity {

    EditText etPoints;
    ProgressDialog progressDialog;
    private TextView bt_back,txtMatka;
    Button btnRequest;
    private TextView txtWallet_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        txtMatka=(TextView)findViewById(R.id.board);
        etPoints=(EditText)findViewById(R.id.etRequstPoints);
        btnRequest=(Button)findViewById(R.id.add_Request);
        progressDialog=new ProgressDialog(RequestActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        bt_back=(TextView)findViewById(R.id.txtBack);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);


        txtMatka.setText("FUNDS");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int points=Integer.parseInt(etPoints.getText().toString().trim());

                if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Enter Some Points");
                    return;
                }
                else
                {
                    if(points<200)
                    {
                        details.errorMessageDialog(RequestActivity.this,"Minimum Range for points is 200");

                    }
                    else
                    {
                        String user_id= Prevalent.currentOnlineuser.getId();
                        String p=String.valueOf(points);
                        String st="pending";
                       saveInfoIntoDatabase(user_id,p,st);
                    }
                }






            }
        });

//        AddPointsFragment searchFragment=new AddPointsFragment();
//        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.flMain,searchFragment);
//        //  fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();


    }



    private void saveInfoIntoDatabase(final String user_id, final String points, final String st) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_data_insert_req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RequestActivity.this,"successfull",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(RequestActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                        return;
                    }
                    else
                    {
                        progressDialog.dismiss();

                        Toast.makeText(RequestActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
                        return;
                    }


                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();

                    Toast.makeText(RequestActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();

                        Toast.makeText(RequestActivity.this,"Error :"+error.getMessage(),Toast.LENGTH_LONG).show();
                        return;

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("user_id",user_id);
                params.put("points",points);
                params.put("request_status",st);

                // params.put("phonepay",phonepaynumber);


                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(RequestActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(RequestActivity.this);
        details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),RequestActivity.this);
    }
}
