package me.kingjason.waldoproject;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasonking on 11/16/16.
 */

public class Utils
{
    /**
     * Load the contents of a file located in the assets folder into a String
     *
     * @param fileName The file in assets folder to read
     * @return Contents of file or null on error.
     */
    public static String loadStringFromFile( String fileName, Context context )
    {
        String ret = null;
        try
        {
            AssetManager am = context.getAssets();
            InputStream is = am.open( fileName );
            int size = is.available();
            byte[] buffer = new byte[ size ];
            is.read( buffer );
            is.close();
            ret = new String( buffer, "UTF-8" );
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            return null;
        }
        return ret;
    }

    /**
     * Wrapper function around JSONObject constructor to prevent exception
     *
     * @param value
     * @return
     */
    public static JSONObject parseJson( String value )
    {
        if( value == null )
        {
            return null;
        }

        try
        {
            return new JSONObject( value );
        }
        catch ( JSONException e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a list of Photo objects from JSON album data.
     *
     * @param json JSON object containing entire album
     * @return List of Photo objects in album
     * @throws JSONException
     */
    public static List<Photo> readAlbumList( JSONObject json )
    {
        List<Photo> album = new ArrayList<>();

        if( json != null )
        {
            JSONArray array = null;
            try
            {
                array = json.getJSONObject( "data" ).getJSONObject( "album" )
                        .getJSONObject( "photos" ).getJSONArray( "records" );

                for ( int x = 0; x < array.length(); x++ )
                {
                    Photo tempPhoto = readPhoto( array.getJSONObject( x ) );
                    if( tempPhoto != null )
                    {
                        album.add( tempPhoto );
                    }
                }
            } catch ( JSONException e )
            {
                e.printStackTrace();
            }
        }

        return album;
    }

    /**
     * Parses JSON to create a Photo object.
     *
     * @param jsonObject The JSON photo
     * @return The Photo object
     * @throws JSONException
     */
    public static Photo readPhoto( JSONObject jsonObject )
    {
        Photo photo = new Photo();

        try
        {
            // TODO: Check if jsonObject contains "urls" to avoid exception
            JSONArray urlList = jsonObject.getJSONArray( "urls" );

            if( urlList == null )
            {
                return null;
            }

            for ( int x = 0; x < urlList.length(); x++ )
            {
                // TODO: Check if jsonObject contains individual fields to avoid exception
                JSONObject url = urlList.getJSONObject( x );
                photo.addUrl( url.getString( "size_code" ),
                        url.getString( "url" ),
                        url.getInt( "width" ),
                        url.getInt( "height" ),
                        url.getDouble( "quality" )
                );
            }
        }
        catch ( JSONException e )
        {
            // Failed to parse photo, returning null
            e.printStackTrace();
            return null;
        }

        return photo;
    }
}
