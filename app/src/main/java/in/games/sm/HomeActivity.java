package in.games.sm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import in.games.sm.Adapter.MatakListViewAdapter;
import in.games.sm.Adapter.details;

import in.games.sm.Adapter.MatkaAdapter;
import in.games.sm.Model.MatkaObject;
import in.games.sm.Model.MatkasObjects;
import in.games.sm.Prevalent.Prevalent;

import static in.games.sm.Adapter.details.isConnected;

public class HomeActivity extends MyBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtWallet,txtNotification;
    ArrayList<MatkaObject> list;
    TextView user_profile_name;
  SwipeRefreshLayout swipe;
    private  MatkaAdapter matkaAdapter;
    private MatakListViewAdapter matakListViewAdapter;
    private Dialog dialog;
    private Button btn_dialog_ok;
    private CardView pgCard,callCard,cardReload;
    private String name="";
    private TextView txtWallet_amount;
    private TextView txtUserName,txtNumber;
    private ArrayList<MatkasObjects> matkaList;
    private ListView listView;
    ProgressDialog progressDialog;

    public static String mainName="";

    static String URL_Matkas = "https://malamaal.anshuwap.com/api/v1/matkas/matka";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtNotification=(TextView)findViewById(R.id.txtNotification);
        txtWallet=(TextView)findViewById(R.id.txtWallet);

        //setSupportActionBar(toolbar);

        boolean sdfff=isConnected(HomeActivity.this);
        if(sdfff==true)
        {
            // Snackbar.make(findViewById(R.id.main_layout),"Network Status: ",Snackbar.LENGTH_INDEFINITE).show();
        }
        else
        {
            Snackbar.make(findViewById(R.id.main_layout),"No Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
        }

        // final String[] net = new String[1];
        IntentFilter intentFilter=new IntentFilter(NetworkStateChangeReciever.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean isNetworkAvailable=intent.getBooleanExtra(NetworkStateChangeReciever.IS_NETWORK_AVAILABLE,false);
                String netwotkStatus=isNetworkAvailable ? "connected" : "disconnected";

                if(netwotkStatus.equals("disconnected"))
                {
                    Snackbar.make(findViewById(R.id.main_layout),"No Internet Connection",Snackbar.LENGTH_INDEFINITE).show();
                }
                else
                {
                    Snackbar.make(findViewById(R.id.main_layout),"Connected",Snackbar.LENGTH_LONG).show();
                }
                //       net[0] =netwotkStatus;


                //  Toast.makeText(MainActivity.this,""+netwotkStatus,Toast.LENGTH_LONG).show();

            }
        },intentFilter);


        // name=getIntent().getStringExtra("username");

        list=new ArrayList<>();
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));list.add(new MatkaObject(R.drawable.pll,"OPENING-BIDS-1:28 am | CLOSING-BIDS-12:28 am","MORNING BAZAR","125-25-250","Betting is Running for today"));
        matkaList=new ArrayList();
         pgCard=(CardView)findViewById(R.id.cardView3);
        callCard=(CardView)findViewById(R.id.cardView4);
        listView=findViewById(R.id.listView);
        swipe=findViewById(R.id.swipe_layout);
        txtNumber=(TextView)findViewById(R.id.txtNumber);

        details.setMobileNumber(HomeActivity.this,txtNumber);

        progressDialog=new ProgressDialog(HomeActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait for a moment");

        txtWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog=new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_wallet_layout);
                btn_dialog_ok=(Button)dialog.findViewById(R.id.wallet_ok);
                txtWallet_amount=(TextView)dialog.findViewById(R.id.wallet_amount);

                dialog.setTitle("Wallet Amount");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                details.setWallet_Amount(txtWallet_amount,progressDialog,Prevalent.currentOnlineuser.getId(),HomeActivity.this);

                btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        txtNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,NotificationActivity.class);
                startActivity(intent);

            }
        });

        //txtUserName.setText(name.toUpperCase());

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
       // lstView=findViewById(R.id.listView);
       // cardReload=findViewById(R.id.cardView1);




              swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                  @Override
                  public void onRefresh() {

                      onStart();
                      swipe.setRefreshing(false);
                  }
              });
        callCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//Toast.makeText(HomeActivity.this,sd,Toast.LENGTH_LONG).show();
//
                Intent intent=new Intent(Intent.ACTION_DIAL);
                String number=txtNumber.getText().toString().trim();
                intent.setData(Uri.parse("tel: "+number));
                startActivity(intent);
            }
        });
        pgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,PlayGameActivity.class);
                startActivity(intent);

             //matkaAdapter.notifyItemRemoved();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String dt=new SimpleDateFormat("EEEE").format(new Date());
                MatkasObjects objects=matkaList.get(position);
//
      // Toast.makeText(HomeActivity.this,""+Prevalent.Matka_count,Toast.LENGTH_LONG).show();

                // String st=txtStatus.getText().toString();
                String m_id=objects.getId().toString().trim();
                String matka_name=objects.getName().toString().trim();

                    // Toast.makeText(context,"Position"+m_id,Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(HomeActivity.this, GameActivity.class);
                //    intent.putExtra("tim",position);
                    intent.putExtra("matkaName",matka_name);
                    intent.putExtra("m_id",m_id);
                  //  intent.putExtra("bet","cb");
                    startActivity(intent);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       navigationView.setItemIconTintList(null);
       txtUserName=(TextView)navigationView.getHeaderView(0).findViewById(R.id.profile_user_name);
       txtUserName.setText(Prevalent.currentOnlineuser.getName());

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HomeActivity.super.onBackPressed();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();


 // setSessionTimeOut(HomeActivity.this);


        getMatkaData();
        matakListViewAdapter=new MatakListViewAdapter(HomeActivity.this,matkaList);
        listView.setAdapter(matakListViewAdapter);
       // listView.setSmoothScrollbarEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            Intent intent=new Intent(HomeActivity.this,NotificationActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.action_wallet)
        {
             dialog=new Dialog(HomeActivity.this);
             dialog.setContentView(R.layout.dialog_wallet_layout);
             btn_dialog_ok=(Button)dialog.findViewById(R.id.wallet_ok);
             txtWallet_amount=(TextView)dialog.findViewById(R.id.wallet_amount);

             dialog.setTitle("Wallet Amount");
             dialog.setCanceledOnTouchOutside(false);
             dialog.show();

             details.setWallet_Amount(txtWallet_amount,progressDialog,Prevalent.currentOnlineuser.getId(),HomeActivity.this);

            btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent=new Intent(HomeActivity.this, DrawerProfileActivity.class);
            startActivity(intent);
      }
 else if (id == R.id.nav_mpin) {
            Intent intent=new Intent(HomeActivity.this, DrawerGenMpinActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_how_toPlay) {

            Intent intent=new Intent(HomeActivity.this, DrawerHowToPlayActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_history) {

            Intent intent=new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_funds) {


            Intent intent=new Intent(HomeActivity.this, FundsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gameRates) {
            Intent intent=new Intent(HomeActivity.this, DrawerGameRates.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_noticeBoard) {
            Intent intent=new Intent(HomeActivity.this, DrawerNoticeBoardActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("LOGOUT?")
                    .setCancelable(false)
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void getMatkaData()
    {
        progressDialog.show();

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URLs.URL_Matka, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //   matkaAdapter.notifyDataSetChanged();

                        Log.d("matka",String.valueOf(response));

                        matkaList.clear();

                        for(int i=0; i<response.length();i++)
                        {
                            try
                            {
                                JSONObject jsonObject=response.getJSONObject(i);

                                MatkasObjects matkasObjects=new MatkasObjects();
                                matkasObjects.setId(jsonObject.getString("id"));
                                matkasObjects.setName(jsonObject.getString("name"));
                                matkasObjects.setStart_time(jsonObject.getString("start_time"));
                                matkasObjects.setEnd_time(jsonObject.getString("end_time"));
                                matkasObjects.setStarting_num(jsonObject.getString("starting_num"));
                                matkasObjects.setNumber(jsonObject.getString("number"));
                                matkasObjects.setEnd_num(jsonObject.getString("end_num"));
                                matkasObjects.setBid_start_time(jsonObject.getString("bid_start_time"));
                                matkasObjects.setBid_end_time(jsonObject.getString("bid_end_time"));
                                matkasObjects.setCreated_at(jsonObject.getString("created_at"));
                                matkasObjects.setUpdated_at(jsonObject.getString("updated_at"));
                                matkasObjects.setSat_start_time(jsonObject.getString("sat_start_time"));
                                matkasObjects.setSat_end_time(jsonObject.getString("sat_end_time"));
                                matkasObjects.setStatus(jsonObject.getString("status"));
                                matkaList.add(matkasObjects);
                                matakListViewAdapter.notifyDataSetChanged();


                            }
                            catch (Exception ex)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(HomeActivity.this,"Error :"+ex.getMessage(),Toast.LENGTH_LONG).show();

                                return;
                            }
                        }

                        progressDialog.dismiss();


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss(); if(error instanceof NoConnectionError){
                            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = null;
                            if (cm != null) {
                                activeNetwork = cm.getActiveNetworkInfo();
                            }
                            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
                                Toast.makeText(HomeActivity.this, "Server is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeActivity.this, "Your device is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("connection"))){
                            Toast.makeText(HomeActivity.this, "Your device is not connected to internet.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof MalformedURLException){
                            Toast.makeText(HomeActivity.this, "Bad Request.", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                                || error.getCause() instanceof JSONException
                                || error.getCause() instanceof XmlPullParserException){
                            Toast.makeText(HomeActivity.this, "Parse Error (because of invalid json or xml).",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof OutOfMemoryError){
                            Toast.makeText(HomeActivity.this, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
                        }else if (error instanceof AuthFailureError){
                            Toast.makeText(HomeActivity.this, "server couldn't find the authenticated request.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                            Toast.makeText(HomeActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
                        }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                                || error.getCause() instanceof ConnectTimeoutException
                                || error.getCause() instanceof SocketException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("Connection timed out"))) {
                            Toast.makeText(HomeActivity.this, "Connection timeout error",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(HomeActivity.this, "An unknown error occurred.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }



}


