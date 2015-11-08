package com.brainscode.twoplan;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by khomenkos on 08/11/15.
 */
public class TransactionLabel  extends AsyncTask<Void, Void, Void> {
    Transaction transaction;
    String username;

    public TransactionLabel(Transaction transaction, String username) {
        this.transaction = transaction;
        this.username = username;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String url = "http://twoplan.mybluemix.net/addExpenses";

        DefaultHttpClient httpclient = new DefaultHttpClient();
        Log.d("test", url);

        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(transaction.toJson(username), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpclient.execute(httpPost);
            Log.d("sent data", response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String localUrl = "http://twoplan.herokuapp.com/saveexpense";
        httpPost = new HttpPost(localUrl);
        httpPost.setHeader("Content-Type", "application/json");

        try {
            httpPost.setEntity(new StringEntity(transaction.toJson(username), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpclient.execute(httpPost);
            Log.d("sent data", convertStreamtoString(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String convertStreamtoString(InputStream is){

        String line="";
        String data="";
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            while((line=br.readLine())!=null){

                data+=line;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return  data;
    }

}
