package in.games.sm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class FundsActivity extends MyBaseActivity {

    CardView cvAdd_Funds,cvWithDraw_Funds,cvFundReq_history;
    TextView bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);

        cvAdd_Funds=findViewById(R.id.cvAddFunds);
        cvWithDraw_Funds=findViewById(R.id.cvWithdrawFunds);
        cvFundReq_history=findViewById(R.id.cvFund_req_history);
        bt_back = (TextView) findViewById(R.id.txt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        cvAdd_Funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(FundsActivity.this,RequestActivity.class);
                startActivity(intent);

            }
        });

        cvWithDraw_Funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(FundsActivity.this,WithdrawalActivity.class);
                startActivity(intent);

            }
        });

        cvFundReq_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FundsActivity.this,Fund_RequestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       // setSessionTimeOut(FundsActivity.this);
    }
}
