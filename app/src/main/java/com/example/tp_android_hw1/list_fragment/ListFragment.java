package com.example.tp_android_hw1.list_fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_android_hw1.number_fragment.NumberFragment;
import com.example.tp_android_hw1.R;
import com.example.tp_android_hw1.number_list.NumberListAdapter;

import java.util.ArrayList;

import static androidx.core.content.res.ResourcesCompat.getColor;

public class ListFragment extends Fragment {
    private static final String COLUMN_COUNT_PARAM = "columnCount";
    private static final String TRANSACTION_OPEN_NUMBER_FRAGMENT = "openNumberFragment";
    private static final String LIST_DATA_BUNDLE_PART = "dataArrayList";
    private static final String LOG_TAG = "ListFragment";
    private GridLayoutManager LayoutManager;
    private NumberListAdapter Adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        int colorNumberTypeEven = 0;
        int colorNumberTypeOdd = 0;
        Context context = getActivity();
        if (context != null) {
            Resources res = context.getResources();
            colorNumberTypeEven = getColor(res, R.color.colorNumberEven, null);
            colorNumberTypeOdd = getColor(res, R.color.colorNumberOdd, null);
        } else {
            Log.wtf(LOG_TAG, "ListFragment has null activity attached");
        }

        int columnCount = 1;
        Bundle arguments = getArguments();
        if (arguments != null) {
            columnCount = arguments.getInt(COLUMN_COUNT_PARAM);
        }

        ArrayList<Integer> data;
        if (savedInstanceState == null) {
            if(Adapter == null) {
                data = new ArrayList<>();
                for (int i = 1; i < 100; i++) {
                    data.add(i);
                }
            } else {
                data = Adapter.getData();
            }
        } else {
            data = savedInstanceState.getIntegerArrayList(LIST_DATA_BUNDLE_PART);
        }

        RecyclerView listView = view.findViewById(R.id.list);
        Adapter = new NumberListAdapter(data, colorNumberTypeEven, colorNumberTypeOdd,
                (Integer value) -> {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    NumberFragment fragment = NumberFragment.newInstance(value);
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(TRANSACTION_OPEN_NUMBER_FRAGMENT);
                    transaction.commit();
                });
        LayoutManager = new GridLayoutManager(getActivity(), columnCount);
        listView.setLayoutManager(LayoutManager);
        listView.setAdapter(Adapter);

        Button addListElemBtn = view.findViewById(R.id.add_list_elem_btn);
        addListElemBtn.setOnClickListener(v -> Adapter.insertElem());

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(LIST_DATA_BUNDLE_PART, Adapter.getData());
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getActivity();
        if (context != null) {
            Resources res = context.getResources();
            LayoutManager.setSpanCount(res.getInteger(R.integer.listColumnCount));
        } else {
            Log.wtf(LOG_TAG, "ListFragment has null activity attached");
        }
    }

    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLUMN_COUNT_PARAM, columnCount);

        fragment.setArguments(bundle);
        return fragment;
    }
}
