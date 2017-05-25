package com.example.android.practiceset2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String, Integer> teamScoreMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.teamScoreMap.put("A", 0);
        this.teamScoreMap.put("B", 0);
        setContentView(R.layout.activity_main);
    }
    public void updateScore(View view) {
        int btnId = view.getId();
        switch (btnId) {
            case R.id.team_a_2ps:
                this._updateScore("A", 100);
                this.displayScore("A");
                break;
            case R.id.team_a_3ps:
                this._updateScore("A", 200);
                this.displayScore("A");
                break;
            case R.id.team_a_free:
                this._updateScore("A", 300);
                this.displayScore("A");
                break;
            case R.id.team_b_2ps:
                this._updateScore("B", 100);
                this.displayScore("B");
                break;
            case R.id.team_b_3ps:
                this._updateScore("B", 200);
                this.displayScore("B");
                break;
            case R.id.team_b_free:
                this._updateScore("B", 300);
                this.displayScore("B");
                break;
            default:
                break;
        }
    }

    private void displayScore(String teamName) {
        if (teamName == "A") {
            TextView taScoreTextView = (TextView) findViewById(R.id.team_a_score);
            String scoreText = "" + this.teamScoreMap.get("A");
            taScoreTextView.setText(scoreText);
        }
        if (teamName == "B") {
            TextView tbScoreTextView = (TextView) findViewById(R.id.team_b_score);
            String scoreText = "" + this.teamScoreMap.get("B");
            tbScoreTextView.setText(scoreText);
        }

    }

    private void _updateScore(String teamName, int action) {
        switch (action) {
            case 100:
                this.teamScoreMap.put(teamName, this.teamScoreMap.get(teamName) + 2);
                break;
            case 200:
                this.teamScoreMap.put(teamName, this.teamScoreMap.get(teamName) + 3);
                break;
            case 300:
                this.teamScoreMap.put(teamName, 0);
                break;
        }
    }
}
