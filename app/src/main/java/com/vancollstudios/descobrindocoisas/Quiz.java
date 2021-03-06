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
        quizQuestionsList.add(new QuizQuestions("Que personagem da mitologia grega era metade homem metade cavalo?", "T??rtaro", "Centauro", "G??rgona", 2));
        quizQuestionsList.add(new QuizQuestions("Que povo foi o primeiro a utilizar a b??ssola?", "Chineses", "Romanos", "Gregos",1));
        quizQuestionsList.add(new QuizQuestions("Em que pa??s nasceu o Conde Dr??cula?", "Esc??cia", "Irlanda", "Transilv??nia",3));
        quizQuestionsList.add(new QuizQuestions("O portugu??s ?? a l??ngua oficial nesses tr??s pa??ses:", "Guin?? Equatorial, Cabo Verde e Angola", "Guin??-Bissau, ??frica do Sul e Brasil", "Macau, Timor-Leste e Mo??ambique",2));
        quizQuestionsList.add(new QuizQuestions("Quantos dias, aproximadamente, a Lua demora para dar uma volta ?? Terra?", "1 dia", "365 dias", "28 dias",3));
        quizQuestionsList.add(new QuizQuestions("Qual desses n??o ?? um instrumento meteorol??gico?", "Etil??metro", "Bar??grafo", "Term??metro",1));
        quizQuestionsList.add(new QuizQuestions("Qual o significado da express??o ???calcanhar de Aquiles????", "Fuga dos problemas", "O ponto mais vulner??vel de algu??m", "Parte em que as pessoas concentram a sua for??a",2));
        quizQuestionsList.add(new QuizQuestions("Qual dessas aves n??o voa?", "Pinguim", "Cegonha", "Galinha",1));

    }


    private void checkAsk() {
        anCorrect = true;
        RadioButton rButtonSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int anSelected = radioGroup.indexOfChild(rButtonSelected) + 1;
        if (anSelected == currentQuestion.getAnCorrect()) {
            score++;
            txtScore.setText("Pontua????o: " + score);
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
            btnNext.setText("Pr??xima Pergunta");
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
            Toast.makeText(Quiz.this, "Voc?? marcou: " + score + " pontos!", Toast.LENGTH_SHORT).show();
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
