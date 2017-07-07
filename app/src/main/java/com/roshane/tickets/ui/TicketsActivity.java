package com.roshane.tickets.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.roshane.tickets.R;
import com.roshane.tickets.adapter.TicketListAdapter;
import com.roshane.tickets.model.Ticket;
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

public class TicketsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static String SERVICE_URL = null;
    private String page = "1";
    private List<Ticket> ticketList;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TicketListAdapter adapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SERVICE_URL = this.getString(R.string.service_url);

        initializeComponents();
        setHookListeners();

        getTickets();
    }

    @Override
    public void onRefresh() {
        getTickets();
    }

    /**
     * Initialize UI Components
     */
    void initializeComponents() {
        ticketList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView);
    }

    /**
     * UI Click Events Trigger
     */
    protected void setHookListeners() {

        swipeRefreshLayout.setOnRefreshListener(this);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getTickets();
            }
        });

        setupTicketData();
    }

    /**
     * Setup Ticket Data inside the Adapter
     */
    private void setupTicketData() {
        // LinearLayoutManager & DefaultItemAnimator
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TicketListAdapter(this, ticketList);
        mRecyclerView.setAdapter(adapter);

    }

    private void getTickets() {

        swipeRefreshLayout.setRefreshing(true);

        String url = "http://liteticket.app1.veery.cloud/DVP/API/1.0.0.0/MyTickets/10/" + page + "?status=new";


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if (response.getBoolean("IsSuccess")) {
//                                        JSONObject dataObject = response.getJSONObject("data");
                                        JSONArray contentArray = response.getJSONArray("Result");
                                        if (contentArray.length() > 0) {
                                            int page_count = Integer.parseInt(page) + 1;
                                            page = "" + page_count;
                                            for (int i = 0; i < contentArray.length(); i++) {
                                                JSONObject tickets = contentArray.getJSONObject(i);
                                                // Insert the Json Object into Employee Model
                                                Ticket enquiry = new Ticket.Builder()
                                                        .setId(tickets.optString(ServiceConstant.Ticket.ID))
                                                        .setSubject(tickets.optString(ServiceConstant.Ticket.SUBJECT))
                                                        .setType(tickets.optString(ServiceConstant.Ticket.TYPE))
                                                        .setPriority(tickets.optString(ServiceConstant.Ticket.PRIORITY))
                                                        .setStatus(tickets.optString(ServiceConstant.Ticket.STATUS))
                                                        .build();
                                                ticketList.add(enquiry);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {

                                }
                                swipeRefreshLayout.setRefreshing(false);


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + CommonUtils.getInstance().getSharedPrefString(ServiceConstant.User.TOKEN));
                return headers;
            }

        };
        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsObjRequest.setRetryPolicy(policy);
        VolleySingleton.getInstance().addToRequestQueue(jsObjRequest);
    }
}
