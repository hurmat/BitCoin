package com.macbook.hurmat.bitcoin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity_conversion extends AppCompatActivity {

    TextView logout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversion_bitcoin);

       // logout = (TextView) findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();

       /* logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putBoolean("Login", false);
                editor.commit();
                startActivity(new Intent(MainActivity_conversion.this , LoginActivity.class));
                MainActivity_conversion.this.finish();
            }
        });*/
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
    }
}
