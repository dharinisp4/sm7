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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.games.sm.Adapter.SingleDigitAdapter;
import in.games.sm.Adapter.details;
import in.games.sm.Model.SingleDigitObjects;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class SingleDigitActivity extends MyBaseActivity {
    static String URL_REGIST="https://malamaal.anshuwap.com/restApi/singledigit.php";
    private Button btnAdd,btnSave,btnType,btnGameType;
    private TextView txtDigit,txtPoint,txtType;
    private TableLayout t1;
    TextView btnDelete;
    private TableRow tr;
    int we_amount=0;
    int tot_amt=0;

    TableRow tr1 ;

     TextView txtD1,txtCountBids,txtAmount,txtBeforeAmount,txtAfterAmount;
     TextView txtP1;
     TextView  txtT1;


    TextView bt_back;
    TextView txtMatka;

  //  Button btnDialog,btnDialogCancel;

    private int stat=0;
    private int val_p=0;

    ArrayList list_digits = new ArrayList();
    ArrayList list_dialog_digits=new ArrayList();
    ArrayList list_type = new ArrayList();
    ArrayList list_points = new ArrayList();
    ListView listView_table;


    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private EditText etDgt,etPnt;
    String matName="";
    ProgressDialog progressDialog;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;



    ArrayList<SingleDigitObjects> arrayList;
    SingleDigitAdapter adapter;
    private Dialog dialog;
    private TextView txtOpen,txtClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_digit);

        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        txtDigit=(TextView)findViewById(R.id.dgt);
        txtPoint=(TextView)findViewById(R.id.pnt);
        txtType=(TextView)findViewById(R.id.type);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        //btnDialog=(Button)findViewById(R.id.digit_dialog);

        txtMatka.setSelected(true);


        bt_back=(TextView)findViewById(R.id.txtBack);

        progressDialog=new ProgressDialog(SingleDigitActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

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
      txtMatka.setText(dashName.toString()+"- Single Digit Board");


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
                    errorMessageDialog(SingleDigitActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(SingleDigitActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();


            }
        });

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpe(SingleDigitActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
            }
        });

        etDgt=(EditText)findViewById(R.id.etSingleDigit);
        etPnt=(EditText)findViewById(R.id.etPoints);

       // t1.setGravity(Gravity.CENTER_HORIZONTAL);


        //tr=(TableRow)findViewById(R.id.tableRow1);

         btnAdd=(Button)findViewById(R.id.digit_add);
         btnSave=(Button)findViewById(R.id.digit_save);








        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tr=new TableRow(SingleDigitActivity.this);
                txtDigit=new TextView(SingleDigitActivity.this);
                txtPoint=new TextView(SingleDigitActivity.this);
                txtType=new TextView(SingleDigitActivity.this);
                String bet=btnType.getText().toString();

                if(bet.equals("Select Type"))
                {
                    String message="select bet type";
                    errorMessageDialog(SingleDigitActivity.this,message);return;
                }
                else if(TextUtils.isEmpty(etDgt.getText().toString()))
                {
                   etDgt.setError("Please enter any digit");
                   etDgt.requestFocus();
                   return;
                }
               else if(TextUtils.isEmpty(etPnt.getText().toString()))
                {
                    etPnt.setError("Please enter some point");
                    etPnt.requestFocus();
                    return;

                }

                else {

                    int pints = Integer.parseInt(etPnt.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPnt.setError("Minimum Biding amount is 10");
                        etPnt.requestFocus();
                        return;


                    } else {
                        //String bet= btnType.getText().toString().trim();
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


                        String d = etDgt.getText().toString();
                        final String p = etPnt.getText().toString();
                        String g = btnGameType.getText().toString();
                        String type = btnType.getText().toString().trim();
                        tr = new TableRow(SingleDigitActivity.this);
                        txtDigit = new TextView(SingleDigitActivity.this);
                        txtPoint = new TextView(SingleDigitActivity.this);
                        txtType = new TextView(SingleDigitActivity.this);
                        btnDelete = new TextView(SingleDigitActivity.this);
                        bt_back = (TextView) findViewById(R.id.txt_back);
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

                        we_amount = t1.getChildCount();


                        for(int i=0; i<we_amount;i++)
                        {
                            TableRow tableRow=(TableRow)t1.getChildAt(i);
                            TextView textPoints=(TextView)tableRow.getChildAt(1);
                            String v_l=textPoints.getText().toString().trim();

                            val_p=val_p+(Integer.parseInt(v_l));
                            //Toast.makeText(SinglePannaActivity.this,""+val_p,Toast.LENGTH_LONG).show();


                        }
                        int points = Integer.parseInt(p);
                        int tot_pnt = we_amount * points;
                        tot_amt=val_p;
                        btnSave.setText("(BIDS=" + we_amount + ")(Points=" + val_p + ")");
                        val_p=0;

                        etPnt.setText("");
                        etDgt.setText("");
                        etDgt.requestFocus();


                    }
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list_digits.clear();
                list_points.clear();
                list_type.clear();
                list_dialog_digits.clear();
                int er=t1.getChildCount();
                if(er<=0)
                {
                    String message="Please Add Some Bids";
                    errorMessageDialog(SingleDigitActivity.this,message);
                    return;
                }
                else {
                    try {
                        int amt = 0;
                        int rows = t1.getChildCount();



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
                            list_dialog_digits.add(asd);
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
//
//
////                     Toast.makeText(OddEvenActivity.this,"data"+jsonArray,Toast.LENGTH_LONG).show();

                        //      Object o1=jsonArray_digits;

                        String w = txtWallet_amount.getText().toString().trim();
                        int wallet_amount = Integer.parseInt(w);
                        if (wallet_amount < amt) {
                           errorMessageDialog(SingleDigitActivity.this,"Insufficient Amount");
                           return;
                        } else {
                            btnSave.setEnabled(false);
                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this,userid,asd);
                    //
                                    updateWalletAmount(SingleDigitActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(SingleDigitActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onStart() {
        super.onStart();
     //   setSessionTimeOut(SingleDigitActivity.this);
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(SingleDigitActivity.this,String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
           // private int stat=0;
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),SingleDigitActivity.this);
        }
        else
        {
            stat=2;
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),SingleDigitActivity.this);
            details.setBetDateDay(SingleDigitActivity.this,m_id,btnGameType,progressDialog);


        }



    }




    public void clearControls()
    {
        etDgt.setText("");
        etPnt.setText("");
        btnType.setText("Select Type");
    }


}
