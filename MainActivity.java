package com.example.geroquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private String KEY_CHEAT ="UserCheated";
    private String  KEY_QUESTIONS_CHEAT="QuestionCheeating";
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    public static final String EXTRA_ANSWER_IS_TRUE ="com.example.geroquiz.answer_is_true";
    private Button mFalseButton;;
    private Button mTrueButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;
    private boolean mIsCheater;
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.sohaib,true)
    };
    private int mCurrentIndex = 0;

    private boolean[] mCheatedQuestion = new boolean[mQuestionBank.length];

    private void updateQuestion(){
        //Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
          //      new Exception());
        int question =mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);

    }
    private void nextUp() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerTrue = mQuestionBank[mCurrentIndex].getTrueQuestion();
        int messageId;
        if ((mIsCheater)||mCheatedQuestion[mCurrentIndex])
            messageId = R.string.judgment_toast;
        else{
                if (answerTrue == userPressedTrue) {
                    messageId = R.string.correct_toast;
                } else {
                    messageId = R.string.incorrect_toast;
                }
            }
        Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        if ((!mCheatedQuestion[mCurrentIndex])&&(mIsCheater))
                mCheatedQuestion[mCurrentIndex]=mIsCheater;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0 );
            mIsCheater=savedInstanceState.getBoolean(KEY_CHEAT,false);
            mCheatedQuestion=savedInstanceState.getBooleanArray(KEY_QUESTIONS_CHEAT);
        }
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

            }
        });
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextUp();
                mIsCheater = false;
            }
        });
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0)
                    mCurrentIndex = mQuestionBank.length - 1;
                else
                    mCurrentIndex = mCurrentIndex - 1;
                updateQuestion();
                mIsCheater = false;
            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextUp();
            }
        });
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v ){
                Intent i = new Intent(MainActivity.this, CheatActivity.class);
                boolean answerIsTrue=mQuestionBank[mCurrentIndex].getTrueQuestion();
                i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                //startActivity(i);
                startActivityForResult(i, 0);
            }
        });
        updateQuestion();
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEAT,mIsCheater);
        savedInstanceState.putBooleanArray(KEY_QUESTIONS_CHEAT,mCheatedQuestion);
    }

}


