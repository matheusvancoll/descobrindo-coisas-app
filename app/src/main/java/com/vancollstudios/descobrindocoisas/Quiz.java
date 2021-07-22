package com.vancollstudios.descobrindocoisas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {

    private List<QuizQuestions> quizQuestionsList;

    private TextView txtQuestion, txtScore, txtNumberQuestion, txtTimer;
    private RadioGroup radioGroup;
    private RadioButton rButton1, rButton2, rButton3;
    private Button btnNext;

    int totalQuestions;
    int quizCounter;
    int score;
    private QuizQuestions currentQuestion;

    ColorStateList defaultColor;
    boolean anCorrect;

    CountDownTimer countTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizQuestionsList = new ArrayList<>();

        txtQuestion = findViewById(R.id.txtQuestion);
        txtNumberQuestion = findViewById(R.id.txtNumberQuestion);
        txtScore = findViewById(R.id.txtScore);
        txtTimer = findViewById(R.id.txtTimer);
        btnNext = findViewById(R.id.btnNextQuestion);

        radioGroup = findViewById(R.id.rdGroupQuestion);
        rButton1 = findViewById(R.id.rbOpt1);
        rButton2 = findViewById(R.id.rbOpt2);
        rButton3 = findViewById(R.id.rbOpt3);

        defaultColor = rButton1.getTextColors();

        newQuestion();
        totalQuestions = quizQuestionsList.size();

        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anCorrect == false) {
                    if (rButton1.isChecked() || rButton2.isChecked() || rButton3.isChecked()) {
                        checkAsk();
                        countTimer.cancel();
                    }else {
                        Toast.makeText(Quiz.this, "Ops! Parece que esqueceu de marcar a resposta!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestion();
                }
            }
        });
    }

    private void newQuestion() {
        quizQuestionsList.add(new QuizQuestions("Que personagem da mitologia grega era metade homem metade cavalo?", "Tártaro", "Centauro", "Górgona", 2));
        quizQuestionsList.add(new QuizQuestions("Que povo foi o primeiro a utilizar a bússola?", "Chineses", "Romanos", "Gregos",1));
        quizQuestionsList.add(new QuizQuestions("Em que país nasceu o Conde Drácula?", "Escócia", "Irlanda", "Transilvânia",3));
        quizQuestionsList.add(new QuizQuestions("O português é a língua oficial nesses três países:", "Guiné Equatorial, Cabo Verde e Angola", "Guiné-Bissau, África do Sul e Brasil", "Macau, Timor-Leste e Moçambique",2));
        quizQuestionsList.add(new QuizQuestions("Quantos dias, aproximadamente, a Lua demora para dar uma volta à Terra?", "1 dia", "365 dias", "28 dias",3));
        quizQuestionsList.add(new QuizQuestions("Qual desses não é um instrumento meteorológico?", "Etilômetro", "Barógrafo", "Termômetro",1));
        quizQuestionsList.add(new QuizQuestions("Qual o significado da expressão “calcanhar de Aquiles”?", "Fuga dos problemas", "O ponto mais vulnerável de alguém", "Parte em que as pessoas concentram a sua força",2));
        quizQuestionsList.add(new QuizQuestions("Qual dessas aves não voa?", "Pinguim", "Cegonha", "Galinha",1));

    }


    private void checkAsk() {
        anCorrect = true;
        RadioButton rButtonSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int anSelected = radioGroup.indexOfChild(rButtonSelected) + 1;
        if (anSelected == currentQuestion.getAnCorrect()) {
            score++;
            txtScore.setText("Pontuação: " + score);
        }
        rButton1.setTextColor(Color.RED);
        rButton2.setTextColor(Color.RED);
        rButton3.setTextColor(Color.RED);

        switch (currentQuestion.getAnCorrect()) {
            case 1:
                rButton1.setTextColor(Color.GREEN);
                break;
            case 2:
                rButton2.setTextColor(Color.GREEN);
                break;
            case 3:
                rButton3.setTextColor(Color.GREEN);
                break;
        }
        if (quizCounter < totalQuestions) {
            btnNext.setText("Próxima Pergunta");
        }else {
            btnNext.setText("Finalizar");
        }
    }

    private void showNextQuestion() {
        radioGroup.clearCheck();
        rButton1.setTextColor(defaultColor);
        rButton2.setTextColor(defaultColor);
        rButton3.setTextColor(defaultColor);

        if (quizCounter < totalQuestions) {
            countTimerQuestion();
            currentQuestion = quizQuestionsList.get(quizCounter);
            txtQuestion.setText(currentQuestion.getQuestion());
            rButton1.setText(currentQuestion.getOption1());
            rButton2.setText(currentQuestion.getOption2());
            rButton3.setText(currentQuestion.getOption3());

            quizCounter++;
            btnNext.setText("Responder");
            txtNumberQuestion.setText("Pergunta "+quizCounter+"/"+totalQuestions);
            anCorrect = false;
        }else{
            currentQuestion.setScore(score);
            Toast.makeText(Quiz.this, "Você marcou: " + score + " pontos!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void countTimerQuestion() {
        countTimer = new CountDownTimer(26000, 1000) {
            @Override
            public void onTick(long i) {
                txtTimer.setText("00:" + i/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }


}
