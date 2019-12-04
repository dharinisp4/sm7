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
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmount;

public class JodiDigitActivity extends MyBaseActivity  {

    private final String[] singlePaana={"11","16","61","66",
            "12","21","17","71","26","62","67","76",
            "13","31","18","81","36","63","68","86",
            "14","41","19","91","46","64","69","96",
            "15","51","10","01","56","65","60","06",
            "22","27","72","77",
            "23","32","28","82","37","73","78","87",
            "24","42","29","92","47","74","79","97",
            "25","52","20","02","57","75","70","07",
            "33","38","83","88",
            "34","43","39","93","48","84","89","98",
            "35","53","30","03","58","85","80","08",
            "44","49","94","99",
            "45","54","40","04","59","95","90","09",
            "55","50","05","00",
            };
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
private int val_p=0;
    private Button btnAdd,btnSave,btnGameType;

    private TextView txtDigit,txtPoint,txtType;

    TextView btnDelete;

    TextView bt_back;
    private TableLayout t1;
    private TableRow tr;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;
    private TextView txtWallet_amount;
    private String game_id;
    private String m_id;
    private Dialog dialog;
    private TextView txtOpen,txtClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jodi_digit);

        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");



        etPoints=(EditText)findViewById(R.id.etPoints);

        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new ProgressDialog(JodiDigitActivity.this);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(JodiDigitActivity.this,android.R.layout.simple_list_item_1,singlePaana);
        editText.setAdapter(adapter);

        bt_back=(TextView)findViewById(R.id.txtBack);

        txtMatka.setSelected(true);
        txtMatka.setText(dashName.toString()+"- Jodi Digit Board");

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details.setDateAndBetTpeTo(JodiDigitActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);

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

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_b = btnGameType.getText().toString().trim();
                String x[] = date_b.split(" ");
                String vt = x[3];

                if (vt.equals("Open")) {


                    int er = t1.getChildCount();
                    if (er <= 0) {
                        String message = "Please Add Some Bids";
                        errorMessageDialog(JodiDigitActivity.this, message);
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


                            String dt = btnGameType.getText().toString().trim();
                            String d[] = dt.split(" ");

                            String c = d[0].toString();
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
                                errorMessageDialog(JodiDigitActivity.this,"Insufficient Amount");
                                return;
                            } else {
                                btnSave.setEnabled(false);
                                //details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,JodiDigitActivity.this,userid,asd);
                                updateWalletAmount(JodiDigitActivity.this, jsonArray, progressDialog,dashName,m_id);
                                //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                                //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                                //  updateWallet(userid,asd);

                                //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                            }

//                  sendOddEvenGameData(jsonArray);


                        } catch (Exception ex) {
                            Toast.makeText(JodiDigitActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }


                } else {
                    String message = "Betting Is Closed For Today Select Another Date";
                    errorMessageDialog(JodiDigitActivity.this, message);
                    return;
                }
            }




        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tr=new TableRow(JodiDigitActivity.this);
                txtDigit=new TextView(JodiDigitActivity.this);
                txtPoint=new TextView(JodiDigitActivity.this);
                txtType=new TextView(JodiDigitActivity.this);
                btnDelete=new TextView(JodiDigitActivity.this);

            //    String bet=btnType.getText().toString();
                String dData=editText.getText().toString().trim();
                 if(TextUtils.isEmpty(editText.getText().toString()))
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

                else if(!Arrays.asList(singlePaana).contains(dData))
                {
                    String message="Invalid Jodi";
                    errorMessageDialog(JodiDigitActivity.this,message);
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
                          String d = editText.getText().toString();
                          final String p = etPoints.getText().toString();
                          String g = btnGameType.getText().toString();
                          String th = null;

                          th="close";

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


//                    String th=btnType.getText().toString();

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
       // setSessionTimeOut(JodiDigitActivity.this);
        details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),JodiDigitActivity.this);
        details.setBetDateDayTo(JodiDigitActivity.this,m_id,btnGameType,progressDialog);

    }


}
