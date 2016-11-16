package me.kingjason.waldoproject;

import java.util.HashMap;

/**
 * Created by jasonking on 11/14/16.
 */

public class Photo
{
    HashMap<String, PhotoURL> photos;
    private String SMALL_PHOTO_KEY = "small2x";
    private String LARGE_PHOTO_KEY = "large";

    public Photo()
    {
        photos = new HashMap<>();
    }

    public boolean addUrl( PhotoURL url )
    {
        if ( url.getSizeCode() != null )
        {
            photos.put( url.getSizeCode(), url );
            return true;
        }

        return false;
    }

    public boolean addUrl( String _sizeCode, String _url, int _width, int _height, double _quality )
    {
        if ( _sizeCode != null )
        {
            photos.put( _sizeCode, new PhotoURL( _sizeCode, _url, _width, _height, _quality ) );
            return true;
        }

        return false;
    }

    public String getSmallPhotoUrl()
    {
        return photos.get( SMALL_PHOTO_KEY ).getUrl();
    }

    public String getLargePhotoUrl()
    {
        return photos.get( LARGE_PHOTO_KEY ).getUrl();
    }
}
