package in.games.sm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.setSessionTimeOut;

public class DrawerProfileActivity extends MyBaseActivity {
    private EditText etPAddress,etPCity,etPPinCode,etAccNo,etBankName,etIfscCode,etAccHolderName,etPaytm,etTez,etPhonePay;
    private Dialog dialog;
    ProgressDialog progressDialog;
    ProgressBar pb;
    static String URL_REGIST="https://malamaal.anshuwap.com/restApi/users.php";
    private TextView btn_back;
    private CardView cvAddress,cvBank,cvPaytm,cvGoogle,cvPhone;
    private Button btnDAddress,btnDBank,btnDPaytm,btnDGoogle,btnDPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_profile);

        cvAddress=(CardView)findViewById(R.id.cvAddress);
        cvBank=(CardView)findViewById(R.id.cvBank);
        cvPaytm=(CardView)findViewById(R.id.cvPaytm);
        cvGoogle=(CardView)findViewById(R.id.cvGoogle);
        cvPhone=(CardView)findViewById(R.id.cvPhone);
        btn_back=(TextView)findViewById(R.id.txt_back);
        progressDialog=new ProgressDialog(DrawerProfileActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSessionTimeOut(DrawerProfileActivity.this);
        cvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_address_layout);
                btnDAddress=(Button)dialog.findViewById(R.id.add_address);
                etPAddress=(EditText)dialog.findViewById(R.id.etAddress);
                etPCity=(EditText)dialog.findViewById(R.id.etCity);
                etPPinCode=(EditText)dialog.findViewById(R.id.etPin);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                String ad=Prevalent.currentOnlineuser.getAddress();
                String ct=Prevalent.currentOnlineuser.getCity();
                String pn=Prevalent.currentOnlineuser.getPincode();

                 setDataEditText(etPAddress,ad);
                 setDataEditText(etPCity,ct);
                 setDataEditText(etPPinCode,pn);

                btnDAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String a=etPAddress.getText().toString().trim();
                        String c=etPCity.getText().toString().trim();
                        String p=etPPinCode.getText().toString().trim();

                        if(TextUtils.isEmpty(a))
                        {
                            etPAddress.setError("Enter your Address");
                            etPAddress.requestFocus();
                            return;

                        }
                        else if(TextUtils.isEmpty(c))
                        {
                            etPCity.setError("Enter city name");
                            etPCity.requestFocus();
                            return;

                        } else if(TextUtils.isEmpty(p))
                        {
                            etPPinCode.setError("Enter pin code");
                            etPPinCode.requestFocus();
                            return;

                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getEmail();
     //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                           storeAddressData(a,c,p,mailid);
                        }




//                        Toast.makeText(DrawerProfileActivity.this,"a12:- "+a.toString()+"c :-"+c+"p :-"+p.toString(),Toast.LENGTH_LONG).show();
                       // Toast.makeText(DrawerProfileActivity.this,"Your address save successfully.",Toast.LENGTH_LONG).show();


                    }
                });

            }
        });

        cvBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_bank_layout);
                btnDBank=(Button)dialog.findViewById(R.id.add_bank);
                etAccNo=(EditText)dialog.findViewById(R.id.etAccNo);
                etBankName=(EditText)dialog.findViewById(R.id.etBankName);
                etIfscCode=(EditText)dialog.findViewById(R.id.etIfsc);
                etAccHolderName=(EditText)dialog.findViewById(R.id.etHName);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                etAccNo.setText(Prevalent.currentOnlineuser.getAccountno());
                etBankName.setText(Prevalent.currentOnlineuser.getBank_name());
                etIfscCode.setText(Prevalent.currentOnlineuser.getIfsc_code());
                etAccHolderName.setText(Prevalent.currentOnlineuser.getAccount_holder_name());

               String ac=Prevalent.currentOnlineuser.getAccountno().toString();
               String bn=Prevalent.currentOnlineuser.getBank_name().toString();
               String ic=Prevalent.currentOnlineuser.getIfsc_code().toString();
               String ah=Prevalent.currentOnlineuser.getAccount_holder_name().toString();

               setDataEditText(etAccNo,ac);
               setDataEditText(etBankName,bn);
               setDataEditText(etIfscCode,ic);
               setDataEditText(etAccHolderName,ah);
                btnDBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        String accno=etAccNo.getText().toString().trim();
                        String bankname=etBankName.getText().toString().trim();
                        String ifsc=etIfscCode.getText().toString().trim();
                        String hod_name=etAccHolderName.getText().toString().trim();

                        if(TextUtils.isEmpty(accno))
                        {
                            etAccNo.setError("Enter your account number");
                            etAccNo.requestFocus();
                            return;

                        }
                        else if(TextUtils.isEmpty(bankname))
                        {
                            etBankName.setError("Enter Bank Name");
                            etBankName.requestFocus();
                            return;

                        } else if(TextUtils.isEmpty(ifsc))
                        {
                            etIfscCode.setError("Enter ifsc code");
                            etIfscCode.requestFocus();
                            return;

                        } else if(TextUtils.isEmpty(hod_name))
                        {
                            etAccHolderName.setError("Enter acc holder name");
                            etAccHolderName.requestFocus();
                            return;

                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getEmail();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storeBankDetails(accno,bankname,ifsc,hod_name,mailid);
                        }


                    }
                });
            }
        });

        cvPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_paytm_layout);
                btnDPaytm=(Button)dialog.findViewById(R.id.add_paytm);
                etPaytm=(EditText)dialog.findViewById(R.id.etPaytmNo);


                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
       String p=Prevalent.currentOnlineuser.getPaytm_no().toString();
                setDataEditText(etPaytm,p);


                btnDPaytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String paytmNumber=etPaytm.getText().toString().trim();
                        if(TextUtils.isEmpty(paytmNumber))
                        {
                            etPaytm.setError("Enter Paytm Number");
                            etPaytm.requestFocus();
                            return;
                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getEmail();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storePaytmDetails(paytmNumber,mailid);
                        }

                       // Toast.makeText(DrawerProfileActivity.this,"Your paytm number save successfully.",Toast.LENGTH_LONG).show();


                    }
                });
            }
        });

        cvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_tez_layout);
                btnDGoogle=(Button)dialog.findViewById(R.id.add_tez);
                etTez=(EditText)dialog.findViewById(R.id.etTezNo);



                dialog.show();
String tz=Prevalent.currentOnlineuser.getTez_no().toString();

             setDataEditText(etTez,tz);
             btnDGoogle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //Toast.makeText(DrawerProfileActivity.this,"Your paytm number save successfully.",Toast.LENGTH_LONG).show();
                        String teznumber=etTez.getText().toString().trim();


                        if(TextUtils.isEmpty(teznumber))
                        {
                            etTez.setError("Enter GooglePay Number");
                            etTez.requestFocus();
                            return;
                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getEmail();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storeTezDetails(teznumber,mailid);
                        }



                    }
                });
            }
        });

        cvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(DrawerProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_phone_layout);
                btnDPhone=(Button)dialog.findViewById(R.id.add_Phone);
               etPhonePay=(EditText)dialog.findViewById(R.id.etPhone);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                String x=Prevalent.currentOnlineuser.getPhonepay_no().toString();

                setDataEditText(etPhonePay,x);
                btnDPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String phonepaynumber=etPhonePay.getText().toString().trim();

                        if(TextUtils.isEmpty(phonepaynumber))
                        {
                            etPhonePay.setError("Enter Phone Pay Number");
                            etPhonePay.requestFocus();
                            return;
                        }
                        else
                        {
                            String mailid= Prevalent.currentOnlineuser.getEmail();
                            //                       Toast.makeText(DrawerProfileActivity.this,"Email :"+mailid,Toast.LENGTH_LONG).show();
                            storePhonePayDetails(phonepaynumber,mailid);
                        }


                    }
                });
            }
        });
    }

    private void storePhonePayDetails(final String phonepaynumber,final String mailid) {
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DrawerProfileActivity.this, "Phone Pay Number Updated!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DrawerProfileActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(DrawerProfileActivity.this, "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("key","6");
                params.put("email",mailid);
                params.put("phonepay",phonepaynumber);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    private void storeTezDetails(final String teznumber,final String mailid) {


        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DrawerProfileActivity.this, "Google Pay Number Updated!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DrawerProfileActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(DrawerProfileActivity.this, "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("key","4");
                params.put("email",mailid);
                params.put("tez",teznumber);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void storePaytmDetails(final String paytmNumber,final String mailid) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DrawerProfileActivity.this, "Paytm Number Updated!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DrawerProfileActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(DrawerProfileActivity.this, "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("key","5");
                params.put("email",mailid);
                params.put("paytm",paytmNumber);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void storeBankDetails(final String accno,final String bankname,final String ifsc,final String hod_name,final String mailid) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DrawerProfileActivity.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DrawerProfileActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(DrawerProfileActivity.this, "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("key","3");
                params.put("email",mailid);
                params.put("accountno",accno);
                params.put("bankname",bankname);
                params.put("ifsc",ifsc);
                params.put("accountholder",hod_name);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void storeAddressData(final String a,final String c,final String p,final String mailid) {

        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try
                        {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("status");
                            if(success.equals("success"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DrawerProfileActivity.this, "Address Updated!!!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(success.equals("unsuccessful"))
                            {
                                progressDialog.dismiss();
                                String msg=jsonObject.getString("message");
                                Toast.makeText(DrawerProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                                return;
                            }



                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DrawerProfileActivity.this, "Updation failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                 dialog.dismiss();
                            //  btnReg.setVisibility(View.VISIBLE);


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(DrawerProfileActivity.this, "Updation failed"+error.getMessage(), Toast.LENGTH_SHORT).show();
                      //  pb.setVisibility(View.GONE);

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("key","2");
                params.put("email",mailid);
                params.put("address",a);
                params.put("city",c);
                params.put("pin",p);


                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void setDataEditText(EditText edt,String data)
    {
        String s=data.toString();
        if(data.equals("null"))
        {

        }
        else
        {
            edt.setText(data.toString());
        }
    }

}
