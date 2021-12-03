package com.example.cis400_ptsd;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


public class CheckIn extends Fragment {

    public CheckIn() {
        // Required empty public constructor
    }
    public static CheckIn newInstance() {
        CheckIn fragment = new CheckIn();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        createGraph(view);

        TextView bad = view.findViewById(R.id.bad_mood);
        TextView mediocre = view.findViewById(R.id.mediocre_mood);
        TextView great = view.findViewById(R.id.great_mood);

        Fragment currFrag = this;

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {recordMood(0);}});

        mediocre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {recordMood(1);}});

        great.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {recordMood(2);}});


    }

    private void recordMood(int recMood) {
        LinkedHashMap<Date, Integer> moodObject = new LinkedHashMap<Date, Integer>();
        try{
            File check = new File(this.getContext().getFilesDir(), "mood");
            if(check.exists()) {

                FileInputStream file = this.getContext().openFileInput("mood");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
                try {
                    moodObject = (LinkedHashMap<Date, Integer>) input.readObject();
                } finally {
                    input.close();
                }
            }else{
                OutputStream file = this.getContext().openFileOutput("mood", Context.MODE_PRIVATE);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
                try{
                    output.writeObject(moodObject);
                }
                finally{
                    output.close();
                }
            }
        }
        catch(ClassNotFoundException ex){
            Log.e("E", "Class not found");
        }
        catch(IOException ex){
            Log.e("E", "IO Exception");
        }

        try{
            OutputStream file = this.getContext().openFileOutput("mood", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try{
                Date currDate = Calendar.getInstance().getTime();
                if (moodObject.containsKey(currDate)){
                    moodObject.remove(currDate);
                }

                moodObject.put(currDate, recMood);
                output.writeObject(moodObject);
            }
            finally{
                output.close();
            }
        }
        catch(IOException ex){
            Log.e("E", "IO Exception in recording 2");
        }

        getActivity().findViewById(R.id.recycler).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fragmentView).setVisibility(View.GONE);
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }


    private void createGraph(View view){
        LinkedHashMap<Date, Integer> moodObject = new LinkedHashMap<Date, Integer>();
        try{
            File check = new File(this.getContext().getFilesDir(), "mood");
            if(check.exists()) {

                FileInputStream file = this.getContext().openFileInput("mood");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
                try {
                    moodObject = (LinkedHashMap<Date, Integer>) input.readObject();
                } finally {
                    input.close();
                }
            }else{
                OutputStream file = this.getContext().openFileOutput("mood", Context.MODE_PRIVATE);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
                try{
                    output.writeObject(moodObject);
                }
                finally{
                    output.close();
                }
            }
        }
        catch(ClassNotFoundException ex){
            Log.e("E", "Class not found");
        }
        catch(IOException ex){
            Log.e("E", "IO Exception");
        }

        ArrayList<Date> dates = new ArrayList<Date>();

        Calendar calendar = Calendar.getInstance();
        for (Date d: moodObject.keySet()){
            dates.add(d);
        }

        DataPoint[] data = new DataPoint[dates.size()];

        for (int i =0; i < dates.size(); i++){
            Date d = dates.get(i);
            data[i] = new DataPoint(d, moodObject.get(d));
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        series.setDrawDataPoints(true);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.purple_500));
        paint.setStrokeWidth(12);
        series.setCustomPaint(paint);

        graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        if(dates.size() > 0) {
            graph.getViewport().setMinX(dates.get(0).getTime());
            graph.getViewport().setMaxX(dates.get(dates.size() - 1).getTime());
        }
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }
}