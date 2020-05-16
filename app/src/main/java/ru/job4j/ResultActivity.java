package ru.job4j;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ru.job4j.store.QuestionStore;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    private ArrayList<TextView> listTextR = new ArrayList<>();
    private ArrayList<TextView> listTextL = new ArrayList<>();
    private ArrayList<Integer> answersId =new ArrayList<>();

    public void initData(){
        answersId = (ArrayList<Integer>) getIntent().getSerializableExtra("Answers");
        QuestionStore questionStore = QuestionStore.getInstance();
        System.out.println(answersId);
        if (answersId != null) {
            for (int i = 0; i < answersId.size(); i++) {
                TextView textR = new TextView(ResultActivity.this);
                TextView textL = new TextView(ResultActivity.this);
                textL.setText(questionStore.get(i).getText());
                String reply = "your:" + answersId.get(i) + " correct:" + questionStore.get(i).getAnswer();
                Log.d(TAG,reply);
                textR.setText(reply);
                listTextR.add(textR);
                listTextL.add(textL);
            }
        }
    }
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.result_activity);
        initData();
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f
        );
        LinearLayout linearLayout = findViewById(R.id.tableLayout);
        TableLayout tableLayoutL = new TableLayout(ResultActivity.this);
        TableLayout tableLayoutR = new TableLayout(ResultActivity.this);

        for (int i = 0; i < answersId.size(); i++) {
            TableRow tableRowL = new TableRow(ResultActivity.this);
            TableRow tableRowR = new TableRow(ResultActivity.this);
            tableRowR.addView(listTextR.get(i));
            tableRowL.addView(listTextL.get(i));
            tableLayoutR.setLayoutParams(param1);
            tableLayoutL.setLayoutParams(param2);
            tableLayoutL.addView(tableRowL);
            tableLayoutR.addView(tableRowR);
        }
        linearLayout.addView(tableLayoutL);
        linearLayout.addView(tableLayoutR);
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
