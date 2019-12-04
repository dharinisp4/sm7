package in.games.sm;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.games.sm.utils.CustomJsonRequest;

public class splash_activity extends AppCompatActivity {

   float version_code;
   // ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
             version_code = pInfo.versionCode;
           // Toast.makeText(splash_activity.this,""+version,Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getApiData();

    }

    private void getApiData() {

        String json_tag="json_splash_request";
        HashMap<String,String> params=new HashMap<String, String>();

        CustomJsonRequest customJsonRequest=new CustomJsonRequest(Request.Method.GET, URLs.URL_INDEX, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
   Log.d("index",response.toString());
                try
                {
                    String text=response.getString("api");
                    float ver_code=Float.parseFloat(response.getString("version"));

                    if(version_code==ver_code)
                    {
                        if(text.equals("welcome"))
                        {
                            Intent intent = new Intent(splash_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(splash_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(splash_activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                         String msg=response.getString("message");
                        AlertDialog.Builder builder=new AlertDialog.Builder(splash_activity.this);
                        builder.setTitle("Version Information");
                        builder.setMessage(msg);
                        builder.setCancelable(false);
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String url = null;
                                try {
                                    url = response.getString("link");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finishAffinity();
                            }
                        });


                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                    }

                }
                catch (Exception ex)
                {
                    Toast.makeText(splash_activity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                }


              //  Toast.makeText(splash_activity.this,""+response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e("Volly Error", error.toString());


                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(splash_activity.this,"Connection TimeOut \nPlease connect to internet",Toast.LENGTH_LONG).show();

                    //This indicates that the reuest has either time out or there is no connection

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(splash_activity.this,"Authentication Failure",Toast.LENGTH_LONG).show();
                    // Error indicating that there was an Authentication Failure while performing the request

                } else if (error instanceof ServerError) {
                    Toast.makeText(splash_activity.this,"Server Not Responding",Toast.LENGTH_LONG).show();
                    //Indicates that the server responded with a error response

                } else if (error instanceof NetworkError) {

                    Toast.makeText(splash_activity.this,"Your Device not connected to internet",Toast.LENGTH_LONG).show();
                    //Indicates that there was network error while performing the request

                } else if (error instanceof ParseError) {
                    Toast.makeText(splash_activity.this,"Unknown Error",Toast.LENGTH_LONG).show();
                    // Indicates that the server response could not be parsed

                }
            }
        });

        AppController.getInstance().addToRequestQueue(customJsonRequest,json_tag);


    }

}
