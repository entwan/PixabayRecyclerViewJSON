package com.dam.recyclerviewjson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* var globales */

    private static final String TAG = "MainActivity";

    private EditText etSearch;

    private String search;

    private Button btnSearch;

    private RecyclerView recyclerView;

    private ArrayList<ModelPixabay> pixabayArrayList;

    private AdapterPixabay adapterPixabay;

    private RequestQueue requestQueue;  //Pour volley

    public static final int PERMISSION_INTERNET = 0;



    //pour tester le recycler
    private void remplissageArrayListEnDur(){
        ModelPixabay modelPixabay1 = new ModelPixabay("https://cdn.shopify.com/s/files/1/0113/2557/1136/products/broderie-diamant-chat-etonne-animaux-chats-figuredart-free-shipping-france_910_540x.jpg?v=1580475405","Auteur 1", 12);
        ModelPixabay modelPixabay2 = new ModelPixabay("https://m.media-amazon.com/images/I/61sP8dAQm6L._AC_SX679_.jpg","Auteur 2", 15);
        ModelPixabay modelPixabay3 = new ModelPixabay("","Auteur 3", 78);

        pixabayArrayList.add(modelPixabay1);
        pixabayArrayList.add(modelPixabay2);
        pixabayArrayList.add(modelPixabay3);

        adapterPixabay = new AdapterPixabay(this,pixabayArrayList);

        recyclerView.setAdapter(adapterPixabay);

        adapterPixabay.setOnItemClickListener(new AdapterPixabay.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(MainActivity.this, "Auteur : " + pixabayArrayList.get(position).getAuteur(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Methode pour verifier les permissions de l'application
    public boolean checkPermission() {
        int INTERNET_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (INTERNET_PERMISSION != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET}, INTERNET_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_INTERNET : {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.INTERNET)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Please allow internet permission", Toast.LENGTH_SHORT).show();
                    }
//                    else {
//                        // Lancement de l'app
//                        remplissageArrayListEnDur();
//                    }
                }
            }
        }
    }


    // Methode d'initialisation
    private void init() {
        // init UI
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recycler);

        requestQueue = Volley.newRequestQueue(this);
        // init

        pixabayArrayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void newSearch() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pixabayArrayList.clear();
                search = etSearch.getText().toString().trim();
                Log.i(TAG, "newSearch: " + search);
                parseJSON();
            }
        });

    }

    private void parseJSON() {
        // API KEY
        String pixabayAPIKey = "27952907-d47ece9bf38c2884f25f80d76";

        //https://pixabay.com/api/
        // ?key=27952907-d47ece9bf38c2884f25f80d76
        // &q=yellow+flowers
        // &image_type=photo
        // &per_page=200
        // &pretty=true


        String urlJSONFile =
                "https://pixabay.com/api/"
                + "?key="
                + pixabayAPIKey
                + "&q="
                + search
                + "&image_type=photo"
                + "&pretty=true";

        Log.i(TAG, "parseJSON: urlJSONFile : " + urlJSONFile);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlJSONFile, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String auteur = hit.getString("user");
                        int nbLikes = hit.getInt("likes");
                        String imageUrl = hit.getString("webformatURL");

                        pixabayArrayList.add(new ModelPixabay(imageUrl,auteur, nbLikes));
                    }

                    adapterPixabay = new AdapterPixabay(MainActivity.this, pixabayArrayList);

                    recyclerView.setAdapter(adapterPixabay);

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        newSearch();

        if (checkPermission()) {
           // remplissageArrayListEnDur();
        }





    }
}