package com.example.android.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initGithubUser() {
        GitHubClient client = ServiceGenerator.createService(GitHubClient.class);
        Call<GitHubUser> call = client.user();
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> c, Response<GitHubUser> response) {
                updateUserCardView(response.body().getName());
                Log.i("jferroal", "response=" + response.body().getBio());
            }

            @Override
            public void onFailure(Call<GitHubUser> res, Throwable t) {
                Log.i("jferroal", "onFailure=" + t.getMessage());
            }
        });
    }

    private void updateUserCardView(String name) {
        TextView tv_name = (TextView) findViewById(R.id.name);
        tv_name.setText(name);
    }

    public void onClickShowBtn(View view) {
        this.initGithubUser();
    }
}
