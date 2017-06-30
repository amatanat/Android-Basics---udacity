package com.am.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private int mScore;
  private RadioButton questionOneTrueRB;
  private RadioButton questionOneFalseRB;
  private RadioButton questionThreeTrueRB;
  private RadioButton questionThreeFalseRB;
  private RadioGroup questionOneRadioGroup;
  private RadioGroup questionThreeRadioGroup;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    mScore = 0;

    questionOneTrueRB = (RadioButton) findViewById(R.id.question_1_true);
    questionOneFalseRB = (RadioButton) findViewById(R.id.question_1_false);
    questionThreeTrueRB = (RadioButton) findViewById(R.id.question_3_true);
    questionThreeFalseRB = (RadioButton) findViewById(R.id.question_3_false);

    questionThreeRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_3);
    questionOneRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_1);

    Button submitButton = (Button) findViewById(R.id.submit_button);
    submitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        submitAnswers();
      }
    });

    Button resetButton = (Button) findViewById(R.id.reset_button);
    resetButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        resetAnswers();
      }
    });

    Button backButton = (Button) findViewById(R.id.back_button);
    backButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        finish();
      }
    });

  }

/*  public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch(view.getId()) {
      case R.id.true_radiobutton:
        if (checked)
          // true is checked
          break;
      case R.id.false_radiobutton:
        if (checked)
          // false is checked
          break;
    }
  }*/

  private void submitAnswers() {

    if (questionOneTrueRB.isChecked()){
      mScore++;
    }

    if (questionOneFalseRB.isChecked()){
      if (mScore != 0)
        mScore--;
    }

    if (questionThreeTrueRB.isChecked()){
      if (mScore != 0)
        mScore--;
    }

    if (questionThreeFalseRB.isChecked()){
      mScore++;
    }

    Toast.makeText(this, getString(R.string.final_score) + " " + mScore, Toast.LENGTH_SHORT).show();

  }

  private void resetAnswers() {
    questionThreeRadioGroup.clearCheck();
    questionOneRadioGroup.clearCheck();
  }
}
