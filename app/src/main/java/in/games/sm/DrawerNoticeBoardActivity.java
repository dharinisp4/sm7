package in.games.sm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.games.sm.utils.CustomVolleyJsonArrayRequest;

public class DrawerNoticeBoardActivity extends MyBaseActivity {
TextView bt_back;
ProgressDialog progressDialog;

TextView txtsp,txtdp,txttp,txtsd,txtjd,txths,txtfs,txtrb,txtNumber,txts_sd,txts_sp,txts_dp,txts_tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_notice_board);
        bt_back=(TextView)findViewById(R.id.txt_back);
        progressDialog=new ProgressDialog(DrawerNoticeBoardActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);


        txtsp=(TextView)findViewById(R.id.t1);
        txtdp=(TextView)findViewById(R.id.t2);
        txttp=(TextView)findViewById(R.id.t3);
        txtsd=(TextView)findViewById(R.id.t4);
        txtjd=(TextView)findViewById(R.id.t5);
        txths=(TextView)findViewById(R.id.t6);
        txtfs=(TextView)findViewById(R.id.t7);
        txtrb=(TextView)findViewById(R.id.t8);
        txts_sd=(TextView)findViewById(R.id.txtst);
        txts_sp=(TextView)findViewById(R.id.txtst1);
        txts_dp=(TextView)findViewById(R.id.txtst2);
        txts_tp=(TextView)findViewById(R.id.txtst3);

        txtNumber=(TextView)findViewById(R.id.number);



        getNotice();
//details.setMobileNumber(DrawerNoticeBoardActivity.this,txtNumber);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setSessionTimeOut(DrawerNoticeBoardActivity.this);
    }

    private void getNotice() {

        progressDialog.show();

        String tag_json_obj = "json_notice_req";
        Map<String, String> params = new HashMap<String, String>();

        CustomVolleyJsonArrayRequest jsonArrayRequest=new CustomVolleyJsonArrayRequest(Request.Method.GET, URLs.URL_NOTICE, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {


                    //[{"id":"1","sp_rate":"160","dp_rate":"320","tp_rate":"800","sd_rate":"10","jd_rate":"100","hs_rate":"1000","fs_rate":"10000","rb_rate":"100","contact":"7354224579","s_sd_rate":"10","s_sp_rate":"160","s_dp_rate":"320","s_tp_rate":"1000","rate_range":"1"}]
                    JSONObject jsonObject=response.getJSONObject(0);
                    String range=jsonObject.getString("rate_range");
                    txtsp.setText("* Single Pana :- "+range+" : "+jsonObject.getString("sp_rate"));
                    txtdp.setText("* Double Pana :- "+range+" : "+jsonObject.getString("dp_rate"));
                    txttp.setText("* Triple Pana :- "+range+" : "+jsonObject.getString("tp_rate"));
                    txtsd.setText("* Single Digit :- "+range+" : "+jsonObject.getString("sd_rate"));
                    txtjd.setText("* Jodi Digit :- "+range+" : "+jsonObject.getString("jd_rate"));
                    txths.setText("* Half Sangam Digits :- "+range+" : "+jsonObject.getString("hs_rate"));
                    txtfs.setText("* Full Sangam Digits :- "+range+" : "+jsonObject.getString("fs_rate"));
                    txtrb.setText("* Red Brackets :- "+range+" : "+jsonObject.getString("rb_rate"));
                    txts_sd.setText("* Single Digit :- "+range+" : "+jsonObject.getString("s_sd_rate"));
                    txts_sp.setText("* Single Pana :- "+range+" : "+jsonObject.getString("s_sp_rate"));
                    txts_dp.setText("* Double Pana :- "+range+" : "+jsonObject.getString("s_dp_rate"));
                    txts_tp.setText("* Triple Pana :- "+range+" : "+jsonObject.getString("s_tp_rate"));
                   // txtNumber.setText(jsonObject.getString("contact"));
                   // Toast.makeText(DrawerNoticeBoardActivity.this,""+jsonObject,Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
                catch (Exception ex)
                {progressDialog.dismiss();
                    Toast.makeText(DrawerNoticeBoardActivity.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DrawerNoticeBoardActivity.this,""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest,tag_json_obj);
    }
}
