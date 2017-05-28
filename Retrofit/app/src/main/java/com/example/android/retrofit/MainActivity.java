package com.example.android.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        Call<GitHubUser> call = client.user("ezirmusitua");
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> c, Response<GitHubUser> response) {
                updateUserCardView(response.body());
            }

            @Override
            public void onFailure(Call<GitHubUser> c, Throwable t) {
                Log.i("jferroal", "onFailure=" + t.getMessage());
            }
        });
    }

    private void updateUserCardView(GitHubUser user) {
        ImageView ig_avatar = (ImageView) findViewById(R.id.avatar);
        Picasso.with(getApplicationContext()).load(user.getAvatar_url()).transform(new CircleTransform()).into(ig_avatar);
        TextView tv_name = (TextView) findViewById(R.id.name);
        tv_name.setText(user.getName());
        TextView tv_username = (TextView) findViewById(R.id.username);
        tv_username.setText(user.getLogin());
        TextView tv_company = (TextView) findViewById(R.id.company);
        tv_company.setText(user.getCompany());
        TextView tv_location = (TextView) findViewById(R.id.location);
        tv_location.setText(user.getLocation());
        TextView tv_bio = (TextView) findViewById(R.id.bio);
        tv_bio.setText(user.getBio());
    }

    public void onClickShowBtn(View view) {
        this.initGithubUser();
    }
}
