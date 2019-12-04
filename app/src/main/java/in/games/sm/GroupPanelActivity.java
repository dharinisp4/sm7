package in.games.sm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;

import in.games.sm.Objects.sp_input_data;
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

public class GroupPanelActivity extends MyBaseActivity{

    private int stat=0;
    static String URL_Login = "https://malamaal.anshuwap.com/restApi/groupactivity.php";
    private Button btnAdd,btnSave,btnType,btnGameType;
    String p,g;
    private TextView txtDigit,txtPoint,txtType;
    private TableLayout t1;
    TextView bt_back;
    private TableRow tr;
    TextView btnDelete;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
    private Dialog dialog;
    private TextView txtOpen,txtClose;
    private final String[] d1={"123","178","137","678","236","367","128","268"};
    private final String[] d2={"240","245","790","470","290","579","259","457"};
    private final String[] d3={"100","150","600","556","155","560"};
    private final String[] d4={"330","880","358","380","588","335"};
    private final String[] d5={"119","169","146","466","114","669"};
    private final String[] d6={"349","344","899","448","489","399"};
    private final String[] d7={"777","222","277","227"};
    private final String[] d8={"246","129","179","147","124","679","467","269"};
    private final String[] d9={"480","340","345","890","390","458","589","359"};
    private final String[] d10={"336","133","138","188","688","368"};
    private final String[] d11={"110","660","566","115","156","160"};
    private final String[] d12={"200","255","557","700","570","250"};
    private final String[] d13={"377","237","223","778","278","228"};
    private final String[] d14={"444","999","449","499"};
    private final String[] d15={"139","189","134","148","468","346","369","689"};
    private final String[] d16={"157","170","567","120","670","125","260","256"};
    private final String[] d17={"238","233","337","378","788","288"};
    private final String[] d18={"300","355","800","558","580","350"};
    private final String[] d19={"490","990","599","445","440","459"};
    private final String[] d20={"247","477","279","779","229","224"};
    private final String[] d21={"666","111","116","166"};

    private final String[] d22={"478","248","234","789","289","347","239","379"};
    private final String[] d23={"135","130","680","180","158","568","360","356"};
    private final String[] d24={"400","455","450","900","559","590"};
    private final String[] d25={"220","225","770","270","577","257"};
    private final String[] d26={"149","144","446","469","699","199"};
    private final String[] d27={"112","126","117","167","667","266"};
    private final String[] d28={"333","888","388","338"};

    private final String[] d29={"258","235","578","780","230","280","357","370"};
    private final String[] d30={"140","456","159","145","460","569","190","690"};
    private final String[] d31={"249","299","799","479","447","244"};
    private final String[] d32={"122","177","267","127","677","226"};
    private final String[] d33={"113","136","168","118","668","366"};
    private final String[] d34={"348","334","389","339","488","889"};
    private final String[] d35={"555","000","550","500"};

    private final String[][] main=new String[][]{d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27
    ,d28,d29,d30,d31,d32,d33,d34,d35};
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;
   // private ListView lstView;
   AutoCompleteTextView editText;
    private String game_id;
    private String m_id;
    private TextView txtWallet_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_panel);

        final String dashName=getIntent().getStringExtra("matkaName");
        game_id=getIntent().getStringExtra("game_id");
        m_id=getIntent().getStringExtra("m_id");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        txtDigit=(TextView)findViewById(R.id.dgt);
        txtPoint=(TextView)findViewById(R.id.pnt);
        txtType=(TextView)findViewById(R.id.type);
        btnDelete=(TextView) findViewById(R.id.del);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
       editText=(AutoCompleteTextView) findViewById(R.id.etSingleDigit);
        progressDialog=new ProgressDialog(GroupPanelActivity.this);
       // lstView=findViewById(R.id.list);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        txtMatka.setSelected(true);

        bt_back=(TextView)findViewById(R.id.txtBack);


//        final AutoCompleteTextView editText=findViewById(R.id.etSingleDigit);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(GroupPanelActivity.this,android.R.layout.simple_list_item_1,sp_input_data.panel_group_array);
        editText.setAdapter(adapter);
        t1=(TableLayout)findViewById(R.id.tblLayout);
        t1.setColumnStretchable(0,true);

        t1.setColumnStretchable(1,true);
        t1.setColumnStretchable(2,true);
        t1.setColumnStretchable(3,true);
        t1.removeAllViews();
        txtMatka.setText(dashName.toString()+"- Group Panel Board");

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details.setDateAndBetTpe(GroupPanelActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);
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
                    errorMessageDialog(GroupPanelActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(GroupPanelActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();

            }
        });

        btnAdd=(Button)findViewById(R.id.digit_add);
        btnSave=(Button)findViewById(R.id.digit_save);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                t1.removeAllViews();
                String bet=btnType.getText().toString();
                String dData=editText.getText().toString().trim();
                if(bet.equals("Select Type"))
                {
                    String message="select bet type";
                    errorMessageDialog(GroupPanelActivity.this,message);
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
                else if(dData.length()!=3)
                {
                    editText.setError("Please enter digitin right format");
                    editText.requestFocus();
                    return;
                }
                else if(!Arrays.asList(sp_input_data.panel_group_array).contains(dData))
                {
                    errorMessageDialog(GroupPanelActivity.this,"Invalid Panna");
                    editText.setText("");
                    etPoints.setText("");
                    editText.requestFocus();

                }


                else {
                    int pints = Integer.parseInt(etPoints.getText().toString().trim());
                    if (pints < 10) {
                        //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                        etPoints.setError("Minimum Biding amount is 10");
                        etPoints.requestFocus();
                        return;


                    } else {

//
//                    arrayList.clear();
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




                        int key = -1;
                        boolean st = false;
                        final String d = editText.getText().toString();
                        p = etPoints.getText().toString();
                        g = btnGameType.getText().toString();

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

                                //         Toast.makeText(GroupPanelActivity.this,"exist"+key,Toast.LENGTH_LONG).show();


                                ArrayList<String> list = new ArrayList<String>();
                                for (int k = 0; k <= main[key].length - 1; k++) {
                                    // progressDialog.show();
                                    //list.add(main[key][k].toString());
//                                 arrayList.add(new SingleDigitObjects(main[key][k].toString(),p,th));
                                    setTableData(main[key][k], p, th);
                                    //arrayList.clear();
                                }


                                // Toast.makeText(GroupPanelActivity.this,"Data in j: "+list,Toast.LENGTH_LONG).show();
                                //  progressDialog.dismiss();
                                break;

                            }


                        }
                        if (st == false) {
                            Toast.makeText(GroupPanelActivity.this, "not exist ", Toast.LENGTH_LONG).show();
                            //progressDialog.dismiss();
                        }

                        // arrayList.clear();
//                    Toast.makeText(GroupPanelActivity.this,"exist"+key,Toast.LENGTH_LONG).show();
//                    arrayList.add(new SingleDigitObjects(p,d,th));
//                    adapter1.notifyDataSetChanged();

                        editText.setText("");
                        etPoints.setText("");

                        btnType.setText("Select Type");
                    }

                    //  arrayList.clear();
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
                    errorMessageDialog(GroupPanelActivity.this,message);
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
                            Toast.makeText(GroupPanelActivity.this, "insufficient amount", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            btnSave.setEnabled(false);
                          //  details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,GroupPanelActivity.this,userid,asd);
                            updateWalletAmount(GroupPanelActivity.this, jsonArray, progressDialog,dashName,m_id);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,SingleDigitActivity.this);
                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    } catch (Exception ex) {
                        Toast.makeText(GroupPanelActivity.this, "Err" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(GroupPanelActivity.this);
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(GroupPanelActivity.this,String.valueOf(m),btnType,progressDialog);
            // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),GroupPanelActivity.this);
        }
        else
        {
            stat=2;
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),GroupPanelActivity.this);
            details.setBetDateDay(GroupPanelActivity.this,m_id,btnGameType,progressDialog);

        }

    }

    private void setTableData(String datum, final String p, String g) {


        tr=new TableRow(GroupPanelActivity.this);
        txtDigit=new TextView(GroupPanelActivity.this);
        txtPoint=new TextView(GroupPanelActivity.this);
        txtType=new TextView(GroupPanelActivity.this);
        btnDelete=new TextView(GroupPanelActivity.this);

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
