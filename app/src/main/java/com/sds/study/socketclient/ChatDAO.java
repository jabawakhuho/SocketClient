package com.sds.study.socketclient;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by student on 2016-11-23.
 */

public class ChatDAO {
    String TAG;
    SQLiteDatabase db;
    public ChatDAO(SQLiteDatabase sqLiteDatabase) {
        db=sqLiteDatabase;
        TAG = getClass().getName();
    }

    public void insert(Chat chat){
        String sql = "insert into chat(ip,port,nickname,img) values(?,?,?,?)";
        try {
            db.execSQL(sql,new Object[]{
                    chat.getIp(),
                    chat.getPort(),
                    chat.getNickname(),
                    chat.getImg()});
            Log.d(TAG,"insert 성공?");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG,"insert 실패!?");
        }

    }

    public List selectAll(){
        return null;
    }

    public Chat select(int chat_id){
        Cursor rs=db.rawQuery("select * from chat",null);
        Chat chat = null;
        if(rs.moveToNext()){
            chat = new Chat();
            Log.d(TAG,"dto의 chat?"+chat);
            chat.setChat_id(rs.getInt(rs.getColumnIndex("chat_id")));
            chat.setIp(rs.getString(rs.getColumnIndex("ip")));
            Log.d(TAG,"dto의 chat의 ip?"+rs.getString(rs.getColumnIndex("ip")));
            chat.setPort(rs.getInt(rs.getColumnIndex("port")));
            chat.setNickname(rs.getString(rs.getColumnIndex("nickname")));
            chat.setImg(rs.getString(rs.getColumnIndex("img")));
        }
        return  chat;
    }

    public void update(Chat chat){
        String sql= "update chat set ip=?,port=?,nickname=?";
        try {
            db.execSQL(sql,new Object[]{chat.getIp(),chat.getPort(),chat.getNickname()});
            Log.d(TAG,"Update 성공");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG,"Update 실패");
        }

    }
    public void delete(int chat_id){

    }
}
