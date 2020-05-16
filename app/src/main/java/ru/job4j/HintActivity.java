package ru.job4j;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class HintActivity extends AppCompatActivity{
    private final Map<Integer, String> answers = new HashMap<Integer, String>();

    public HintActivity() {
        this.answers.put(0, "Hint 1");
        this.answers.put(1, "Hint 2");
        this.answers.put(2, "Hint 3");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint_activity);
        TextView text = findViewById(R.id.hint);
        int question = getIntent().getIntExtra(MainActivity.HINT_FOR, 0);
        text.setText(this.answers.get(question));

        Button back = findViewById(R.id.back);
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );

    }
}
