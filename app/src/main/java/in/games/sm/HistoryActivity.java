package in.games.sm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class HistoryActivity extends MyBaseActivity {

    CardView cvBid_history,cv_Fund_req_history,cv_Trans_hitory,cv_Starline_history,cv_Withdraw_req;
    TextView bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        cvBid_history=findViewById(R.id.cvBidHistory);
        cv_Fund_req_history=findViewById(R.id.cvFund_req_history);
        cv_Trans_hitory=findViewById(R.id.cvTrans_history);
        cv_Starline_history=findViewById(R.id.cvGoogle);
        cv_Withdraw_req=findViewById(R.id.cvWithdraw_req);
        bt_back = (TextView) findViewById(R.id.txt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        cvBid_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryActivity.this,DoubleDigitActivity.class);

              //  intent.putExtra("game_id","11");

                startActivity(intent);
            }
        });
        cv_Starline_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryActivity.this,Starline_Activity.class);

              //  intent.putExtra("game_id","11");

                startActivity(intent);
            }
        });

        cv_Trans_hitory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryActivity.this,TransactionHistory.class);

                //  intent.putExtra("game_id","11");

                startActivity(intent);

            }
        });

        cv_Fund_req_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryActivity.this,Fund_RequestActivity.class);
                startActivity(intent);

            }
        });


        cv_Withdraw_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryActivity.this,Withdraw_history.class);
                startActivity(intent);



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(HistoryActivity.this);
    }
}
