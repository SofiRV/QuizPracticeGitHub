package com.example.quizpractice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Preventing repeat answers(deshabilitar botones de respuesta cuando ya el usuario respondio)
    //Graded quiz(Contar correctas)

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button previousButton;
    private TextView questionTextView;
    private ImageView questionImageView;
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    private ArrayList<Question> questions;

    String[] questionsArray;
    String[] answersArray;
    String[] imagesArray;

    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        questionImageView = findViewById(R.id.question_image_view);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);


        trueButton.setOnClickListener(v -> checkAnswer(true));
        falseButton.setOnClickListener(v -> checkAnswer(false));
        nextButton.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
            updateQuestion();
        });
        previousButton.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex - 1 + questions.size()) % questions.size();
            updateQuestion();
        });

        questionsArray = getResources().getStringArray(R.array.questions_array);
        answersArray = getResources().getStringArray(R.array.answers_array);
        imagesArray = getResources().getStringArray(R.array.images_array);


        questions = new ArrayList<>();
        for (int i = 0; i < questionsArray.length; i++) {
            boolean answerTrue = Boolean.parseBoolean(answersArray[i]);
            questions.add(new Question(questionsArray[i], answerTrue));
        }

        updateQuestion();
    }

    private void updateQuestion() {
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
        String questionText = questions.get(currentQuestionIndex).getText();
        questionTextView.setText(questionText);
        String imageName=imagesArray[currentQuestionIndex];
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if(imageResId != 0) {
            questionImageView.setImageResource(imageResId);
        }else{
            questionImageView.setImageResource(android.R.color.transparent);
        }
    }

    private void checkAnswer(boolean userAnswer) {

        boolean correctAnswer = questions.get(currentQuestionIndex).isAnswerTrue();
        int messageResId = 0;
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentQuestionIndex);
    }
}
