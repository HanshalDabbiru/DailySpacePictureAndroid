package com.example.spacepictureoftheday;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

import static com.example.spacepictureoftheday.R.id.date_picker;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragmentJSONObject reader = new JSONObject(response);
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final DatePicker datePicker = view.findViewById(date_picker);

        Calendar calendar = Calendar.getInstance();
        datePicker.setMaxDate(calendar.getTimeInMillis());
        datePicker.setMinDate((long) 803624400 * 1000 + 86400000);
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(day, month, year);
                NavHostFragment.findNavController(FirstFragment.this).navigate(action);
            }
        });
    }
}