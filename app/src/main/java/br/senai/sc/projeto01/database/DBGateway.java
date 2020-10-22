package br.senai.sc.projeto01.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBGateway {

    private static DBGateway dbGatewey;
    private SQLiteDatabase db;

    public static DBGateway getInstance(Context context) {
        if (dbGatewey == null) {
            dbGatewey = new DBGateway(context);
        }
        return dbGatewey;
    }

    private DBGateway(Context context) {
        DatabaseDBHelper dbHelper = new DatabaseDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }
}
