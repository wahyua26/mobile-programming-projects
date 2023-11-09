package com.example.hitungluas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hitungluas.databinding.FragmentFirstBinding;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String panjang = binding.editTextNumberDecimal.getText().toString().trim();
                String lebar = binding.editTextNumberDecimal2.getText().toString().trim();

                double p = Double.parseDouble(panjang);
                double l = Double.parseDouble(lebar);

                double luas = p * l;

                binding.textviewHasil.setText("Luas : "+luas);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}