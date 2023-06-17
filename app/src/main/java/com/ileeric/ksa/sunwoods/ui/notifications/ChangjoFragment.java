package com.ileeric.ksa.sunwoods.ui.notifications;

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
import com.ileeric.ksa.sunwoods.databinding.FragmentChangjoBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChangjoFragment extends Fragment {
    double Elv_Bias =0;
    String C_sto="";

    private FragmentChangjoBinding binding;
    TextView C1 = null;
    TextView C2 = null;
    TextView C3 = null;
    TextView C4 = null;
    TextView C5 = null;
    TextView C6 = null;
    TextView C7 = null;
    TextView C8 = null;
    ImageView C_E=null;
    Button inputDone;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChangjoViewModel changjoViewModel =
                new ViewModelProvider(this).get(ChangjoViewModel.class);
        binding = FragmentChangjoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        C1 = (TextView) root.findViewById(R.id.C_Num1);
        C2 = (TextView) root.findViewById(R.id.C_Num2);
        C3 = (TextView) root.findViewById(R.id.C_Num3);
        C4 = (TextView) root.findViewById(R.id.C_Num4);
        C5 = (TextView) root.findViewById(R.id.C_Num5);
        C6 = (TextView) root.findViewById(R.id.C_Num6);
        C7 = (TextView) root.findViewById(R.id.C_Num7);
        C8 = (TextView) root.findViewById(R.id.C_Num8);
        C_E= (ImageView) root.findViewById(R.id.C_E);
        inputDone = (Button) root.findViewById(R.id.C_reset);

        ChangjoTask apiTask = new ChangjoTask("changjo");
        apiTask.execute();
        inputDone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangjoTask apiTask = new ChangjoTask("changjo");
                apiTask.execute();
            }
        });
        return root;
    }
    class ChangjoTask extends AsyncTask<Integer, Void, Boolean> {
        // Variable to store url
        protected String mURL;
        String result = null;

        ArrayList C_data = new ArrayList();

        // Constructor
        public ChangjoTask(String task) {
            if (task == "changjo")
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
                JSONObject changjo = jsonobject.getJSONObject("Changjo");
                JSONObject inElevator = changjo.getJSONObject("inElevator");
                C_data.add(Integer.parseInt(inElevator.getString("sto")));
                C_data.add(Integer.parseInt(inElevator.getString("ppl")));
                C_data.add(Integer.parseInt(inElevator.getString("drc")));
                C_data.add(Integer.parseInt(changjo.getString("C1")));
                C_data.add(Integer.parseInt(changjo.getString("C2")));
                C_data.add(Integer.parseInt(changjo.getString("C3")));
                C_data.add(Integer.parseInt(changjo.getString("C4")));
                C_data.add(Integer.parseInt(changjo.getString("C5")));
                C_data.add(Integer.parseInt(changjo.getString("C6")));
                C_data.add(Integer.parseInt(changjo.getString("C7")));
                C_data.add(Integer.parseInt(changjo.getString("C8")));
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
            C1.setText(C_data.get(3).toString());
            C2.setText(C_data.get(4).toString());
            C3.setText(C_data.get(5).toString());
            C4.setText(C_data.get(6).toString());
            C5.setText(C_data.get(7).toString());
            C6.setText(C_data.get(8).toString());
            C7.setText(C_data.get(9).toString());
            C8.setText(C_data.get(10).toString());

            C_sto = C_data.get(0).toString();
            Elv_Bias = -0.1198*(Integer.parseInt(C_sto))+1.1339;
            View myView = root.findViewById(R.id.C_E);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) myView.getLayoutParams();
            params.verticalBias = (float) Elv_Bias;
            myView.setLayoutParams(params);
            if (Integer.parseInt(C_data.get(1).toString())<=4){
                C_E.setImageResource(R.drawable.baseline_elevator_24_blue);
            }else if (Integer.parseInt(C_data.get(1).toString())>=10){
                C_E.setImageResource(R.drawable.baseline_elevator_24_red);
            }else{
                C_E.setImageResource(R.drawable.baseline_elevator_24_yellow);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}