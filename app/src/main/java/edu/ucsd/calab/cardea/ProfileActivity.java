package edu.ucsd.calab.cardea;

import android.app.Activity;
import android.os.Bundle;


/** SharedPreferences */

public class ProfileActivity extends Activity {

/*        CheckBox checkBox1;
        EditText editText1;

        String walkingTime;
        String runningTime;
        String sittingTime;
        String standingTime;
        String lyingDownTime;*/

/*        Button button;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
    }
}

/*
            checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
            editText1 = (EditText) findViewById(R.id.editText1);



            button = (Button) findViewById(R.id.button1);
            button.setOnClickListener(this);


            loadSavedPreferences1();


            updateGoalstoSever();

        }

        private void loadSavedPreferences1() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value1", false);
            String name = sharedPreferences.getString("Walking", "0");
            if (checkBoxValue) {
                checkBox1.setChecked(true);
            } else {
                checkBox1.setChecked(false);
            }

            editText1.setText(name);
        }



        private void savePreferences1(String key, boolean value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        private void savePreferences1(String key, String value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }





    @Override
        public void onClick(View v) {

            savePreferences1("CheckBox_Value1", checkBox1.isChecked());
            if (checkBox1.isChecked())
                savePreferences1("Walking", editText1.getText().toString());



        Toast.makeText(getApplicationContext(),
                "Goals Updated",Toast.LENGTH_SHORT).show();

        }



        */
/** upload sharedpreference file to sever*//*

        private void updateGoalstoSever() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue1 = sharedPreferences.getBoolean("CheckBox_Value1", false);
            if (checkBoxValue1) {
                walkingTime = sharedPreferences.getString("Walking", "0");
            } else {
                walkingTime = "0";
            }
            boolean checkBoxValue2 = sharedPreferences.getBoolean("CheckBox_Value2", false);
            if (checkBoxValue2) {
                runningTime = sharedPreferences.getString("Running", "0");
            } else {
                runningTime = "0";
            }
            boolean checkBoxValue3 = sharedPreferences.getBoolean("CheckBox_Value3", false);
            if (checkBoxValue3) {
                sittingTime = sharedPreferences.getString("Sitting", "0");
            } else {
                sittingTime = "0";
            }
            boolean checkBoxValue4 = sharedPreferences.getBoolean("CheckBox_Value4", false);
            if (checkBoxValue4) {
                standingTime = sharedPreferences.getString("Standing", "0");
            } else {
                standingTime = "0";
            }
            boolean checkBoxValue5 = sharedPreferences.getBoolean("CheckBox_Value5", false);
            if (checkBoxValue5) {
                lyingDownTime = sharedPreferences.getString("Lying_Down", "0");
            } else {
                lyingDownTime = "0";
            }

            */
/** hasmap goaltime saved as string*//*

            Map<String, String> goalMap = new HashMap<>();
            goalMap.put("Walking",walkingTime);
            goalMap.put("Running",runningTime);
            goalMap.put("Sitting",sittingTime);
            goalMap.put("Standing",standingTime);
            goalMap.put("Lying_Down",lyingDownTime);

            */
/** json goaltime saved as string*//*

            JSONObject goalJson = new JSONObject(goalMap);





        }
        }
*/



/** addGoalButton = (Button)findViewById(R.id.addGoalButton);
 addGoalButton.setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View arg0) {


FragmentManager fm = getFragmentManager();
FragmentTransaction ft = fm.beginTransaction();
GoalFragment gf = new GoalFragment();
ft.add(R.id.add_goal_fragment_container, gf);
ft.commit();

}
});  */

