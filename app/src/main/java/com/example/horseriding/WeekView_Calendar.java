package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeekView_Calendar extends AppCompatActivity {
    String startDateString,endDateString;
    TextView Lun_08,Lun_09,Lun_10,Lun_11,Lun_12,Lun_13,Lun_14,Lun_15,Lun_16,Lun_17,Lun_18,
            Mar_08,Mar_09,Mar_10,Mar_11,Mar_12,Mar_13,Mar_14,Mar_15,Mar_16,Mar_17,Mar_18,
            Mer_08,Mer_09,Mer_10,Mer_11,Mer_12,Mer_13,Mer_14,Mer_15,Mer_16,Mer_17,Mer_18,
            Jeu_08,Jeu_09,Jeu_10,Jeu_11,Jeu_12,Jeu_13,Jeu_14,Jeu_15,Jeu_16,Jeu_17,Jeu_18,
            Ven_08,Ven_09,Ven_10,Ven_11,Ven_12,Ven_13,Ven_14,Ven_15,Ven_16,Ven_17,Ven_18,
            Sam_08,Sam_09,Sam_10,Sam_11,Sam_12,Sam_13,Sam_14,Sam_15,Sam_16,Sam_17,Sam_18,
            Dim_08,Dim_09,Dim_10,Dim_11,Dim_12,Dim_13,Dim_14,Dim_15,Dim_16,Dim_17,Dim_18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view__calendar);
        LocalDateTime dateInit=LocalDateTime.of(2020, 9, 14, 15, 56);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime starDate=dateInit,endDate=dateInit;
        switch(dateInit.getDayOfWeek().getValue()) {
            case 1:starDate=dateInit;endDate=starDate.plusDays(6);break;
            case 2:starDate=dateInit.minusDays(1);endDate=starDate.plusDays(6);break;
            case 3:starDate=dateInit.minusDays(2);endDate=starDate.plusDays(6);break;
            case 4:starDate=dateInit.minusDays(3);endDate=starDate.plusDays(6);break;
            case 5:starDate=dateInit.minusDays(4);endDate=starDate.plusDays(6);break;
            case 6:starDate=dateInit.minusDays(5);endDate=starDate.plusDays(6);break;
            case 7:starDate=dateInit.minusDays(6);endDate=starDate.plusDays(6);break;
        }
        startDateString=dateFormatter.format(starDate);
        endDateString=dateFormatter.format(endDate);
        Lun_08=findViewById(R.id.Lun_08);
        Lun_09=findViewById(R.id.Lun_09);
        Lun_10=findViewById(R.id.Lun_10);
        Lun_11=findViewById(R.id.Lun_11);
        Lun_12=findViewById(R.id.Lun_12);
        Lun_13=findViewById(R.id.Lun_13);
        Lun_14=findViewById(R.id.Lun_14);
        Lun_15=findViewById(R.id.Lun_15);
        Lun_16=findViewById(R.id.Lun_16);
        Lun_17=findViewById(R.id.Lun_17);
        Lun_18=findViewById(R.id.Lun_18);
        Mar_08=findViewById(R.id.Mar_08);
        Mar_09=findViewById(R.id.Mar_09);
        Mar_10=findViewById(R.id.Mar_10);
        Mar_11=findViewById(R.id.Mar_11);
        Mar_12=findViewById(R.id.Mar_12);
        Mar_13=findViewById(R.id.Mar_13);
        Mar_14=findViewById(R.id.Mar_14);
        Mar_15=findViewById(R.id.Mar_15);
        Mar_16=findViewById(R.id.Mar_16);
        Mar_17=findViewById(R.id.Mar_17);
        Mar_18=findViewById(R.id.Mar_18);
        Mer_08=findViewById(R.id.Mer_08);
        Mer_09=findViewById(R.id.Mer_09);
        Mer_10=findViewById(R.id.Mer_10);
        Mer_11=findViewById(R.id.Mer_11);
        Mer_12=findViewById(R.id.Mer_12);
        Mer_13=findViewById(R.id.Mer_13);
        Mer_14=findViewById(R.id.Mer_14);
        Mer_15=findViewById(R.id.Mer_15);
        Mer_16=findViewById(R.id.Mer_16);
        Mer_17=findViewById(R.id.Mer_17);
        Mer_18=findViewById(R.id.Mer_18);
        Jeu_08=findViewById(R.id.Jeu_08);
        Jeu_09=findViewById(R.id.Jeu_09);
        Jeu_10=findViewById(R.id.Jeu_10);
        Jeu_11=findViewById(R.id.Jeu_11);
        Jeu_12=findViewById(R.id.Jeu_12);
        Jeu_13=findViewById(R.id.Jeu_13);
        Jeu_14=findViewById(R.id.Jeu_14);
        Jeu_15=findViewById(R.id.Jeu_15);
        Jeu_16=findViewById(R.id.Jeu_16);
        Jeu_17=findViewById(R.id.Jeu_17);
        Jeu_18=findViewById(R.id.Jeu_18);
        Ven_08=findViewById(R.id.Ven_08);
        Ven_09=findViewById(R.id.Ven_09);
        Ven_10=findViewById(R.id.Ven_10);
        Ven_11=findViewById(R.id.Ven_11);
        Ven_12=findViewById(R.id.Ven_12);
        Ven_13=findViewById(R.id.Ven_13);
        Ven_14=findViewById(R.id.Ven_14);
        Ven_15=findViewById(R.id.Ven_15);
        Ven_16=findViewById(R.id.Ven_16);
        Ven_17=findViewById(R.id.Ven_17);
        Ven_18=findViewById(R.id.Ven_18);
        Sam_08=findViewById(R.id.Sam_08);
        Sam_09=findViewById(R.id.Sam_09);
        Sam_10=findViewById(R.id.Sam_10);
        Sam_11=findViewById(R.id.Sam_11);
        Sam_12=findViewById(R.id.Sam_12);
        Sam_13=findViewById(R.id.Sam_13);
        Sam_14=findViewById(R.id.Sam_14);
        Sam_15=findViewById(R.id.Sam_15);
        Sam_16=findViewById(R.id.Sam_16);
        Sam_17=findViewById(R.id.Sam_17);
        Sam_18=findViewById(R.id.Sam_18);
        Dim_08=findViewById(R.id.Dim_08);
        Dim_09=findViewById(R.id.Dim_09);
        Dim_10=findViewById(R.id.Dim_10);
        Dim_11=findViewById(R.id.Dim_11);
        Dim_12=findViewById(R.id.Dim_12);
        Dim_13=findViewById(R.id.Dim_13);
        Dim_14=findViewById(R.id.Dim_14);
        Dim_15=findViewById(R.id.Dim_15);
        Dim_16=findViewById(R.id.Dim_16);
        Dim_17=findViewById(R.id.Dim_17);
        Dim_18=findViewById(R.id.Dim_18);

    }

    @Override
    protected void onResume() {
        super.onResume();
        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, "http://192.168.1.7:45455/seances/"+startDateString+"/"+endDateString, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LocalDateTime dateFromResponse;
                JSONObject j = null;
                for(int i=0;i<response.length();i++){
                    try {
                        j = response.getJSONObject(i);
                        dateFromResponse=LocalDateTime.parse(j.getString("startDate"));
                        String dateTime=j.getString("startDate").substring(11,16);
                        switch (dateFromResponse.getDayOfWeek().getValue()){
                            case 1: switch(dateTime){
                                case "08:00":Lun_08.setText(j.getInt("seanceId")+""+""+"");Lun_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "09:00":Lun_09.setText(j.getInt("seanceId")+""+""+"");Lun_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "10:00":Lun_10.setText(j.getInt("seanceId")+""+""+"");Lun_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "11:00":Lun_11.setText(j.getInt("seanceId")+""+""+"");Lun_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "12:00":Lun_12.setText(j.getInt("seanceId")+""+""+"");Lun_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "13:00":Lun_13.setText(j.getInt("seanceId")+""+""+"");Lun_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "14:00":Lun_14.setText(j.getInt("seanceId")+""+""+"");Lun_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "15:00":Lun_15.setText(j.getInt("seanceId")+""+""+"");Lun_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "16:00":Lun_16.setText(j.getInt("seanceId")+""+""+"");Lun_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "17:00":Lun_17.setText(j.getInt("seanceId")+""+""+"");Lun_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "18:00":Lun_18.setText(j.getInt("seanceId")+""+""+"");Lun_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                            }
                                break;
                            case 2: switch(dateTime){
                                case "08:00":Mar_08.setText(j.getInt("seanceId")+""+""+"");Mar_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "09:00":Mar_09.setText(j.getInt("seanceId")+""+""+"");Mar_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "10:00":Mar_10.setText(j.getInt("seanceId")+""+""+"");Mar_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "11:00":Mar_11.setText(j.getInt("seanceId")+""+""+"");Mar_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "12:00":Mar_12.setText(j.getInt("seanceId")+""+""+"");Mar_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "13:00":Mar_13.setText(j.getInt("seanceId")+""+""+"");Mar_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "14:00":Mar_14.setText(j.getInt("seanceId")+""+""+"");Mar_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "15:00":Mar_15.setText(j.getInt("seanceId")+""+""+"");Mar_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "16:00":Mar_16.setText(j.getInt("seanceId")+""+""+"");Mar_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "17:00":Mar_17.setText(j.getInt("seanceId")+""+""+"");Mar_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "18:00":Mar_18.setText(j.getInt("seanceId")+""+""+"");Mar_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                            }
                                break;
                            case 3:switch(dateTime){
                                case "08:00":Mer_08.setText(j.getInt("seanceId")+""+""+"");Mer_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "09:00":Mer_09.setText(j.getInt("seanceId")+""+""+"");Mer_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "10:00":Mer_10.setText(j.getInt("seanceId")+""+""+"");Mer_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "11:00":Mer_11.setText(j.getInt("seanceId")+""+""+"");Mer_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "12:00":Mer_12.setText(j.getInt("seanceId")+""+""+"");Mer_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "13:00":Mer_13.setText(j.getInt("seanceId")+""+""+"");Mer_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "14:00":Mer_14.setText(j.getInt("seanceId")+""+""+"");Mer_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "15:00":Mer_15.setText(j.getInt("seanceId")+""+""+"");Mer_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "16:00":Mer_16.setText(j.getInt("seanceId")+""+""+"");Mer_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "17:00":Mer_17.setText(j.getInt("seanceId")+""+""+"");Mer_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "18:00":Mer_18.setText(j.getInt("seanceId")+""+""+"");Mer_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                            }
                                break;
                            case 4:
                                switch(dateTime){
                                    case "08:00":Jeu_08.setText(j.getInt("seanceId")+""+""+"");Jeu_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "09:00":Jeu_09.setText(j.getInt("seanceId")+""+""+"");Jeu_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "10:00":Jeu_10.setText(j.getInt("seanceId")+""+""+"");Jeu_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "11:00":Jeu_11.setText(j.getInt("seanceId")+""+""+"");Jeu_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "12:00":Jeu_12.setText(j.getInt("seanceId")+""+""+"");Jeu_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "13:00":Jeu_13.setText(j.getInt("seanceId")+""+""+"");Jeu_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "14:00":Jeu_14.setText(j.getInt("seanceId")+""+""+"");Jeu_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "15:00":Jeu_15.setText(j.getInt("seanceId")+""+""+"");Jeu_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "16:00":Jeu_16.setText(j.getInt("seanceId")+""+""+"");Jeu_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "17:00":Jeu_17.setText(j.getInt("seanceId")+""+""+"");Jeu_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                    case "18:00":Jeu_18.setText(j.getInt("seanceId")+""+""+"");Jeu_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                        break;
                                }
                                break;
                            case 5:switch(dateTime){
                                case "08:00":Ven_08.setText(j.getInt("seanceId")+""+""+"");Ven_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "09:00":Ven_09.setText(j.getInt("seanceId")+""+""+"");Ven_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "10:00":Ven_10.setText(j.getInt("seanceId")+""+""+"");Ven_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "11:00":Ven_11.setText(j.getInt("seanceId")+""+""+"");Ven_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "12:00":Ven_12.setText(j.getInt("seanceId")+""+""+"");Ven_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "13:00":Ven_13.setText(j.getInt("seanceId")+""+""+"");Ven_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "14:00":Ven_14.setText(j.getInt("seanceId")+""+""+"");Ven_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "15:00":Ven_15.setText(j.getInt("seanceId")+""+""+"");Ven_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "16:00":Ven_16.setText(j.getInt("seanceId")+""+""+"");Ven_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "17:00":Ven_17.setText(j.getInt("seanceId")+""+""+"");Ven_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "18:00":Ven_18.setText(j.getInt("seanceId")+""+""+"");Ven_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                            }
                                break;
                            case 6:	 switch(dateTime){
                                case "08:00":Sam_08.setText(j.getInt("seanceId")+""+""+"");Sam_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "09:00":Sam_09.setText(j.getInt("seanceId")+""+""+"");Sam_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "10:00":Sam_10.setText(j.getInt("seanceId")+""+""+"");Sam_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "11:00":Sam_11.setText(j.getInt("seanceId")+""+""+"");Sam_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "12:00":Sam_12.setText(j.getInt("seanceId")+""+""+"");Sam_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "13:00":Sam_13.setText(j.getInt("seanceId")+""+""+"");Sam_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "14:00":Sam_14.setText(j.getInt("seanceId")+""+""+"");Sam_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "15:00":Sam_15.setText(j.getInt("seanceId")+""+""+"");Sam_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "16:00":Sam_16.setText(j.getInt("seanceId")+""+""+"");Sam_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "17:00":Sam_17.setText(j.getInt("seanceId")+""+""+"");Sam_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "18:00":Sam_18.setText(j.getInt("seanceId")+""+""+"");Sam_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                            }
                                break;
                            case 7:	 switch(dateTime){
                                case "08:00":Dim_08.setText(j.getInt("seanceId")+""+""+"");Dim_08.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "09:00":Dim_09.setText(j.getInt("seanceId")+""+""+"");Dim_09.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "10:00":Dim_10.setText(j.getInt("seanceId")+""+""+"");Dim_10.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "11:00":Dim_11.setText(j.getInt("seanceId")+""+""+"");Dim_11.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "12:00":Dim_12.setText(j.getInt("seanceId")+""+""+"");Dim_12.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "13:00":Dim_13.setText(j.getInt("seanceId")+""+""+"");Dim_13.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "14:00":Dim_14.setText(j.getInt("seanceId")+""+""+"");Dim_14.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "15:00":Dim_15.setText(j.getInt("seanceId")+""+""+"");Dim_15.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "16:00":Dim_16.setText(j.getInt("seanceId")+""+""+"");Dim_16.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "17:00":Dim_17.setText(j.getInt("seanceId")+""+""+"");Dim_17.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                                case "18:00":Dim_18.setText(j.getInt("seanceId")+""+""+"");Dim_18.setBackgroundColor(Color.parseColor("#EC7C32"));
                                    break;
                            }
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("testoooooooooooooo",error+" ");
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jArray);
    }
    public void initId(String jour){

    }

}