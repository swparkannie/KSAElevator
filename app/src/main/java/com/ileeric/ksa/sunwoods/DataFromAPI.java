
package com.ileeric.ksa.sunwoods;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

public class DataFromAPI {

    Boolean isInternetError = Boolean.FALSE;
    /*
    index0= 현재 엘레베이터가 있는 위치
    index1= 현재 엘레베이터 내부에 있는 사람 수
    inex2=현제 엘레베이터 방향
    이후= 1층부터 차례대로 각 층 엘레베이터 앞에 몇명이 서 있는지 담김
    모든 데이터는 string으로 들어와 있다
    */
    public ArrayList T_data = new ArrayList();
    public ArrayList C_data = new ArrayList();


    Thread GetChangjoThread = new Thread() {
        public void run()
        {
            try {
                String connUrl = "http://omoknuni.mireene.com/get2.php";
                System.out.println("Inside API Request");
                System.out.println("Inside API 1");
                String jsonString = Jsoup.connect(connUrl)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .timeout(1000)
                        .execute()
                        .body();
                System.out.println(jsonString);
                Log.d("URL SET", "Done setting URL");
                System.out.println(jsonString.toString());
                JSONObject jsonobject = new JSONObject(jsonString);
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

            } catch (IOException e) {
                isInternetError = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



    Thread GetTamguThread = new Thread() {
        public void run()
        {
            try {
                String connUrl = "http://omoknuni.mireene.com/get2.php";
                System.out.println("Inside API Request");
                System.out.println("Inside API 1");
                String jsonString = Jsoup.connect(connUrl)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .timeout(1000)
                        .execute()
                        .body();
                System.out.println(jsonString);
                Log.d("URL SET", "Done setting URL");
                System.out.println(jsonString.toString());
                JSONObject jsonobject = new JSONObject(jsonString);
                System.out.println(jsonobject.length());
                JSONObject changjo = jsonobject.getJSONObject("Tamgu");
                JSONObject inElevator = changjo.getJSONObject("inElevator");
                T_data.add(Integer.parseInt(inElevator.getString("sto")));
                T_data.add(Integer.parseInt(inElevator.getString("ppl")));
                C_data.add(Integer.parseInt(inElevator.getString("drc")));
                T_data.add(Integer.parseInt(changjo.getString("T1")));
                T_data.add(Integer.parseInt(changjo.getString("T2")));
                T_data.add(Integer.parseInt(changjo.getString("T3")));
                T_data.add(Integer.parseInt(changjo.getString("T4")));
                T_data.add(Integer.parseInt(changjo.getString("T5")));
                T_data.add(Integer.parseInt(changjo.getString("T6")));

            } catch (IOException e) {
                isInternetError = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    public void getPopulation(String building_name) {
        if (building_name == "Tamgu")
        {
            GetTamguThread.start();
            try {
                GetTamguThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (building_name=="Changjo"){
            GetChangjoThread.start();
            try {
                GetChangjoThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

