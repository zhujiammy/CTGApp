package com.c.ctgapp.ui.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.c.ctgapp.R;



//交易
public class TransactionFragmnet extends Fragment {

    private TransactionViewMode transactionViewMode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionViewMode =
                ViewModelProviders.of(this).get(TransactionViewMode.class);
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);
        final TextView textView = root.findViewById(R.id.text_transaction);
        transactionViewMode.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
