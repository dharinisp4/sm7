package in.games.sm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends MyBaseActivity {

    TextView txtPostion,txtMatkaName;

   private ImageView imgSingleDigit,imgJodiDigits,imgOddEven,imgRedBracket,imgGroupJodi,imgSinglePana,imgSinglePana2;
   private ImageView imgDoublePana,imgTriplePana,imgPanelGroup,imgSpMotor,imgDpMotor,imgHalfSangum,imgFullSangum;
    //private ImageView imgSpDpTp,imgTwoDigitPanel,imgChoicepana,imgDigitBasedJodi;


    GridView gridView;
    TextView bt_back;
    //private String ID;

    private String dashName;
    private ImageView img;
    private Toolbar toolbar;

    private String m_id;
    //private String bet_type;
   String[] name={"Single Pana","Double Pana","Triple Pana","Single Pana","Shoes","Shoes"};
    int[] images={};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

     //   ID=getIntent().getStringExtra("tim");
        m_id=getIntent().getStringExtra("m_id");
        dashName=getIntent().getStringExtra("matkaName");
     //   bet_type=getIntent().getStringExtra("bet");


        bt_back=(TextView)findViewById(R.id.txtBack);
        txtPostion=(TextView)findViewById(R.id.position);
        img=(ImageView)findViewById(R.id.singlePanna);

        imgSingleDigit=(ImageView)findViewById(R.id.gameSingleDigit);
        imgJodiDigits=(ImageView)findViewById(R.id.gameJodiDigit);
        imgOddEven=(ImageView)findViewById(R.id.gameOddEven);
        imgRedBracket=(ImageView)findViewById(R.id.gameRedBracket);
        //imgDigitBasedJodi=(ImageView)findViewById(R.id.gameDigitBasedJodi);
        imgGroupJodi=(ImageView)findViewById(R.id.gameGroupJodi);
        imgSinglePana=(ImageView)findViewById(R.id.singlePanna);
        imgSinglePana2=(ImageView)findViewById(R.id.singlePanna2);
        imgDoublePana=(ImageView)findViewById(R.id.gameDoublePana);
        imgTriplePana=(ImageView)findViewById(R.id.gameTriplePana);
        //imgSpDpTp=(ImageView)findViewById(R.id.gameSpdptp);
        //imgTwoDigitPanel=(ImageView)findViewById(R.id.gameTwoDigitPanel);
        imgPanelGroup=(ImageView)findViewById(R.id.gamePanelGroup);
        //imgChoicepana=(ImageView)findViewById(R.id.gameChoicePana);
        imgSpMotor=(ImageView)findViewById(R.id.gameSpMotor);
        imgDpMotor=(ImageView)findViewById(R.id.gameDpMotor);
        imgHalfSangum=(ImageView)findViewById(R.id.gamehalfSangam);
        imgFullSangum=(ImageView)findViewById(R.id.gameFullSangam);

        toolbar=findViewById(R.id.toolbar);
        txtMatkaName=findViewById(R.id.board);


        txtMatkaName.setText(dashName+ " DASHBOARD");
        txtMatkaName.setSelected(true);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        imgSingleDigit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      //  Toast.makeText(GameActivity.this,"ID   "+m_id.toString(),Toast.LENGTH_LONG).show();
        Intent intent=new Intent(GameActivity.this,SingleDigitActivity.class);
        intent.putExtra("matkaName",dashName.toString());
        intent.putExtra("game_id","2");
        intent.putExtra("m_id",m_id.toString());
     //   intent.putExtra("m_type",bet_type);
        startActivity(intent);

    }
});
//        imgTwoDigitPanel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GameActivity.this,DoubleDigitActivity.class);
//                intent.putExtra("matkaName",dashName.toString());
//                startActivity(intent);
//            }
//        });

        imgRedBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,RedBracketsActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","4");
                intent.putExtra("m_id",m_id.toString());
             //   intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

        imgSinglePana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,SinglePannaActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","7");
                intent.putExtra("m_id",m_id.toString());
               // intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

        imgFullSangum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,FullSangamActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","13");
                intent.putExtra("m_id",m_id.toString());
                //intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });


        imgDoublePana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,DoublePanaActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","8");
                intent.putExtra("m_id",m_id.toString());
                //intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

        imgTriplePana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,TriplePanaActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","9");
                intent.putExtra("m_id",m_id.toString());
              //  intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });
        imgPanelGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,GroupPanelActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","5");
                intent.putExtra("m_id",m_id.toString());
             //   intent.putExtra("m_type",bet_type);
                startActivity(intent);

            }
        });

        imgSpMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,SpMotorActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","10");
                intent.putExtra("m_id",m_id.toString());
                //intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

        imgJodiDigits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GameActivity.this,JodiDigitActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","3");
                intent.putExtra("m_id",m_id.toString());
                //intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

        imgGroupJodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(GameActivity.this,GroupJodiActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","6");
                intent.putExtra("m_id",m_id.toString());
             //   intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

        imgOddEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameActivity.this,OddEvenActivity.class);
                intent.putExtra("matkaName",dashName.toString());
                intent.putExtra("game_id","1");
                intent.putExtra("m_id",m_id.toString());
             //   intent.putExtra("m_type",bet_type);
                startActivity(intent);
            }
        });

imgHalfSangum.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(GameActivity.this,HalfSangamActivity.class);
        intent.putExtra("matkaName",dashName.toString());
        intent.putExtra("game_id","12");
        intent.putExtra("m_id",m_id.toString());
        //intent.putExtra("m_type",bet_type);
        startActivity(intent);
    }
});

imgDpMotor.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(GameActivity.this,DPMotorActivity.class);
        intent.putExtra("matkaName",dashName.toString());
        intent.putExtra("game_id","11");
        intent.putExtra("m_id",m_id.toString());
        //intent.putExtra("m_type",bet_type);
        startActivity(intent);
    }
});


//         txtPostion.setText("Position"+ID);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    @Override
    protected void onStart() {        super.onStart();
        //setSessionTimeOut(GameActivity.this);

        int d=Integer.parseInt(m_id.toString());

        if(d>15)
        {
            imgJodiDigits.setVisibility(View.GONE);
            imgRedBracket.setVisibility(View.GONE);
            imgFullSangum.setVisibility(View.GONE);
            imgHalfSangum.setVisibility(View.GONE);
            imgGroupJodi.setVisibility(View.GONE);
            imgSinglePana.setVisibility(View.GONE);
            imgSinglePana2.setVisibility(View.VISIBLE);


            imgSinglePana2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(GameActivity.this,SinglePannaActivity.class);
                    intent.putExtra("matkaName",dashName.toString());
                    intent.putExtra("game_id","7");
                    intent.putExtra("m_id",m_id.toString());
                  //  intent.putExtra("m_type",bet_type);
                    startActivity(intent);
                }
            });


        }
        else
        {

            imgJodiDigits.setVisibility(View.VISIBLE);
            imgRedBracket.setVisibility(View.VISIBLE);
            imgFullSangum.setVisibility(View.VISIBLE);
            imgHalfSangum.setVisibility(View.VISIBLE);
            imgGroupJodi.setVisibility(View.VISIBLE);
        }


    }
}
