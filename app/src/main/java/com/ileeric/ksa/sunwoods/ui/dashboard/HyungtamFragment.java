package com.ileeric.ksa.sunwoods.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ileeric.ksa.sunwoods.R;
import com.ileeric.ksa.sunwoods.databinding.FragmentHyungtamBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HyungTamFragment extends Fragment {
    double Elv_Bias =0;
    String H_sto="";

    private @NonNull FragmentHyungtamBinding binding;
    TextView H1 = null;
    TextView H2 = null;
    TextView H3 = null;
    TextView H4 = null;
    TextView H5 = null;
    TextView H6 = null;
    ImageView H_E=null;
    Button inputDone;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HyungtamViewModel HyungtamViewModel =
                new ViewModelProvider(this).get(HyungtamViewModel.class);

        binding = FragmentHyungtamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        H1 = (TextView) root.findViewById(R.id.H_Num0);
        H2 = (TextView) root.findViewById(R.id.H_Num1);
        H3 = (TextView) root.findViewById(R.id.H_Num2);
        H4 = (TextView) root.findViewById(R.id.H_Num3);
        H5 = (TextView) root.findViewById(R.id.H_Num4);
        H6 = (TextView) root.findViewById(R.id.H_NumR);
        H_E = (ImageView) root.findViewById(R.id.H_E);
        inputDone = (Button) root.findViewById(R.id.T_reset);

        HyungtamTask apiTask = new HyungtamTask("Tamgu");

        apiTask.execute();
        inputDone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                HyungTamFragment.HyungtamTask apiTask = new HyungTamFragment.HyungtamTask("Tamgu");
                apiTask.execute();
            }
        });
        return root;

    }
    class HyungtamTask extends AsyncTask<Integer, Void, Boolean> {
        // Variable to store url
        protected String mURL;
        String result = null;

        ArrayList T_data = new ArrayList();

        // Constructor
        public HyungtamTask(String task) {
            if (task == "Tamgu")
            {
                mURL = "http://omoknuni.mireene.com/get2.php";
            }
        }

        // Background work
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                // Open the connection
                URL url = new URL(mURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();

                // Get the stream
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                // Set the result
                result = builder.toString();
                System.out.println("RES IS\n" + result);
                JSONObject jsonobject = new JSONObject(result);
                System.out.println(jsonobject.length());
                JSONObject hyungtam = jsonobject.getJSONObject("Tamgu");
                JSONObject inElevator = hyungtam.getJSONObject("inElevator");
                T_data.add(Integer.parseInt(inElevator.getString("sto")));
                T_data.add(Integer.parseInt(inElevator.getString("ppl")));
                T_data.add(Integer.parseInt(inElevator.getString("drc")));
                T_data.add(Integer.parseInt(hyungtam.getString("T1")));
                T_data.add(Integer.parseInt(hyungtam.getString("T2")));
                T_data.add(Integer.parseInt(hyungtam.getString("T3")));
                T_data.add(Integer.parseInt(hyungtam.getString("T4")));
                T_data.add(Integer.parseInt(hyungtam.getString("T5")));
                T_data.add(Integer.parseInt(hyungtam.getString("T6")));

            } catch (Exception e) {
                // Error calling the rest api
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            View root = binding.getRoot();
            H1.setText(T_data.get(3).toString());
            H2.setText(T_data.get(4).toString());
            H3.setText(T_data.get(5).toString());
            H4.setText(T_data.get(6).toString());
            H5.setText(T_data.get(7).toString());
            H6.setText(T_data.get(8).toString());

            H_sto = T_data.get(0).toString();
            Elv_Bias = -0.1331*(Integer.parseInt(H_sto))+1.1321;
            View myView = root.findViewById(R.id.H_E);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) myView.getLayoutParams();
            params.verticalBias = (float) Elv_Bias;
            myView.setLayoutParams(params);
            if (Integer.parseInt(T_data.get(1).toString())<=4){
                H_E.setImageResource(R.drawable.baseline_elevator_24_blue);
            }else if (Integer.parseInt(T_data.get(1).toString())>=10){
                H_E.setImageResource(R.drawable.baseline_elevator_24_red);
            }else{
                H_E.setImageResource(R.drawable.baseline_elevator_24_yellow);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
