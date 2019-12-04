package in.games.sm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Model.MatkasObjects;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class DoublePanaActivity extends MyBaseActivity  {

    private int stat=0;
    private final String[] doublePanna={"118","226","244","299","334","488","550","668","677","100","119","155","227","335","344",
            "399","588","669","110","200","228","255","336","499","660","688","778",
            "166","129","300","337","355","445","599","779","788","112","220","266",
            "338","400","446","455","699","770","113","122","177","339","366","447",
            "500","799","889","600","114","277","330","448","466","556","880","899",
            "115","133","188","223","377","449","557","566","700","116","224","233",
            "288","440","477","558","800","990","117","144","199","225","388","559",
            "577","667","900","229"};

    int val_p=0;
    TextView  txtClose,txtOpen;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;

    private TableLayout t1;
    private TableRow tr;
    TextView btnDelete;
    private TextView txtDigit,txtPoint,txtType;
    //    private TableRow tr;
    TextView bt_back;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_pana);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new ProgressDialog(DoublePanaActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        txtMatka.setSelected(true);

        bt_back=(TextView)findViewById(R.id.txtBack);


        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(DoublePanaActivity.this,android.R.layout.simple_list_item_1,doublePanna);
        editText.setAdapter(adapter);

        txtMatka.setText(dashName.toString()+"- Double Pana Board");

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
                    errorMessageDialog(DoublePanaActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(DoublePanaActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                //
            }
        });

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

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpe(DoublePanaActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
            }
        });

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bet=btnType.getText().toString();
                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                {
                    String message="select bet type";
                    errorMessageDialog(DoublePanaActivity.this,message);
                    return;
                }

                else if(TextUtils.isEmpty(editText.getText().toString()))
                {
                    editText.setError("Please enter any digit");
                    editText.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }
                else if(!Arrays.asList(doublePanna).contains(dData))
                {
                    Toast.makeText(DoublePanaActivity.this,"This is invalid paana",Toast.LENGTH_LONG).show();
                    editText.setText("");
                    editText.requestFocus();
                    return;
                }
                else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
                        return;


                    } else {
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

                        String d = editText.getText().toString();
                        final String p = etPoints.getText().toString();
                        String g = btnGameType.getText().toString();

                        tr = new TableRow(DoublePanaActivity.this);
                        txtDigit = new TextView(DoublePanaActivity.this);
                        txtPoint = new TextView(DoublePanaActivity.this);
                        txtType = new TextView(DoublePanaActivity.this);
                        btnDelete = new TextView(DoublePanaActivity.this);
                        btnDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.del_btn, 0, 0, 0);
                        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams();
                        layoutParams.setMargins(0, 10, 0, 10);
                        tr.setLayoutParams(layoutParams);
                        //    tr.setElevation(20);
                        // tr.setDividerPadding(20);
                        tr.setPadding(10, 10, 10, 10);
                        txtPoint.setText(p);
                        txtDigit.setText(d.toString());
                        txtType.setText(th.toString());
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

                                View row = (View) v.getParent();
                                ViewGroup container = ((ViewGroup) row.getParent());
                                container.removeView(row);
                                container.invalidate();

                                int we = t1.getChildCount();


                                for(int i=0; i<we;i++)
                                {
                                    TableRow tableRow=(TableRow)t1.getChildAt(i);
                                    TextView textPoints=(TextView)tableRow.getChildAt(1);
                                    String v_l=textPoints.getText().toString().trim();

                                    val_p=val_p+(Integer.parseInt(v_l));
                                    //Toast.makeText(SinglePannaActivity.this,""+val_p,Toast.LENGTH_LONG).show();


                                }
                                int points = Integer.parseInt(p);
                                int tot_pnt = we * points;

                                btnSave.setText("(BIDS=" + we + ")(Points=" + val_p + ")");
                                val_p=0;

                            }
                        });

                        int we = t1.getChildCount();


                        for(int i=0; i<we;i++)
                        {
                            TableRow tableRow=(TableRow)t1.getChildAt(i);
                            TextView textPoints=(TextView)tableRow.getChildAt(1);
                            String v_l=textPoints.getText().toString().trim();

                            val_p=val_p+(Integer.parseInt(v_l));
                            //Toast.makeText(SinglePannaActivity.this,""+val_p,Toast.LENGTH_LONG).show();


                        }
                        int points = Integer.parseInt(p);
                        int tot_pnt = we * points;

                        btnSave.setText("(BIDS=" + we + ")(Points=" + val_p + ")");
                        val_p=0;
                        editText.setText("");
                        etPoints.setText("");
                        editText.requestFocus();

                       // btnType.setText("Select Type");
                    }

                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int er=t1.getChildCount();
                if(er<=0)
                {
                    String message="Please Add Some Bids";
                    errorMessageDialog(DoublePanaActivity.this,message);
                    return;
                }
                else {
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
                          errorMessageDialog(DoublePanaActivity.this,"Insufficient Amount");
                          return;
                        } else {
                            int up_amt = wallet_amount - amt;
                            String asd = String.valueOf(up_amt);
                            String userid = Prevalent.currentOnlineuser.getId();
                            btnSave.setEnabled(false);
                           updateWalletAmount(DoublePanaActivity.this, jsonArray, progressDialog,dashName,m_id);
                      //      details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,DoublePanaActivity.this,userid,asd);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(DoublePanaActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    private void setData(final TextView txtOpen, final TextView txtClose, final String m_id) {
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                        JSONObject object=jsonObject.getJSONObject("data");
                        MatkasObjects matkasObjects=new MatkasObjects();
                        matkasObjects.setId(object.getString("id"));
                        matkasObjects.setName(object.getString("name"));
                        matkasObjects.setStart_time(object.getString("start_time"));
                        matkasObjects.setStarting_num(object.getString("starting_num"));
                        matkasObjects.setNumber(object.getString("number"));
                        matkasObjects.setEnd_num(object.getString("end_num"));
                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
                        matkasObjects.setCreated_at(object.getString("created_at"));
                        matkasObjects.setUpdated_at(object.getString("updated_at"));
                        matkasObjects.setStatus(object.getString("status"));

                     String bid_start=matkasObjects.getBid_start_time();
                     Date current_time=new Date();
                       SimpleDateFormat sformat=new SimpleDateFormat("HH:mm:ss");
                    //Date time_start=sformat.parse(bid_start);
                    String c_date=sformat.format(current_time);

                   // int flag=time_start.compareTo(current_time);
                   //txtOpen.setText(""+d+"\n"+current_time);
                  // txtClose.setText(current_time.toString());

                        String startTimeSplliting[]=bid_start.split(":");
                        int s_hours=Integer.parseInt(startTimeSplliting[0]);
                        int s_min=Integer.parseInt(startTimeSplliting[1]);
                        int s_sec=Integer.parseInt(startTimeSplliting[2]);
                        String currentTimeSplitting[]=c_date.split(":");
                        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
                        int c_min=Integer.parseInt(currentTimeSplitting[1]);
                        int c_sec=Integer.parseInt(currentTimeSplitting[2]);

                        int flag=0;
                        if(s_hours>c_hours)
                        {
                            flag=1;
                        }
                        else if(s_hours==c_hours)
                        {
                            if(s_min>c_min)
                            {
                                flag=1;
                            }
                            else if(s_min==c_min)
                            {
                                if(s_sec>c_sec)
                                {
                                    flag=1;
                                }
                                else
                                {
                                    flag=0;
                                }
                                flag=0;
                            }
                            else
                            {
                                flag=0;
                            }
                        }
                        else
                        {
                            flag=0;
                        }

                        if(flag==1)
                        {
                            txtOpen.setText("Open Bet");
                            txtClose.setText("Close Bet");
                        }
                        else if(flag==0)
                        {
                            txtClose.setText("Close Bet");
                            txtOpen.setVisibility(View.GONE);
                        }

//String data="Hours : "+s_hours+"\n min : "+s_min+"\n sec : "+s_sec+"\n hours :"+c_hours+"\n minn :"+c_min+"\n seccc :"+c_sec;

                        progressDialog.dismiss();
                       // Toast.makeText(DoublePanaActivity.this,data,Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(DoublePanaActivity.this,"Something ",Toast.LENGTH_LONG).show();

                    }
                }
                catch(Exception ex)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DoublePanaActivity.this,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();

                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DoublePanaActivity.this,"Something erong",Toast.LENGTH_LONG).show();


                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("id",m_id);

                // params.put("phonepay",phonepaynumber);


                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(DoublePanaActivity.this);
        requestQueue.add(stringRequest);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(DoublePanaActivity.this);
        //private int stat=0;
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(DoublePanaActivity.this,String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),DoublePanaActivity.this);
        }
        else
        {
            stat=2;

            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),DoublePanaActivity.this);
            details.setBetDateDay(DoublePanaActivity.this,m_id,btnGameType,progressDialog);

        }
    }





}
