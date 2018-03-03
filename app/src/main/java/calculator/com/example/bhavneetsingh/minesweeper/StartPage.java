package calculator.com.example.bhavneetsingh.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPage extends AppCompatActivity implements View.OnClickListener{

    private Intent game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Button start=findViewById(R.id.start);
        start.setBackgroundResource(R.drawable.button_bg);
    }
    public void onClick(View view)
    {
        game=new Intent(this,MainActivity.class);

        startActivityForResult(game,0);
    }
}
