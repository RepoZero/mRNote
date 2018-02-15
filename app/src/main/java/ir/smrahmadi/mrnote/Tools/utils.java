package ir.smrahmadi.mrnote.Tools;

import android.content.Context;
import android.content.Intent;

/**
 * Created by cloner on 8/25/17.
 */

public class utils {

    public static void shareText(String title, String text, Context context) {


//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        String my_string =  title + "\n" + text;
//        intent.putExtra(Intent.EXTRA_TEXT, my_string);
//        context.startActivity(Intent.createChooser(intent, "Share via : "));

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, title + "\n" + text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
