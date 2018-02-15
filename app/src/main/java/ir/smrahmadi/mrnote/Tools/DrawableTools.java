package ir.smrahmadi.mrnote.Tools;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class DrawableTools {

    public static android.graphics.drawable.Drawable setTint(android.graphics.drawable.Drawable d, int color) {
        android.graphics.drawable.Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static String BitmapToBase64(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, 0);

    }

    public static Bitmap Base64ToBitmap(String base64){

        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}
