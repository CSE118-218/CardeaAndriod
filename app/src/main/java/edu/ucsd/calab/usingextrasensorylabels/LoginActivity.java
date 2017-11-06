package edu.ucsd.calab.usingextrasensorylabels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.jiuzhang.guojing.dribbbo.dribbble.Dribbble;
import com.jiuzhang.guojing.dribbbo.dribbble.auth.Auth;
import com.jiuzhang.guojing.dribbbo.dribbble.auth.AuthActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.activity_login_btn)
    TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // load access token from shared preference
        Cardea.init(this);

        if (!Cardea.isLoggedIn()) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Auth.openAuthActivity(LoginActivity.this);
                }
            });
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Auth.REQ_CODE && resultCode == RESULT_OK) {
            final String authCode = data.getStringExtra(AuthActivity.KEY_CODE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // this is a network call and it's time consuming
                        // that's why we're doing this in a non-UI thread
                        String token = Auth.fetchAccessToken(authCode);

                        // store access token in SharedPreferences
                        Cardea.login(LoginActivity.this, token);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (IOException | JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
