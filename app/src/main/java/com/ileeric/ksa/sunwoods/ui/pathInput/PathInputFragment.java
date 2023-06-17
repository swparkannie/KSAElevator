package com.ileeric.ksa.sunwoods.ui.pathInput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.ileeric.ksa.sunwoods.DataFromAPI;
import com.ileeric.ksa.sunwoods.R;
import com.ileeric.ksa.sunwoods.databinding.FragmentPathInputBinding;
import com.ileeric.ksa.sunwoods.ui.pathInput.PathInputViewModel;


public class PathInputFragment extends Fragment {

    private FragmentPathInputBinding binding;

    Integer checker (Integer a) {
        Integer bulding=(a/1000)%10;
        Integer floor=((a-bulding*1000)/100)%10;
        Integer classNum=a%100;
        if (bulding==2 || bulding==3){
            if (floor > 5){
                return -1;
            }
            else if (classNum>9) {
                return -1;
            }
        }
        else if (bulding==7){
            if (floor>8){
                return -1;
            }
            else if (floor==8 && classNum>1){
                return -1;
            }
            else if (floor==1 && classNum>1){
                return -7;
            }
            else if (classNum>8){
                return -1;
            }
        }
        return -1;
    }
/*
    String HT_HT(Integer in, Integer out){
        Integer in_floor=((in-((in/1000)%10)*1000)/100)%10;
        Integer out_floor=((out-((out/1000)%10)*1000)/100)%10;


    }

 */
    Integer c_in = 3404;
    Integer c_out = 3404;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PathInputViewModel pathinputviewmodel =
                new ViewModelProvider(this).get(PathInputViewModel.class);

        binding = FragmentPathInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button button_input = (Button) root.findViewById(R.id.button_input);
        EditText startInput = (EditText) root.findViewById(R.id.et_startpt);
        EditText endInput = (EditText) root.findViewById(R.id.et_finishpt);



        button_input.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(c_in);
                System.out.println(c_out);
                c_in = Integer.parseInt(startInput.getText().toString());
                c_out = Integer.parseInt(endInput.getText().toString());
                if (checker(c_in) == -1 || checker(c_out) == -1){
                    Toast.makeText(getActivity().getApplicationContext(), "똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
                }
                else if (checker(c_in)==-7 || checker(c_out)==-7){
                    Toast.makeText(getActivity().getApplicationContext(), "창조카페는 7101 로 넣어주세요", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}