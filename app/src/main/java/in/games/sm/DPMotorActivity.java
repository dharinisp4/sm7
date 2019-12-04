package in.games.sm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class DPMotorActivity extends MyBaseActivity  {
  private int stat=0;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtDigit,txtPoint,txtType;
     TextView txtMatka;
    private EditText etDgt,etPnt;
    private TableLayout t1;
    private TableRow tr;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;

    String matName="";
    TextView btnDelete;
    //    private TableRow tr;
    TextView bt_back;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;

    private Dialog dialog;
    private TextView txtOpen,txtClose;
    String dashName;

    ProgressDialog progressDialog;
    static String URLSPMotor="http://jannat.projects.anshuwap.com/restApi/spmotor.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpmotor);

        dashName=getIntent().getStringExtra("matkaName");
        game_id = getIntent().getStringExtra("game_id");
        m_id = getIntent().getStringExtra("m_id");

        txtDigit=(TextView)findViewById(R.id.dgt);
        txtPoint=(TextView)findViewById(R.id.pnt);
        txtType=(TextView)findViewById(R.id.type);

        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new ProgressDialog(DPMotorActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        txtMatka.setSelected(true);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setText(dashName.toString()+"- DP MOTOR Board");
        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String game_type=btnGameType.getText().toString().trim();
                String g[]=game_type.split(" ");
                String t=g[3];
                String dww=g[0];
                //   Toast.makeText(OddEvenActivity.this,""+dww,Toast.LENGTH_LONG).show();
                if(t.equals("Close"))
                {
                    errorMessageDialog(DPMotorActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(DPMotorActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();



            }
        });

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpe(DPMotorActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
            }
        });

        etDgt=(EditText)findViewById(R.id.etSingleDigit);
        etPnt=(EditText)findViewById(R.id.etPoints);
        t1=(TableLayout)findViewById(R.id.tblLayout);
        t1.setColumnStretchable(0,true);
        t1.setColumnStretchable(1,true);
        t1.setColumnStretchable(2,true);
        t1.setColumnStretchable(3,true);
        t1.removeAllViews();

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });




        //tr=(TableRow)findViewById(R.id.tableRow1);


        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int er = t1.getChildCount();
                if (er <= 0) {
                    String message = "Please Add Some Bids";
                    errorMessageDialog(DPMotorActivity.this, message);
                    return;
                } else {

                    try {
                        int amt = 0;
                        ArrayList list_digits = new ArrayList();
                        ArrayList list_type = new ArrayList();
                        ArrayList list_points = new ArrayList();
                        int rows = t1.getChildCount();

                        ArrayList<Map<String, String>> dataMap = new ArrayList<Map<String, String>>();

                        for (int i = 0; i <= rows - 1; i++) {


                            TableRow tableRow = (TableRow) t1.getChildAt(i);
                            TextView d = (TextView) tableRow.getChildAt(0);
                            TextView p = (TextView) tableRow.getChildAt(1);
                            TextView t = (TextView) tableRow.getChildAt(2);

                            String asd = d.getText().toString();
                            String asd1 = p.getText().toString();
                            String asd2 = t.getText().toString();
                            int b = 9;

                            if (asd2.equals("close")) {
                                b = 1;
                            } else if (asd2.equals("open")) {
                                b = 0;
                            }


                            amt = amt + Integer.parseInt(asd1);

                            list_digits.add(asd);
                            list_points.add(asd1);
                            list_type.add(b);


                            // String sd=list_digits.add();
                        }


                        String id = Prevalent.currentOnlineuser.getId().toString().trim();
                        String matka_id = m_id.toString().trim();
                        String date = "15/02/2020";
                        String dt=btnGameType.getText().toString().trim();
       String d[]=dt.split(" ");
//
        String c=d[0].toString();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("points", list_points);
                        jsonObject.put("digits", list_digits);
                        jsonObject.put("bettype", list_type);
                        jsonObject.put("user_id", id);
                        jsonObject.put("matka_id", matka_id);
                        jsonObject.put("date", c);
                        jsonObject.put("game_id", game_id);

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject);


//                     Toast.makeText(OddEvenActivity.this,"data"+jsonArray,Toast.LENGTH_LONG).show();

                        //      Object o1=jsonArray_digits;

                        String w = txtWallet_amount.getText().toString().trim();
                        int wallet_amount = Integer.parseInt(w);
                        if (wallet_amount < amt) {
                            errorMessageDialog(DPMotorActivity.this,"Insufficient Amount");
                            etPnt.setText("");
                            etDgt.setText("");
                            etDgt.requestFocus();
                            return;
                        } else {
                            int up_amt = wallet_amount - amt;
                            String asd = String.valueOf(up_amt);
                            String userid = Prevalent.currentOnlineuser.getId();
                            btnSave.setEnabled(false);
                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,DPMotorActivity.this,userid,asd);
                            updateWalletAmount( DPMotorActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(DPMotorActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }

                // t1.removeAllViews();

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                t1.removeAllViews();
                txtDigit = new TextView(DPMotorActivity.this);
                txtPoint = new TextView(DPMotorActivity.this);
                txtType = new TextView(DPMotorActivity.this);
                String bet = btnType.getText().toString();

                if (bet.equals("Select Type")) {
                    String message="Select bet type";
                    errorMessageDialog(DPMotorActivity.this,message);
                    return;
                }
               else if (TextUtils.isEmpty(etDgt.getText().toString())) {
                    etDgt.setError("Please enter any digit");
                    etDgt.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPnt.getText().toString())) {
                    etPnt.setError("Please enter some point");
                    etPnt.requestFocus();
                    return;

                }  else {
                    int pints = Integer.parseInt(etPnt.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPnt.setError("Minimum Biding amount is 10");
                        etPnt.requestFocus();
                        return;


                    } else {
                        String d = etDgt.getText().toString();
                        final String p = etPnt.getText().toString();

                        String g=null;
                        if(stat==1)
                        {
                            g="open";
                        }
                        else if(stat==2)
                        {
                            if(bet.equals("Open Bet"))
                            {
                                g="open";
                            }
                            else  if(bet.equals("Close Bet"))
                            {
                                g="close";
                            }

                        }

                      //  String g = btnType.getText().toString();

                        String inputData =etDgt.getText().toString().trim();
                        if (inputData.equals("false")) {
                            Toast.makeText(DPMotorActivity.this, "Wrong input", Toast.LENGTH_LONG).show();
                        } else {
                            getDataSet(inputData, p, g);
                        }

//                    Toast.makeText(SpMotorActivity.this,"DDat"+asd,Toast.LENGTH_LONG).show();


                        etDgt.setText("");
                        etPnt.setText("");
                        etDgt.requestFocus();
                       // btnType.setText("Select Type");

                                //arrayList.add(new SingleDigitObjects(data[i].toString(),p,g));




                        //                  Toast.makeText(DPMotorActivity.this,"Data :"+d+"\n"+p+"\n"+g,Toast.LENGTH_LONG).show();
//                    List<String> assd=new ArrayList<String>();
//
//                    String[] s=d.split("");
//
//
//                    //Toast.makeText(SpMotorActivity.this,"DDat"+d[0],Toast.LENGTH_LONG).show();
//
//
//
//                    for(int i=0;i<=s.length-1;i++)
//                    {
//
//                        assd.add(i, Arrays.asList(s).get(i)
//                        );
//                    }
//                    assd.remove(0);
//
//                    String inputData=assd.toString();
//
//                    getDataSet(inputData,p,g);
                    }


                }

            }


        });
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        String day=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String saveDate=currentDate.format(calendar.getTime());


        String full=saveDate+" "+day+" Bet";
        btnGameType.setText(full);



    }

    private void getDataSet(final String inputData,final String p,final String g) {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DpMotor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //    Toast.makeText(SpMotorActivity.this, "Data" + response, Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");
                            JSONArray as = jsonObject.getJSONArray("data");

                            if (status.equals("success")) {
                                for (int i = 0; i <= as.length() - 1; i++) {
                                    String q = as.getString(i);
                                    setTableData(q,p,g);
                                    //arrayList.add(new SingleDigitObjects(p,d,th));
                                }
                                Toast.makeText(DPMotorActivity.this, "Something wrong"+as, Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(DPMotorActivity.this, "Something wrong", Toast.LENGTH_LONG).show();

                            }


//                            JSONObject object=new JSONObject(response);
//                            String status=object.getString("status");
//                            List asd=Arrays.asList(object.getString("answer"));

                        } catch (Exception ex) {
                            progressDialog.dismiss();
                            Toast.makeText(DPMotorActivity.this, "Error :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("arr", inputData);
                //params.put("password",pass);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   setSessionTimeOut(DPMotorActivity.this);
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(DPMotorActivity.this,String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),DPMotorActivity.this);
        }
        else
        {
            stat=2;
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),DPMotorActivity.this);
            details.setBetDateDay(DPMotorActivity.this,m_id,btnGameType,progressDialog);

        }

        }

    private void setTableData(String datum, final String p, String g) {


        tr=new TableRow(DPMotorActivity.this);
        txtDigit=new TextView(DPMotorActivity.this);
        txtPoint=new TextView(DPMotorActivity.this);
        txtType=new TextView(DPMotorActivity.this);
        btnDelete=new TextView(DPMotorActivity.this);

        btnDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.del_btn,0,0,0);
        TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams();
        layoutParams.setMargins(0,10,0,10);
        tr.setLayoutParams(layoutParams);
        //    tr.setElevation(20);
        // tr.setDividerPadding(20);
        tr.setPadding(10,10,10,10);
        txtPoint.setText(p);
        txtDigit.setText(datum.toString());
        txtType.setText(g.toString());
        tr.setBackgroundColor(Color.LTGRAY);
        tr.addView(txtDigit);
        tr.addView(txtPoint);
        tr.addView(txtType);
        tr.addView(btnDelete);
        //              t1.removeAllViews();
        t1.addView(tr);
        // t1.removeViewAt(i);
        //tr.removeViewAt(i);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View row=(View)v.getParent();
                ViewGroup container= ((ViewGroup)row.getParent());
                container.removeView(row);
                container.invalidate();
                int we= t1.getChildCount();
                int points=Integer.parseInt(p);
                int tot_pnt=we*points;

                btnSave.setText("(BIDS="+we+")(Points="+tot_pnt+")");

            }
        });

        int we= t1.getChildCount();
        int points=Integer.parseInt(p);
        int tot_pnt=we*points;

        btnSave.setText("(BIDS="+we+")(Points="+tot_pnt+")");




    }

//    private void getDataSet(final String inputData,final String d,final String g) {
//
//        progressDialog.show();
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLSPMotor,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try
//                        {
//
//                            JSONObject jsonObject=new JSONObject(response);
//
//                            String status =jsonObject.getString("status");
//                            JSONArray as =jsonObject.getJSONArray("data");
//
//                            if(status.equals("success"))
//                            {
//                                for(int i=0; i<=as.length()-1; i++)
//                                {
//                                    String p= as.getString(i);
//                                    arrayList.add(new SingleDigitObjects(p,d,g));
//                                }
//
//                                adapter1.notifyDataSetChanged();
//
//                                progressDialog.dismiss();
//                                Toast.makeText(DPMotorActivity.this,"Data"+as,Toast.LENGTH_LONG).show();
//                            }
//                            else
//                            {
//                                progressDialog.dismiss();
//                                Toast.makeText(DPMotorActivity.this,"Something wrong",Toast.LENGTH_LONG).show();
//
//                            }
//
////
////                            JSONObject object=new JSONObject(response);
////                            String status=object.getString("status");
////                            List asd=Arrays.asList(object.getString("answer"));
////                            if(status.equals("success"))
////                            {
////                                progressDialog.dismiss();
////                                Toast.makeText(SpMotorActivity.this,"Data"+asd,Toast.LENGTH_LONG).show();
////
////                            }
////                            else
////                            {
////                                progressDialog.dismiss();
////                                Toast.makeText(SpMotorActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
////                                return;
////                            }
//
//                        }
//                        catch (Exception ex)
//                        {
//                            progressDialog.dismiss();
//                            Toast.makeText(DPMotorActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                })
//        {
//
//            protected Map<String,String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put("arr", inputData);
//                //params.put("password",pass);
//                return params;
//            }
//
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }


}
