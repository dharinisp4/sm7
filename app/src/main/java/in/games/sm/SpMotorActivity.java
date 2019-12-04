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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;


public class SpMotorActivity extends MyBaseActivity   {
    private int stat=0;
    private final String[] triplePanna={"012","712","435","123","890","567","234","912","678","345"};
static String URLSPMotor="https://malamaal.anshuwap.com/restApi/spmotor.php";
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private TableLayout t1;
    private TableRow tr;
    TextView txtMatka;
    private TextView txtDigit,txtPoint,txtType;
    //  private TableLayout t1;
    TextView btnDelete;
    //    private TableRow tr;
    TextView bt_back;
private EditText etDigits;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;
    private Dialog dialog;
    private TextView txtOpen,txtClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_motor);

        final String dashName = getIntent().getStringExtra("matkaName");
        game_id = getIntent().getStringExtra("game_id");
        m_id = getIntent().getStringExtra("m_id");
        etPoints = (EditText) findViewById(R.id.etPoints);
        btnType = (Button) findViewById(R.id.btnBetType);
        btnGameType = (Button) findViewById(R.id.btnBetStatus);
        txtMatka = (TextView) findViewById(R.id.board);
        progressDialog = new ProgressDialog(SpMotorActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        btnAdd = (Button) findViewById(R.id.digit_add);
        btnSave = (Button) findViewById(R.id.digit_save);

        bt_back=(TextView)findViewById(R.id.txtBack);
        txtMatka.setSelected(true);
        t1=(TableLayout)findViewById(R.id.tblLayout);
        t1.setColumnStretchable(0,true);

        t1.setColumnStretchable(1,true);
        t1.setColumnStretchable(2,true);
        t1.setColumnStretchable(3,true);
        t1.removeAllViews();
        txtMatka.setText(dashName.toString()+"- SP Motor Board");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        etDigits = findViewById(R.id.etSingleDigit);
//        final AutoCompleteTextView editText = findViewById(R.id.etSingleDigit);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SpMotorActivity.this, android.R.layout.simple_list_item_1, triplePanna);
//        editText.setAdapter(adapter);

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpe(SpMotorActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
            }
        });


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
                    errorMessageDialog(SpMotorActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(SpMotorActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();


            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bet = btnType.getText().toString().trim();
                String dData = etDigits.getText().toString().trim();
                if (bet.equals("Select Type")) {
                    String message="select bet type";
                    errorMessageDialog(SpMotorActivity.this,message);
                    return;
                }
               else if (TextUtils.isEmpty(etDigits.getText().toString())) {
                    etDigits.setError("Please enter any digit");
                    etDigits.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }  else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
                        return;


                    } else {
                        t1.removeAllViews();
                        String d = etDigits.getText().toString();
                        String p = etPoints.getText().toString();
                        String th = null;
                        if(stat==1)
                        {
                            th="open";
                        }
                        else if(stat==2)
                        {
                            if(bet.equals("Open Bet"))
                            {
                                th="open";
                            }
                            else  if(bet.equals("Close Bet"))
                            {
                                th="close";
                            }

                        }



                        //Toast.makeText(SpMotorActivity.this,"DDat"+d[0],Toast.LENGTH_LONG).show();

                        //String asd=spInput(d);


                        //  String inputData = String.valueOf(assd);
                        String inputData =etDigits.getText().toString().trim();
                        if (inputData.equals("false")) {
                            Toast.makeText(SpMotorActivity.this, "Wrong input", Toast.LENGTH_LONG).show();
                        } else {
                            getDataSet(inputData, p, th);
                        }

//                    Toast.makeText(SpMotorActivity.this,"DDat"+asd,Toast.LENGTH_LONG).show();


                        etPoints.setText("");
                        etDigits.setText("");
                        etDigits.requestFocus();
                      //  btnType.setText("Select Type");
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int er = t1.getChildCount();
                if (er <= 0) {
                    String message = "Please Add Some Bids";
                    errorMessageDialog(SpMotorActivity.this, message);
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

                            char quotes='"';
                            list_digits.add(quotes+asd+quotes);
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
                            details.errorMessageDialog(SpMotorActivity.this,"insufficient amount");
                           // Toast.makeText(SpMotorActivity.this, "insufficient amount", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            btnSave.setEnabled(false);
                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SpMotorActivity.this,userid,asd);
                           updateWalletAmount(SpMotorActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(SpMotorActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //private int stat=0;
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(SpMotorActivity.this,String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),SpMotorActivity.this);
        }
        else
        {
            stat=2;
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),SpMotorActivity.this);
            details.setBetDateDay(SpMotorActivity.this,m_id,btnGameType,progressDialog);


        }

     }

    private void getDataSet(final String inputData, final String d, final String th) {
      //  Toast.makeText(SpMotorActivity.this,"at"+inputData,Toast.LENGTH_LONG).show();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSPMotor,
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
                                    String p = as.getString(i);
                                      setTableData(p,d,th);
                                    //arrayList.add(new SingleDigitObjects(p,d,th));
                                }
///                                Toast.makeText(SpMotorActivity.this, "Something wrong"+as, Toast.LENGTH_LONG).show();

                              progressDialog.dismiss();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SpMotorActivity.this, "Something wrong", Toast.LENGTH_LONG).show();

                            }


//                            JSONObject object=new JSONObject(response);
//                            String status=object.getString("status");
//                            List asd=Arrays.asList(object.getString("answer"));

                        } catch (Exception ex) {
                            progressDialog.dismiss();
                            Toast.makeText(SpMotorActivity.this, "Error :" + ex.getMessage(), Toast.LENGTH_LONG).show();
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

    private void setTableData(String datum, final String p, String g) {


        tr=new TableRow(SpMotorActivity.this);
        txtDigit=new TextView(SpMotorActivity.this);
        txtPoint=new TextView(SpMotorActivity.this);
        txtType=new TextView(SpMotorActivity.this);
        btnDelete=new TextView(SpMotorActivity.this);

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






    }

