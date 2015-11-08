package com.brainscode.twoplan;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khomenkos on 08/11/15.
 */
public class UnicreditWrapper extends AsyncTask<String, Void, String> {

    private String keyId;
    private String baseUrl = "https://ucg-apimanager.axwaycloud.net:8065";

    private List<Transaction> transactionList = new ArrayList<Transaction>();;

    public List<Transaction> getTransactions() {
        return transactionList;
    }

    public UnicreditWrapper(String keyId) {
        this.keyId = keyId;
    }

    @Override
    protected String doInBackground(String... params) {
        String entryPoint = "/transactions/v1";
        String url = baseUrl + entryPoint;

        DefaultHttpClient httpclient = new DefaultHttpClient();
        final HttpParams httpParams = httpclient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
        HttpConnectionParams.setSoTimeout(httpParams, 30000);

        Log.d("test", url);

        HttpGet httpGet = new HttpGet(url); //
        httpGet.setHeader("Keyid", this.keyId);

        String jsonResult = "";
        try{

            HttpResponse response = httpclient.execute(httpGet);  //response class to handle responses
            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

            try {
                parse(jsonResult);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject object = new JSONObject(jsonResult);
        }
        catch(ConnectTimeoutException e){
//            Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonResult;
    }

    private List<Transaction> parse(String jsonResult) throws JSONException {
        JSONObject jObject = new JSONObject(jsonResult);

        JSONArray transactions = jObject.getJSONArray("transactions");

        for (int i=0; i< transactions.length(); i++)	{
            JSONObject transaction = transactions.getJSONObject(i);

            String description = transaction.getString("description");
            String currency = transaction.getString("currency");
            double amount = transaction.getDouble("amount");

            Log.d("new transaction: ", new Transaction(amount, description, currency).toString());

            transactionList.add(new Transaction(amount, description, currency));
        }

        Log.i("JSON", "transactions: " + transactionList);
        return transactionList;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Transactions", "loaded everything");
    }
}
