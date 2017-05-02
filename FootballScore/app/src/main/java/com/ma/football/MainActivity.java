package com.ma.football;

import android.ma.football.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mGoalScoreTeamA ;
    private int mGoalScoreTeamB ;

    private int mYellowCardScoreTeamA ;
    private int mYellowCardScoreTeamB ;

    private int mRedCardScoreTeamA ;
    private int mRedCardScoreTeamB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mGoalScoreTeamA = savedInstanceState.getInt("GoalTeamA");
            mGoalScoreTeamB = savedInstanceState.getInt("GoalTeamB");

            mYellowCardScoreTeamA = savedInstanceState.getInt("YellowTeamA");
            mYellowCardScoreTeamB = savedInstanceState.getInt("YellowTeamB");

            mRedCardScoreTeamA = savedInstanceState.getInt("RedTeamA");
            mRedCardScoreTeamB = savedInstanceState.getInt("RedTeamB");
        }

        setContentView(R.layout.activity_main);

        displayForTeamA(mGoalScoreTeamA);
        displayForTeamB(mGoalScoreTeamB);
        displayYellowCardForTeamA(mYellowCardScoreTeamA);
        displayYellowCardForTeamB(mYellowCardScoreTeamB);
        displayRedCardForTeamA(mRedCardScoreTeamA);
        displayRedCardForTeamB(mRedCardScoreTeamB);
    }


    private void displayForTeamA(int score) {
        TextView goalScoreView = (TextView) findViewById(R.id.teama_goalscore);
        if (goalScoreView != null)
            goalScoreView.setText(String.valueOf(score));
    }

    private void displayForTeamB(int score) {
        TextView goalScoreView = (TextView) findViewById(R.id.teamb_goalscore);
        if (goalScoreView != null)
            goalScoreView.setText(String.valueOf(score));
    }

    private void displayYellowCardForTeamB(int score) {
        TextView yellowCardScoreView = (TextView) findViewById(R.id.teamb_yellowcard_score);
        if (yellowCardScoreView != null)
            yellowCardScoreView.setText(String.valueOf(score));
    }

    private void displayYellowCardForTeamA(int score) {
        TextView yellowCardScoreView = (TextView) findViewById(R.id.teama_yellowcard_score);
        if (yellowCardScoreView != null)
            yellowCardScoreView.setText(String.valueOf(score));
    }

    private void displayRedCardForTeamA(int score) {
        TextView redCardScoreView = (TextView) findViewById(R.id.teama_redcard_score);
        if (redCardScoreView != null)
            redCardScoreView.setText(String.valueOf(score));
    }

    private void displayRedCardForTeamB(int score) {
        TextView redCardScoreView = (TextView) findViewById(R.id.teamb_redcard_score);
        if (redCardScoreView != null)
            redCardScoreView.setText(String.valueOf(score));
    }

    public void addGoalToTeamA(View view){
        mGoalScoreTeamA += 1;
        displayForTeamA(mGoalScoreTeamA);
    }

    public void addGoalToTeamB(View view){
        mGoalScoreTeamB += 1;
        displayForTeamB(mGoalScoreTeamB);
    }

    public void addYellowCardToTeamA(View view){
        mYellowCardScoreTeamA += 1;
        displayYellowCardForTeamA(mYellowCardScoreTeamA);
    }

    public void addYellowCardToTeamB(View view){
        mYellowCardScoreTeamB += 1;
        displayYellowCardForTeamB(mYellowCardScoreTeamB);
    }

    public void addRedCardToTeamB(View view){
        mRedCardScoreTeamB += 1;
        displayRedCardForTeamB(mRedCardScoreTeamB);
    }

    public void addRedCardToTeamA(View view){
        mRedCardScoreTeamA += 1;
        displayRedCardForTeamA(mRedCardScoreTeamA);
    }

    public void reset(View view){
        mGoalScoreTeamA = 0;
        mGoalScoreTeamB = 0;
        displayForTeamA(mGoalScoreTeamA);
        displayForTeamB(mGoalScoreTeamB);

        mYellowCardScoreTeamA = 0;
        mYellowCardScoreTeamB = 0;
        displayYellowCardForTeamA(mYellowCardScoreTeamA);
        displayYellowCardForTeamB(mYellowCardScoreTeamB);

        mRedCardScoreTeamA = 0;
        mRedCardScoreTeamB = 0;
        displayRedCardForTeamA(mRedCardScoreTeamA);
        displayRedCardForTeamB(mRedCardScoreTeamB);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("GoalTeamA", mGoalScoreTeamA);
        savedInstanceState.putInt("GoalTeamB", mGoalScoreTeamB);

        savedInstanceState.putInt("YellowTeamA", mYellowCardScoreTeamA);
        savedInstanceState.putInt("YellowTeamB", mYellowCardScoreTeamB);

        savedInstanceState.putInt("RedTeamA", mRedCardScoreTeamA);
        savedInstanceState.putInt("RedTeamB", mRedCardScoreTeamB);

        super.onSaveInstanceState(savedInstanceState);
    }

}
