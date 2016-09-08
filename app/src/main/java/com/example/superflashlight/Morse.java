package com.example.superflashlight;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suluner on 2016/8/31.
 */
public class Morse extends WarningLight {
    private final int DOT_TIME = 200;
    private final int LINE_TIME = DOT_TIME * 3;
    private final int DOT_LINE_TIME = DOT_TIME;

    private final int CHAR_CHAR_TIME = DOT_TIME * 3;
    private final int WORD_WORD_TIME = DOT_TIME * 7;

    private String mMorseCode;
    private Map<Character, String> mMorseCodeMap = new HashMap<Character, String>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMorseCodeMap.put('a', ".-");
        mMorseCodeMap.put('b', "-...");
        mMorseCodeMap.put('c', "-.-.");
        mMorseCodeMap.put('d', "-..");
        mMorseCodeMap.put('e', ".");
        mMorseCodeMap.put('f', "..-.");
        mMorseCodeMap.put('g', "--.");
        mMorseCodeMap.put('h', "....");
        mMorseCodeMap.put('i', "..");
        mMorseCodeMap.put('j', ".---");
        mMorseCodeMap.put('k', "-.-");
        mMorseCodeMap.put('l', ".-..");
        mMorseCodeMap.put('m', "--");
        mMorseCodeMap.put('n', "-.");
        mMorseCodeMap.put('o', "---");
        mMorseCodeMap.put('p', ".--.");
        mMorseCodeMap.put('q', "--.-");
        mMorseCodeMap.put('r', ".-.");
        mMorseCodeMap.put('s', "...");
        mMorseCodeMap.put('t', "-");
        mMorseCodeMap.put('u', "..-");
        mMorseCodeMap.put('v', "...-");
        mMorseCodeMap.put('w', ".--");
        mMorseCodeMap.put('x', "-..-");
        mMorseCodeMap.put('y', "-.--");
        mMorseCodeMap.put('z', "--..");

        mMorseCodeMap.put('0', "-----");
        mMorseCodeMap.put('1', ".----");
        mMorseCodeMap.put('2', "..---");
        mMorseCodeMap.put('3', "...--");
        mMorseCodeMap.put('4', "....-");
        mMorseCodeMap.put('5', ".....");
        mMorseCodeMap.put('6', "-....");
        mMorseCodeMap.put('7', "--...");
        mMorseCodeMap.put('8', "---..");
        mMorseCodeMap.put('9', "----.");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick_SendMorseCode(View view)
    {
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            Toast.makeText(this,"当前设备没有闪光灯",Toast.LENGTH_LONG).show();
            return;
        }
        if (verifyMorseCode())
        {
            sendSentence(mMorseCode);
        }
    }

    protected void sleepExt(long t) {
        try {
            Thread.sleep(t);
        } catch (Exception e) {

        }
    }

    private void sendDot() {
        openFlashlight();
        sleepExt(DOT_TIME);
        closeFlashlight();
    }

    private void sendLine() {
        openFlashlight();
        sleepExt(LINE_TIME);
        closeFlashlight();
    }

    private void sendChar(char c) {
        String morseCode = mMorseCodeMap.get(c);
        if (morseCode != null) {
            char lastChar = ' ';
            for (int i = 0; i < morseCode.length(); i++) {
                char dotLine = morseCode.charAt(i);
                if (dotLine == '.') {
                    sendDot();
                } else if (dotLine == '-') {
                    sendLine();
                }
                if (i > 0 && i < morseCode.length() - 1) {
                    if (lastChar == '.' && dotLine == '-') {
                        sleepExt(DOT_LINE_TIME);
                    }
                }
                lastChar = dotLine;
            }
        }

    }

    private void sendWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sendChar(c);
            if (i < s.length() - 1) {
                sleepExt(CHAR_CHAR_TIME);
            }
        }
    }

    private void sendSentence(String s) {
        String[] words = s.split(" +");
        for (int i = 0; i < words.length; i++) {
            sendWord(words[i]);
            if (i < words.length - 1) {
                sleepExt(WORD_WORD_TIME);
            }
        }
        Toast.makeText(this, "摩尔斯电码已经发送完成！", Toast.LENGTH_LONG).show();
    }

    private boolean verifyMorseCode() {
        mMorseCode = mEditTextMorseCode.getText().toString().toLowerCase();
        if ("".equals(mMorseCode)) {
            Toast.makeText(this, "请输入摩尔斯电码字符串！", Toast.LENGTH_LONG).show();
            return false;
        }

        for (int i = 0; i < mMorseCode.length(); i++) {
            char c = mMorseCode.charAt(i);
            if (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c != ' ') {
                Toast.makeText(this, "摩尔斯电码只能是字母和数字！", Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        return true;
    }
}
