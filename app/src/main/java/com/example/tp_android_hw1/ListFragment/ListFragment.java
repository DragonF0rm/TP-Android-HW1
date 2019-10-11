package com.example.tp_android_hw1.ListFragment;

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

import com.example.tp_android_hw1.NumberFragment.NumberFragment;
import com.example.tp_android_hw1.NumberList.InteractiveNumberList;
import com.example.tp_android_hw1.NumberList.NumberDataSource;
import com.example.tp_android_hw1.R;

import static androidx.core.content.res.ResourcesCompat.getColor;

public class ListFragment extends Fragment {
    private static final String COLUMN_COUNT_PARAM = "columnCount";
    private static final String TRANSACTION_OPEN_NUMBER_FRAGMENT = "openNumberFragment";
    private GridLayoutManager mLayoutManager;

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
            Log.w(getLogTag(), "ListFragment has null activity attached");
        }

        int columnCount = 1;
        Bundle arguments = getArguments();
        if (arguments != null) {
            columnCount = arguments.getInt(COLUMN_COUNT_PARAM);
        }

        RecyclerView listView = view.findViewById(R.id.list);
        InteractiveNumberList list = new InteractiveNumberList(
                NumberDataSource.getInstance().getData(), colorNumberTypeEven, colorNumberTypeOdd,
                (Integer value) -> {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    NumberFragment fragment = NumberFragment.newInstance(value);
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(TRANSACTION_OPEN_NUMBER_FRAGMENT);
                    transaction.commit();
                });
        mLayoutManager = new GridLayoutManager(getActivity(), columnCount);
        listView.setLayoutManager(mLayoutManager);
        listView.setAdapter(list.mNumberListAdapter);

        Button addListElemBtn = view.findViewById(R.id.add_list_elem_btn);
        addListElemBtn.setOnClickListener(v -> list.mNumberListAdapter.insertElem());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Context context = getActivity();
        if (context != null) {
            Resources res = context.getResources();
            mLayoutManager.setSpanCount(res.getInteger(R.integer.listColumnCount));
        } else {
            Log.w(getLogTag(), "ListFragment has null activity attached");
        }
    }

    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COLUMN_COUNT_PARAM, columnCount);

        fragment.setArguments(bundle);
        return fragment;
    }

    private static String getLogTag() {
        return "ListFragment";
    }
}
