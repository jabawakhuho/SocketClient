package com.sds.study.socketclient;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 Fragment란 Activity내의 화면 일부를 관리하는 객체
 따라서 액티비티의 생명주기에 의존적이며, 화면을 관리한다는 점에 있어서는 액티비티화 상당히 유사하므로
 자체적으로 생명주기 메서드가 있다.
 */

public class ConfigFragment extends Fragment implements View.OnClickListener{
    String TAG;
    EditText txt_ip,txt_port,txt_nickname;
    ImageView img;
    View view;
    Button bt_regist;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG=getClass().getName();
        view=inflater.inflate(R.layout.configfragment_layout,null);
        txt_ip=(EditText)view.findViewById(R.id.txt_ip);
        txt_port=(EditText)view.findViewById(R.id.txt_port);
        txt_nickname=(EditText)view.findViewById(R.id.txt_nickname);
        bt_regist=(Button)view.findViewById(R.id.bt_regist);
        bt_regist.setOnClickListener(this);
        img = (ImageView)view.findViewById(R.id.img);

        return view;
    }

    /*각 뷰에 알맞는 데이터 넣기*/
    public void setData(Chat chat){
        txt_ip.setText(chat.getIp());
        txt_port.setText(Integer.toString(chat.getPort()));
        txt_nickname.setText(chat.getNickname());
        AssetManager asset=getContext().getAssets();
        try {
            InputStream is = asset.open(chat.getImg());
            Bitmap bitmap= BitmapFactory.decodeStream(is);
            img.setImageBitmap(bitmap);

            Log.d(TAG,"이미지날옴?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG,"버튼 누름");
        MainActivity mainActivity=(MainActivity)getContext();
        Chat chat = new Chat();
        chat.setIp(txt_ip.getText().toString());
        chat.setPort(Integer.parseInt(txt_port.getText().toString()));
        chat.setNickname(txt_nickname.getText().toString());
        Log.d(TAG,"update 시작");
        mainActivity.chatDAO.update(chat);
    }
}
