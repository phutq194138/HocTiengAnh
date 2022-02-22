package com.example.hoctienganh.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.Question;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Word;
import com.example.hoctienganh.adapter.WatchAndChooseResultAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WatchAndChoose extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCurrentQuestion;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, btnHelp, btnExit;
    private ImageView imgQuestion;

    private MediaPlayer correctSound, incorrectSound;

    private List<Question> questionList = new ArrayList<>();
    private int countNumberQuestion = 10;
    private int iCurrentQuestion = 0;
    private int countCorrectQuestion = 0;
    private Question currentQuestion;
    private List<Word> chosenWordList = new ArrayList<>();
    private List<Boolean> chosenBoolList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_and_choose);

        initUi();

        createListQuestion();
        setDataQuestion(questionList.get(iCurrentQuestion));
    }

    private void initUi(){
        tvCurrentQuestion = this.findViewById(R.id.tv_question_watch_and_choose);
        btnAnswer1 = this.findViewById(R.id.btn_answer1_watch_and_choose);
        btnAnswer2 = this.findViewById(R.id.btn_answer2_watch_and_choose);
        btnAnswer3 = this.findViewById(R.id.btn_answer3_watch_and_choose);
        btnAnswer4 = this.findViewById(R.id.btn_answer4_watch_and_choose);
        imgQuestion = this.findViewById(R.id.img_question_watch_and_choose);
        btnExit = this.findViewById(R.id.btn_exit_watch_and_choose);
        btnHelp = this.findViewById(R.id.btn_help_watch_and_choose);

        correctSound = MediaPlayer.create(this, R.raw.correctsound1);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrectsound2);

        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);
        btnAnswer4.setOnClickListener(this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogHelp();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickExit();
            }
        });
    }

    private void clickExit(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Bạn có chắc chắn thoát?");
        builder.setCancelable(false);

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDialogHelp(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_help);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);

        Button btnDone;
        TextView tvHelp;

        btnDone = dialog.findViewById(R.id.btn_done_help);
        tvHelp = dialog.findViewById(R.id.tv_help);

        String str = "Quan sát hình ảnh và\n chọn đáp án đúng.";
        tvHelp.setText(str);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        MyData.soundEffect.start();
        switch (v.getId()){
            case R.id.btn_answer1_watch_and_choose:
                btnAnswer1.setBackgroundResource(R.drawable.background_orange);
                checkAnswer1();
                break;

            case R.id.btn_answer2_watch_and_choose:
                btnAnswer2.setBackgroundResource(R.drawable.background_orange);
                checkAnswer2();
                break;

            case R.id.btn_answer3_watch_and_choose:
                btnAnswer3.setBackgroundResource(R.drawable.background_orange);
                checkAnswer3();
                break;

            case R.id.btn_answer4_watch_and_choose:
                btnAnswer4.setBackgroundResource(R.drawable.background_orange);
                checkAnswer4();
                break;

            default:
                break;
        }
    }

    private void checkAnswer1(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer1.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer1.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer2.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer3.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer4.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer1());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer2(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer2.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer2.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer1.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer3.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer4.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer2());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer3(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer3.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer3.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer1.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer2.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer4.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer3());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer4(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer4.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer4.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer2.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer3.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer1.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer4());
                nextQuestion();
            }
        }, 500);
    }

    private List<Word> createRandomTopic(){
        Random random = new Random();
        int a = random.nextInt(18);
        switch (a){
            case 0:
                return MyData.data.getListAnimal();
            case 1:
                return MyData.data.getListColor();
            case 2:
                return MyData.data.getListClothes();
            case 3:
                return MyData.data.getListFruit();
            case 4:
                return MyData.data.getListFood();
            case 5:
                return MyData.data.getListFlower();
            case 6:
                return MyData.data.getListCountry();
            case 7:
                return MyData.data.getListHouse();
            case 8:
                return MyData.data.getListStudy();
            case 9:
                return MyData.data.getListSport();
            case 10:
                return MyData.data.getListVegetable();
            case 11:
                return MyData.data.getListVehicle();
            case 12:
                return MyData.data.getListJob();
            case 13:
                return MyData.data.getListBody();
            case 14:
                return MyData.data.getListPlace();
            case 15:
                return MyData.data.getListChristmas();
            case 16:
                return MyData.data.getListNumber();
            case 17:
                return MyData.data.getListDrink();

            default:
                break;
        }
        return MyData.data.getListAnimal();
    }

    private void createListQuestion(){
        chosenBoolList.clear();
        chosenWordList.clear();
        questionList.clear();
        countCorrectQuestion = 0;
        while (questionList.size() < countNumberQuestion){
            List<Word> list = createRandomTopic();
            Question question = createQuestion(list);
            if (question != null) {
                questionList.add(question);
            }
        }
    }

    private void setDataQuestion(Question question){
        if (question == null){
            return;
        }

        btnAnswer1.setBackgroundResource(R.drawable.background_default);
        btnAnswer2.setBackgroundResource(R.drawable.background_default);
        btnAnswer3.setBackgroundResource(R.drawable.background_default);
        btnAnswer4.setBackgroundResource(R.drawable.background_default);

        String str = "";
        str = "Câu hỏi " + (iCurrentQuestion + 1) + "/" + countNumberQuestion;
        currentQuestion = question;
        tvCurrentQuestion.setText(str);
        imgQuestion.setImageResource(question.getQuestion().getIdRes());
        btnAnswer1.setText(question.getAnswer1().getWord());
        btnAnswer2.setText(question.getAnswer2().getWord());
        btnAnswer3.setText(question.getAnswer3().getWord());
        btnAnswer4.setText(question.getAnswer4().getWord());
    }

    private void nextQuestion(){
        iCurrentQuestion++;
        if (iCurrentQuestion == countNumberQuestion){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openDialogResult();
                }
            }, 1000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDataQuestion(questionList.get(iCurrentQuestion));
                }
            }, 700);
        }
    }

    public int checkQuestion(Word word){
        for (int i = 0; i < questionList.size(); i++)
            if (word.getWord().equals(questionList.get(i).getQuestion().getWord())) return 0;
        return 1;
    }

    private Question createQuestion(List<Word> list){
        Question question = new Question();

        int a[] = new int[5];
        Random random = new Random();
        int x0 = random.nextInt(list.size());
        int iCheck = checkQuestion(list.get(x0));
        while (iCheck==0) {
            x0 = random.nextInt(list.size());
            iCheck = checkQuestion(list.get(x0));
        }
        int x1 = x0;
        int x2 = random.nextInt(list.size());
        while (x2 == x1) x2 = random.nextInt(list.size());
        int x3 = random.nextInt(list.size());
        while (x3 == x1||x3 == x2) x3 = random.nextInt(list.size());
        int x4 = random.nextInt(list.size());
        while (x4 == x1||x4 == x2||x4 == x3) x4 = random.nextInt(list.size());

        a[0] = x0; a[1] = x1; a[2] = x2; a[3] = x3; a[4] = x4;
        Arrays.sort(a,1,5);
        question.setQuestion(list.get(x0));
        question.setAnswer1(list.get(a[1]));
        question.setAnswer2(list.get(a[2]));
        question.setAnswer3(list.get(a[3]));
        question.setAnswer4(list.get(a[4]));

        return question;
    }

    private void openDialogResult(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_result_watch_and_choose);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);

        Button btnCancer, btnTryAgain;
        TextView tvScore;
        RecyclerView rcvResult;

        btnCancer = dialog.findViewById(R.id.btn_cancer_watch_and_choose);
        btnTryAgain = dialog.findViewById(R.id.btn_try_again_watch_and_choose);
        tvScore = dialog.findViewById(R.id.tv_score_watch_and_choose);
        rcvResult = dialog.findViewById(R.id.rcv_result_watch_and_choose);

        WatchAndChooseResultAdapter resultAdapterWaC = new WatchAndChooseResultAdapter();
        resultAdapterWaC.setData(questionList, chosenBoolList, chosenWordList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dialog.getContext(), RecyclerView.VERTICAL, false);
        rcvResult.setLayoutManager(linearLayoutManager);
        rcvResult.setAdapter(resultAdapterWaC);

        String str = "Điểm số: " + countCorrectQuestion + "/" + countNumberQuestion;
        tvScore.setText(str);

        btnCancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iCurrentQuestion = 0;
                finish();
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createListQuestion();
                iCurrentQuestion = 0;
                setDataQuestion(questionList.get(iCurrentQuestion));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        MyData.soundBackground.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyData.soundBackground.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        MyData.soundBackground.pause();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        clickExit();
    }
}