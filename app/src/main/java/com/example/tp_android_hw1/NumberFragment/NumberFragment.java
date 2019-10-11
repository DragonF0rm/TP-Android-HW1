package com.example.tp_android_hw1.NumberFragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tp_android_hw1.R;

import java.util.Locale;

import static androidx.core.content.res.ResourcesCompat.getColor;

public class NumberFragment extends Fragment {
    private static final String NUMBER_PARAM = "numberFragmentInt";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number, container, false);

        int number = 0;
        Bundle arguments = getArguments();
        if (arguments != null) {
            number = arguments.getInt(NUMBER_PARAM);
        }

        int colorNumberTypeEven = 0;
        int colorNumberTypeOdd = 0;
        Context context = getActivity();
        if (context != null) {
            Resources res = context.getResources();
            colorNumberTypeEven = getColor(res, R.color.colorNumberEven, null);
            colorNumberTypeOdd = getColor(res, R.color.colorNumberOdd, null);
        } else {
            Log.w(getLogTag(), "NumberFragment has null activity attached");
        }
        int color = (number % 2 == 0) ? colorNumberTypeEven : colorNumberTypeOdd;

        TextView numberTextView = view.findViewById(R.id.number_fragment_text);
        numberTextView.setText(String.format(Locale.getDefault(), "%d", number));
        numberTextView.setTextColor(color);

        return view;
    }

    public static NumberFragment newInstance(int number) {
        NumberFragment fragment = new NumberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NUMBER_PARAM, number);

        fragment.setArguments(bundle);
        return fragment;
    }

    private static String getLogTag() {
        return "NumberFragment";
    }
}
