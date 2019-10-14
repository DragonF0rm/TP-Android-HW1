package com.example.tp_android_hw1.number_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_android_hw1.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

class NumberViewHolder extends RecyclerView.ViewHolder {
    private final TextView Title;

    NumberViewHolder(@NonNull View itemView, Consumer<Integer> elementOnClickCallback) {
        super(itemView);
        Title = itemView.findViewById(R.id.list_elem_text);
        Title.setOnClickListener(
                v -> elementOnClickCallback.accept(Integer.parseInt(Title.getText().toString()))
        );
    }

    void SetValue(Integer value) {
        Title.setText(String.format(Locale.getDefault(), "%d", value));
    }

    void SetTextColor(int color) {
        Title.setTextColor(color);
    }
}

public class NumberListAdapter extends RecyclerView.Adapter<NumberViewHolder> {
    private static final int TYPE_EVEN = 0;
    private static final int TYPE_ODD = 1;
    private final int ColorTypeEven;
    private final int ColorTypeOdd;
    private final Random Random;
    private final Consumer<Integer> ElementOnClickCallback;
    private final ArrayList<Integer> Data;

    public NumberListAdapter(ArrayList<Integer> data, int colorNumberTypeEven, int colorNumberTypeOdd, Consumer<Integer> elementOnClickCallback) {
        Data = data;
        ElementOnClickCallback = elementOnClickCallback;
        ColorTypeEven = colorNumberTypeEven;
        ColorTypeOdd = colorNumberTypeOdd;
        Random = new Random();
    }

    public void insertElem() {
        Data.add(Random.nextInt(99) + 1);
        this.notifyItemInserted(Data.size() - 1);
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new NumberViewHolder(view, ElementOnClickCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        Integer data = Data.get(position);
        holder.SetValue(data);
        switch (this.getItemViewType(position)) {
            case TYPE_EVEN: {
                holder.SetTextColor(ColorTypeEven);
                return;
            }
            case TYPE_ODD: {
                holder.SetTextColor(ColorTypeOdd);
                return;
            }
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (Data.get(position) % 2 == 0) ? TYPE_EVEN : TYPE_ODD;
    }

    public ArrayList<Integer> getData() {
        return Data;
    }
}
