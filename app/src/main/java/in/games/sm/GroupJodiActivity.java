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
import in.games.sm.Objects.sp_input_data;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class GroupJodiActivity extends MyBaseActivity {

    private Button btnAdd,btnSave,btnGameType;
    String p,g;
    private TextView txtDigit,txtPoint,txtType;
    private TableLayout t1;
    TextView bt_back;
    private TableRow tr;
    TextView btnDelete;
    private Dialog dialog;
    private TextView txtOpen,txtClose;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;


    private final String[] d1={"11","16","61","66"};
    private final String[] d2={"12","21","17","71","26","62","67","76"};
    private final String[] d3={"13","31","18","81","36","63","68","86"};
    private final String[] d4={ "14","41","19","91","46","64","69","96"};
    private final String[] d5={"15","51","10","01","56","65","60","06"};
    private final String[] d6={"22","27","72","77"};
    private final String[] d7={"23","32","28","82","37","73","78","87"};
    private final String[] d8={"24","42","29","92","47","74","79","97"};
    private final String[] d9={"25","52","20","02","57","75","70","07"};
    private final String[] d10={"33","38","83","88"};
    private final String[] d11={"34","43","39","93","48","84","89","98"};
    private final String[] d12={"35","53","30","03","58","85","80","08"};
    private final String[] d13={"44","49","94","99"};
    private final String[] d14={"45","54","40","04","59","95","90","09"};
    private final String[] d15={"55","50","05","00"};

    private final String[][] main=new String[][]{d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15};
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;

    AutoCompleteTextView editText;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_jodi);

        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        etPoints=(EditText)findViewById(R.id.etPoints);

        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        editText=(AutoCompleteTextView) findViewById(R.id.etSingleDigit);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        progressDialog=new ProgressDialog(GroupJodiActivity.this);
        bt_back=(TextView)findViewById(R.id.txtBack);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        txtMatka.setSelected(true);

//        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(GroupJodiActivity.this,android.R.layout.simple_list_item_1,sp_input_data.group_jodi_array);
        editText.setAdapter(adapter);


        txtMatka.setText(dashName.toString()+"- Group Jodi Board");



        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        t1=(TableLayout)findViewById(R.id.tblLayout);
        t1.setColumnStretchable(0,true);

        t1.setColumnStretchable(1,true);
        t1.setColumnStretchable(2,true);
        t1.setColumnStretchable(3,true);
        t1.removeAllViews();
//        txtMatka.setText(dashName.toString()+"- Group Panel Board");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpeTo(GroupJodiActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String bet=btnType.getText().toString();
                String date_b=btnGameType.getText().toString().trim();
                String b[]=date_b.split(" ");
                String vt=b[3];

                if(vt.equals("Open")) {

                    String dData = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        editText.setError("Please enter any digit");
                        editText.requestFocus();
                        return;
                    } else if (TextUtils.isEmpty(etPoints.getText().toString())) {
                        etPoints.setError("Please enter some point");
                        etPoints.requestFocus();
                        return;

                    } else {
                        int pints = Integer.parseInt(etPoints.getText().toString().trim());
                        if (pints < 10) {
                            //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                            etPoints.setError("Minimum Biding amount is 10");
                            etPoints.requestFocus();
                            return;


                        } else {
                            String th = null;

                            th = "close";
                            int key = -1;
                            boolean st = false;
                            String d = editText.getText().toString();
                            String p = etPoints.getText().toString();
                            String g = btnGameType.getText().toString();
                            boolean sr = false;

                            for (int i = 0; i <= main.length - 1; i++) {
                                for (int j = 0; j <= main[i].length - 1; j++) {
                                    if (main[i][j].contains(d)) {
                                        key = i;
                                        st = true;

                                        break;
                                    }

                                    // Toast.makeText(GroupPanelActivity.this,"Data in j: "+main[i][j],Toast.LENGTH_LONG).show();
                                }
                                if (st == true) {

                                     t1.removeAllViews();
//                            Toast.makeText(GroupJodiActivity.this,"exist"+key,Toast.LENGTH_LONG).show();


                                    ArrayList<String> list = new ArrayList<String>();
                                    for (int k = 0; k <= main[key].length - 1; k++) {
                                        // progressDialog.show();
                                        //list.add(main[key][k].toString());
                                        setTableData(main[key][k], p, th);

                                        //arrayList.clear();
                                    }


                                    // Toast.makeText(GroupPanelActivity.this,"Data in j: "+list,Toast.LENGTH_LONG).show();
                                    //  progressDialog.dismiss();
                                    break;

                                }


                            }
                            if (st == false) {
                                Toast.makeText(GroupJodiActivity.this, "not exist ", Toast.LENGTH_LONG).show();
                                // progressDialog.dismiss();
                            }

                            editText.setText("");
                            etPoints.setText("");

                            editText.requestFocus();
                        }
                        //  arrayList.clear();
                    }
                }
                else
                {
                    String message="Betting Is Closed For Today Select Another Date";
                    errorMessageDialog(GroupJodiActivity.this,message);
                    return;
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
                    errorMessageDialog(GroupJodiActivity.this,message);
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
                            errorMessageDialog(GroupJodiActivity.this,"Insufficient Amount");
                            return;
                        } else {

                            btnSave.setEnabled(false);
//                            Toast.makeText(GroupJodiActivity.this,""+jsonArray,Toast.LENGTH_LONG).show();
                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,GroupJodiActivity.this,userid,asd);
                           updateWalletAmount( GroupJodiActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(GroupJodiActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(GroupJodiActivity.this);
        details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),GroupJodiActivity.this);
        details.setBetDateDayTo(GroupJodiActivity.this,m_id,btnGameType,progressDialog);
    }

    private void setTableData(String datum, final String p, String g) {


        tr=new TableRow(GroupJodiActivity.this);
        txtDigit=new TextView(GroupJodiActivity.this);
        txtPoint=new TextView(GroupJodiActivity.this);
        txtType=new TextView(GroupJodiActivity.this);
        btnDelete=new TextView(GroupJodiActivity.this);

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

editText.setText("");
etPoints.setText("");
editText.requestFocus();


    }




}
