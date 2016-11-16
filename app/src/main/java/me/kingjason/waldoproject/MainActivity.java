package me.kingjason.waldoproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{
    private Context context;
    private GridView gridview;
    private List<Photo> gridData;
    private ImageAdapter imageAdapter;
    private ProgressBar spinner;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        context = this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder( this )
                .build();
        ImageLoader.getInstance().init( config );

        gridview = ( GridView ) findViewById( R.id.gridview );
        spinner = ( ProgressBar ) findViewById( R.id.progressBar );

        // Create gridview with empty list
        gridData = new ArrayList<>();
        imageAdapter = new ImageAdapter( this, gridData );
        gridview.setAdapter( imageAdapter );

        // Load the album from JSON, this will update the gridView
        new LoadAlbumTask().execute( "album.json" );

        // Click listener to show larger photo
        gridview.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            public void onItemClick( AdapterView<?> parent, View v,
                                     int position, long id )
            {
                //Get item at position
                Photo photo = ( Photo ) parent.getItemAtPosition( position );

                Intent intent = new Intent( MainActivity.this, PhotoActivity.class );

                //Pass the image title and url to DetailsActivity
                intent.putExtra( "largeImage", photo.getLargePhotoUrl() )
                        .putExtra( "smallImage", photo.getSmallPhotoUrl() );

                //Start activity to show large photo
                startActivity( intent );
            }
        } );

    }

    private class LoadAlbumTask extends AsyncTask<String, Void, Boolean>
    {
        protected Boolean doInBackground( String... params )
        {
            String fileContents = Utils.loadStringFromFile( params[ 0 ], context );

            // If we are unable to read the file, don't try to parse JSON
            if( fileContents == null )
            {
                return false;
            }

            // Parse JSON from file contents
            JSONObject json = Utils.parseJson( fileContents );

            // If initial parsing failed, return false
            if( json == null )
            {
                return false;
            }

            // Set the grid data to the contents of the album
            gridData = Utils.readAlbumList( json );

            return true;
        }

        protected void onPostExecute( Boolean result )
        {
            // Parsed album from JSON, let's update the UI!
            if ( result )
            {
                imageAdapter.setGridData( gridData );
            } else
            {
                Toast.makeText( MainActivity.this, "Failed to parse album data!", Toast.LENGTH_SHORT ).show();
            }

            // Show grid and hide progressbar
            gridview.setVisibility( View.VISIBLE );
            spinner.setVisibility( View.GONE );
        }
    }

}
