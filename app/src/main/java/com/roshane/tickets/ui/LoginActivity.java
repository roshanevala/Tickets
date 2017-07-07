package com.roshane.tickets.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roshane.tickets.R;
import com.roshane.tickets.util.CommonUtils;
import com.roshane.tickets.util.ServiceConstant;
import com.roshane.tickets.util.TicketsGlobal;
import com.roshane.tickets.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private static String SERVICE_URL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SERVICE_URL = this.getString(R.string.service_url);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                if (!mEmailView.getText().toString().equals("") && !mPasswordView.getText().toString().equals("")) {
                    userLogin();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "You have to fill all required fields",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void userLogin() {

        CommonUtils.getInstance().showProgressDialog(LoginActivity.this, R.string.signing_in);
        // Sending String Request to Server
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("userName", mEmailView.getText().toString());
        jsonParams.put("password", mPasswordView.getText().toString());
        jsonParams.put("scope", "all_all");
        jsonParams.put("console", "AGENT_CONSOLE");
        jsonParams.put("clientID", "e8ea7bb0-5026-11e7-a69b-b153a7c332b9");

        String url = SERVICE_URL + "auth/login";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (response.optString("state").equals("login")) {
                                    // Store Shared Pref Value
                                    CommonUtils.getInstance().storeSharedPrefString(ServiceConstant.User.TOKEN, response.optString("token"));
                                    // Set as Employee Registered
                                    CommonUtils.getInstance().storeSharedPrefBoolean(TicketsGlobal.PREF_USER_REGISTERED, true);
                                    Toast.makeText(LoginActivity.this,
                                            "You have Successfully Logged In",
                                            Toast.LENGTH_SHORT).show();

                                    CommonUtils.getInstance().navigateTo(LoginActivity.this, TicketsActivity.class);
                                    finish();
                                }
                                if (!response.optString("message").equals(null)) {
                                    Toast.makeText(LoginActivity.this,
                                            response.optString("message"),
                                            Toast.LENGTH_SHORT).show();
                                }
                                CommonUtils.getInstance().hideProgressDialog();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,
                                "Please Try Again",
                                Toast.LENGTH_SHORT).show();
                        CommonUtils.getInstance().hideProgressDialog();
                    }
                }) {

        };
        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsObjRequest.setRetryPolicy(policy);
        VolleySingleton.getInstance().addToRequestQueue(jsObjRequest);
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}

