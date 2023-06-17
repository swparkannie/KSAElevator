package com.ileeric.ksa.sunwoods.ui.dashboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ileeric.ksa.sunwoods.DataFromAPI;
import com.ileeric.ksa.sunwoods.R;
import com.ileeric.ksa.sunwoods.databinding.FragmentHyungtamBinding;
public class HyungtamFragment extends Fragment {
    double Elv_Bias =0;
    String C_sto="";
    private FragmentHyungtamBinding binding;
    String HRT;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HyungtamViewModel hyungtamViewModel =
                new ViewModelProvider(this).get(HyungtamViewModel.class);


        binding = FragmentHyungtamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =  lf.inflate(R.layout.fragment_hyungtam, container, false); //pass the correct layout name for the fragment
        TextView HR = (TextView) root.findViewById(R.id.H_NumR);
        TextView H4 = (TextView) root.findViewById(R.id.H_Num4);
        TextView H3 = (TextView) root.findViewById(R.id.H_Num3);
        TextView H2 = (TextView) root.findViewById(R.id.H_Num2);
        TextView H1 = (TextView) root.findViewById(R.id.H_Num1);
        TextView H0 = (TextView) root.findViewById(R.id.H_Num0);
        ImageView Elv = (ImageView) root.findViewById(R.id.H_E);

        DataFromAPI data = new DataFromAPI();
        try {
            H0.setText(data.T_data.get(2).toString());
            H1.setText(data.T_data.get(3).toString());
            H2.setText(data.T_data.get(4).toString());
            H3.setText(data.T_data.get(5).toString());
            H4.setText(data.T_data.get(6).toString());
            HR.setText(data.T_data.get(7).toString());
            C_sto = data.T_data.get(0).toString();
            Elv_Bias = -0.1511*(Integer.parseInt(C_sto))+1.0548;


            /*
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(getContext(), R.id.);
            constraintSet.setHorizontalBias(R.id.H_E, (float) Elv_Bias);
            constraintSet.applyTo((Constraint) root.findViewById(R.id.hyungsul));

             */

        } catch (Exception e){
            Log.e("Error","Population...");
            View myView = root.findViewById(R.id.H_E);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) myView.getLayoutParams();
            params.verticalBias = 0.8f;
            myView.setLayoutParams(params);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
