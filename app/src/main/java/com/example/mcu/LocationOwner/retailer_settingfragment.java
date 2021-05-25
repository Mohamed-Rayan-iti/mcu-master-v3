package com.example.mcu.LocationOwner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.mcu.R;
import com.example.mcu.aboutus;
import com.example.mcu.login_Activity;
import com.example.mcu.sign_up_Activity;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link retailer_settingfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class retailer_settingfragment extends Fragment {
    Activity referenceActivity;
    View parentHolder;

    SwitchMaterial switchLang;
    SwitchMaterial  switchTheme;


    //    Button btn_about, out ;
    private Session session;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public retailer_settingfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment retailer_profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static retailer_settingfragment newInstance(String param1, String param2) {
        retailer_settingfragment fragment = new retailer_settingfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.retailer_settingfragment, container, false);

        switchLang = parentHolder.findViewById(R.id.lang_switch);
        switchTheme = parentHolder.findViewById(R.id.theme_switch);

        // SharedPreferences to set checked language
        String language = getActivity().getSharedPreferences("Language", MODE_PRIVATE)
                .getString("lang", "en");
        if (language.equals("ar"))
            switchLang.setChecked(true);
        else
            switchLang.setChecked(false);

        // On Checked Change switch language
        switchLang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switchLanguage("ar");
                else
                    switchLanguage("en");
            }
        });

        // SharedPreferences to set checked theme
        boolean isDarkMode = getActivity().getSharedPreferences("Theme", MODE_PRIVATE)
                .getBoolean("selectedTheme", false);
        if (isDarkMode)
            switchTheme.setChecked(true);
        else
            switchTheme.setChecked(false);

        // On Checked Change switch Themes
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switchThemes(true);
                else
                    switchThemes(false);
            }

        });

        return parentHolder;
    }

    private void switchThemes(boolean theme) {
        getActivity().getSharedPreferences("Theme", MODE_PRIVATE)
                .edit()
                .putBoolean("selectedTheme", theme)
                .apply();

        if (theme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent intent = new Intent(retailer_settingfragment.this.getActivity(), retailer_dashboard_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void switchLanguage(String language) {
        getActivity().getSharedPreferences("Language", MODE_PRIVATE)
                .edit()
                .putString("lang", language)
                .apply();

        //Configuration Language
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        Intent intent = new Intent(retailer_settingfragment.this.getActivity(), retailer_dashboard_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}