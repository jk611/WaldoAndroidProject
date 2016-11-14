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
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Photo> album;
    ImageLoader imageLoader;
    private DisplayImageOptions options;
    private LayoutInflater inflater;

    public ImageAdapter( Context c, List<Photo> _album ) {
        mContext = c;
        album = _album;
        inflater = LayoutInflater.from(c);
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.spinner)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public int getCount() {
        return album.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    /*public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;
        if ( convertView == null ) {
            // if it's not recycled, initialize some attributes
            imageView = new SquareImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(1500, 1500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
            //imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (SquareImageView) convertView;
        }

        imageLoader.displayImage(album.get( position ).getSmallPhotoUrl(), imageView);
        return imageView;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_grid_image, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance()
                .displayImage(album.get( position ).getSmallPhotoUrl(), holder.imageView, options);

        return view;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}