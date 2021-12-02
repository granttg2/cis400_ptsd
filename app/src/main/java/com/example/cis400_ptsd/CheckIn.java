package com.example.cis400_ptsd;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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

    private void recordMood(int mood){


        getActivity().findViewById(R.id.recycler).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fragmentView).setVisibility(View.GONE);
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }


    private void createGraph(View view){
        ArrayList<Date> dates = new ArrayList();

        Calendar calendar = Calendar.getInstance();
        for (int i =0; i < 20; i++){
            dates.add(calendar.getTime());
            calendar.add(calendar.DATE, 1);
        }

        DataPoint[] data = new DataPoint[dates.size()];

        for (int i =0; i < dates.size(); i++){
            data[i] = new DataPoint(dates.get(i), Math.random() % 3);
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

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

        graph.getViewport().setMinX(dates.get(0).getTime());
        graph.getViewport().setMaxX(dates.get(dates.size() - 1).getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }
}