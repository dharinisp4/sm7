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
import in.games.sm.Adapter.details;

import com.rey.material.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.currentDateDay;
import static in.games.sm.Adapter.details.errorMessageDialog;


public class OddEvenActivity extends MyBaseActivity {



    private Button btnAdd,btnSave,btnType,btnGameType;
    private Dialog dialog;
    private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;

    private TextView txt_day;
    private int stat=0;
    private TextView txtDigit,txtPoint,txtType,bt_back,txtWallet_amount;
    private TableLayout t1;
   // static String URL_REGIST="http://jannat.projects.anshuwap.com/restApi/singledigit.php";
    private TableRow tr;
    private String game_id;
    TextView txtMatka;
    private EditText etDgt,etPnt;
    String matName="";
    private EditText etPoints;
    ProgressDialog progressDialog;
    TextView btnDelete;
    CheckBox chkOdd,chkEven;
    private String m_id;
    private String m_type;
//    private Dialog dialog;
    private TextView txtOpen,txtClose;
    String bet_date="";
    private String bet_status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odd_even);

        final String dashName=getIntent().getStringExtra("matkaName");
        m_id=getIntent().getStringExtra("m_id");
        game_id=getIntent().getStringExtra("game_id");
        m_type=getIntent().getStringExtra("m_type");
        etPoints=(EditText)findViewById(R.id.etPoints);
        btnType=(Button)findViewById(R.id.btnBetType);
        btnGameType=(Button)findViewById(R.id.btnBetStatus);
        txtMatka=(TextView)findViewById(R.id.board);
        txtWallet_amount=(TextView)findViewById(R.id.wallet_amount);
        txt_day=(TextView)findViewById(R.id.txt_day);

        progressDialog=new ProgressDialog(OddEvenActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");
        btnType.setText("Select Type");
        chkOdd=findViewById(R.id.oddDigits);
        chkEven=findViewById(R.id.evenDigits);

        currentDateDay(btnGameType,OddEvenActivity.this);


        txtMatka.setText(dashName.toString()+"- Odd/Even Board");

        txtMatka.setSelected(true);
        bt_back=(TextView)findViewById(R.id.txtBack);

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

        chkOdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(chkEven.isChecked())
        {
            chkOdd.setChecked(true);
            chkEven.setChecked(false);
        }
        else
        {
            chkOdd.setChecked(true);
        }

    }
});

chkEven.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(chkOdd.isChecked())
        {
            chkOdd.setChecked(false);
            chkEven.setChecked(true);
        }
        else
        {
            chkEven.setChecked(true);
        }

    }
});

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.removeAllViews();





              String bet= btnType.getText().toString().trim();
                if(bet.equals("Select Type"))
                {
                    String message="Select bet type";
                    errorMessageDialog(OddEvenActivity.this,message);
                    return;
                    //Toast.makeText(OddEvenActivity.this,"Please select bet type",Toast.LENGTH_LONG).show();
                    //return;
                }
               // String dData=editText.getText().toString().trim();
                 else if(TextUtils.isEmpty(etPoints.getText().toString()))
                {
                    etPoints.setError("Please enter some point");
                    etPoints.requestFocus();
                    return;

                }



                else
                {
                    int pints=Integer.parseInt(etPoints.getText().toString().trim());
                    if(pints<10)
                {
                    //  Toast.makeText(OddEvenActivity.this,"",Toast.LENGTH_LONG).show();

                    etPoints.setError("Minimum Biding amount is 10");
                    etPoints.requestFocus();
                    return;


                }
                    else
                    {
                        String th=null;
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


                        String p=etPoints.getText().toString().trim();
                        if(chkOdd.isChecked())
                        {

                            String[] odd={"1","3","5","7","9"};

                            for(int i=0; i<=odd.length-1; i++)
                            {

                                setOddData(odd[i],p,th);
                            }
                        }
                        else if(chkEven.isChecked())
                        {

                            String[] even={"0","2","4","6","8"};


                            for(int i=0; i<=even.length-1; i++)
                            {
                                setOddData(even[i],p,th);
                                // arrayList.add(new SingleDigitObjects(even[i],p,th));
                            }
                        }
                        else
                        {
                            Toast.makeText(OddEvenActivity.this,"Please select any digit type",Toast.LENGTH_LONG).show();
                            return;
                        }
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
                    errorMessageDialog(OddEvenActivity.this,message);
                    return;
                }
                else
                {
                    try
                    {
                        int amt=0;
                        ArrayList list_digits=new ArrayList();
                        ArrayList list_type=new ArrayList();
                        ArrayList list_points=new ArrayList();
                        int rows=t1.getChildCount();
                        Object[][] data=null;
                        ArrayList<Map<String,String>> dataMap=new ArrayList<Map<String,String>>();

                        for(int i=0; i<=rows-1; i++)
                        {


                            TableRow tableRow=(TableRow) t1.getChildAt(i);
                            TextView d= (TextView)tableRow.getChildAt(0);
                            TextView p= (TextView)tableRow.getChildAt(1);
                            TextView t= (TextView)tableRow.getChildAt(2);

                            String asd=d.getText().toString();
                            String asd1=p.getText().toString();
                            String asd2=t.getText().toString();
                            int b=9;

                            if(asd2.equals("close"))
                            {
                                b=1;
                            }
                            else if(asd2.equals("open"))
                            {
                                b=0;
                            }


                            amt=amt+Integer.parseInt(asd1);

                            char quotes='"';
                            list_digits.add(quotes+asd+quotes);
                            list_points.add(asd1);
                            list_type.add(b);



                            // String sd=list_digits.add();
                        }


                        String id=Prevalent.currentOnlineuser.getId().toString().trim();
                        String matka_id=m_id.toString().trim();
                        String date=  "15/02/2020";
                        String dt=btnGameType.getText().toString().trim();
                        String d[]=dt.split(" ");

                        String c=d[0].toString();
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("points",list_points);
                        jsonObject.put("digits",list_digits);
                        jsonObject.put("bettype",list_type);
                        jsonObject.put("user_id",id);
                        jsonObject.put("matka_id",matka_id);
                        jsonObject.put("date",c);
                        jsonObject.put("game_id",game_id);

                        JSONArray jsonArray=new JSONArray();
                        jsonArray.put(jsonObject);




//                     Toast.makeText(OddEvenActivity.this,"data"+jsonArray,Toast.LENGTH_LONG).show();

                        //      Object o1=jsonArray_digits;

                        String w= txtWallet_amount.getText().toString().trim();
                        int wallet_amount=Integer.parseInt(w);
                        if(wallet_amount<amt)
                        {

                            String message="Insufficient Amount";
                            errorMessageDialog(OddEvenActivity.this,message);
                            return;

                        }
                        else
                        {
                            int up_amt=wallet_amount-amt;
                            String asd=String.valueOf(up_amt);
                            String userid=Prevalent.currentOnlineuser.getId();
                            btnSave.setEnabled(false);
                           // details.saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,OddEvenActivity.this,userid,asd);
                            //saveGameDataToDatabase(jsonArray,userid,asd,URLs.Url_data_insert,game_id,progressDialog,OddEvenActivity.this);
                            details.updateWalletAmount(OddEvenActivity.this,jsonArray,progressDialog,dashName,m_id);


                            //sendOddEvenGameData(jsonArray,userid,asd);
//                        String userid=Prevalent.currentOnlineuser.getId();
                            //  updateWallet(userid,asd);

                            //updateWalletAmount(id,String.valueOf(up_amt),OddEvenActivity.this,progressDialog);
                        }

//                  sendOddEvenGameData(jsonArray);


                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(OddEvenActivity.this,"Err"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    }



                }

          }});

        //private TextView txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id;
//        String dt=btnGameType.getText().toString().trim();
//        String d[]=dt.split(" ");
//
//        String c=d[0].toString();

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
                    errorMessageDialog(OddEvenActivity.this,"Betting Is Closed For Today Select Another Date");
                    return;
                }
                else if(t.equals("Open"))
                {
                    details.setBetTypeTo(OddEvenActivity.this,dialog,txtOpen,txtClose,m_id,btnType,progressDialog,dww.toString());
                }
                // Toast.makeText(OddEvenActivity.this,""+t.toString(),Toast.LENGTH_LONG).show();




            }
        });


        btnGameType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        dialog=new Dialog(OddEvenActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_date_bet);
//
//
//        txtCurrentDate=(TextView)dialog.findViewById(R.id.currentDate);
//        txtNextDate=(TextView)dialog.findViewById(R.id.nextDate);
//        txtAfterNextDate=(TextView)dialog.findViewById(R.id.afterNextDate);
//        txtDate_id=(TextView)dialog.findViewById(R.id.date_id);
//
//txtDate_id.setVisibility(View.GONE);
//        dialog.setCanceledOnTouchOutside(false);
//
//        //setData(txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,m_id,progressDialog,OddEvenActivity.this);
//        dialog.show();
//        details.getDateData(OddEvenActivity.this,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,progressDialog);
//
//
//
//
//      txtCurrentDate.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//              String c=txtCurrentDate.getText().toString();
//
//           ///   String as=getDataString(c);
//              btnGameType.setText(c.toString());
//              dialog.dismiss();
//          }
//      });
//
//      txtNextDate.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//
//              String c=txtNextDate.getText().toString();
//
//             // String as=getDataString(c);
//              btnGameType.setText(c.toString());
//              dialog.dismiss();
//          }
//      });
//
//      txtAfterNextDate.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//
//
//              String c=txtAfterNextDate.getText().toString();
//
//             // String as=getDataString(c);
//              btnGameType.setText(c.toString());
//              dialog.dismiss();
//          }
//      });
//
//
                details.setDateAndBetTpe(OddEvenActivity.this,dialog,m_id,txtCurrentDate,txtNextDate,txtAfterNextDate,txtDate_id,btnGameType,progressDialog);


            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(OddEvenActivity.this);
        //private int stat=0;
        int m=Integer.parseInt(m_id.toString());
        if(m>Prevalent.Matka_count)
        {
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String ctt=dateFormat.format(date);
            btnGameType.setText(""+ctt);
            details.getStarlineGameData(OddEvenActivity.this,String.valueOf(m),btnType,progressDialog);
           // btnType.setText("5:00");
            btnGameType.setClickable(false);
            stat=1;
            btnType.setClickable(false);
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),OddEvenActivity.this);
        }
        else
        {
            stat=2;
            details.setWallet_Amount(txtWallet_amount,progressDialog, Prevalent.currentOnlineuser.getId(),OddEvenActivity.this);
          //  details.setBetDateDay(OddEvenActivity.this,m_id,btnGameType,progressDialog);
           // details.setBetDemoDay(OddEvenActivity.this,m_id,btnGameType,progressDialog);
            details.setBetDateDay(OddEvenActivity.this,m_id,btnGameType,progressDialog);

        }

        }

    private void setOddData(String s, final String p, String th) {


        tr=new TableRow(OddEvenActivity.this);
        txtDigit=new TextView(OddEvenActivity.this);
        txtPoint=new TextView(OddEvenActivity.this);
        txtType=new TextView(OddEvenActivity.this);
        btnDelete=new TextView(OddEvenActivity.this);

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



//    private static void setData(final TextView txtCurrentDate, final TextView txtNextDate,final TextView txtAfterNextDate,final TextView txtDate_id, final String m_id, final ProgressDialog progressDialog, final Context context) {
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try
//                {
//                    JSONObject jsonObject=new JSONObject(response);
//                    String status=jsonObject.getString("status");
//                    if(status.equals("success"))
//                    {
//                        JSONObject object=jsonObject.getJSONObject("data");
//                        MatkasObjects matkasObjects=new MatkasObjects();
//                        matkasObjects.setId(object.getString("id"));
//                        matkasObjects.setName(object.getString("name"));
//                        matkasObjects.setStart_time(object.getString("start_time"));
//                        matkasObjects.setStarting_num(object.getString("starting_num"));
//                        matkasObjects.setNumber(object.getString("number"));
//                        matkasObjects.setEnd_num(object.getString("end_num"));
//                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
//                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
//                        matkasObjects.setCreated_at(object.getString("created_at"));
//                        matkasObjects.setUpdated_at(object.getString("updated_at"));
//                        matkasObjects.setStatus(object.getString("status"));
//
//                        String bid_start=matkasObjects.getBid_start_time();
//                        Date current_time=new Date();
//                        SimpleDateFormat sformat=new SimpleDateFormat("HH:mm:ss");
//                        //Date time_start=sformat.parse(bid_start);
//                        String c_date=sformat.format(current_time);
//
//                        // int flag=time_start.compareTo(current_time);
//                        //txtOpen.setText(""+d+"\n"+current_time);
//                        // txtClose.setText(current_time.toString());
//
//                        String startTimeSplliting[]=bid_start.split(":");
//                        int s_hours=Integer.parseInt(startTimeSplliting[0]);
//                        int s_min=Integer.parseInt(startTimeSplliting[1]);
//                        int s_sec=Integer.parseInt(startTimeSplliting[2]);
//                        String currentTimeSplitting[]=c_date.split(":");
//                        int c_hours=Integer.parseInt(currentTimeSplitting[0]);
//                        int c_min=Integer.parseInt(currentTimeSplitting[1]);
//                        int c_sec=Integer.parseInt(currentTimeSplitting[2]);
//
//                        int flag=0;
//                        if(s_hours>c_hours)
//                        {
//                            flag=1;
//                        }
//                        else if(s_hours==c_hours)
//                        {
//                            if(s_min>c_min)
//                            {
//                                flag=1;
//                            }
//                            else if(s_min==c_min)
//                            {
//                                if(s_sec>c_sec)
//                                {
//                                    flag=1;
//                                }
//                                else
//                                {
//                                    flag=0;
//                                }
//                                flag=0;
//                            }
//                            else
//                            {
//                                flag=0;
//                            }
//                        }
//                        else
//                        {
//                            flag=0;
//                        }
//
//                        Date c_dat=new Date();
//                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
//                        String s_dt=dateFormat.format(c_dat);
//
//
//
//                     String   ss = getNextDate(s_dt);
//
//                        String dd=getNextDate(ss);
//
//                        if(flag==0)
//                        {
//                            txtCurrentDate.setText(s_dt+" Bet Close");
//                            txtNextDate.setText(ss+" Bet Open");
//                            txtAfterNextDate.setText(dd+" Bet Open");
//
//                            txtDate_id.setText("c");
//                            txtDate_id.setVisibility(View.GONE);
//
//                           // txtClose.setText("Close Bet");
//                        }
//                        else if(flag==1)
//                        {
//                            txtCurrentDate.setText(s_dt+" Bet Open");
//                            txtNextDate.setText(ss+" Bet Open");
//                            txtAfterNextDate.setText(dd+" Bet Open");
//                            txtDate_id.setText("o");
//                            txtDate_id.setVisibility(View.GONE);
//                            //txtOpen.setVisibility(View.GONE);
//                        }
//
////String data="Hours : "+s_hours+"\n min : "+s_min+"\n sec : "+s_sec+"\n hours :"+c_hours+"\n minn :"+c_min+"\n seccc :"+c_sec;
//
//                        progressDialog.dismiss();
//                        // Toast.makeText(DoublePanaActivity.this,data,Toast.LENGTH_LONG).show();
//
//
//                    }
//                    else
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(context,"Something ",Toast.LENGTH_LONG).show();
//
//                    }
//                }
//                catch(Exception ex)
//                {
//                    progressDialog.dismiss();
//                    Toast.makeText(context,"Something "+ex.getMessage(),Toast.LENGTH_LONG).show();
//
//                }
//
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(context,"Something erong",Toast.LENGTH_LONG).show();
//
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("id",m_id);
//
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);
//
//
//    }

//    public static String getNextDate(String currentDate)
//    {
//        String nextDate="";
//
//        try
//        {
//            Calendar calendar=Calendar.getInstance();
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
//            Date c=simpleDateFormat.parse(currentDate);
//            calendar.setTime(c);
//            calendar.add(Calendar.DAY_OF_WEEK,1);
//            nextDate=simpleDateFormat.format(calendar.getTime());
//
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            //Toast.makeText(OddEvenActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
//        }
//
//        return nextDate.toString();
//    }


//    public void getDateData(final String m_id,final TextView txtCurrentDate,final TextView txtNextDate,final TextView txtAfterNextDate)
//    {
//
//        progressDialog.show();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.Url_matka_with_id, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String status = jsonObject.getString("status");
//                    if (status.equals("success")) {
//                        JSONObject object = jsonObject.getJSONObject("data");
//                        MatkasObjects matkasObjects = new MatkasObjects();
//                        matkasObjects.setId(object.getString("id"));
//                        matkasObjects.setName(object.getString("name"));
//                        matkasObjects.setStart_time(object.getString("start_time"));
//                        matkasObjects.setStarting_num(object.getString("starting_num"));
//                        matkasObjects.setNumber(object.getString("number"));
//                        matkasObjects.setEnd_num(object.getString("end_num"));
//                        matkasObjects.setBid_start_time(object.getString("bid_start_time"));
//                        matkasObjects.setBid_end_time(object.getString("bid_end_time"));
//                        matkasObjects.setCreated_at(object.getString("created_at"));
//                        matkasObjects.setUpdated_at(object.getString("updated_at"));
//                        matkasObjects.setStatus(object.getString("status"));
//
//                        String bid_start = matkasObjects.getBid_start_time();
//                        String bid_end=matkasObjects.getBid_end_time().toString();
//
//                        String time1 = bid_start.toString();
//                        String time2 = bid_end.toString();
//
//                        Date cdate=new Date();
//
//
//
//                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//                        String time3=format.format(cdate);
//                        Date date1 = null;
//                        Date date2=null;
//                        Date date3=null;
//                        try {
//                            date1 = format.parse(time1);
//                            date2 = format.parse(time2);
//                            date3=format.parse(time3);
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//                        }
//
//                        long difference = date3.getTime() - date1.getTime();
//                        long as=(difference/1000)/60;
//
//                        long diff_close=date3.getTime()-date2.getTime();
//                        long c=(diff_close/1000)/60;
//
//                        Date c_dat=new Date();
//                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy EEEE");
//                        String s_dt=dateFormat.format(c_dat);
//                       String n_dt= getNextDate(s_dt);
//                       String a_dt= getNextDate(n_dt);
//                        if(as<0)
//                        {
//                            progressDialog.dismiss();
//                            //btn.setText(s_dt+" Bet Open");
//                            txtCurrentDate.setText(s_dt+" Bet Open");
//                            txtNextDate.setText(n_dt+" Bet Open");
//                            txtAfterNextDate.setText(a_dt+" Bet Open");
//
//                            //Toast.makeText(OddEvenActivity.this,""+s_dt+"  Open",Toast.LENGTH_LONG).show();
//                        }
//                        else if(c>0)
//                        {progressDialog.dismiss();
//                            txtCurrentDate.setText(s_dt+" Bet Close");
//                            txtNextDate.setText(n_dt+" Bet Open");
//                            txtAfterNextDate.setText(a_dt+" Bet Open");
//
//                           // Toast.makeText(OddEvenActivity.this,""+s_dt+"  Close",Toast.LENGTH_LONG).show();
//                        }
//
//
//                    } else {
//                        progressDialog.dismiss();
//                        Toast.makeText(OddEvenActivity.this,"Something erong",Toast.LENGTH_LONG).show();
//
//
//                    }
//                } catch (Exception ex) {
//                    progressDialog.dismiss();
//                    Toast.makeText(OddEvenActivity.this,"Something erong"+ex.getMessage(),Toast.LENGTH_LONG).show();
//
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        Toast.makeText(OddEvenActivity.this,"Something erong",Toast.LENGTH_LONG).show();
//
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//
//                params.put("id",m_id);
//
//                // params.put("phonepay",phonepaynumber);
//
//
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(OddEvenActivity.this);
//        requestQueue.add(stringRequest);
//
//
//    }

//    public String getDataString(String dt)
//    {
//        String as[]=dt.split(" ");
//        String date=as[0];
//        String bet_type=as[3];
//        return (date+" "+bet_type).toString();
//    }
}
