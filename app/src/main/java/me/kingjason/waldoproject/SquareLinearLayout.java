package me.kingjason.waldoproject;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by jasonking on 11/16/16.
 */

public class SquareLinearLayout extends LinearLayout
{
    public SquareLinearLayout( Context context )
    {
        super( context );
    }

    public SquareLinearLayout( Context context, AttributeSet attrs )
    {
        super( context, attrs );
    }

    public SquareLinearLayout( Context context, AttributeSet attrs, int defStyleAttr )
    {
        super( context, attrs, defStyleAttr );
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    public SquareLinearLayout( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes )
    {
        super( context, attrs, defStyleAttr, defStyleRes );
    }

    @Override
    public void onMeasure( int widthMeasureSpec, int heightMeasureSpec )
    {
        super.onMeasure( widthMeasureSpec, widthMeasureSpec );
    }
}
