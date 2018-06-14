package com.example.sushmitha.findfriends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText useremail, pwd;
    Button bt_login;
    private static final String TAG = "loginActivity";
    String BASE_URL = "";
    private static final String LOGIN_URL = "login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BASE_URL = getString(R.string.baseUrl);
        useremail = (EditText) findViewById(R.id.editText);
        pwd = (EditText) findViewById(R.id.editText2);
        bt_login = (Button) findViewById(R.id.log_btn);
        bt_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final String email = useremail.getText().toString().trim().toLowerCase();
        final String password = pwd.getText().toString().trim().toLowerCase();
        if (email.equalsIgnoreCase("")) {
            useremail.setError("Invalid Email");
        }
        if (password.equalsIgnoreCase("")) {
            pwd.setError("Invalid Password");
        }
        if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
            Log.d(TAG, "login successful");
            String[] data = new String[2];
            data[0] = email;
            data[1] = password;
            LoginUser loginTask = new LoginUser();
            loginTask.execute(data);
        } else {
            Log.w(TAG, "login failed");
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private class LoginUser extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            SharedPreferences details = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor e = details.edit();

            e.putString("email", String.valueOf(useremail.getText().toString()));
            e.apply();

            //redirect to maps activity
            Intent i = new Intent(LoginActivity.this, FriendsActivity.class);
            i.putExtra("email", String.valueOf(useremail.getText()));
            startActivity(i);
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String response = "";
            try {

                url = new URL(BASE_URL + LOGIN_URL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                String requestJsonString = new JSONObject()
                        .put("email", params[0])
                        .put("password", params[1])
                        .toString();
                Log.d("REQUEST : ", requestJsonString);
                writer.write(requestJsonString);

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    line = br.readLine();
                    while (line != null) {
                        response += line;
                        line = br.readLine();
                    }

                    br.close();
                }
                Log.d("RESPONSE: ", response);
                conn.disconnect();

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            Log.d("RESPONSE:", response);
            return response;
        }
    }
}


