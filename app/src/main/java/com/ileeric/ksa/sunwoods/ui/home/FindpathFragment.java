package com.ileeric.ksa.sunwoods.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import com.ileeric.ksa.sunwoods.R;
import com.ileeric.ksa.sunwoods.databinding.FragmentPathInputBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class FindpathFragment extends Fragment {
    private FragmentPathInputBinding binding;
    public String input="";
    public String output="";
    EditText I_editText;
    EditText O_editText;
    TextView pathinputRes;
    Button inputDone;
    Integer C_E_dir=0;
    Integer T_E_dir=0;
    Integer C_E_sto=0;
    Integer T_E_Sto=0;
    Integer C_E_ppl=0;
    Integer T_E_ppl=0;
    ArrayList C_data = new ArrayList();
    ArrayList T_data = new ArrayList();
    class Task extends AsyncTask<Integer, Void, Boolean> {
        // Variable to store url
        protected String mURL;
        String result = null;

        ArrayList C_data = new ArrayList();

        // Constructor
        public Task() {
            mURL = "http://omoknuni.mireene.com/get2.php";
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
                C_E_sto = Integer.parseInt(inElevator.getString("sto"));
                C_E_ppl = Integer.parseInt(inElevator.getString("ppl"));
                C_E_dir = Integer.parseInt(inElevator.getString("drc"));
                C_data.add(Integer.parseInt(changjo.getString("C1")));
                C_data.add(Integer.parseInt(changjo.getString("C2")));
                C_data.add(Integer.parseInt(changjo.getString("C3")));
                C_data.add(Integer.parseInt(changjo.getString("C4")));
                C_data.add(Integer.parseInt(changjo.getString("C5")));
                C_data.add(Integer.parseInt(changjo.getString("C6")));
                C_data.add(Integer.parseInt(changjo.getString("C7")));
                C_data.add(Integer.parseInt(changjo.getString("C8")));
                JSONObject tamgu = jsonobject.getJSONObject("Tamgu");
                JSONObject inElevator1 = tamgu.getJSONObject("inElevator");
                T_E_Sto = Integer.parseInt(inElevator1.getString("sto"));
                T_E_ppl = Integer.parseInt(inElevator1.getString("ppl"));
                T_E_dir = Integer.parseInt(inElevator1.getString("drc"));
                C_data.add(Integer.parseInt(changjo.getString("T1")));
                C_data.add(Integer.parseInt(changjo.getString("T2")));
                C_data.add(Integer.parseInt(changjo.getString("T3")));
                C_data.add(Integer.parseInt(changjo.getString("T4")));
                C_data.add(Integer.parseInt(changjo.getString("T5")));
                C_data.add(Integer.parseInt(changjo.getString("T6")));
            } catch (Exception e) {
                // Error calling the rest api
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FindpathViewModel findpathViewModel =
                new ViewModelProvider(this).get(FindpathViewModel.class);

        binding = FragmentPathInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inputDone = (Button) root.findViewById(R.id.button_input);
        pathinputRes = (TextView) root.findViewById(R.id.pathinputRes);

        Task apiTask=new Task();
        apiTask.execute();
        inputDone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                I_editText = (EditText) root.findViewById(R.id.et_startpt);
                input= I_editText.getText().toString();
                O_editText = (EditText) root.findViewById(R.id.et_finishpt);
                output= O_editText.getText().toString();
                Integer input_floor=Integer.parseInt(String.valueOf(input.charAt(1)));
                Integer output_floor=Integer.parseInt(String.valueOf(output.charAt(1)));
                if (input.charAt(0)=="3".charAt(0)){
                    input_floor+=1;
                }
                if (output.charAt(0)=="3".charAt(0)){
                    output_floor+=1;
                }

                Integer deathfactor=Math.abs(output_floor-input_floor);
                if(checker(input) && checker(output)) {
                    if (input.charAt(0)=="5".charAt(0) && output.charAt(0)=="5".charAt(0)&& deathfactor>=4){
                        Toast.makeText(getActivity().getApplicationContext(),"CtoC", Toast.LENGTH_SHORT).show();
                        pathinputRes.setText(CtoC(input_floor,output_floor));
                    }
                    else if (((input.charAt(0)=="2".charAt(0)&&output.charAt(0)=="3".charAt(0))||
                            (input.charAt(0)=="3".charAt(0)&&output.charAt(0)=="2".charAt(0))) &&deathfactor>=4){

                        pathinputRes.setText(HTtoHT(input_floor,output_floor));
                    } else if ((input.charAt(0)=="5".charAt(0)&&output.charAt(0)=="5".charAt(0))||
                            ((input.charAt(0)=="2".charAt(0)||input.charAt(0)=="3".charAt(0)&&(output.charAt(0)=="2".charAt(0)||output.charAt(0)=="3".charAt(0))))){
                        pathinputRes.setText("그냥 계단을 사용하세요");
                    } else if(((input.charAt(0)=="2".charAt(0))||(input.charAt(0)=="3".charAt(0)))){
                        pathinputRes.setText(HTtoC(input_floor,output_floor));
                    }
                    else if ((input.charAt(0)=="5".charAt(0))){
                        pathinputRes.setText(CtoHT(input_floor,output_floor));
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"넌뭐야", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
                }
            }

        });


        return root;
    }
    //path functions
    public String CtoC(Integer In, Integer Out){
        Integer dir=Out-In;
        //DUD&UDU
        if (C_E_dir==0 || (C_E_dir==-1 && dir==-1 && C_E_sto>In) || (C_E_dir==1 && dir==1 && C_E_sto<In)){
            return "창조관 엘레베이터를 타세요";

        } else if ((dir==-1 && C_sum(8,In)<=10) || (dir==1 && C_sum(1,In)<=10)){
            return "기다리면 엘레베이터를 탈 수 있지만, 계단을 추천합니다";
        } else  {
            return "창조관 계단을 사용하세요";
        }
    }

    public String HTtoHT(Integer In, Integer Out){
        pathinputRes.setText("Wrong input");
        Integer dir=Out-In;
        //DUD&UDU
        if (In==Out){
            return "걸어가세요!";
        } else if (T_E_dir==0 || (T_E_dir==-1 && dir==-1 && T_E_Sto>In) ||
                (T_E_dir==1 && dir==1 && T_E_Sto<In) || (T_E_dir==-1 && dir==1)){
            return "형설-탐구 엘레베이터를 타세요";

        } else if ((dir==-1 && HT_sum(6,In)<=10) || (dir==1 && HT_sum(1,In)<=10)){
            return "기다리면 엘레베이터를 탈 수 있지만, 계단을 추천합니다";
        }
        else{
            return "형설-탐구 계단을 사용하세요";
        }
    }
    public String CtoHT(Integer In, Integer Out){
        if (In==1){
            return "형설관 1층으로 이동하세요"+"\n"+HTtoHT(1,Out);
        }else if (In==2){
            return "계단을 타고 3층으로 가세요. 이후 형-탐으로 이동한뒤 계단을 타고 올라가세요";
        }else if (In==3){
            return "형-탐으로 이동한뒤 계단을 타고 올라가세요";
        }
        else if (In>3){
            if (Out>=3){
                return CtoC(In,3)+"\n"+HTtoHT(3,Out);
            }
            else{
                return CtoC(In,3)+"\n"+HTtoHT(Out,3);
            }
        }
        return "개발자에게 문의하세요";
    }
    public String HTtoC(Integer In, Integer Out){
        if (Out==3){
            return HTtoHT(In,3);
        }else if (In>=3 && Out>3){
            if (C_sum(1,3)>=13){
                if ((Integer) C_data.get(1)>13){
                    return HTtoHT(In,3)+"\n"+"계단을 통해 올라가세요";
                }
                else{
                    return HTtoHT(In,1)+"\n"+"창조관1층에서 엘레베이터를 타세요";
                }
            }else{
                return HTtoHT(In,3)+"\n"+"창조관 3층에서 엘레베이터를 타세요";
            }
        }else if (In>3 && Out<3){
            return HTtoHT(In,3)+"\n"+"창조관 계단을 사용하세요";
        }else if(In<3&&Out>3) {
            if (In == 1) {
                return CtoC(1, Out);
            } else {
                if ((Integer)C_data.get(0) >= 13) {
                    return "계단을 타고 창조관 3층으로 간뒤, 창조관 계단을 사용하세요";
                } else {
                    return "창조관 1층으로 가세요, 이때"+"\n"+HTtoHT(In,1)+"\n"+"창조관 엘레베이터를 사용하세요";
                }
            }
        }
        return "개발자에게 문의하세요";
    }
    //sum functions
    public Integer C_sum(Integer startpt, Integer endpt){
        Integer sum=0;
        if (startpt==1){
            for(int i=startpt;i<=endpt;i++){
                sum += (Integer) C_data.get(i-1);
            }
            return sum;
        }else{
            for(int i=startpt;i<=endpt;i++){
                sum += (Integer) C_data.get(i-1);
            }
            return sum+C_E_ppl;
        }

    }
    public Integer HT_sum(Integer startpt, Integer endpt){
        Integer sum=0;
        if (startpt==1){
            for(int i=startpt;i<=endpt;i++){
                sum += (Integer) T_data.get(i-1);
            }
            return sum;
        }else{
            for(int i=startpt;i<=endpt;i++){
                sum += (Integer) T_data.get(i-1);
            }
            return sum+T_E_ppl;
        }
    }
    //checker functions
    public Boolean checker (String Input){
        if (Input.length()!=4)
        {
            Toast.makeText(getActivity().getApplicationContext(), "똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }

        else if (Input.charAt(0)=="2".charAt(0)){
            return T_checker(Input);
        }
        else if (Input.charAt(0)=="3".charAt(0)){
            return H_checker(Input);
        }
        else if (Input.charAt(0)=="5".charAt(0)){
            return C_checker(Input);
        }
        Toast.makeText(getActivity().getApplicationContext(), "Out of range", Toast.LENGTH_SHORT).show();
        return Boolean.FALSE;
    }
    public Boolean H_checker(String Input){
        //현재 형설관에 있는 강의실 목록
        String[] H_class = {
                "3101","3102","3103","3104","3105","3106","3107","3108","3109","3110",
                "3201","3202","3203","3204","3205","3206","3207","3208","3209","3210",
                "3301","3302","3303","3304","3305","3306","3307","3308","3309","3310",
                "3401","3402","3403","3404","3405","3406","3407","3408","3409","3410",
                "3501","3502","3503","3504","3505","3506","3507","3508","3509","3510",};
        if (Arrays.asList(H_class).contains(Input)){
            System.out.println("Hi");
            return Boolean.TRUE;
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "형설 똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }
    }
    public Boolean C_checker(String Input){
        //현재 창조관에 있는 강의실 목록
        String[] C_class = {
                "5101",
                "5201",
                "5301","5302","5303","5304","5305",
                "5401","5402","5403","5404","5405","5406","5407","5408","5409","5410","5411",
                "5501","5502","5503","5504","5505","5506","5507","5508","5509","5410","5411",
                "5601","5602","5603","5604","5605","5606","5607","5608","5609","5410","5411",
                "5701","5702","5703","5704","5705","5706","5707","5708","5709","5410","5411",
                "5801"};
        if (Arrays.asList(C_class).contains(Input)){
            System.out.println("Hi");
            return Boolean.TRUE;
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "창조 똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
            return Boolean.FALSE;
        }
    }
    public Boolean T_checker(String Input){
        //현재 탐구관에 있는 강의실 목록
        String[] T_class = {
                "2101","2102","2103","2104","2105","2106","2107","2108","2109","2110",
                "2201","2202","2203","2204","2205","2206","2207","2208","2209","2210",
                "2301","2302","2303","2304","2305","2306","2307","2308","2309","2310",
                "2401","2402","2403","2404","2405","2406","2407","2408","2409","2410",
                "2501","2502","2503","2504","2505","2506","2507","2508","2509","2510"};
        if (Arrays.asList(T_class).contains(Input)){
            System.out.println("Hi");
            return Boolean.TRUE;
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "탐구 똑바로 넣어라 닝겐", Toast.LENGTH_SHORT).show();
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