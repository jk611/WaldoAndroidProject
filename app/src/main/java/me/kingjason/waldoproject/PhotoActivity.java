package me.kingjason.waldoproject;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by jasonking on 11/15/16.
 */
public class PhotoActivity extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        //Setting details screen layout
        setContentView( R.layout.activity_photo );

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //retrieves the thumbnail data
        Bundle bundle = getIntent().getExtras();
        final String image = bundle.getString( "largeImage" );
        String smallImage = bundle.getString( "smallImage" );

        //Set image url
        final ImageView imageView = ( ImageView ) findViewById( R.id.grid_item_image );

        final DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory( true )
                .cacheOnDisk( true )
                .considerExifParams( true )
                .bitmapConfig( Bitmap.Config.RGB_565 )
                .build();

        ImageLoader.getInstance().displayImage( smallImage, imageView, options, new SimpleImageLoadingListener()
        {
            @Override
            public void onLoadingComplete( String imageUri, View view, Bitmap loadedImage )
            {
                ImageLoader.getInstance().displayImage( image, imageView, options );
            }
        } );

        //Set the background color to black
        FrameLayout frameLayout = ( FrameLayout ) findViewById( R.id.main_background );
        ColorDrawable colorDrawable = new ColorDrawable( Color.BLACK );
        frameLayout.setBackground( colorDrawable );
    }
}
