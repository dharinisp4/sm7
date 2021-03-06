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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import in.games.sm.Adapter.details;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.errorMessageDialog;
import static in.games.sm.Adapter.details.updateWalletAmountSangum;

public class HalfSangamActivity extends MyBaseActivity {

    private TextView txtDigit,txtPoint,txtType,btnDelete;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    int val_p=0;
    private final String[] singlePaana={"137","128","146","236","245","290","380","470","489","560","678","579",
            "119","155","227","335","344","399","588","669","777","100","129","138","147","156","237","246",
            "345","390","480","570","589","679","110","228","255","336","499","660","778","200","444",
            "120","139","148","157","238","247","256","346","490","580","670","689","779","788","300","111",
            "130","149","158","167","239","248","257","347","356","590","680","789","699","770","400","888",
            "140","159","168","230","249","258","267","348","357","456","690","780","113","122","177","339",
            "366","447","799","889","500","555",
            "123","150","169","178","240","259","268","349","358","367","457","790","114","277","330","448",
            "466","556","880","899","600","222",
            "124","160","179","250","269","278","340","359","368","458","467","890","115","133","188","223","377",
            "449","557","566","700","999",
            "125","134","170","189","260","279","350","369","378","459","468","567","116","224","233","288","440",
            "477","558","666", "126","135","180","235","270","289","360","379","450","469","478",
            "568","117","144","199","225","388","559","577","667","900","333",
            "127","136","145","190","234","280","370","389","460","479","569","578","118","226","244","299","334","488",
            "668","677","000","550",
            "688",
            "166","229","337","355","445","599","112","220","266",
            "338","446","455",
            "800","990"};


    RelativeLayout rlLayout_open_digit,rlLayout_open_panna,rlLayout_close_digit,rlLayout_close_panna;
    private Button btnChange;
    AutoCompleteTextView etOpenPanna,etClosePanna;
    private Button btnAdd,btnSave,btnGameType;
    TextView bt_back;
    private TableLayout t1;
    private TableRow tr;
    TextView txtMatka;

    String matName="";

    private EditText etPoints,etOpenDigit,etCloseDigit;
    ProgressDialog progressDialog;
    private ListView lstView;

    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;
    private Dialog dialog;


    private static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half_sangam);

        btnChange=(Button)findViewById(R.id.btnChange);
        rlLayout_open_digit=(RelativeLayout)findViewById(R.id.relativeLayout4);
        rlLayout_open_panna=(RelativeLayout)findViewById(R.id.relative_c_Layout4);
        rlLayout_close_panna=(RelativeLayout)findViewById(R.id.relativeLayout11);
        rlLayout_close_digit=(RelativeLayout)findViewById(R.id.relative_c_Layout11);
        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);
        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        etPoints=(EditText)findViewById(R.id.etPoints);
        etCloseDigit=(EditText)findViewById(R.id.et_c_closedigit);
        etOpenDigit=(EditText)findViewById(R.id.etSingleDigit);
        etOpenPanna=(AutoCompleteTextView)findViewById(R.id.et_c_openpanna);
        etClosePanna=(AutoCompleteTextView)findViewById(R.id.et_ClosePanna);
         btnAdd=(Button)findViewById(R.id.digit_add);
        bt_back=(TextView)findViewById(R.id.txtBack);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        progressDialog=new ProgressDialog(HalfSangamActivity.this);
        txtMatka.setSelected(true);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(HalfSangamActivity.this,android.R.layout.simple_list_item_1,singlePaana);
        etOpenPanna.setAdapter(adapter);
        etClosePanna.setAdapter(adapter);

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

                details.setDateAndBetTpeTo(HalfSangamActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
            }
        });

        txtMatka.setText(dashName.toString()+"- Half Sangum Board");
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rlLayout_open_digit.getVisibility()==View.VISIBLE)
                {
                    rlLayout_open_digit.setVisibility(View.INVISIBLE);
                    rlLayout_close_panna.setVisibility(View.INVISIBLE);
                    rlLayout_close_digit.setVisibility(View.VISIBLE);
                    rlLayout_open_panna.setVisibility(View.VISIBLE);
                    t1.removeAllViews();
                }
                else if(rlLayout_close_digit.getVisibility()==View.VISIBLE)
                {

                    rlLayout_open_digit.setVisibility(View.VISIBLE);
                    rlLayout_close_panna.setVisibility(View.VISIBLE);
                    rlLayout_close_digit.setVisibility(View.INVISIBLE);
                    rlLayout_open_panna.setVisibility(View.INVISIBLE);
                    t1.removeAllViews();
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int er = t1.getChildCount();
                if (er <= 0) {
                    String message = "Please Add Some Bids";
                    errorMessageDialog(HalfSangamActivity.this, message);
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
                            String d_all[]=asd.split("-");
                            String d0=d_all[0].toString();
                            String d1=d_all[1].toString();

                            String asd1 = p.getText().toString();
                            String asd2 = t.getText().toString();
                            int b = 1;
                            if (asd2.equals("Half sangum")) {
                                b = 0;
                            } else {
                                b = 0;
                            }


                            amt = amt + Integer.parseInt(asd1);



                            char quotes='"';
                            list_digits.add(quotes+d0+quotes);
                            list_points.add(asd1);
                            list_type.add(quotes+d1+quotes);


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
                           errorMessageDialog(HalfSangamActivity.this,"Insufficient Amount");
                           return;
                        } else {

                            btnSave.setEnabled(false);
                            //    Toast.makeText(HalfSangamActivity.this,""+jsonArray,Toast.LENGTH_LONG).show();
try
{
    updateWalletAmountSangum( HalfSangamActivity.this, jsonArray, progressDialog,dashName,m_id);
}
catch (Exception err)
{
    Toast.makeText(HalfSangamActivity.this, "Err" + err.getMessage(), Toast.LENGTH_LONG).show();
}

                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(HalfSangamActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date_b = btnGameType.getText().toString().trim();
                String b[] = date_b.split(" ");
                String vt = b[3];

                if (vt.equals("Open")) {

                    if (rlLayout_open_digit.getVisibility() == View.VISIBLE) {
                        String open_digit = etOpenDigit.getText().toString().trim();
                        String close_panna = etClosePanna.getText().toString().trim();
                        String points = etPoints.getText().toString().trim();

                        if (TextUtils.isEmpty(open_digit)) {
                            etOpenDigit.setError("Enter digits");
                            etOpenDigit.requestFocus();
                            return;
                        } else if (TextUtils.isEmpty(close_panna)) {
                            etClosePanna.setError("Enter panna");
                            etClosePanna.requestFocus();
                            return;
                        } else if (TextUtils.isEmpty(points)) {
                            etPoints.setError("Enter points");
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
                                setTableRowforOPenDigit(open_digit, close_panna, points);
                                etOpenDigit.requestFocus();
                                clearCtrls();

                            }
                        }

                    } else if (rlLayout_close_digit.getVisibility() == View.VISIBLE) {
                        String close_digit = etCloseDigit.getText().toString().trim();
                        String open_panna = etOpenPanna.getText().toString().trim();
                        String points = etPoints.getText().toString().trim();

                        if (TextUtils.isEmpty(close_digit)) {
                            etCloseDigit.setError("Enter digits");
                            etCloseDigit.requestFocus();
                            return;
                        } else if (TextUtils.isEmpty(open_panna)) {
                            etOpenPanna.setError("Enter panna");
                            etOpenPanna.requestFocus();
                            return;
                        } else if (TextUtils.isEmpty(points)) {
                            etPoints.setError("Enter points");
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
                                setTableRowforCloseDigit(close_digit, open_panna, points);
                                etCloseDigit.requestFocus();
                                clearCtrls();

                            }
                        }


                    }

                    //   Toast.makeText(HalfSangamActivity.this,""+status,Toast.LENGTH_LONG).show();
                } else if (vt.equals("Close")) {
                    String message = "Betting Is Closed For Today Select Another Date";
                    errorMessageDialog(HalfSangamActivity.this, message);
                    return;
                }
            }
        });
    }



    private void setTableRowforCloseDigit(String close_digit, String open_panna,final String points) {

        tr=new TableRow(HalfSangamActivity.this);
        txtDigit=new TextView(HalfSangamActivity.this);
        txtPoint=new TextView(HalfSangamActivity.this);
        txtType=new TextView(HalfSangamActivity.this);
        btnDelete=new TextView(HalfSangamActivity.this);

        btnDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.del_btn,0,0,0);
        TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams();
        layoutParams.setMargins(0,10,0,10);
        tr.setLayoutParams(layoutParams);
        //    tr.setElevation(20);
        // tr.setDividerPadding(20);
        tr.setPadding(10,10,10,10);
        txtPoint.setText(points);
        txtDigit.setText(open_panna+"-"+close_digit);
        txtType.setText("Half Sangum");
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

                int po=Integer.parseInt(points);
                int tot_pnt=we*po;


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

        int po=Integer.parseInt(points);
        int tot_pnt=we*po;


        btnSave.setText("(BIDS=" + we + ")(Points=" + val_p + ")");
        val_p=0;



    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(HalfSangamActivity.this);
        details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),HalfSangamActivity.this);
        details.setBetDateDayTo(HalfSangamActivity.this,m_id,btnGameType,progressDialog);
    }

    private void setTableRowforOPenDigit(final String open_digit, final String close_panna, final String p) {

        tr=new TableRow(HalfSangamActivity.this);
        txtDigit=new TextView(HalfSangamActivity.this);
        txtPoint=new TextView(HalfSangamActivity.this);
        txtType=new TextView(HalfSangamActivity.this);
        btnDelete=new TextView(HalfSangamActivity.this);

        btnDelete.setCompoundDrawablesWithIntrinsicBounds(R.drawable.del_btn,0,0,0);
        TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams();
        layoutParams.setMargins(0,10,0,10);
        tr.setLayoutParams(layoutParams);
        //    tr.setElevation(20);
        // tr.setDividerPadding(20);
        tr.setPadding(10,10,10,10);
        txtPoint.setText(p);
        txtDigit.setText(open_digit+"-"+close_panna);
        txtType.setText("Half Sangum");
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

                int po=Integer.parseInt(p);
                int tot_pnt=we*po;


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

        int po=Integer.parseInt(p);
        int tot_pnt=we*po;


        btnSave.setText("(BIDS=" + we + ")(Points=" + val_p + ")");
        val_p=0;





    }

    public void clearCtrls()
    {
        etOpenDigit.setText("");
        etCloseDigit.setText("");
        etClosePanna.setText("");
        etOpenPanna.setText("");
        etPoints.setText("");

    }
}
