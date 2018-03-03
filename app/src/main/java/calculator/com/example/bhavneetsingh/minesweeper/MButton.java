package calculator.com.example.bhavneetsingh.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.PointerIcon;
import android.widget.Button;

/**
 * Created by bhavneet singh on 28-Jan-18.
 */

public class MButton extends AppCompatButton {
        private int near_mines=0;
        private int positionI,positionJ;
        private boolean visited;
        private boolean flag=false;
        public MButton(Context context)
        {

          super(context);
          setBackgroundResource(R.drawable.button_bg);
          visited=false;
          positionI=0;
          positionJ=0;
        }
        public void setNumber(int number)
        {
            near_mines=number;
        }
        public int getNumber()
        {
            return near_mines;
        }
    public void setPositionI(int i)
    {
        positionI=i;
    }
    public void setPositionJ(int j)
    {
        positionJ=j;
    }
    public int getPositionI()
    {
        return positionI;
    }
    public int getPositionJ()
    {
      return  positionJ;
    }
    public void setVisited(boolean con)
    {
        setEnabled(false);
        visited=con;
        if(visited==true&&getNumber()!=-1)
        {
            setBackgroundResource(R.drawable.button_on);
            if(near_mines!=0)
            setText(getNumber()+"");

            setTextColor(Color.RED);
            setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        }
        else
        {
            setBackgroundResource(R.drawable.button_on);
            setBackground(getResources().getDrawable(R.drawable.mine1));
        }

    }
    public void setFlag(boolean con)
    {
        flag=con;
    }
    public boolean getFlag()
    {
        return flag;
    }
    public boolean isVisited()
    {
        return visited;
    }
}
