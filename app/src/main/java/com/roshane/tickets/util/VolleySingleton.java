package com.roshane.tickets.util;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.roshane.tickets.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * Created by roshanedesilva on 7/7/17.
 */

public class VolleySingleton {
    private static final String TAG = VolleySingleton.class.getSimpleName();
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton()
    {
        mRequestQueue = Volley.newRequestQueue(RootApplication.getAppContext());
    }

    /**
     * Singleton class for VolleySingleton
     * @return volley singleton object
     */
    public static synchronized VolleySingleton getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    /**
     * Get the Volley Request Queue
     *
     * @return Request Queue
     */
    private RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            mRequestQueue = Volley.newRequestQueue(RootApplication.getAppContext());
        }
        return mRequestQueue;
    }

    /**
     * Adding API request to Volley
     * @param req request
     * @param <T> getting the request in queue
     */
    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }



    /**
     * Getting the Error description from VolleyError
     *
     * @param error volley error
     * @return error message
     */
    public String getErrorMessage(VolleyError error)
    {
        if(error != null)
        {
            try
            {
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject response = new JSONObject(responseBody);
                return response.optString("error_description");
            }
            catch (JSONException e)
            {
                return RootApplication.getAppContext().getString(R.string.server_error);
            }
            catch (UnsupportedEncodingException e)
            {
                return RootApplication.getAppContext().getString(R.string.server_error);
            }
        }
        else
        {
            return RootApplication.getAppContext().getString(R.string.server_error);
        }
    }
}
