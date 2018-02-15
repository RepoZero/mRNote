package ir.smrahmadi.mrnote;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import ir.smrahmadi.mrnote.Adapter.noteAdapter;
import ir.smrahmadi.mrnote.Model.noteModel;
import ir.smrahmadi.mrnote.SQLiteDB.Connection;

/**
 * Created by cloner on 8/22/17.
 */

public class app extends Application {


    public static Connection MyDataBase;
    static public SQLiteDatabase DB;

    public static String[] colors = { "#000000","#ffffff", "#00ddff", "#0099cc", "#33b5e5", "#669900", "#99cc00", "#ff8800", "#ffbb33", "#aa66cc", "#cc0000", "#ff4444"};

    public static ArrayList<String> fontsName = new ArrayList<String>();
    public static ArrayList<Typeface> fonts = new ArrayList<Typeface>();


    public static boolean unlock=false;
    public static boolean passStatus=false;
    public static String password;
    public static boolean viewMode;



    public static  noteAdapter adapter;
    public static  List<noteModel> homeNoteList;

    public static ArrayList<Integer> id = new ArrayList<Integer>();
    public static ArrayList<String> title = new ArrayList<String>();
    public static ArrayList<String> text = new ArrayList<String>();
    public static ArrayList<String> imgTitle = new ArrayList<String>();
    public static ArrayList<Integer> backgroundColor = new ArrayList<Integer>();
    public static ArrayList<Integer> bookmark = new ArrayList<Integer>();



    @Override
    public void onCreate() {
        super.onCreate();

        MyDataBase = new Connection(this);
        DB = MyDataBase.getWritableDatabase();

        // Get User Information
        String SelectQuery = "SELECT * FROM user WHERE id=1";
        Cursor cursor = DB.rawQuery(SelectQuery, null);
        cursor.moveToFirst();

        if(cursor.getInt(cursor.getColumnIndex("passwordStatus"))==1){
            passStatus=true;
            password=cursor.getString(cursor.getColumnIndex("password"));
        }



        if(cursor.getInt(cursor.getColumnIndex("viewMode"))==1){
            viewMode=true;
        }else
            viewMode=false;






        fontsName.add("RobotoSlab-Regular.ttf");


        fontsName.add("iran_sans.ttf");


        fontsName.add("BBaran.ttf");
        fontsName.add("BFarnaz.ttf");
        fontsName.add("BHamid.ttf");
        fontsName.add("BNazanin.ttf");
        fontsName.add("BTehran.ttf");
        fontsName.add("BYekan.ttf");





        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/RobotoSlab/RobotoSlab-Regular.ttf"));


        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/IranSans/iran_sans.ttf"));


        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/Bseries/BBaran.ttf"));
        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/Bseries/BFarnaz.ttf"));
        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/Bseries/BHamid.ttf"));
        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/Bseries/BNazanin.ttf"));
        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/Bseries/BTehran.ttf"));
        fonts.add(Typeface.createFromAsset(getAssets(), "fonts/Bseries/BYekan.ttf"));


    }


    public static void prepareNotes() {


        getNoteList();

            for(int i = 0 ; i<title.size();i++){
                noteModel b = new noteModel(id.get(i),title.get(i),text.get(i),imgTitle.get(i),backgroundColor.get(i),bookmark.get(i));
                    homeNoteList.add(b);
            }

            adapter.notifyDataSetChanged();
        }




    public static void getNoteList(){


        String SelectQuery = "SELECT * FROM notes";
        Cursor cursor = DB.rawQuery(SelectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {

            id.clear();
            title.clear();
            text.clear();
            imgTitle.clear();
            backgroundColor.clear();
            bookmark.clear();


            if (cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex("id"));
                    String _title = cursor.getString(cursor.getColumnIndex("title"));
                    String _text = cursor.getString(cursor.getColumnIndex("text"));
                    String _imgTitle = cursor.getString(cursor.getColumnIndex("image"));
                    int _backgroundColor = cursor.getInt(cursor.getColumnIndex("backgroundColor"));
                    int _bookmark = cursor.getInt(cursor.getColumnIndex("bookmark"));


                    id.add(_id);
                    title.add(_title);
                    text.add(_text);
                    backgroundColor.add(_backgroundColor);
                    imgTitle.add(_imgTitle);
                    bookmark.add(_bookmark);







                } while (cursor.moveToNext());
            }

            cursor.close();



        }


    }
}
