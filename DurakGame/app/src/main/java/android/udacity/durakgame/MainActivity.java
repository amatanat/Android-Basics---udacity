package android.udacity.durakgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int mScoreTeamA = 0;
    int mScoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        if (scoreView != null)
            scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        if (scoreView != null)
            scoreView.setText(String.valueOf(score));
    }

    public void addPoint10ToA(View view){
        mScoreTeamA += 10;
        displayForTeamA(mScoreTeamA);

    }

    public void addPoint10ToB(View view){
        mScoreTeamB += 10;
        displayForTeamB(mScoreTeamB);
    }

    public void addPoint11ToB(View view){
        mScoreTeamB += 11;
        displayForTeamB(mScoreTeamB);
    }

    public void addPoint12ToB(View view){
        mScoreTeamB += 12;
        displayForTeamB(mScoreTeamB);
    }

    public void addPoint13ToB(View view){
        mScoreTeamB += 13;
        displayForTeamB(mScoreTeamB);
    }

    public void addPoint20ToB(View view){
        mScoreTeamB += 20;
        displayForTeamB(mScoreTeamB);
    }

    public void addPoint11ToA(View view){
        mScoreTeamA += 11;
        displayForTeamA(mScoreTeamA);
    }

    public void addPoint12ToA(View view){
        mScoreTeamA += 12;
        displayForTeamA(mScoreTeamA);
    }

    public void addPoint13ToA(View view){
        mScoreTeamA += 13;
        displayForTeamA(mScoreTeamA);
    }

    public void addPoint20ToA(View view){
        mScoreTeamA += 20;
        displayForTeamA(mScoreTeamA);
    }

    public void reset(View view){
        mScoreTeamB = 0;
        mScoreTeamA = 0;
        displayForTeamA(mScoreTeamA);
        displayForTeamB(mScoreTeamB);
    }

}
