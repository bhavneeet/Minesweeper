package calculator.com.example.bhavneetsingh.minesweeper;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener{

     private int size;
     private MButton[][]board;
     private LinearLayout root;
     private int level=3;
     private int first_i,first_j;
     private int total_count,mines_count,score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        size=6;
        total_count=size*size;
        root=(LinearLayout)findViewById(R.id.root);
        board=new MButton[size][size];
        addViews();

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.game_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.isCheckable()&&item.isChecked())
            return false;
        if(item.getItemId()==R.id.easy)
        {
            score=0;
            item.setChecked(true);
            size=6;
            level=3;
            addViews();
        }
        else if(item.getItemId()==R.id.medium)
        {
            item.setChecked(true);
            size=8;
            level=2;
         addViews();
         score=0;
        }
        else if(item.getItemId()==R.id.expert)
        {
            level=1;
            item.setChecked(true);
            score=0;
            size=10;
            addViews();
        }
        else if(item.getItemId()==R.id.refresh)
        {
            addViews();
            score=0;

        }

        else if(item.getItemId()==R.id.instruct)
        {
            Intent instruct_activity=new Intent(this,InstructionActivity.class);
            startActivity(instruct_activity);
        }
        else if(item.getItemId()==R.id.hint)
        {
            showNextNumber();
        }
        return true;
    }
    public void addViews()
    {
        root.removeAllViews();
        board=new MButton[size][size];
        for(int i=0;i<size;i++)
        {
            LinearLayout row=new LinearLayout(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            row.setLayoutParams(params);
            for(int j=0;j<size;j++)
            {
                LinearLayout.LayoutParams param1=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                board[i][j]=new MButton(this);
                board[i][j].setLayoutParams(param1);
                board[i][j].setOnClickListener(this);
                board[i][j].setPositionI(i);
                board[i][j].setPositionJ(j);
                board[i][j].setLongClickable(true);
                board[i][j].setOnLongClickListener(this);
                row.addView(board[i][j]);

            }
            root.addView(row);
        }
    }
    public boolean onLongClick(View view)
    {
     MButton button=(MButton)view;
     if(!button.getFlag()&&!button.isVisited())
     {
         button.setFlag(true);
         button.setForeground(getDrawable(R.drawable.flag4));
     }
     else if(!button.isVisited()&&button.getFlag())
     {
         button.setForeground(null);
         button.setFlag(false);
     }
     return true;
    }
    @Override
    public void onClick(View view) {

      MButton button=(MButton)view;
      if(button.getFlag())
          return;
      if(allNotVisited())
      {
          first_i=button.getPositionI();
          first_j=button.getPositionJ();
          initialize();
          visitNumber(button.getPositionI(),button.getPositionJ());
          return;
      }
      if(button.getNumber()==-1)
      {
          showAllMines();
          disableAll();
          toScoreActivity();
          return;
      }
      else
      {
        visitNumber(button.getPositionI(),button.getPositionJ());

          if(allNumberVisited())
          {
              Toast.makeText(this,"Congrats",Toast.LENGTH_LONG).show();
              toScoreActivity();
              disableAll();

          }
      }
    }
    public boolean allNotVisited()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].isVisited())
                    return false;
            }
        }
        return true;
    }
    public  boolean allNumberVisited()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getNumber()!=-1&&!board[i][j].isVisited())
                    return false;
            }
        }
        return true;
    }
    public void toScoreActivity()
    {
        Intent scoreActivity=new Intent(this,ScoreActivity.class);
        scoreActivity.putExtra("score",score);
        startActivity(scoreActivity);
    }
    public  void showNextNumber()
    {

        if(score==total_count-mines_count)
        {
            return;
        }
        int tmp=score;
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
            {
                if((!board[i][j].isEnabled()||board[i][j].isVisited()||board[i][j].getNumber()==-1)&&(!allNumberVisited()))
                    continue;
                else
                {
                    visitNumber(i,j);
                    score=tmp;
                    return;
                }
            }

    }
    private int setMines()
    {
        int a=0;
        if(level==3)
        {
             a=0;
            while(a==0)
            a=(int)(Math.random()*3);
        }
        else if(level==2)
        {
             a=0;
            while(a==0)
                a=(int)(Math.random()*6);
        }
        else
        {

             a=0;
            while(a==0)
                a=(int)(Math.random()*8);
        }
        return a;
    }


    public void initialize()
    {

        total_count=size*size;
        //Setting Mines
        for(int i=0;i<size;i++)
        {
            int no_mines=(int)(Math.random()*size)/level;
            if(no_mines==0)
                no_mines=setMines();
            mines_count+=no_mines;
            for(int j=0;j<no_mines;j++)
            {
                int ind=(int)(Math.random()*size);
                if(i==first_i&&ind==first_j)
                {
                    j--;
                    continue;
                }
                if(board[i][ind].getNumber()==-1)
                {
                    j--;
                    continue;
                }
                board[i][ind].setNumber(-1);
            }
        }
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getNumber()==-1)
                {
                    for(int m=i-1;m<i+2;m++)
                    {

                        for(int n=j-1;n<j+2;n++)
                        {
                            setWeight(m,n);
                        }
                    }
                }
            }
        }

    }
    //For setting no of nearMines for numbers
    public void setWeight(int i,int j)
    {
        if((i>=0&&j>=0)&&(i<size&&j<size))
        {
           int number=board[i][j].getNumber();
           if(number!=-1)
           {
               board[i][j].setNumber(number+1);
           }
        }
        else
            return;
    }
    public void visitNumber(int i,int j)
    {
        if(!checkIndexes(i,j))
            return ;
        int number=board[i][j].getNumber();
        if(board[i][j].isVisited()||board[i][j].getFlag())
            return ;
        score++;
        board[i][j].setVisited(true);
        if(board[i][j].getNumber()==0)
        {
            for(int m=i-1;m<i+2;m++)
            {
                for(int n=j-1;n<j+2;n++)
                {
                    visitNumber(m,n);
                }
            }
        }
        else if(number!=-1)
        {
            board[i][j].setText(number+"");
        }
        //all number are visted

    }
    public void disableAll()
    {
        for(int i=0;i<size;i++)
        {
            for(int  j=0;j<size;j++)
            {
                board[i][j].setEnabled(false);
            }
        }
    }
    public void showAllMines()
    {
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
            {
                if(board[i][j].getNumber()==-1&&!board[i][j].getFlag())
                {
                    board[i][j].setVisited(true);
                }

            }
     if(score<size*size*25/100)
         Toast.makeText(this,"Loser",Toast.LENGTH_LONG).show();
        else if(score<size*size*60/100)
            Toast.makeText(this,"Nice Try",Toast.LENGTH_LONG).show();
        else if(score<size*size*80/100)
            Toast.makeText(this,"Good",Toast.LENGTH_LONG).show();
    }
    public boolean checkIndexes(int i,int j)
    {
        return (i>=0&&j>=0)&&(i<size&&j<size);
    }
}