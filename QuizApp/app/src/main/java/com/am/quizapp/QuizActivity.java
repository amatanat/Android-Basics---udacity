package com.am.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private int mScore;
  private RadioButton questionOneTrueRB;
  private RadioButton questionThreeFalseRB;
  private RadioGroup questionOneRadioGroup;
  private RadioGroup questionThreeRadioGroup;
  private CheckBox questionTwoChBOne;
  private CheckBox questionTwoChBTwo;
  private CheckBox questionTwoChBThree;
  private CheckBox questionTwoChBFour;
  private CheckBox questionFourChBOne;
  private CheckBox questionFourChBTwo;
  private CheckBox questionFourChBThree;
  private CheckBox questionFourChBFour;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    mScore = 0;

    questionOneTrueRB = (RadioButton) findViewById(R.id.question_1_true);
    questionThreeFalseRB = (RadioButton) findViewById(R.id.question_3_false);

    questionThreeRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_3);
    questionOneRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_1);

    questionTwoChBOne = (CheckBox) findViewById(R.id.question_2_chb_1);
    questionTwoChBTwo = (CheckBox) findViewById(R.id.question_2_chb_2);
    questionTwoChBThree = (CheckBox) findViewById(R.id.question_2_chb_3);
    questionTwoChBFour = (CheckBox) findViewById(R.id.question_2_chb_4);

    questionFourChBOne = (CheckBox) findViewById(R.id.question_4_chb_1);
    questionFourChBTwo = (CheckBox) findViewById(R.id.question_4_chb_2);
    questionFourChBThree =  (CheckBox) findViewById(R.id.question_4_chb_3);
    questionFourChBFour = (CheckBox) findViewById(R.id.question_4_chb_4);

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

  private void submitAnswers() {

    if (questionOneTrueRB.isChecked()){
      mScore++;
    }

    if (questionThreeFalseRB.isChecked()){
      mScore++;
    }

    if (questionTwoChBOne.isChecked() && questionTwoChBFour.isChecked() &&
        !questionTwoChBThree.isChecked() && !questionTwoChBTwo.isChecked()){
      mScore++;
    }

    if (!questionFourChBOne.isChecked() && !questionFourChBTwo.isChecked() &&
        !questionFourChBThree.isChecked() && questionFourChBFour.isChecked()){
      mScore++;
    }
    Toast.makeText(this, getString(R.string.final_score) + " " + mScore, Toast.LENGTH_SHORT).show();
    mScore = 0;
  }

  private void resetAnswers() {
    questionThreeRadioGroup.clearCheck();
    questionOneRadioGroup.clearCheck();

    if(questionTwoChBOne.isChecked())
      questionTwoChBOne.setChecked(false);
    if(questionTwoChBTwo.isChecked())
      questionTwoChBTwo.setChecked(false);
    if(questionTwoChBThree.isChecked())
      questionTwoChBThree.setChecked(false);
    if(questionTwoChBFour.isChecked())
      questionTwoChBFour.setChecked(false);

    if(questionFourChBOne.isChecked())
      questionFourChBOne.setChecked(false);
    if(questionFourChBTwo.isChecked())
      questionFourChBTwo.setChecked(false);
    if(questionFourChBThree.isChecked())
      questionFourChBThree.setChecked(false);
    if(questionFourChBFour.isChecked())
      questionFourChBFour.setChecked(false);
  }
}
