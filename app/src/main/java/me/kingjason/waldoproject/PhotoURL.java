package me.kingjason.waldoproject;

/**
 * Created by jasonking on 11/14/16.
 */

public class PhotoURL {
    protected String sizeCode, url, mime;
    protected int width, height;
    protected float quality;

    public  PhotoURL( String _sizeCode, String _url, int _width, int _height, float _quality, String _mime )
    {
        sizeCode = _sizeCode;
        url = _url;
        width = _width;
        height = _height;
        quality = _quality;
        mime = _mime;
    }

    public String getSizeCode() {
        return sizeCode;
    }
}
