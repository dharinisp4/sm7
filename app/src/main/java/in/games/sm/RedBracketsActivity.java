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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class RedBracketsActivity extends MyBaseActivity {

   private final String[] red_bracket={"00","11","22","33","44","55","66","77","88","99","05","16","27","38","49","50",
           "61","72","83","94"};

    TextView  txtClose,txtOpen;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;

    private TableLayout t1;
    private int val_p=0;
    private TableRow tr;
    TextView btnDelete;
    private TextView txtDigit,txtPoint,txtType;
    //    private TableRow tr;
    TextView bt_back;
    TextView txtMatka;
    AutoCompleteTextView etDgt;
    private EditText etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount,txtdigit;
    private RelativeLayout relativeLayout;
    private Dialog dialog;
    private CheckBox chkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_brackets);

        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");

        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new ProgressDialog(RedBracketsActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        relativeLayout=findViewById(R.id.relativeLayout4);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        txtMatka.setSelected(true);
        chkBox=findViewById(R.id.chk_bx);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtDigit=(TextView)findViewById(R.id.txtSingleDigit);
        etDgt=(AutoCompleteTextView)findViewById(R.id.etSingleDigit);
        etPoints=(EditText)findViewById(R.id.etPoints);

        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setText(dashName.toString()+"- Red Bracket Board");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                details.setDateAndBetTpeTo(RedBracketsActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
            }
        });
        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        t1=(TableLayout)findViewById(R.id.tblLayout);
        t1.setColumnStretchable(0,true);

        t1.setColumnStretchable(1,true);
        t1.setColumnStretchable(2,true);
        t1.setColumnStretchable(3,true);
        t1.removeAllViews();
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(RedBracketsActivity.this,android.R.layout.simple_list_item_1,red_bracket);
        etDgt.setAdapter(adapter);
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true)
                {
                    relativeLayout.setVisibility(View.GONE);
                    t1.removeAllViews();
//                    txtdigit.setVisibility(View.INVISIBLE);
//                    etDgt.setVisibility(View.INVISIBLE);
                }
                else
                {
                    relativeLayout.setVisibility(View.VISIBLE);
                    t1.removeAllViews();
                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_b=btnGameType.getText().toString().trim();
                String b[]=date_b.split(" ");
                String vt=b[3];

                if(vt.equals("Open"))
                {

                    if(chkBox.isChecked()==true)
                    {
                        String points=etPoints.getText().toString().trim();
                        if(TextUtils.isEmpty(points))
                        {
                            etPoints.setError("Enter Some Points");
                            etPoints.requestFocus();
                            return;
                        }
                        else
                        {
                            int pints = Integer.parseInt(etPoints.getText().toString().trim());
                            if (pints < 10) {
                                //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                                etPoints.setError("Minimum Biding amount is 10");
                                etPoints.requestFocus();
                                return;


                            } else {
                                for (int i = 0; i <= red_bracket.length - 1; i++) {
                                    setOddData(red_bracket[i], points, "close");

                                }

                                etPoints.setText("");
                                etPoints.requestFocus();
                            }
                        }
                    }
                    else
                    {
                        String digits=etDgt.getText().toString().trim();
                        String points=etPoints.getText().toString().trim();

                        if(TextUtils.isEmpty(digits))
                        {
                            etDgt.setError("Enter Some Digits");
                            etDgt.requestFocus();
                            return;
                        }
                        else if(TextUtils.isEmpty(points))
                        {
                            etPoints.setError("Enter Some Points");
                            etPoints.requestFocus();
                            return;
                        }
                        else if(!Arrays.asList(red_bracket).contains(digits))
                        {
                            errorMessageDialog(RedBracketsActivity.this,"Invalid Jodi");
                            return;
                        }
                        else
                        {
                            int pints = Integer.parseInt(points);
                            if (pints < 10) {
                                //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                                etPoints.setError("Minimum Biding amount is 10");
                                etPoints.requestFocus();
                                return;


                            } else {

                                setOddData(digits, points, "close");
                                etPoints.setText("");
                                etDgt.setText("");
                                etDgt.requestFocus();
                            }
                        }
                    }
                }
                else if(vt.equals("Close"))
                {
                    String message="Betting Is Closed For Today Select Another Date";
                    errorMessageDialog(RedBracketsActivity.this,message);
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
                    errorMessageDialog(RedBracketsActivity.this,message);
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
                            String message="insufficient amount";
                            errorMessageDialog(RedBracketsActivity.this,message);
                            return;
//                            Toast.makeText(RedBracketsActivity.this, "insufficient amount", Toast.LENGTH_LONG).show();
//                            return;
                        } else {
                            btnSave.setEnabled(false);                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,RedBracketsActivity.this,userid,asd);
                            updateWalletAmount(RedBracketsActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(RedBracketsActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(RedBracketsActivity.this);
        details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),RedBracketsActivity.this);
        details.setBetDateDayTo(RedBracketsActivity.this,m_id,btnGameType,progressDialog);
    }

    private void setOddData(String s, final String p, String th) {


        tr=new TableRow(RedBracketsActivity.this);
        txtDigit=new TextView(RedBracketsActivity.this);
        txtPoint=new TextView(RedBracketsActivity.this);
        txtType=new TextView(RedBracketsActivity.this);
        btnDelete=new TextView(RedBracketsActivity.this);

        btnDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.del_btn,0,0,0);
        TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams();
        layoutParams.setMargins(0,10,0,10);
        tr.setLayoutParams(layoutParams);
        //    tr.setElevation(20);
        // tr.setDividerPadding(20);
        tr.setPadding(10,10,10,10);
        txtPoint.setText(p);
        txtDigit.setText(s.toString());
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

                View row=(View)v.getParent();
                ViewGroup container= ((ViewGroup)row.getParent());
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




    }

}
