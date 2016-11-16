package me.kingjason.waldoproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;

/**
 * Created by jasonking on 11/14/16.
 */
public class ImageAdapter extends BaseAdapter
{
    private List<Photo> album;
    ImageLoader imageLoader;
    private DisplayImageOptions options;
    private LayoutInflater inflater;

    public ImageAdapter( Context c, List<Photo> _album )
    {
        album = _album;
        inflater = LayoutInflater.from( c );
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .cacheInMemory( true )
                .showImageOnLoading( R.color.gray )
                .cacheOnDisk( true )
                .considerExifParams( true )
                .bitmapConfig( Bitmap.Config.RGB_565 )
                .build();
    }

    public void setGridData( List<Photo> gridData )
    {
        this.album = gridData;
        notifyDataSetChanged();
    }

    public int getCount()
    {
        return album.size();
    }

    @Override
    public Object getItem( int position )
    {
        return album.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        final ViewHolder holder;
        View view = convertView;
        if ( view == null )
        {
            view = inflater.inflate( R.layout.item_grid_image, parent, false );
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = ( ImageView ) view.findViewById( R.id.image );
            view.setTag( holder );
        } else
        {
            holder = ( ViewHolder ) view.getTag();
        }

        // Download photo and display in imageView
        ImageLoader.getInstance()
                .displayImage( album.get( position ).getSmallPhotoUrl(), holder.imageView, options );
        return view;
    }

    static class ViewHolder
    {
        ImageView imageView;
    }
}