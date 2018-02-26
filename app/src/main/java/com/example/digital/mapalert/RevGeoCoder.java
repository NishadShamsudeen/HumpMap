package com.example.digital.mapalert;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Digital on 2/26/2018.
 */

public class RevGeoCoder extends AsyncTask<LatLng, Void, String>{

    private Context context;
    private ReverseGeoderResultListener callback;
    private LatLng latLng;

    public RevGeoCoder(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(LatLng... latLngs) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        latLng = latLngs[0];
        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (list != null && list.size() > 0) {
                Address address = list.get(0);
                // sending back first address line and locality
                return address.getAddressLine(0) + ", " + address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (callback != null) {
            callback.onResult(s, latLng);
        }
    }


    public void setCallback(ReverseGeoderResultListener callback) {
        this.callback = callback;
    }

    public interface ReverseGeoderResultListener {
        void onResult(String location, LatLng latLng);
    }
}

