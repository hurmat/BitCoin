package com.macbook.hurmat.bitcoin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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

    Spinner spinnerOne, spinnerTwo;

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
        tabHost.addTab(specOne);

        //tabTwo
        TabHost.TabSpec specTwo = tabHost.newTabSpec("Ethereum");
        specTwo.setContent(R.id.tabEthereum);
        specTwo.setIndicator("Ethereum");
        tabHost.addTab(specTwo);

        tvBtcUsd = (TextView)findViewById(R.id.tvBtc_usd);
        tvBtcMxn = (TextView)findViewById(R.id.tvBtc_mxn);
        tvBtcMxntoUsd = (TextView)findViewById(R.id.tvBtc_MxnUsd);
        tvEthUsd = (TextView)findViewById(R.id.tvEth_Usd);
        tvEthMxn = (TextView)findViewById(R.id.tvEth_mxn);
        tvEthMxntoUsd =(TextView)findViewById(R.id.tvEth_MxnUsd);
        spinnerOne = (Spinner) findViewById(R.id.spinnerOne);
        tvBtcToday =(TextView)findViewById(R.id.tvBtcToday);
        spinnerTwo=(Spinner) findViewById(R.id.spinnertwo);
        tvEthToday =(TextView) findViewById(R.id.tvEthToday);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1 * 60 * 1000); // every 1 minutes

                coinRates();
                Toast.makeText(MainActivity_conversion.this, "Updated!!", Toast.LENGTH_SHORT).show();
            }
        }, 0);

        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                    tvBtcToday.setText(String.valueOf(BTCPriceInUSD));
                }
                else if(position==1){
                    tvBtcToday.setText(String.valueOf(BTCPriceInMXN));
                }
                else tvBtcToday.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    tvEthToday.setText(String.valueOf(ETHPriceInUSD));
                }
                else if(position==1){
                    tvEthToday.setText(String.valueOf(ETHPriceInMXN));
                }
                else tvEthToday.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                            BTCPriceInMXN = jsonObject.getDouble("BTCPriceInMXN");
                            tvBtcMxn.setText(String.valueOf(BTCPriceInMXN));
                            ETHPriceInUSD = jsonObject.getDouble("ETHPriceInUSD");
                            tvEthUsd.setText(String.valueOf(ETHPriceInUSD));
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
        Volley.newRequestQueue(this).add(request);
    }
}
