package me.kingjason.waldoproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{
    private GridView gridview;
    private List<Photo> gridData;
    private ImageAdapter imageAdapter;
    private ProgressBar spinner;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder( this )
                .build();
        ImageLoader.getInstance().init( config );

        gridview = ( GridView ) findViewById( R.id.gridview );
        spinner = ( ProgressBar ) findViewById( R.id.progressBar );

        // Create gridview with empty list
        gridData = new ArrayList<>();
        imageAdapter = new ImageAdapter( this, gridData );
        gridview.setAdapter( imageAdapter );

        // Load the album from JSON
        new LoadAlbumTask().execute( "album.json" );

        // Set the click listener for grid elements
        gridview.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            public void onItemClick( AdapterView<?> parent, View v,
                                     int position, long id )
            {
                //Get item at position
                Photo photo = ( Photo ) parent.getItemAtPosition( position );

                Intent intent = new Intent( MainActivity.this, PhotoActivity.class );
                ImageView imageView = ( ImageView ) v.findViewById( R.id.image );
                Drawable thumb = imageView.getDrawable();

                int[] screenLocation = new int[ 2 ];
                imageView.getLocationOnScreen( screenLocation );

                //Pass the image title and url to DetailsActivity
                intent.putExtra( "largeImage", photo.getLargePhotoUrl() )
                        .putExtra( "smallImage", photo.getSmallPhotoUrl() );

                //Start details activity
                startActivity( intent );
            }
        } );

    }

    public String loadAlbumFromJson( String fileName )
    {
        String json = null;
        try
        {
            AssetManager am = getAssets();
            InputStream is = am.open( fileName );
            int size = is.available();
            byte[] buffer = new byte[ size ];
            is.read( buffer );
            is.close();
            json = new String( buffer, "UTF-8" );
        } catch ( Exception ex )
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private List<Photo> readAlbumList( JSONObject json ) throws JSONException
    {
        List<Photo> album = new ArrayList<Photo>();

        JSONArray array = json.getJSONObject( "data" ).getJSONObject( "album" ).getJSONObject( "photos" ).getJSONArray( "records" );

        for ( int x = 0; x < array.length(); x++ )
        {
            album.add( readPhoto( array.getJSONObject( x ) ) );
        }

        return album;
    }

    private Photo readPhoto( JSONObject jsonObject ) throws JSONException
    {
        Photo photo = new Photo();

        JSONArray urlList = jsonObject.getJSONArray( "urls" );
        for ( int x = 0; x < urlList.length(); x++ )
        {
            JSONObject url = urlList.getJSONObject( x );
            photo.addUrl( url.getString( "size_code" ),
                    url.getString( "url" ),
                    url.getInt( "width" ),
                    url.getInt( "height" ),
                    url.getDouble( "quality" ),
                    url.getString( "mime" )
            );
        }

        return photo;
    }

    private class LoadAlbumTask extends AsyncTask<String, Void, Boolean>
    {
        protected Boolean doInBackground( String... params )
        {
            try
            {
                JSONObject json = new JSONObject( loadAlbumFromJson( params[ 0 ] ) );
                gridData = readAlbumList( json );
            } catch ( JSONException e )
            {
                e.printStackTrace();
            }
            return true;
        }

        protected void onPostExecute( Boolean result )
        {
            // Download complete. Lets update UI

            if ( result )
            {
                imageAdapter.setGridData( gridData );
            } else
            {
                Toast.makeText( MainActivity.this, "Failed to parse data!", Toast.LENGTH_SHORT ).show();
            }

            //Hide progressbar
            gridview.setVisibility( View.VISIBLE );
            spinner.setVisibility( View.GONE );
        }
    }

}
