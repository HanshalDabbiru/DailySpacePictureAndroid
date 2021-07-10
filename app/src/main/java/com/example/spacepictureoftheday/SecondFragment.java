package com.example.spacepictureoftheday;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class SecondFragment extends Fragment {
    private RequestQueue queue;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView image = view.findViewById((R.id.imageDisplay));
        TextView dateDisplay = view.findViewById(R.id.dateDisplay);
        WebView web = view.findViewById((R.id.videoDisplay));
        String m;
        String d;
        final int[] day = {SecondFragmentArgs.fromBundle(getArguments()).getDay()};
        if(day[0] < 10)
        {
            d = "0" + day[0];
        }
        else
        {
            d = Integer.toString(day[0]);
        }
        final int[] month = {SecondFragmentArgs.fromBundle(getArguments()).getMonth() + 1};
        if (month[0] < 10)
        {
            m = "0" + month[0];
        }
        else
        {
            m = Integer.toString(month[0]);
        }
        final int[] year = {SecondFragmentArgs.fromBundle(getArguments()).getYear()};
        String y = Integer.toString(year[0]);
        queue = Volley.newRequestQueue(this.getContext());
        String url = "https://api.nasa.gov/planetary/apod?api_key=pmXp2SwU4ZR2pG4kZXVpVhfzSczQRI4G6YhI104K&date=" + y + "-" + m + "-" + d;
        @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"}) StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {

                JSONObject reader = new JSONObject(response);
                String mediaType = reader.getString("media_type");
                String url1 = reader.getString("url");
                if(mediaType.equals("image"))
                {
                    Picasso.get().load(url1).into(image);
                    image.setVisibility(View.VISIBLE);
                }
                if(mediaType.equals("video"))
                {
                    web.getSettings().setJavaScriptEnabled(true);
                    web.getSettings().setPluginState(WebSettings.PluginState.ON);
                    web.loadUrl(url1);
                    web.setWebChromeClient(new WebChromeClient());
                    web.setVisibility(View.VISIBLE);
                }
                dateDisplay.setText(m + "/" + d + "/" + y);
                Calendar calendar = Calendar.getInstance();
                if(calendar.get(Calendar.YEAR) == year[0] && calendar.get(Calendar.MONTH) + 1 == month[0] && calendar.get(calendar.DAY_OF_MONTH) == day[0])
                {
                    view.findViewById(R.id.nextImageButton).setVisibility(View.INVISIBLE);
                }
                if(year[0]== 1995 && month[0] - 1 == Calendar.JUNE && day[0] == 20)
                {
                    view.findViewById(R.id.previousImageButton).setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> dateDisplay.setText("That didn't work"));
        queue.add(stringRequest);

        view.findViewById(R.id.dateDisplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        view.findViewById(R.id.nextImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String[] arr = nextImage(view, image, web, dateDisplay, year[0], month[0], day[0]);
                year[0] = Integer.parseInt(arr[0]);
                month[0] = Integer.parseInt(arr[1]);
                day[0] = Integer.parseInt(arr[2]);

            }
        });
        view.findViewById(R.id.previousImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String[] arr = previousImage(view, image, web, dateDisplay, year[0], month[0], day[0]);
                year[0] = Integer.parseInt(arr[0]);
                month[0] = Integer.parseInt(arr[1]);
                day[0] = Integer.parseInt(arr[2]);

            }
        });

    }

    public String[] nextImage(View view, ImageView image, WebView web, TextView dateDisplay, int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.DAY_OF_MONTH, 1);

        String[] arr = new String[3];
        arr[0] = String.valueOf(c.get(Calendar.YEAR));
        arr[1] = String.valueOf(c.get(Calendar.MONTH) + 1);
        arr[2] = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String d;
        String m;
        String y;
        if(c.get(Calendar.DAY_OF_MONTH) < 10)
        {
            d = "0" + c.get(Calendar.DAY_OF_MONTH);
        }
        else
        {
            d = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        }
        if (c.get(Calendar.MONTH) < 10)
        {
            m = "0" + arr[1];
        }
        else
        {
            m = arr[1];
        }
        y = Integer.toString(c.get(Calendar.YEAR));
        queue = Volley.newRequestQueue(this.getContext());
        String url = "https://api.nasa.gov/planetary/apod?api_key=pmXp2SwU4ZR2pG4kZXVpVhfzSczQRI4G6YhI104K&date=" + y + "-" + m + "-" + d;
        @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"}) StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {

                JSONObject reader = new JSONObject(response);
                String mediaType = reader.getString("media_type");
                String url1 = reader.getString("url");
                if(mediaType.equals("image"))
                {
                    web.setVisibility(View.INVISIBLE);
                    Picasso.get().load(url1).into(image);
                    image.setVisibility(View.VISIBLE);
                }
                if(mediaType.equals("video"))
                {
                    image.setVisibility(View.INVISIBLE);
                    web.getSettings().setJavaScriptEnabled(true);
                    web.getSettings().setPluginState(WebSettings.PluginState.ON);
                    web.loadUrl(url1);
                    web.setWebChromeClient(new WebChromeClient());
                    web.setVisibility(View.VISIBLE);
                }
                dateDisplay.setText(arr[1] + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
                Calendar calendar = Calendar.getInstance();
                if(calendar.get(Calendar.YEAR) == c.get(Calendar.YEAR) && calendar.get(calendar.MONTH) == c.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH))
                {
                    view.findViewById(R.id.nextImageButton).setVisibility(View.INVISIBLE);
                }
                Button b = view.getRootView().findViewById(R.id.previousImageButton);
                if(c.get(Calendar.YEAR) == 1995 && c.get(Calendar.MONTH) == Calendar.JUNE && c.get(Calendar.DAY_OF_MONTH) == 20)
                {
                    b.setVisibility(View.INVISIBLE);
                }
                else
                {
                    b.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> dateDisplay.setText("That didn't work"));
        queue.add(stringRequest);

        return arr;

    }

    public String[] previousImage(View view, ImageView image, WebView web, TextView dateDisplay, int year, int month, int day)
    {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.DAY_OF_MONTH, -1);

        String[] arr = new String[3];
        arr[0] = String.valueOf(c.get(Calendar.YEAR));
        arr[1] = String.valueOf(c.get(Calendar.MONTH) + 1);
        arr[2] = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String d;
        String m;
        String y;
        if(c.get(Calendar.DAY_OF_MONTH) < 10)
        {
            d = "0" + c.get(Calendar.DAY_OF_MONTH);
        }
        else
        {
            d = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        }
        if (c.get(Calendar.MONTH) < 10)
        {
            m = "0" + arr[1];
        }
        else
        {
            m = arr[1];
        }
        y = Integer.toString(c.get(Calendar.YEAR));
        queue = Volley.newRequestQueue(this.getContext());
        String url = "https://api.nasa.gov/planetary/apod?api_key=pmXp2SwU4ZR2pG4kZXVpVhfzSczQRI4G6YhI104K&date=" + y + "-" + m + "-" + d;
        @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"}) StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {

                JSONObject reader = new JSONObject(response);
                String mediaType = reader.getString("media_type");
                String url1 = reader.getString("url");
                if(mediaType.equals("image"))
                {
                    web.setVisibility(View.INVISIBLE);
                    Picasso.get().load(url1).into(image);
                    image.setVisibility(View.VISIBLE);
                }
                if(mediaType.equals("video"))
                {
                    image.setVisibility(View.INVISIBLE);
                    web.getSettings().setJavaScriptEnabled(true);
                    web.getSettings().setPluginState(WebSettings.PluginState.ON);
                    web.loadUrl(url1);
                    web.setWebChromeClient(new WebChromeClient());
                    web.setVisibility(View.VISIBLE);
                }
                dateDisplay.setText(arr[1] + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
                Calendar calendar = Calendar.getInstance();
                Button button = view.getRootView().findViewById(R.id.nextImageButton);
                //Button button = view.findViewById(R.id.nextImageButton);
                if(calendar.get(Calendar.YEAR) == c.get(Calendar.YEAR) && calendar.get(calendar.MONTH) + 1 == c.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH))
                {
                    button.setVisibility(View.INVISIBLE);
                }
                else
                {
                    button.setVisibility(View.VISIBLE);
                }
                Button b = view.findViewById(R.id.previousImageButton);
                if(c.get(Calendar.YEAR) == 1995 && c.get(Calendar.MONTH) == Calendar.JUNE && c.get(Calendar.DAY_OF_MONTH) == 20)
                {
                   b.setVisibility(View.INVISIBLE);
                }
                //view.findViewById(R.id.nextImageButton).setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> dateDisplay.setText("That didn't work"));
        queue.add(stringRequest);

        return arr;

    }


}