package com.jamal.marathon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context) {
        super(context, "marathon.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table candidats(id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT,prenom TEXT,email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists candidats");
        onCreate(db);
    }

    public boolean insertCondidat(Condidat condidat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", condidat.getNom());
        contentValues.put("prenom", condidat.getPrenom());
        contentValues.put("email", condidat.getEmail());
        long result = db.insert("candidats", null, contentValues);
        return (result == -1) ? false : true;
    }

    public Boolean UpdateCondidat(Condidat condidat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", condidat.getNom());
        contentValues.put("prenom", condidat.getPrenom());
        contentValues.put("email", condidat.getEmail());
        Cursor cursor = db.rawQuery("select * from candidats where id=?", new String[]{Integer.toString(condidat.getId())});
        if (cursor.getCount()>0){
            long result = db.update("candidats", contentValues, "id=?", new String[]{Integer.toString(condidat.getId())});
            return (result == -1) ? false : true;
        } else {
            return null;
        }
    }

    public Boolean deleteCondidat(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from candidats where id=?", new String[]{Integer.toString(id)});
        if (cursor.getCount()>0){
            long result = db.delete("candidats", "id=?", new String[]{Integer.toString(id)});
            return (result == -1) ? false : true;
        } else {
            return null;
        }
    }

    public List<Condidat> getAllCondidats(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from candidats", new String[]{});
        List<Condidat> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                list.add(new Condidat(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
