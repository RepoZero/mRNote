package ir.smrahmadi.mrnote.SQLiteDB;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Connection extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "res.zip";
    private static final int DATABASE_VERSION = 1;

    public Connection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();

    }




}
