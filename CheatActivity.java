package com.example.geroquiz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.geroquiz.MainActivity.EXTRA_ANSWER_IS_TRUE;

public class CheatActivity extends AppCompatActivity {
    private String TAG="CheatActivity";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private String KEY_CHEAT ="UserCheated";
    private String KEY_ANSWER="key_answer";
    private String ANSWER;
    private boolean cheated=false;
    public static final String EXTRA_ANSWER_SHOWN =
            "com.example.geroquiz.answer_shown";
    private Button mShowButton;
    private TextView mAPILevelText;

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        if (savedInstanceState!= null) {
            ANSWER=savedInstanceState.getString(KEY_ANSWER);
            cheated = savedInstanceState.getBoolean(KEY_CHEAT);
        }
        mAnswerTextView.setText(ANSWER);
        setAnswerShownResult(cheated);

        mShowButton = (Button)findViewById(R.id.showAnswerButton);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    ANSWER = "True";
                    mAnswerTextView.setText(ANSWER);
                }
                else {
                    ANSWER = "False";
                    mAnswerTextView.setText(ANSWER);
                }
                cheated=true;
                setAnswerShownResult(cheated);

            }
        });
        //Challenge;
        mAPILevelText =(TextView)findViewById(R.id.APILevel);
        String tra = "API level " + Build.VERSION.SDK_INT;
        mAPILevelText.setText(tra);

    }
    @Override
    public  void onSaveInstanceState(Bundle savedInstanceState ){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_CHEAT,cheated);
        savedInstanceState.putString(KEY_ANSWER,ANSWER);
    }
}
