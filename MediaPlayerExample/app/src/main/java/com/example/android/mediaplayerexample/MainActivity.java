package com.example.android.mediaplayerexample;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    int audioDuration;
    MediaPlayer audioPlayer;
    SeekBar audioProgress;
    Handler progressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.progressHandler = new Handler();
        this.audioPlayer = MediaPlayer.create(this, R.raw.demo);
        this.audioDuration = this.audioPlayer.getDuration();
        this.audioProgress = (SeekBar) findViewById(R.id.seek_bar_audio);
        this.audioProgress.setProgress(0);
        this.audioProgress.setMax(this.audioDuration);
        Log.i("TOTAL", this.audioDuration + "");
    }

    public void onClickProgressCtrl(View view) {
        int newTimeStamp = this.audioPlayer.getCurrentPosition();
        Log.i("Current time", "" + newTimeStamp);
        switch (view.getId()) {
            case R.id.btn_forward:
                newTimeStamp = newTimeStamp + (5 * 1000);
                if (newTimeStamp > this.audioDuration) {
                    newTimeStamp = this.audioDuration;
                    Toast.makeText(getApplicationContext(),
                            "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_back:
                newTimeStamp = newTimeStamp - (5 * 1000);
                if (newTimeStamp < 0) {
                    newTimeStamp = 0;
                    Toast.makeText(getApplicationContext(),
                            "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return;
        }
        this.audioProgress.setProgress(newTimeStamp);
        Log.i("AAAA", "" + this.audioProgress.getProgress());
        this.audioPlayer.seekTo(newTimeStamp);
        Log.i("AAAA", "" + this.audioPlayer.getCurrentPosition());
    }

    public void onClickStatusCtrl(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (!this.audioPlayer.isPlaying()) {
                    this.audioPlayer.start();
                    this.progressHandler.postDelayed(UpdateSongTime, 100);
                }
                break;
            case R.id.btn_pause:
                if (this.audioPlayer.isPlaying()) {
                    this.audioPlayer.pause();
                    this.progressHandler.removeCallbacks(UpdateSongTime);
                }
                break;
            case R.id.btn_stop:
                if (this.audioPlayer.isPlaying()) {
                    this.audioPlayer.stop();
                    this.audioPlayer = MediaPlayer.create(this, R.raw.demo);
                    this.progressHandler.removeCallbacks(UpdateSongTime);
                    this.audioProgress.setProgress(0);
                }
                break;
            default:
                break;
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            long currTimestamp = audioPlayer.getCurrentPosition();
            audioProgress.setProgress((int) currTimestamp);
            progressHandler.postDelayed(this, 100);
        }
    };
}
