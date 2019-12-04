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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class TriplePanaActivity extends MyBaseActivity  {

    private final String[] triplePanna={"000","777","444","111","888","555","222","999","666","333"};
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtDigit,txtPoint,txtType;
    //  private TableLayout t1;
    TextView btnDelete;
    //    private TableRow tr;
    TextView bt_back;
    private int stat=0;
    int val_p=0;

    private TableLayout t1;
    private TableRow tr;
    TextView txtMatka;
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
        setContentView(R.layout.activity_triple_pana);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new ProgressDialog(TriplePanaActivity.this);
        txtMatka.setSelected(true);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);

        bt_back=(TextView)findViewById(R.id.txtBack);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(TriplePanaActivity.this,android.R.layout.simple_list_item_1,triplePanna);
        editText.setAdapter(adapter);

        txtMatka.setText(dashName.toString()+"- Triple Pana Board");

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpe(TriplePanaActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
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
                    errorMessageDialog(TriplePanaActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(TriplePanaActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();

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



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bet=btnType.getText().toString();

                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                { String message="select bet type";
                    errorMessageDialog(TriplePanaActivity.this,message);
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

                else if(!Arrays.asList(triplePanna).contains(dData))
                {
                    Toast.makeText(TriplePanaActivity.this,"This is invalid paana",Toast.LENGTH_LONG).show();
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


                        tr = new TableRow(TriplePanaActivity.this);
                        txtDigit = new TextView(TriplePanaActivity.this);
                        txtPoint = new TextView(TriplePanaActivity.this);
                        txtType = new TextView(TriplePanaActivity.this);
                        btnDelete = new TextView(TriplePanaActivity.this);
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
                     //   btnType.setText("Select Type");
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
                    errorMessageDialog(TriplePanaActivity.this, message);
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
                            String message="Insufficient Amount";
                            errorMessageDialog(TriplePanaActivity.this,message);
                            return;
                        } else {

                            btnSave.setEnabled(false);
                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,TriplePanaActivity.this,userid,asd);
                          updateWalletAmount( TriplePanaActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(TriplePanaActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(TriplePanaActivity.this);
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(TriplePanaActivity.this,String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),TriplePanaActivity.this);
        }
        else
        {
            stat=2;
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),TriplePanaActivity.this);
            details.setBetDateDay(TriplePanaActivity.this,m_id,btnGameType,progressDialog);
        }




    }


}
