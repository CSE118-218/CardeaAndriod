package edu.ucsd.calab.usingextrasensorylabels;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by UsernameChecksOut on 11/5/17.
 */

public class GoalsActivity  extends AppCompatActivity {

    Button addGoalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals_page);

        addGoalButton = (Button)findViewById(R.id.addGoalButton);
        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GoalFragment gf = new GoalFragment();
                ft.add(R.id.add_goal_fragment_container, gf);
                ft.commit();
            }
        });

    }
}
