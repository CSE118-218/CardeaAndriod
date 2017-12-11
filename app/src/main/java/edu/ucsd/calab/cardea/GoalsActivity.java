package edu.ucsd.calab.cardea;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.util.*;



/** SharedPreferences */

public class GoalsActivity extends Activity implements OnClickListener {

        CheckBox checkBox1;
        EditText editText1;

        CheckBox checkBox2;
        EditText editText2;

        CheckBox checkBox3;
        EditText editText3;

        CheckBox checkBox4;
        EditText editText4;

        CheckBox checkBox5;
        EditText editText5;

        Double walkingTime;
        Double runningTime;
        Double sittingTime;
        Double standingTime;
        Double lyingDownTime;

        Button button;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.goals_page);


            checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
            editText1 = (EditText) findViewById(R.id.editText1);

            checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
            editText2 = (EditText) findViewById(R.id.editText2);

            checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
            editText3 = (EditText) findViewById(R.id.editText3);

            checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
            editText4 = (EditText) findViewById(R.id.editText4);

            checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
            editText5 = (EditText) findViewById(R.id.editText5);

            button = (Button) findViewById(R.id.button1);
            button.setOnClickListener(this);

            // animate the stroke tips in the goal page
            TextView tv = (TextView) this.findViewById(R.id.stroke_animated_tips);
            tv.setSelected(true);  // Set focus to the textview

            final Button callButton = (Button) findViewById(R.id.health_tips);
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    healthWebsite();


                }
            });

            loadSavedPreferences1();
            loadSavedPreferences2();
            loadSavedPreferences3();
            loadSavedPreferences4();
            loadSavedPreferences5();

        }



        private void healthWebsite() {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.heart.org/HEARTORG/HealthyLiving/How-to-Help-Prevent-Heart-Disease---At-Any-Age_UCM_442925_Article.jsp#"));
            startActivity(browserIntent);
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

        private void loadSavedPreferences2() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value2", false);
            String name = sharedPreferences.getString("Running", "0");
            if (checkBoxValue) {
                checkBox2.setChecked(true);
            } else {
                checkBox2.setChecked(false);
            }

            editText2.setText(name);
        }

        private void loadSavedPreferences3() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value3", false);
            String name = sharedPreferences.getString("Sitting", "0");
            if (checkBoxValue) {
                checkBox3.setChecked(true);
            } else {
                checkBox3.setChecked(false);
            }

            editText3.setText(name);
        }

        private void loadSavedPreferences4() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value4", false);
            String name = sharedPreferences.getString("Standing", "0");
            if (checkBoxValue) {
                checkBox4.setChecked(true);
            } else {
                checkBox4.setChecked(false);
            }

            editText4.setText(name);
        }

        private void loadSavedPreferences5() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value5", false);
            String name = sharedPreferences.getString("Lying_Down", "0");
            if (checkBoxValue) {
                checkBox5.setChecked(true);
            } else {
                checkBox5.setChecked(false);
            }

            editText5.setText(name);
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



        private void savePreferences2(String key, boolean value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        private void savePreferences2(String key, String value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }

        private void savePreferences3(String key, boolean value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        private void savePreferences3(String key, String value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }

        private void savePreferences4(String key, boolean value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        private void savePreferences4(String key, String value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }

        private void savePreferences5(String key, boolean value) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        private void savePreferences5(String key, String value) {
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

            savePreferences2("CheckBox_Value2", checkBox2.isChecked());
            if (checkBox2.isChecked())
                savePreferences2("Running", editText2.getText().toString());

            savePreferences3("CheckBox_Value3", checkBox3.isChecked());
            if (checkBox3.isChecked())
                savePreferences3("Sitting", editText3.getText().toString());

            savePreferences4("CheckBox_Value4", checkBox4.isChecked());
            if (checkBox4.isChecked())
                savePreferences4("Standing", editText4.getText().toString());

            savePreferences5("CheckBox_Value5", checkBox5.isChecked());
            if (checkBox5.isChecked())
                savePreferences5("Lying_Down", editText5.getText().toString());


        Toast.makeText(getApplicationContext(),
                "Goals Updated",Toast.LENGTH_SHORT).show();
        updateGoalstoSever();

        }



        /** upload sharedpreference file to sever*/
        private void updateGoalstoSever() {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
            boolean checkBoxValue1 = sharedPreferences.getBoolean("CheckBox_Value1", false);
            if (checkBoxValue1) {
                walkingTime =  Double.parseDouble(sharedPreferences.getString("Walking", "0"));
            } else {
                walkingTime = 0.0;
            }
            boolean checkBoxValue2 = sharedPreferences.getBoolean("CheckBox_Value2", false);
            if (checkBoxValue2) {
                runningTime = Double.parseDouble(sharedPreferences.getString("Running", "0"));
            } else {
                runningTime = 0.0;
            }
            boolean checkBoxValue3 = sharedPreferences.getBoolean("CheckBox_Value3", false);
            if (checkBoxValue3) {
                sittingTime = Double.parseDouble(sharedPreferences.getString("Sitting", "0"));
            } else {
                sittingTime = 0.0;
            }
            boolean checkBoxValue4 = sharedPreferences.getBoolean("CheckBox_Value4", false);
            if (checkBoxValue4) {
                standingTime = Double.parseDouble(sharedPreferences.getString("Standing", "0"));
            } else {
                standingTime = 0.0;
            }
            boolean checkBoxValue5 = sharedPreferences.getBoolean("CheckBox_Value5", false);
            if (checkBoxValue5) {
                lyingDownTime = Double.parseDouble(sharedPreferences.getString("Lying_Down", "0"));
            } else {
                lyingDownTime = 0.0;
            }

            /** hasmap goaltime saved as string*/
            Map<String, Double> goalMap = new HashMap<>();
            goalMap.put("walking",walkingTime);
            goalMap.put("running",runningTime);
            goalMap.put("sitting",sittingTime);
            goalMap.put("standing",standingTime);
            goalMap.put("lyingDown",lyingDownTime);



            /** json goaltime saved as string*/
            JSONObject goalJson = new JSONObject(goalMap);
            String user = new String("admin");
            try {
                goalJson.put("user", user);
            }
            catch (Exception e) {
                Log.i("invalid user", user);
            }

            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://ec2-54-202-77-233.us-west-2.compute.amazonaws.com:8000/activity/goal";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, goalJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("response", response.toString());
                            //  YOUR RESPONSE
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });
            queue.add(jsonObjReq);
        }

}

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

