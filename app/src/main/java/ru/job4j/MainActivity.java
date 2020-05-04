package ru.job4j;
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
import java.util.Arrays;
import java.util.List;
import ru.job4j.model.Option;
import ru.job4j.model.Question;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  static int value = 0;
    private int position = 0;
    private List<Integer> answers  = new ArrayList<>();

    private final List<Question> questions = Arrays.asList(
            new Question(
                    1, "How many primitive variables does Java have?",
                    Arrays.asList(
                            new Option(1, "1.1"), new Option(2, "1.2"),
                            new Option(3, "1.3"), new Option(4, "1.4")
                    ), 4
            ),
            new Question(
                    2, "What is Java Virtual Machine?",
                    Arrays.asList(
                            new Option(1, "2.1"), new Option(2, "2.2"),
                            new Option(3, "2.3"), new Option(4, "2.4")
                    ), 4
            ),
            new Question(
                    3, "What is happen if we try unboxing null?",
                    Arrays.asList(
                            new Option(1, "3.1"), new Option(2, "3.2"),
                            new Option(3, "3.3"), new Option(4, "3.4")
                    ), 4
            )
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            int i = savedInstanceState.getInt("number");
            Log.d(TAG,"onCreate new Data " + i);
        }
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
        this.fillForm();
        Button next = findViewById(R.id.next);
        next.setOnClickListener(this::nextBtn);
        Button previous = findViewById(R.id.back);
        previous.setOnClickListener(this::previousBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        value = value + 1;
        outState.putInt("number",value);
        super.onSaveInstanceState(outState);
        Log.d(TAG,"save Instance : " + value);
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
        for (Integer number : answers) {
            Log.d(TAG,"Added answers: " + number);
        }
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
    }

    private void fillForm() {
        findViewById(R.id.back).setEnabled(position != 0);
        findViewById(R.id.next).setEnabled(position != questions.size() - 1);
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
        if(id > 0) {
            answers.add(id);
            flag = true;
            Toast.makeText(
                    this, "Your answer is " + id + ", correct is " + question.getAnswer(),
                    Toast.LENGTH_SHORT
            ).show();
        }
        return flag;
    }

    private void nextBtn(View view) {
        if(showAnswer()){
            position++;
            fillForm();
        }
    }

    private void previousBtn(View view) {
            position--;
            fillForm();
    }
}
