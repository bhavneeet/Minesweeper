package calculator.com.example.bhavneetsingh.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

   LinearLayout scroll;
   Integer score;
   SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score=0;
        setContentView(R.layout.activity_score);
        Intent mainIntent=getIntent();
         score=mainIntent.getIntExtra("score",0);
        addText();

    }
    public void addText()
    {
        preferences=getSharedPreferences("scores_rec",MODE_PRIVATE);
       int i=0;
       int temp=0;
       ArrayList<Integer>allKeys=new ArrayList<>();
        int count=preferences.getAll().size();

       for(i=0;i<count;i++)
       {
           int c=preferences.getInt(""+i,-1);
           if(c==-1)
               continue;
           allKeys.add(c);
       }
       allKeys.add(0,score);

        SharedPreferences.Editor edit=preferences.edit();
       for( i=allKeys.size()-1;i>=0;i--)
       {
           edit.putInt(i+"",allKeys.get(i));
       }
       edit.apply();
       ArrayAdapter<Integer>adapterView=new ArrayAdapter<Integer>(this,R.layout.list_layout,R.id.list_text,allKeys);

       ListView list=(ListView)findViewById(R.id.history);
       list.setAdapter(adapterView);
       list.setOnItemClickListener(this);
       list.setOnItemLongClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view , int pos,long id)
    {
        Toast.makeText(this,"dd",Toast.LENGTH_LONG).show();
    }
    public boolean onItemLongClick(AdapterView<?> parent,View view ,int pos,long id)
    {
        ArrayAdapter<Integer> adapter=(ArrayAdapter<Integer>)(parent.getAdapter());
        Integer object=adapter.getItem(pos);
        SharedPreferences.Editor editor=preferences.edit();
        String key=pos+"";
        editor.remove(key);
        editor.apply();
        adapter.remove(object);
        adapter.notifyDataSetInvalidated();
        return true;
    }
}
