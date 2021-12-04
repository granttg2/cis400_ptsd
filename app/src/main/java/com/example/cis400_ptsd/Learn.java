package com.example.cis400_ptsd;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Learn extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        //TextView contact_text = findViewById(R.id.important_contacts);
        TextView vet_crisis_text = findViewById(R.id.veteranCrisis);
        vet_crisis_text.setClickable(true);
        vet_crisis_text.setMovementMethod(LinkMovementMethod.getInstance());
        String vetSite = "<a href='https://www.veteranscrisisline.net'> Veteran Crisis Hotline:</a> \n call : 1–800–273–8255  press 1 \n or Text 838255 </a>";
        vet_crisis_text.setText(Html.fromHtml(vetSite));
        TextView center_for_ptsd_text = findViewById(R.id.nationalPTSD);
        center_for_ptsd_text.setClickable(true);
        center_for_ptsd_text.setMovementMethod(LinkMovementMethod.getInstance());
        String nationalPTSDSite = "<a href='https://www.ptsd.va.gov/'> National Center for PTSD:</a> (802) 296–6300";
        center_for_ptsd_text.setText(Html.fromHtml(nationalPTSDSite));
        TextView support_groups_text = findViewById(R.id.suicideHotline);
        support_groups_text.setClickable(true);
        support_groups_text.setMovementMethod(LinkMovementMethod.getInstance());
        String groupSite = "<a href='https://militarysupportgroups.org/home/'>Military Support Groups Of America</a>";
        support_groups_text.setText(Html.fromHtml(groupSite));
        TextView wounded_warrior_text = findViewById(R.id.woundedWarriors);
        wounded_warrior_text.setClickable(true);
        wounded_warrior_text.setMovementMethod(LinkMovementMethod.getInstance());
        String wwSite = "<a href='https://www.woundedwarriorproject.org/programs/warrior-care-network?gclid=Cj0KCQiAnaeNBhCUARIsABEee8UCHmpugtZT5D05gbeanCoKtjsDzW-8jTDThq0ZKprqc_nTyToq4CgaAryIEALw_wcB&gclsrc=aw.ds'>Wounded Warrior Project:</a> (888) 997–2586";
        wounded_warrior_text.setText(Html.fromHtml(wwSite));

    }
}
