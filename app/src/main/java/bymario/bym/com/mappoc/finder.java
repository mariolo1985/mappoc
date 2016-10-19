package bymario.bym.com.mappoc;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Key;
import java.util.Iterator;
import java.util.List;

public class finder extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // SEARCH BUTTON CLICK
        Button btnSearchLocation = (Button) findViewById(R.id.btn_search_loc);
        btnSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                getLocation();
            }
        });

        // SOFT KEYBOARD [ENTER]
        EditText txbLocation = (EditText) findViewById(R.id.txb_location);

        // DETECT KEYPRESS
        txbLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    hideKeyboard();
                    getLocation();
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng utah = new LatLng(40.6138122,-111.9159724);
        mMap.addMarker(new MarkerOptions().position(utah).title("Marker in Utah"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(utah,10));
    }

    /*****
     * HELPERS
     *****/

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // MAP SERVICES
    public void animateMapLocation(LatLng latlng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13));
    }

    // LOCATION SERVICES
    public void getLocation() {
        EditText txbLocation = (EditText) findViewById(R.id.txb_location);
        String locStr = txbLocation.getText().toString();

        Geocoder gc = new Geocoder(this);

        try {
            List<Address> locResults = gc.getFromLocationName(locStr, 100);
            for (Iterator<Address> i = locResults.iterator(); i.hasNext(); ) {
                Address addr = i.next();
                LatLng addrLatLng = new LatLng(addr.getLatitude(), addr.getLongitude());
                MarkerOptions mkrOpts = new MarkerOptions()
                        .position(addrLatLng)
                        .title(locStr);
                mMap.addMarker(mkrOpts);
                animateMapLocation(addrLatLng);
                makeToast(locStr + " found!");
                break;// FOR TESTING
            }
        } catch (Exception ex) {
            makeToast(ex.getMessage().toString());
        }


    }


}
