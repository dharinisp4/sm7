package in.games.sm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;
import in.games.sm.utils.CustomJsonRequest;

public class WithdrawalActivity extends MyBaseActivity {

    private TextView txtback,txtWalletAmount,txtMobile;
    private ProgressDialog progressDialog;
    private EditText etPoint;
    private Button btnSave;


    String saveCurrentDate,saveCurrentTime;
    int day,hours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        txtback=(TextView)findViewById(R.id.txtBack);
        txtWalletAmount=(TextView)findViewById(R.id.wallet_amount);
        etPoint=(EditText)findViewById(R.id.etRequstPoints);
        btnSave=(Button)findViewById(R.id.add_Request);
        txtMobile=(TextView)findViewById(R.id.textview5);
       // details.setMobileNumber(WithdrawalActivity.this,txtMobile);
        progressDialog=new ProgressDialog(WithdrawalActivity.this);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String points=etPoint.getText().toString().trim();

                if(TextUtils.isEmpty(points))
                {
                    etPoint.setError("Enter Some points");
                    return;
                }
                else
                {

                    Calendar calendar=Calendar.getInstance();

                    SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
                    saveCurrentDate=currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
                    saveCurrentTime=currentTime.format(calendar.getTime());

                    day=calendar.get(Calendar.DAY_OF_WEEK);


                   String a[]=saveCurrentTime.split(":");
                   hours=Integer.parseInt(a[0]);
                  //  Toast.makeText(WithdrawalActivity.this, ""+day +hours, Toast.LENGTH_SHORT).show();
                 if((hours>=10&&hours<15)&&(day>1 && day<7)) {


                     String user_id = Prevalent.currentOnlineuser.getId();
                     String pnts = String.valueOf(points);
                     String st = "pending";
                     int w_amt = Integer.parseInt(txtWalletAmount.getText().toString().trim());
                     int t_amt = Integer.parseInt(pnts);
                     if (w_amt > 0) {

                         if(t_amt<1000)
                         {
                             details.errorMessageDialog(WithdrawalActivity.this, "Minimum Withdraw amount 1000.");
                         }
                         else
                         {
                             if (t_amt > w_amt) {
                                 details.errorMessageDialog(WithdrawalActivity.this, "Your requested amount exceeded");
                                 return;
                             } else {
                                // saveInfoIntoDatabase(user_id, String.valueOf(t_amt), st);
                                 saveInfoWithDate(user_id,String.valueOf(t_amt),st);
                             }
//
                         }

                     } else {
                         details.errorMessageDialog(WithdrawalActivity.this, "You don't have enough points in wallet ");
                     }

                 }
                    else{
                        details.errorMessageDialog(WithdrawalActivity.this, "Time Out ");
                        return;

                    }

//                        saveInfoIntoDatabase(user_id,pnts,st);
                    }
                }



        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(WithdrawalActivity.this);
//        details.setWallet_Amount(txtWalletAmount,progressDialog,Prevalent.currentOnlineuser.getId(),WithdrawalActivity.this);
        details.setWallet_Amount(txtWalletAmount,progressDialog, Prevalent.currentOnlineuser.getId(),WithdrawalActivity.this);
    }



    private void saveInfoWithDate(final String user_id, final String points, final String st)
    {
        progressDialog.show();
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        String dt=dateFormat.format(date);

                 String json_request_tag="json_withdraw_request";
                 HashMap<String,String> params=new HashMap<String, String>();
                params.put("user_id",user_id);
                params.put("points",points);
                 params.put("request_status",st);
                 params.put("date",dt);

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.POST, URLs.URL_WITHDRAW_REQUEST, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try
                {
                       JSONObject jsonObject=response;
                       String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {

                        String msg=jsonObject.getString("message");
                        progressDialog.dismiss();
                        details.errorMessageDialog(WithdrawalActivity.this,msg);
                        //Toast.makeText(WithdrawalActivity.this,msg,Toast.LENGTH_LONG).show();

                    }
                else
                    {
                        progressDialog.dismiss();
                        details.errorMessageDialog(WithdrawalActivity.this,""+jsonObject.getString("message").toString());
                        //Toast.makeText(WithdrawalActivity.this,""+,Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(WithdrawalActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                details.errorMessageDialog(WithdrawalActivity.this,""+error.getMessage());

            }
        });
        AppController.getInstance().addToRequestQueue(customJsonRequest,json_request_tag);
    }




    private void saveInfoIntoDatabase(final String user_id, final String points, final String st) {

        //progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_data_insert_with_req, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {
          //              progressDialog.dismiss();
                    //    onStart();
                        Toast.makeText(WithdrawalActivity.this,"successfull req",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(WithdrawalActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                        return;
                    }
                    else
                    {
                        progressDialog.dismiss();

                        Toast.makeText(WithdrawalActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
                        return;
                    }


                }
                catch (Exception ex)
                {
                    progressDialog.dismiss();

                    Toast.makeText(WithdrawalActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();

                        Toast.makeText(WithdrawalActivity.this,"Error :"+error.getMessage(),Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue= Volley.newRequestQueue(WithdrawalActivity.this);
        requestQueue.add(stringRequest);
    }

}
