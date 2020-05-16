package ru.job4j;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import ru.job4j.model.Option;
import ru.job4j.model.Question;
import ru.job4j.store.QuestionStore;

public class MainActivity extends AppCompatActivity {
    public static final String HINT_FOR = "hint_for";
    private static final String TAG = "MainActivity";
    private  static int value = 0;
    private int position = 0;
    private ArrayList<Integer> answers  = new ArrayList<>();
    private final QuestionStore questions = QuestionStore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            answers = (ArrayList<Integer>) savedInstanceState.getSerializable("answers");
            Log.d(TAG,"onCreate answer rotate: " + answers);

        }
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
        this.fillForm();
        Button next = findViewById(R.id.next);
        next.setOnClickListener(this::nextBtn);
        Button previous = findViewById(R.id.back);
        previous.setOnClickListener(this::previousBtn);
        Button hint = findViewById(R.id.hint);
        hint.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, HintActivity.class);
                        intent.putExtra(HINT_FOR,position);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        if (answers.size() > 2){
            answers.remove(answers.size() - 1);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("position",position);
        outState.putSerializable("answers",answers);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy:");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        Log.d(TAG,"position done on Resume" + position);
    }

    private void fillForm() {
        findViewById(R.id.back).setEnabled(position != 0);
        if(position >=2 ){
            findViewById(R.id.next).setEnabled(true);
        }else {
            findViewById(R.id.next).setEnabled(position != questions.size() - 1);
        }
        final TextView text = findViewById(R.id.question);
        Question question = this.questions.get(this.position);
        text.setText(question.getText());
        RadioGroup variants = findViewById(R.id.variants);
        for (int index = 0; index != variants.getChildCount(); index++) {
            RadioButton button = (RadioButton) variants.getChildAt(index);
            Option option = question.getOptions().get(index);
            button.setId(option.getId());
            button.setText(option.getText());
        }
    }

    private boolean showAnswer() {
        boolean flag = false;
        RadioGroup variants = findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        Question question = this.questions.get(this.position);
        showToastQuestion(question);
        if(id > 0) {
            answers.add(id);
            flag = true;
            showToastAnswer(id,question);
        }
        if(position >=2){
            position--;
            Log.d(TAG,"choose answer" + answers.toString());
            Intent intent = new Intent(MainActivity.this,ResultActivity.class);
            intent.putExtra("Answers",answers);
            startActivity(intent);
        }
        return flag;
    }

    public void showToastQuestion(Question question){
        Toast.makeText(
                this, "Your answer is " + question.getText(),
                Toast.LENGTH_LONG
        ).show();
    }

    public void showToastAnswer(int id,Question question){
        Toast.makeText(
                this, "Your answer is " + id + ", correct is " + question.getAnswer(),
                Toast.LENGTH_SHORT
        ).show();
    }

    private void nextBtn(View view) {
            if(showAnswer()){
                position++;
                fillForm();
            }
        Log.d(TAG,"position done next" + position);
    }

    private void previousBtn(View view) {
            position--;
            if(answers.size() > 0){
                answers.remove(answers.size()-1);
            }
            fillForm();
        Log.d(TAG,"position done back" + position);
    }
}
