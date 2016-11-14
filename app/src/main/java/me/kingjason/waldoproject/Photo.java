package me.kingjason.waldoproject;

import java.util.HashMap;

/**
 * Created by jasonking on 11/14/16.
 */

public class Photo
{
    HashMap< String, PhotoURL > photos;

    public Photo()
    {
        photos = new HashMap<>();
    }

    public boolean addUrl(PhotoURL url )
    {
        if( url.getSizeCode() != null )
        {
            photos.put(url.getSizeCode(), url);
            return  true;
        }

        return false;
    }

    public boolean addUrl(String _sizeCode, String _url, int _width, int _height, double _quality, String _mime )
    {
        if( _sizeCode != null )
        {
            photos.put(_sizeCode, new PhotoURL(_sizeCode, _url, _width, _height, _quality, _mime));
            return true;
        }

        return  false;
    }
}
