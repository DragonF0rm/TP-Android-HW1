package com.example.tp_android_hw1.NumberList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_android_hw1.R;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class InteractiveNumberList {
    public NumberListAdapter mNumberListAdapter;
    private Consumer<Integer> mElementOnClickCallback;

    public InteractiveNumberList(List<Integer> data, int colorNumberTypeEven, int colorNumberTypeOdd,
                                 Consumer<Integer> elementOnClickCallback) {
        mElementOnClickCallback = elementOnClickCallback;
        mNumberListAdapter = new NumberListAdapter(data, colorNumberTypeEven, colorNumberTypeOdd);
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;

        NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.list_elem_text);
            mTitle.setOnClickListener(
                    v -> mElementOnClickCallback.accept(Integer.parseInt(mTitle.getText().toString()))
            );
        }

        void SetValue(Integer value) {
            mTitle.setText(String.format(Locale.getDefault(), "%d", value));
        }

        void SetTextColor(int color) {
            mTitle.setTextColor(color);
        }
    }

    public class NumberListAdapter extends RecyclerView.Adapter<NumberViewHolder> {
        private static final int TYPE_EVEN = 0;
        private static final int TYPE_ODD = 1;
        private int mColorTypeEven;
        private int mColorTypeOdd;
        private Random mRandom;

        private List<Integer> mData;

        NumberListAdapter(List<Integer> data, int colorNumberTypeEven, int colorNumberTypeOdd) {
            mData = data;
            mColorTypeEven = colorNumberTypeEven;
            mColorTypeOdd = colorNumberTypeOdd;
            mRandom = new Random();
        }

        public void insertElem() {
            mData.add(mRandom.nextInt(99) + 1);
            this.notifyItemInserted(mData.size() - 1);
        }

        @NonNull
        @Override
        public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new NumberViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
            Integer data = mData.get(position);
            holder.SetValue(data);
            switch (this.getItemViewType(position)) {
                case TYPE_EVEN: {
                    holder.SetTextColor(mColorTypeEven);
                    return;
                }
                case TYPE_ODD: {
                    holder.SetTextColor(mColorTypeOdd);
                    return;
                }
                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (mData.get(position) % 2 == 0) ? TYPE_EVEN : TYPE_ODD;
        }
    }
}
