package com.brainscode.twoplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFetchTransactions = (Button) findViewById(R.id.btnFetchTransactions);
        btnFetchTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UnicreditWrapper unicreditWrapper = new UnicreditWrapper("e80cecaf-e421-46e2-b8cf-9546e992e8ef");
                unicreditWrapper.execute();

                List<Transaction> transactions = unicreditWrapper.getTransactions();

                if (transactions != null) {
                    Log.d("transactions", transactions.toString());
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
