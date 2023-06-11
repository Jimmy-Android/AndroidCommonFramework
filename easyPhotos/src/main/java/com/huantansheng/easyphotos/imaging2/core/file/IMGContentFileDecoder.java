package com.huantansheng.easyphotos.imaging2.core.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by felix on 2017/12/26 下午3:07.
 */

public class IMGContentFileDecoder extends IMGDecoder {

    private Context mContext;

    public IMGContentFileDecoder(Context context, Uri uri) {
        super(uri);
        mContext = context;
    }

    @Override
    public Bitmap decode(BitmapFactory.Options options) {
        Uri uri = getUri();
        if (uri == null) {
            return null;
        }

        InputStream iStream = null;
        try {
            iStream = mContext.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(iStream, null, options);
        } catch (Exception ignore) {

        } finally {
            if (iStream != null) {
                try {
                    iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
