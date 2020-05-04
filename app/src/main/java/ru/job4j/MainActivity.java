package ru.job4j;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  static int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            int i = savedInstanceState.getInt("number");
            Log.d(TAG,"onCreate new Data " + i);
        }
        setContentView(R.layout.custom_layout);
        Log.d(TAG,"onCreate");
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
        Log.d(TAG,"onDestroy");
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
}
