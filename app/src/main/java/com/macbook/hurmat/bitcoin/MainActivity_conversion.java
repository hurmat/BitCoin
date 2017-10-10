package com.macbook.hurmat.bitcoin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity_conversion extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;

    String url = "http://staging.techeasesol.com/coinrates/api.php";

    TextView tvBtcUsd, tvBtcMxn, tvBtcMxntoUsd;
    TextView tvEthUsd, tvEthMxn, tvEthMxntoUsd;
    TextView tvEthToday, tvBtcToday;

    Double BTCPriceInUSD,BTCPriceInMXN, ETHPriceInUSD,ETHPriceInMXN,USDtoMXNRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion_bitcoin);

       // logout = (TextView) findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();


        TabHost tabHost =(TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //tabOne
        TabHost.TabSpec specOne = tabHost.newTabSpec("Bitcoin");
        specOne.setContent(R.id.tabBitcoin);
        specOne.setIndicator("Bitcoin");
        specOne.setIndicator(new Tab(getApplicationContext(), R.drawable.bitcoin_b,"Bitcoin"));
        tabHost.addTab(specOne);


        //tabTwo
        TabHost.TabSpec specTwo = tabHost.newTabSpec("Ethereum");
        specTwo.setContent(R.id.tabEthereum);
        specTwo.setIndicator("Ethereum");
        specTwo.setIndicator(new Tab(getApplicationContext(), R.drawable.bitcoin_b,"Ethereum"));
        tabHost.addTab(specTwo);


        tvBtcUsd = (TextView)findViewById(R.id.tvBtc_usd);
        tvBtcMxn = (TextView)findViewById(R.id.tvBtc_mxn);
        tvBtcMxntoUsd = (TextView)findViewById(R.id.tvBtc_MxnUsd);
        tvEthUsd = (TextView)findViewById(R.id.tvEth_Usd);
        tvEthMxn = (TextView)findViewById(R.id.tvEth_mxn);
        tvEthMxntoUsd =(TextView)findViewById(R.id.tvEth_MxnUsd);
        tvBtcToday =(TextView)findViewById(R.id.tvBtcToday);
        tvEthToday =(TextView) findViewById(R.id.tvEthToday);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1 * 60 * 1000); // every 1 minutes
                coinRates();
            }
        }, 0);

    }

    public void coinRates(){

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            BTCPriceInUSD = jsonObject.getDouble("BTCPriceInUSD");
                            tvBtcUsd.setText(String.valueOf(BTCPriceInUSD));
                            tvBtcToday.setText(String.valueOf(BTCPriceInUSD));

                            BTCPriceInMXN = jsonObject.getDouble("BTCPriceInMXN");
                            tvBtcMxn.setText(String.valueOf(BTCPriceInMXN));

                            ETHPriceInUSD = jsonObject.getDouble("ETHPriceInUSD");
                            tvEthUsd.setText(String.valueOf(ETHPriceInUSD));
                            tvEthToday.setText(String.valueOf(ETHPriceInUSD));

                            ETHPriceInMXN = jsonObject.getDouble("ETHPriceInMXN");
                            tvEthMxn.setText(String.valueOf(ETHPriceInMXN));
                            USDtoMXNRate =  jsonObject.getDouble("USDtoMXNRate");
                            tvEthMxntoUsd.setText(String.valueOf(USDtoMXNRate));
                            tvBtcMxntoUsd.setText(String.valueOf(USDtoMXNRate));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity_conversion.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
    }
}


