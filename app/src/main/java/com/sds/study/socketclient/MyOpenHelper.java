package com.sds.study.socketclient;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 *SQLite 데이터베이스 파일은 이미 그 위치가 정해져있고
 * (data/data/나의 패키지/databases)
 * 일반 유저는 절대로 접근할수 없는 내무 storage이기 때문에
 * 프로그래밍적으로 데이터 베이스 파일 생성 및 DDL을 수행해야 하는데,
 * 이 작업은 개발자에게 번거로운 작업이므로
 * SQLiteOpenHelper클래스가 지원된다.
 */

public class MyOpenHelper extends SQLiteOpenHelper{
    String TAG;
    ChatDAO chatDAO;

    public MyOpenHelper(Context context) {
        super(context, "chat.sqlite", null, 1);
        TAG=getClass().getName();

    }

    /*data/data/나의 패키지/databases 위치에 파일이 지정한 이름과
        * 동일한 파일이 없다면 아래의 메서드 호출*/
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table chat(");
        sb.append("chat_id integer primary key autoincrement");
        sb.append(", ip varchar(15)");
        sb.append(", port int");
        sb.append(", nickname varchar(25)");
        sb.append(", img varchar(30)");
        sb.append(");");

        try {
            db.execSQL(sb.toString());
            Log.d(TAG,"SQL문(DDL생성문)이 성공했습니다.");
            /*insert 할 예정*/
            chatDAO = new ChatDAO(db);
            Chat chat = new Chat();
            chat.setIp("192.168.0.20");
            chat.setPort(9090);
            chat.setNickname("huho");
            chat.setImg("kei.jpg");

            chatDAO.insert(chat);
        } catch (SQLException e) {
            Log.d(TAG,"SQL문(DDL생성문)이 잘못되었습니다.");
            e.printStackTrace();
        }
    }

    /*data/data/나의 패키지/databases위치에 이미 같은 파일이 존재하되, version 숫자가 틀려야 한다.*/
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
