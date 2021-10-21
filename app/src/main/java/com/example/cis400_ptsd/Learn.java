package com.example.cis400_ptsd;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Learn extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        TextView contact_text = findViewById(R.id.contacts);
        TextView vet_crisis_text = findViewById(R.id.veteranCrisis);
        TextView center_for_ptsd_text = findViewById(R.id.nationalPTSD);
        TextView suicide_hotline_text = findViewById(R.id.suicideHotline);
        TextView wounded_warrior_text = findViewById(R.id.woundedWarriors);

    }
}
