package me.kingjason.waldoproject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public String loadAlbumFromJson( String fileName ) {
        String json = null;
        try {
            AssetManager am = getAssets();
            InputStream is = am.open( fileName );
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            }
        });

        try
        {
            JSONObject json = new JSONObject(loadAlbumFromJson( "album.json"));

            List<Photo> album = readAlbumList( json );
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private List<Photo> readAlbumList(JSONObject json) throws JSONException
    {
        List<Photo> album = new ArrayList<Photo>();

        JSONArray array = json.getJSONObject( "data" ).getJSONObject( "album" ).getJSONObject( "photos" ).getJSONArray( "records");

        for( int x = 0; x < array.length(); x++ )
        {
            album.add( readPhoto( array.getJSONObject( x ) ) );
        }

        return album;
    }

    private Photo readPhoto(JSONObject jsonObject) throws JSONException
    {
        Photo photo = new Photo();

        JSONArray urlList = jsonObject.getJSONArray( "urls" );
        for( int x = 0; x < urlList.length(); x++ )
        {
            JSONObject url = urlList.getJSONObject( x );
            photo.addUrl( url.getString( "size_code" ),
                    url.getString( "url" ),
                    url.getInt( "width" ),
                    url.getInt( "height" ),
                    url.getDouble( "quality" ),
                    url.getString( "mime")
                    );
        }

        return photo;
    }
}
