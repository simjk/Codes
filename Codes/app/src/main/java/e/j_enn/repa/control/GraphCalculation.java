//package com.example.andy9.test;
package e.j_enn.repa.control;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphCalculation extends AppCompatActivity {

    ArrayList<String> eachyear = new ArrayList<String>();
    ArrayList<String> eachyear1 = new ArrayList<String>();
    ArrayList<String> eachyear2 = new ArrayList<String>();
    int[] fa;
    int[] fa1;
    int[] fa2;
    int[] fa3;
    int[] fa4;
    public ArrayList<String> calculateAverageResalePrice(ArrayList<String> year, ArrayList<String> averageperyear, ArrayList<String> price) {
        long total1=0,total2=0,total3=0,total4=0;
        long count1=0,count2=0,count3=0,count4=0;
        long average1=0, average2=0, average3=0, average4=0;


        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("2015")) {
                Double temp = Double.parseDouble(price.get(i));
                total1 += temp;
                count1++;
            }
        }

        average1 = total1 / count1;
        averageperyear.add(Long.toString(average1));
        eachyear.add("2015");
        Log.d("2015 Price:", Long.toString(average1));
        //Log.d("2015 Count:", Integer.toString(count1));

        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("2016")) {
                Double temp = Double.parseDouble(price.get(i));
                total2 += temp;
                count2++;
            }
        }
        average2 = total2 / count2;
        averageperyear.add(Long.toString(average2));
        eachyear.add("2016");
        Log.d("2016 Price:", Long.toString(average2));
        // Log.d("2016 Count:", Integer.toString(count2));

        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("2017")) {
                Double temp = Double.parseDouble(price.get(i));
                total3 += temp;
                count3++;
            }
        }
        average3 = total3 / count3;
        averageperyear.add(Long.toString(average3));
        eachyear.add("2017");
        Log.d("2017 Price:", Long.toString(average3));
        // Log.d("2017 Count:", Integer.toString(count3));

        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("2018")) {
                Double temp = Double.parseDouble(price.get(i));
                total4 += temp;
                count4++;
            }
        }

        average4 = total4 / count4;
        averageperyear.add(Long.toString(average4));
        eachyear.add("2018");
        Log.d("2018 Price:", Long.toString(average4));
        // Log.d("2018 Count:", Integer.toString(count4));

        return eachyear;
    }

    public ArrayList<String> calculateFilterResalePrice(ArrayList<String> year, ArrayList<String> averageperyear1, ArrayList<String> price, ArrayList<String> townarea, ArrayList<String> flattype, ArrayList<String> floorarea, String towntext, String flattypetext, String floorareatext) {
        long total1=0,total2=0,total3=0,total4=0;
        long count1=0,count2=0,count3=0,count4=0;
        long average1=0, average2=0, average3=0, average4=0;

        if(floorareatext.contains("30-50")) {
            int o = 30;
            int i = 0;
            fa = new int[21];
            while(o <= 50) {
                fa[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("51-70")) {
            int o = 51;
            int i = 0;
            fa = new int[20];
            while(o <= 70) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("71-90")) {
            int o = 71;
            int i = 0;
            fa = new int[20];
            while(o <= 90) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("91-110")) {
            int o = 91;
            int i = 0;
            fa = new int[20];
            while(o <= 110) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("111-130")) {
            int o = 111;
            int i = 0;
            fa = new int[20];
            while (o <= 130) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("131-150")) {
            int o = 131;
            int i = 0;
            fa = new int[20];
            while (o <= 150) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("151-170")) {
            int o = 151;
            int i = 0;
            fa = new int[20];
            while(o <= 170) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("171-190")) {
            int o = 171;
            int i = 0;
            fa = new int[20];
            while (o <=190) {
                fa[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("191-210")) {
            int o = 191;
            int i = 0;
            fa = new int[20];
            while(o <= 210) {
                fa[i] += o;
                i++;
                o++;
            }
        }


        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2015") && townarea.get(i).contains(towntext) && flattype.get(i).contains(flattypetext) && contains(fa, Integer.parseInt(floorarea.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total1 += temp;
                count1++;
            }
        }
        if(count1 == 0) {
            count1++;
        }
        average1 = total1 / count1;
        averageperyear1.add(Long.toString(average1));
        eachyear1.add("2015");
        Log.d("2015 Filter Price:", Long.toString(average1));

        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2016") && townarea.get(i).contains(towntext) && flattype.get(i).contains(flattypetext) && contains(fa, Integer.parseInt(floorarea.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total2 += temp;
                count2++;
            }
        }
        if(count2 == 0) {
            count2++;
        }
        average2 = total2 / count2;
        averageperyear1.add(Long.toString(average2));
        eachyear1.add("2016");
        Log.d("2016 Filter Price:", Long.toString(average2));

        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2017") && townarea.get(i).contains(towntext) && flattype.get(i).contains(flattypetext) && contains(fa, Integer.parseInt(floorarea.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total3 += temp;
                count3++;
            }
        }
        if(count3 == 0) {
            count3++;
        }
        average3 = total3 / count3;
        averageperyear1.add(Long.toString(average3));
        eachyear1.add("2017");
        Log.d("2017 Filter Price:", Long.toString(average3));


        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2018") && townarea.get(i).contains(towntext) && flattype.get(i).contains(flattypetext) && contains(fa, Integer.parseInt(floorarea.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total4 += temp;
                count4++;
            }
        }
        if(count4 == 0) {
            count4++;
        }
        average4 = total4 / count4;
        averageperyear1.add(Long.toString(average4));
        eachyear1.add("2018");
        Log.d("2018 Filter Price:", Long.toString(average4));

        for(int i=0;i<averageperyear1.size(); i++) {
            Log.d("FILTER EACH YEAR", averageperyear1.get(i));
        }


        return eachyear1;
    }

    public double calculateTotalAverageResalePrice(ArrayList<String> price) {
        long count = 0;
        long total = 0;
        long average = 0;
        for(int i = 0; i< price.size(); i++) {
            total += Double.parseDouble(price.get(i));
            count++;
        }
        average = total / count;
        return average;
    }

    public double calculate2018TotalAverageResalePrice(ArrayList<String> price, ArrayList<String> year) {
        long count = 0;
        long average = 0;
        long total = 0;
        for (int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2018")) {
                total += Double.parseDouble(price.get(i));
                count++;
            }
        }
        average = total / count ;
        return average;

    }

    public double calculate2017TotalAverageResalePrice(ArrayList<String> price, ArrayList<String> year) {
        long count = 0;
        long average = 0;
        long total = 0;
        for (int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2017")) {
                total += Double.parseDouble(price.get(i));
                count++;
            }
        }
        average = total / count ;
        return average;
    }

    public double calculate2016TotalAverageResalePrice(ArrayList<String> price, ArrayList<String> year) {
        long count = 0;
        long average = 0;
        long total = 0;
        for (int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("2016")) {
                total += Double.parseDouble(price.get(i));
                count++;
            }
        }
        average = total / count ;
        return average;
    }

    public double calculateTotalFilterResalePrice(ArrayList<String> year, ArrayList<String> price, ArrayList<String> townarea, ArrayList<String> flattype, ArrayList<String> floorarea, String towntext, String flattypetext, String floorareatext) {
        long count = 0;
        long average = 0;
        long total = 0;
        if(floorareatext.contains("30-50")) {
            int o = 30;
            int i = 0;
            fa1 = new int[21];
            while(o <= 50) {
                fa1[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("51-70")) {
            int o = 51;
            int i = 0;
            fa1 = new int[20];
            while(o <= 70) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("71-90")) {
            int o = 71;
            int i = 0;
            fa1 = new int[20];
            while(o <= 90) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("91-110")) {
            int o = 91;
            int i = 0;
            fa1 = new int[20];
            while(o <= 110) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("111-130")) {
            int o = 111;
            int i = 0;
            fa1 = new int[20];
            while (o <= 130) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("131-150")) {
            int o = 131;
            int i = 0;
            fa1 = new int[20];
            while (o <= 150) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("151-170")) {
            int o = 151;
            int i = 0;
            fa1 = new int[20];
            while(o <= 170) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("171-190")) {
            int o = 171;
            int i = 0;
            fa1 = new int[20];
            while (o <=190) {
                fa1[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa));
        } else if (floorareatext.contains("191-210")) {
            int o = 191;
            int i = 0;
            fa1 = new int[20];
            while(o <= 210) {
                fa1[i] += o;
                i++;
                o++;
            }
        }
        for(int i = 0; i<year.size(); i++) {
            if(townarea.get(i).contains(towntext) && flattype.get(i).contains(flattypetext) && contains(fa1, Integer.parseInt(floorarea.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total += temp;
                count++;
            }
        }
        if (count == 0) {
            count++;
        }
        average = total / count;
        return average;
    }

    public ArrayList<String> calculateAverageResalePriceCondo(ArrayList<String> year, ArrayList<String> averageperyear, ArrayList<String> price) {
        ArrayList<String> eachyearCondo = new ArrayList<String>();

        long total1=0,total2=0,total3=0,total4=0;
        int count1=0,count2=0,count3=0,count4=0;
        long average1=0, average2=0, average3=0, average4=0;


        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("-15")) {
                Double temp = Double.parseDouble(price.get(i));
                total1 += temp;
                count1++;
            }
        }
        //Log.d("TEST", Integer.toString(total1));
        average1 = total1 / count1;
        averageperyear.add(Long.toString(average1));
        eachyearCondo.add("2015");
        Log.d("2015 Price:", Long.toString(average1));
        //Log.d("2015 Count:", Integer.toString(count1));

        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("-16")) {
                Double temp = Double.parseDouble(price.get(i));
                total2 += temp;
                count2++;
            }
        }
        average2 = total2 / count2;
        averageperyear.add(Long.toString(average2));
        eachyearCondo.add("2016");
        Log.d("2016 Price:", Long.toString(average2));
        // Log.d("2016 Count:", Integer.toString(count2));

        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("-17")) {
                Double temp = Double.parseDouble(price.get(i));
                total3 += temp;
                count3++;
            }
        }
        average3 = total3 / count3;
        averageperyear.add(Long.toString(average3));
        eachyearCondo.add("2017");
        Log.d("2017 Price:", Long.toString(average3));
        // Log.d("2017 Count:", Integer.toString(count3));

        for(int i =0; i<year.size(); i++) {
            if(year.get(i).contains("-18")) {
                Double temp = Double.parseDouble(price.get(i));
                total4 += temp;
                count4++;
            }
        }

        average4 = total4 / count4;
        averageperyear.add(Long.toString(average4));
        eachyearCondo.add("2018");
        Log.d("2018 Price:", Long.toString(average4));
        // Log.d("2018 Count:", Integer.toString(count4));

        return eachyearCondo;
    }

    public ArrayList<String> calculateAverageFilterResalePriceCondo (ArrayList<String> year, ArrayList<String> averageperyearfilter, ArrayList<String> price, ArrayList<String> townarea, ArrayList<String> floor_area, String towntext, String floorareatext) {
        long total1=0,total2=0,total3=0,total4=0;
        long count1=0,count2=0,count3=0,count4=0;
        long average1=0, average2=0, average3=0, average4=0;
        if(floorareatext.contains("30-50")) {
            int o = 30;
            int i = 0;
            fa3 = new int[21];
            while(o <= 50) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("51-70")) {
            int o = 51;
            int i = 0;
            fa3 = new int[20];
            while(o <= 70) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("71-90")) {
            int o = 71;
            int i = 0;
            fa3 = new int[20];
            while(o <= 90) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("91-110")) {
            int o = 91;
            int i = 0;
            fa3 = new int[20];
            while(o <= 110) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("111-130")) {
            int o = 111;
            int i = 0;
            fa3 = new int[20];
            while (o <= 130) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("131-150")) {
            int o = 131;
            int i = 0;
            fa3 = new int[20];
            while (o <= 150) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("151-170")) {
            int o = 151;
            int i = 0;
            fa3 = new int[20];
            while(o <= 170) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("171-190")) {
            int o = 171;
            int i = 0;
            fa3 = new int[20];
            while (o <=190) {
                fa3[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa3));
        } else if (floorareatext.contains("191-210")) {
            int o = 191;
            int i = 0;
            fa3 = new int[20];
            while(o <= 210) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("211-230")) {
            int o = 211;
            int i = 0;
            fa3 = new int[20];
            while(o <= 230) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("231-250")) {
            int o = 231;
            int i = 0;
            fa3 = new int[20];
            while(o <= 250) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("251-270")) {
            int o = 251;
            int i = 0;
            fa3 = new int[20];
            while(o <= 270) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("271-290")) {
            int o = 271;
            int i = 0;
            fa3 = new int[20];
            while(o <= 290) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("291-310")) {
            int o = 291;
            int i = 0;
            fa3 = new int[20];
            while(o <= 310) {
                fa3[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("311-330")) {
            int o = 311;
            int i = 0;
            fa3 = new int[20];
            while(o <= 330) {
                fa3[i] += o;
                i++;
                o++;
            }
        }

        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("-15") && townarea.get(i).contains(towntext) && contains(fa3, Integer.parseInt(floor_area.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total1 += temp;
                count1++;
            }
        }
        if(count1 == 0) {
            count1++;
        }
        average1 = total1 / count1;
        averageperyearfilter.add(Long.toString(average1));
        eachyear1.add("2015");
        Log.d("2015 Filter Condo :", Long.toString(average1));

        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("-16") && townarea.get(i).contains(towntext) && contains(fa3, Integer.parseInt(floor_area.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total2 += temp;
                count2++;
            }
        }
        if(count2 == 0) {
            count2++;
        }
        average2 = total2 / count2;
        averageperyearfilter.add(Long.toString(average2));
        eachyear1.add("2016");
        Log.d("2016 Filter Condo :", Long.toString(average2));

        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("-17") && townarea.get(i).contains(towntext) && contains(fa3, Integer.parseInt(floor_area.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total3 += temp;
                count3++;
            }
        }
        if(count3 == 0) {
            count3++;
        }
        average3 = total3 / count3;
        averageperyearfilter.add(Long.toString(average3));
        eachyear1.add("2017");
        Log.d("2017 Filter Condo :", Long.toString(average3));

        for(int i = 0; i<year.size(); i++) {
            if(year.get(i).contains("-18") && townarea.get(i).contains(towntext) && contains(fa3, Integer.parseInt(floor_area.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total4 += temp;
                count4++;
            }
        }
        if(count4 == 0) {
            count4++;
        }
        average4 = total4 / count4;
        averageperyearfilter.add(Long.toString(average4));
        eachyear1.add("2018");
        Log.d("2018 Filter Condo :", Long.toString(average4));

        return eachyear1;
    }

    public double calculateTotalAverageResalePriceCondo (ArrayList<String> price) {
        long count = 0;
        long total = 0;
        long average = 0;
        for(int i = 0; i< price.size(); i++) {
            total += Double.parseDouble(price.get(i));
            count++;
        }
        average = total / count;
        return average;
    }

    public double calculateTotalFilterResalePriceCondo (ArrayList<String> year, ArrayList<String> price, ArrayList<String> townarea, ArrayList<String> floorarea, String towntext, String floorareatext) {
        long count = 0;
        long average = 0;
        long total = 0;
        if(floorareatext.contains("30-50")) {
            int o = 30;
            int i = 0;
            fa4 = new int[21];
            while(o <= 50) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("51-70")) {
            int o = 51;
            int i = 0;
            fa4 = new int[20];
            while(o <= 70) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("71-90")) {
            int o = 71;
            int i = 0;
            fa4 = new int[20];
            while(o <= 90) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("91-110")) {
            int o = 91;
            int i = 0;
            fa4 = new int[20];
            while(o <= 110) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("111-130")) {
            int o = 111;
            int i = 0;
            fa4 = new int[20];
            while (o <= 130) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("131-150")) {
            int o = 131;
            int i = 0;
            fa4 = new int[20];
            while (o <= 150) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("151-170")) {
            int o = 151;
            int i = 0;
            fa4 = new int[20];
            while(o <= 170) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("171-190")) {
            int o = 171;
            int i = 0;
            fa4 = new int[20];
            while (o <=190) {
                fa4[i] += o;
                i++;
                o++;
            }
            Log.d("TEST",Arrays.toString(fa4));
        } else if (floorareatext.contains("191-210")) {
            int o = 191;
            int i = 0;
            fa4 = new int[20];
            while(o <= 210) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("211-230")) {
            int o = 211;
            int i = 0;
            fa4 = new int[20];
            while(o <= 230) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("231-250")) {
            int o = 231;
            int i = 0;
            fa4 = new int[20];
            while(o <= 250) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("251-270")) {
            int o = 251;
            int i = 0;
            fa4 = new int[20];
            while(o <= 270) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("271-290")) {
            int o = 271;
            int i = 0;
            fa4 = new int[20];
            while(o <= 290) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("291-310")) {
            int o = 291;
            int i = 0;
            fa4 = new int[20];
            while(o <= 310) {
                fa4[i] += o;
                i++;
                o++;
            }
        } else if (floorareatext.contains("311-330")) {
            int o = 311;
            int i = 0;
            fa4 = new int[20];
            while(o <= 330) {
                fa4[i] += o;
                i++;
                o++;
            }
        }
        for(int i = 0; i<year.size(); i++) {
            if(townarea.get(i).contains(towntext) && contains(fa4, Integer.parseInt(floorarea.get(i)))) {
                Double temp = Double.parseDouble(price.get(i));
                total += temp;
                count++;
            }
        }
        if (count == 0) {
            count++;
        }
        average = total / count;
        return average;
    }
    public static boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }

}
