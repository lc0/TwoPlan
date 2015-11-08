package com.brainscode.twoplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final UnicreditWrapper unicreditWrapper = new UnicreditWrapper("e80cecaf-e421-46e2-b8cf-9546e992e8ef");
        unicreditWrapper.execute();

        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("Webview", "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("Webview", "Error: " + description);
                Toast.makeText(getParent(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        webview.loadUrl("http://twoplan.herokuapp.com");

        final Button btnFetchTransactions = (Button) findViewById(R.id.btnFetchTransactions);
        btnFetchTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Transaction> transactions = unicreditWrapper.getTransactions();
                if (transactions != null) {
                    Log.d("transactions", transactions.toString());
                    Toast.makeText(getApplicationContext(), "Fetched " +
                            Integer.toString(transactions.size()) + " transactions", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), ClassifyActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("transactions", (Serializable) transactions);
                    getApplicationContext().startActivity(intent);

                    // TODO: wear?
//                    for (int i=0; i<10; i++) {
//                        Intent notificationIntent = new Intent();
//                        intent.setAction("com.brainscode.twoplan.SHOW_NOTIFICATION");
//                        intent.putExtra("New transaction", transactions.get(i).toString());
//                        Log.d("notification", transactions.get(i).toString());
//                        sendBroadcast(notificationIntent);
//                    }
                }

            }
        });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
