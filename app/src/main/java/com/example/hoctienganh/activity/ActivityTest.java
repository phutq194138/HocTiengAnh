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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hoctienganh.MyData;
import com.example.hoctienganh.Question;
import com.example.hoctienganh.R;
import com.example.hoctienganh.Test;
import com.example.hoctienganh.Word;
import com.example.hoctienganh.adapter.WatchAndChooseResultAdapter;
import com.example.hoctienganh.custom.MyImageButton;
import com.example.hoctienganh.custom.MyImageView;
import com.example.hoctienganh.database.TestDatabase;
import com.example.hoctienganh.database.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ActivityTest extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout layoutWatchAndChoose, layoutWatchAndWrite, layoutListenAndChoose;
    private ImageView imgQuestionWatchAndChoose, imgVolume, imgQuestionWatchAndWrite;
    private MyImageButton imgAnswer1ListenAndChoose, imgAnswer2ListenAndChoose, imgAnswer3ListenAndChoose, imgAnswer4ListenAndChoose;
    private Button btnAnswer1WatchAndChoose, btnAnswer2WatchAndChoose, btnAnswer3WatchAndChoose, btnAnswer4WatchAndChoose, btnCheckWatchAndWrite,
            btnHelpWatchAndChoose, btnHelpListenAndChoose, btnHelpWatchAndWrite,
            btnExitWatchAndChoose, btnExitListenAndChoose, btnExitWatchAndWrite;
    private TextView tvCurrentQuestionWatchAndChoose, tvCurrentQuestionWatchAndWrite, tvCurrentQuestionListenAndChoose;
    private EditText edtAnswerWatchAndWrite;

    private MyImageView imgAns1Correct, imgAns1Wrong, imgAns2Correct, imgAns2Wrong, imgAns3Correct, imgAns3Wrong,
            imgAns4Correct, imgAns4Wrong;

    private TextToSpeech textToSpeech;
    private MediaPlayer correctSound, incorrectSound, music;

    private List<Question> questionList = new ArrayList<>();
    private int countNumberQuestion = 30;
    private int iCurrentQuestion = 0;
    private int countCorrectQuestion = 0;
    private Question currentQuestion;
    private List<Word> chosenWordList = new ArrayList<>();
    private List<Boolean> chosenBoolList = new ArrayList<>();

    private SimpleDateFormat formatDay;
    private SimpleDateFormat formatHour;

    private Test mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_test);

        initUi();
        startTest();
        createListQuestion();
        setDataQuestion(questionList.get(iCurrentQuestion));
    }

    private void startTest(){
        if (MyData.user == null){
            finish();
            return;
        }
        mTest = new Test(MyData.user.getUsername());

        Date date = Calendar.getInstance().getTime();

        String hourStart = formatHour.format(date);
        String dayStart = formatDay.format(date);

        mTest.setHourStart(hourStart);
        mTest.setDayStart(dayStart);
    }

    private void finishTest(){
        Date date = Calendar.getInstance().getTime();

        String hourEnd = formatHour.format(date);
        String dayEnd = formatDay.format(date);

        mTest.setHourEnd(hourEnd);
        mTest.setDayEnd(dayEnd);

        mTest.setCorrect(countCorrectQuestion);
        mTest.setExp(10);
        mTest.setPoint(countCorrectQuestion*10/countNumberQuestion);
        mTest.setStar(5);

        Test test = new Test(mTest.getUsername());
        test.setDayStart(mTest.getDayStart());
        test.setDayEnd(mTest.getDayEnd());
        test.setHourStart(mTest.getHourStart());
        test.setHourEnd(mTest.getHourEnd());
        test.setCorrect(mTest.getCorrect());
        test.setExp(mTest.getExp());
        test.setPoint(mTest.getPoint());
        test.setStar(mTest.getStar());

        TestDatabase.getInstance(this).testDAO().insertTest(test);

        MyData.user.setExp(MyData.user.getExp() + test.getExp());
        MyData.user.setPoint(MyData.user.getPoint() + test.getPoint());
        MyData.user.setStar(MyData.user.getStar() + test.getStar());
        UserDatabase.getInstance(this).userDAO().updateUser(MyData.user);

    }

    private void initUi(){
        layoutWatchAndChoose = this.findViewById(R.id.layout_watch_and_choose);
        layoutListenAndChoose = this.findViewById(R.id.layout_listen_and_choose);
        layoutWatchAndWrite = this.findViewById(R.id.layout_watch_and_write);

        correctSound = MediaPlayer.create(this, R.raw.correctsound1);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrectsound2);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        imgQuestionWatchAndChoose = this.findViewById(R.id.img_question_test_watch_and_choose);
        tvCurrentQuestionWatchAndChoose = this.findViewById(R.id.tv_question_test_watch_and_choose);
        btnAnswer1WatchAndChoose = this.findViewById(R.id.btn_answer1_test_watch_and_choose);
        btnAnswer2WatchAndChoose = this.findViewById(R.id.btn_answer2_test_watch_and_choose);
        btnAnswer3WatchAndChoose = this.findViewById(R.id.btn_answer3_test_watch_and_choose);
        btnAnswer4WatchAndChoose = this.findViewById(R.id.btn_answer4_test_watch_and_choose);

        imgVolume = this.findViewById(R.id.img_volume_test);
        tvCurrentQuestionListenAndChoose = this.findViewById(R.id.tv_question_test_listen_and_choose);
        imgAnswer1ListenAndChoose = this.findViewById(R.id.img_answer1_test_listen_and_choose);
        imgAnswer2ListenAndChoose = this.findViewById(R.id.img_answer2_test_listen_and_choose);
        imgAnswer3ListenAndChoose = this.findViewById(R.id.img_answer3_test_listen_and_choose);
        imgAnswer4ListenAndChoose = this.findViewById(R.id.img_answer4_test_listen_and_choose);

        imgAns1Correct = this.findViewById(R.id.img_ans1_correct_test);
        imgAns2Correct = this.findViewById(R.id.img_ans2_correct_test);
        imgAns3Correct = this.findViewById(R.id.img_ans3_correct_test);
        imgAns4Correct = this.findViewById(R.id.img_ans4_correct_test);

        imgAns1Wrong = this.findViewById(R.id.img_ans1_wrong_test);
        imgAns2Wrong = this.findViewById(R.id.img_ans2_wrong_test);
        imgAns3Wrong = this.findViewById(R.id.img_ans3_wrong_test);
        imgAns4Wrong = this.findViewById(R.id.img_ans4_wrong_test);

        imgQuestionWatchAndWrite = this.findViewById(R.id.img_test_question_watch_and_write);
        edtAnswerWatchAndWrite = this.findViewById(R.id.edt_answer_test_watch_and_write);
        btnCheckWatchAndWrite = this.findViewById(R.id.btn_check_test_watch_and_write);
        tvCurrentQuestionWatchAndWrite = this.findViewById(R.id.tv_test_question_watch_and_write);

        btnAnswer1WatchAndChoose.setOnClickListener(this);
        btnAnswer2WatchAndChoose.setOnClickListener(this);
        btnAnswer3WatchAndChoose.setOnClickListener(this);
        btnAnswer4WatchAndChoose.setOnClickListener(this);

        btnHelpWatchAndChoose = this.findViewById(R.id.btn_help_watch_and_choose_test);
        btnHelpListenAndChoose = this.findViewById(R.id.btn_help_listen_and_choose_test);
        btnHelpWatchAndWrite = this.findViewById(R.id.btn_help_watch_and_write_test);

        btnExitWatchAndChoose = this.findViewById(R.id.btn_exit_watch_and_choose_test);
        btnExitListenAndChoose = this.findViewById(R.id.btn_exit_listen_and_choose_test);
        btnExitWatchAndWrite = this.findViewById(R.id.btn_exit_watch_and_write_test);

        btnExitWatchAndChoose.setOnClickListener(this);
        btnExitListenAndChoose.setOnClickListener(this);
        btnExitWatchAndWrite.setOnClickListener(this);

        btnHelpWatchAndWrite.setOnClickListener(this);
        btnHelpListenAndChoose.setOnClickListener(this);
        btnHelpWatchAndChoose.setOnClickListener(this);

        imgVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(currentQuestion.getQuestion().getWord(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        imgAnswer1ListenAndChoose.setOnClickListener(this);
        imgAnswer2ListenAndChoose.setOnClickListener(this);
        imgAnswer3ListenAndChoose.setOnClickListener(this);
        imgAnswer4ListenAndChoose.setOnClickListener(this);

        btnCheckWatchAndWrite.setOnClickListener(this);

        formatHour = new SimpleDateFormat("HH:mm:ss");
        formatDay = new SimpleDateFormat("dd-MM-yyyy");

    }

    private void setLayout(int iCurrentQuestion){
        if (iCurrentQuestion < countNumberQuestion/3){
            layoutWatchAndChoose.setVisibility(View.VISIBLE);
            layoutListenAndChoose.setVisibility(View.INVISIBLE);
            layoutWatchAndWrite.setVisibility(View.INVISIBLE);
        }
        else if (iCurrentQuestion < countNumberQuestion/3*2){
            layoutWatchAndChoose.setVisibility(View.INVISIBLE);
            layoutListenAndChoose.setVisibility(View.VISIBLE);
            layoutWatchAndWrite.setVisibility(View.INVISIBLE);
        }
        else {
            layoutWatchAndChoose.setVisibility(View.INVISIBLE);
            layoutListenAndChoose.setVisibility(View.INVISIBLE);
            layoutWatchAndWrite.setVisibility(View.VISIBLE);
        }
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

        setLayout(iCurrentQuestion);

        String str = "";
        str = "Câu hỏi: " + (iCurrentQuestion + 1) + "/" + countNumberQuestion;

        currentQuestion = question;
        if (iCurrentQuestion < countNumberQuestion/3){
            tvCurrentQuestionWatchAndChoose.setText(str);

            btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_default);
            btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_default);
            btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_default);
            btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_default);

            imgQuestionWatchAndChoose.setImageResource(question.getQuestion().getIdRes());

            btnAnswer1WatchAndChoose.setText(question.getAnswer1().getWord());
            btnAnswer2WatchAndChoose.setText(question.getAnswer2().getWord());
            btnAnswer3WatchAndChoose.setText(question.getAnswer3().getWord());
            btnAnswer4WatchAndChoose.setText(question.getAnswer4().getWord());
        }
        else if (iCurrentQuestion < countNumberQuestion/3*2){
            tvCurrentQuestionListenAndChoose.setText(str);

            imgAns1Correct.setVisibility(View.GONE);
            imgAns2Correct.setVisibility(View.GONE);
            imgAns3Correct.setVisibility(View.GONE);
            imgAns4Correct.setVisibility(View.GONE);

            imgAns1Wrong.setVisibility(View.GONE);
            imgAns2Wrong.setVisibility(View.GONE);
            imgAns3Wrong.setVisibility(View.GONE);
            imgAns4Wrong.setVisibility(View.GONE);

            imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_default);
            imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_default);
            imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_default);
            imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_default);

            imgAnswer1ListenAndChoose.setImageResource(question.getAnswer1().getIdRes());
            imgAnswer2ListenAndChoose.setImageResource(question.getAnswer2().getIdRes());
            imgAnswer3ListenAndChoose.setImageResource(question.getAnswer3().getIdRes());
            imgAnswer4ListenAndChoose.setImageResource(question.getAnswer4().getIdRes());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textToSpeech.speak(currentQuestion.getQuestion().getWord(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 400);
        }
        else {
            tvCurrentQuestionWatchAndWrite.setText(str);
            btnCheckWatchAndWrite.setBackgroundResource(R.drawable.background_button_check_default);
            imgQuestionWatchAndWrite.setImageResource(question.getQuestion().getIdRes());
            edtAnswerWatchAndWrite.setText("");
        }
    }

    private void nextQuestion(){
        iCurrentQuestion++;
        if (iCurrentQuestion == countNumberQuestion){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishTest();
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
            }, 1000);
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
        dialog.setContentView(R.layout.dialog_result_test);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);

        Button btnDone;
        TextView tvScore;
        RecyclerView rcvResult;

        btnDone = dialog.findViewById(R.id.btn_done_test);
        tvScore = dialog.findViewById(R.id.tv_score_test);
        rcvResult = dialog.findViewById(R.id.rcv_result_test);

        WatchAndChooseResultAdapter resultAdapterWaC = new WatchAndChooseResultAdapter();
        resultAdapterWaC.setData(questionList, chosenBoolList, chosenWordList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dialog.getContext(), RecyclerView.VERTICAL, false);
        rcvResult.setLayoutManager(linearLayoutManager);
        rcvResult.setAdapter(resultAdapterWaC);

        String str = "Kết quả: " + countCorrectQuestion + "/" + countNumberQuestion;
        tvScore.setText(str);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iCurrentQuestion = 0;
                openDialogResult2();
            }
        });

        dialog.show();
    }

    private void openDialogResult2(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_result);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);

        Button btnDone;
        TextView tvHourStart, tvDayStart, tvHourEnd, tvDayEnd, tvTime, tvCount, tvPoint, tvExp, tvStar;

        btnDone = dialog.findViewById(R.id.btn_done_result);
        tvHourStart = dialog.findViewById(R.id.tv_hour_start_result);
        tvDayStart = dialog.findViewById(R.id.tv_day_start_result);
        tvHourEnd = dialog.findViewById(R.id.tv_hour_end_result);
        tvDayEnd = dialog.findViewById(R.id.tv_day_end_result);
        tvTime = dialog.findViewById(R.id.tv_time_result);
        tvCount = dialog.findViewById(R.id.tv_correct_answer);
        tvPoint = dialog.findViewById(R.id.tv_point_result);
        tvExp = dialog.findViewById(R.id.tv_exp_result);
        tvStar = dialog.findViewById(R.id.tv_star_result);

        tvHourStart.setText(mTest.getHourStart());
        tvDayStart.setText(mTest.getDayStart());
        tvHourEnd.setText(mTest.getHourEnd());
        tvDayEnd.setText(mTest.getDayEnd());
        tvTime.setText(mTest.getTime()+" giây");
        tvCount.setText(mTest.getCorrect()+"/30");
        tvPoint.setText(mTest.getPoint()+"");
        tvExp.setText(mTest.getExp()+"");
        tvStar.setText(mTest.getStar()+"");

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                iCurrentQuestion = 0;
                finish();
            }
        });

        dialog.show();
    }

    private void checkAnswer1WatchAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer1());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer2WatchAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer2());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer3WatchAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer3());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer4WatchAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_red);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_green);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer4());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer1ListenAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    imgAns1Correct.setVisibility(View.VISIBLE);
                    imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_red);
                    imgAns1Wrong.setVisibility(View.VISIBLE);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns2Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns3Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns4Correct.setVisibility(View.VISIBLE);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer1());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer2ListenAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    imgAns2Correct.setVisibility(View.VISIBLE);
                    imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_red);
                    imgAns2Wrong.setVisibility(View.VISIBLE);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns1Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns3Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns4Correct.setVisibility(View.VISIBLE);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer2());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer3ListenAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    imgAns3Correct.setVisibility(View.VISIBLE);
                    imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_red);
                    imgAns3Wrong.setVisibility(View.VISIBLE);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns1Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns2Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns4Correct.setVisibility(View.VISIBLE);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer3());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswer4ListenAndChoose(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentQuestion.getAnswer4().getWord().equals(currentQuestion.getQuestion().getWord())) {
                    imgAns4Correct.setVisibility(View.VISIBLE);
                    imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else  {
                    imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_red);
                    imgAns4Wrong.setVisibility(View.VISIBLE);
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    if (currentQuestion.getAnswer2().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns2Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer3().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns3Correct.setVisibility(View.VISIBLE);
                    }
                    if (currentQuestion.getAnswer1().getWord().equals(currentQuestion.getQuestion().getWord())){
                        imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_green);
                        imgAns1Correct.setVisibility(View.VISIBLE);
                    }
                }
                chosenWordList.add(currentQuestion.getAnswer4());
                nextQuestion();
            }
        }, 500);
    }

    private void checkAnswerWatchAndWrite(){
        String answer = edtAnswerWatchAndWrite.getText().toString().trim();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (answer.equals(currentQuestion.getQuestion().getWord())){
                    btnCheckWatchAndWrite.setBackgroundResource(R.drawable.background_green);
                    correctSound.start();
                    chosenBoolList.add(true);
                    countCorrectQuestion++;
                }
                else {
                    incorrectSound.start();
                    chosenBoolList.add(false);
                    btnCheckWatchAndWrite.setBackgroundResource(R.drawable.background_red);
                }
                edtAnswerWatchAndWrite.setText("Đáp án: " + currentQuestion.getQuestion().getWord());
                chosenWordList.add(new Word(answer, "1", R.drawable.cat));
                nextQuestion();
            }
        }, 500);
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

        String str = "";
        if (iCurrentQuestion<countNumberQuestion/3){
            str = "Quan sát hình ảnh và\n chọn đáp án đúng.";
        }
        else if (iCurrentQuestion<countNumberQuestion/3*2){
            str = "Lắng nghe và chọn đáp án đúng.\n Bấm loa để nghe lại.";
        }
        else {
            str = "Quan sát hình ảnh và\n điền từ thích hợp.";
        }
        tvHelp.setText(str);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openDialogExit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    @Override
    public void onClick(View v) {
        MyData.soundEffect.start();
        switch (v.getId()){
            case R.id.btn_answer1_test_watch_and_choose:
                btnAnswer1WatchAndChoose.setBackgroundResource(R.drawable.background_orange);
                checkAnswer1WatchAndChoose();
                break;

            case R.id.btn_answer2_test_watch_and_choose:
                btnAnswer2WatchAndChoose.setBackgroundResource(R.drawable.background_orange);
                checkAnswer2WatchAndChoose();
                break;

            case R.id.btn_answer3_test_watch_and_choose:
                btnAnswer3WatchAndChoose.setBackgroundResource(R.drawable.background_orange);
                checkAnswer3WatchAndChoose();
                break;

            case R.id.btn_answer4_test_watch_and_choose:
                btnAnswer4WatchAndChoose.setBackgroundResource(R.drawable.background_orange);
                checkAnswer4WatchAndChoose();
                break;

            case R.id.img_answer1_test_listen_and_choose:
                imgAnswer1ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_press);
                checkAnswer1ListenAndChoose();
                break;

            case R.id.img_answer2_test_listen_and_choose:
                imgAnswer2ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_press);
                checkAnswer2ListenAndChoose();
                break;

            case R.id.img_answer3_test_listen_and_choose:
                imgAnswer3ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_press);
                checkAnswer3ListenAndChoose();
                break;

            case R.id.img_answer4_test_listen_and_choose:
                imgAnswer4ListenAndChoose.setBackgroundResource(R.drawable.background_image_button_press);
                checkAnswer4ListenAndChoose();
                break;

            case R.id.btn_check_test_watch_and_write:
                btnCheckWatchAndWrite.setBackgroundResource(R.drawable.background_orange);
                checkAnswerWatchAndWrite();
                break;

            case R.id.btn_help_watch_and_choose_test:
            case R.id.btn_help_listen_and_choose_test:
            case R.id.btn_help_watch_and_write_test:
                openDialogHelp();
                break;

            case R.id.btn_exit_watch_and_choose_test:
            case R.id.btn_exit_listen_and_choose_test:
            case R.id.btn_exit_watch_and_write_test:
                openDialogExit();
                break;

            default:
                break;

        }
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
        openDialogExit();
    }
    
}