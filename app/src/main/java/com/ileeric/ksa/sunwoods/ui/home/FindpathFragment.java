package com.ileeric.ksa.sunwoods.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ileeric.ksa.sunwoods.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ileeric.ksa.sunwoods.databinding.FragmentFindpathBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class FindpathFragment extends Fragment {
    private FragmentFindpathBinding binding;
    public String input="";
    public String output="";
    EditText I_editText;
    EditText O_editText;
    Button inputDone;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FindpathViewModel findpathViewModel =
                new ViewModelProvider(this).get(FindpathViewModel.class);

        binding = FragmentFindpathBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inputDone = (Button) root.findViewById(R.id.button_input);

        inputDone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                I_editText = (EditText) root.findViewById(R.id.et_startpt);
                input= I_editText.getText().toString();
                O_editText = (EditText) root.findViewById(R.id.et_finishpt);
                output= O_editText.getText().toString();

                if(checker(input) && checker(output)) {
                    //Yeah!
                }
                else {
                    TextView pathinputRes = (TextView) root.findViewById(R.id.pathinputRes);
                    pathinputRes.setText("Wrong input");
                }
            }

        });


        return root;
    }
    public Boolean checker (String Input){
        if (Input.length()>4)
        {
            Toast.makeText(getActivity().getApplicationContext(), "똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }
        /*
        if (Input.charAt(0)=="2"){

        }
        
         */
        return Boolean.TRUE;
    }
    public Boolean H_checker(String Input){
        //현재 형설관에 있는 강의실 목록
        String[] H_class ={"2101","2102","2103","2104"};
        if (Arrays.asList(H_class).contains(Input)){
            System.out.println("Hi");
            return Boolean.TRUE;
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }
    }
    public Boolean C_checker(String Input){
        //현재 형설관에 있는 강의실 목록
        String[] C_class ={"2101","2102","2103","2104"};
        if (Arrays.asList(C_class).contains(Input)){
            System.out.println("Hi");
            return Boolean.TRUE;
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }
    }
    public Boolean T_checker(String Input){
        //현재 형설관에 있는 강의실 목록
        String[] T_class ={"2101","2102","2103","2104"};
        if (Arrays.asList(T_class).contains(Input)){
            System.out.println("Hi");
            return Boolean.TRUE;
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }
    }

    //button click event

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}