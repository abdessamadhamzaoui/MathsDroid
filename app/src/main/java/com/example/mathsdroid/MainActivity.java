package com.example.mathsdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText ET;
    String str, str_initial, str4;
    Scanner scan = new Scanner(System.in);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET=(EditText)findViewById(R.id.Edit1);
        btn=(Button)findViewById(R.id.btn);
        str4=ET.getText().toString();

        infinit();

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verification();
            }
        });
    }




    //infinit: used in the first time for making the text no editable and
    public void infinit(){
        str=ET.getText().toString();
        ET.setSelection(str.length());
        Selection.setSelection(ET.getText(), ET.getText().length());
        no_editable();
    }





    //verification: for verify that the command entered is a valid command and run the code of this command
    public void verification(){
        String str2=ET.getText().toString(),str_now;

        //str_now: pour avoir seulement la commande entrée
        str_now=str2.substring(str4.length(),str2.length());

        ET=(EditText)findViewById(R.id.Edit1);

        if(str_now.indexOf("clear")==-1){

            if(str_now.indexOf("help")!=-1){
                str4=(str2 +"\n"+"   prime \n"+"   dividers \n"+"   primeBet \n"+"   interval \n"+"   greatest \n"+"   charge \n"+"   arr \n"+"   multcum \n"+"   pgcd \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("prime")!=-1){
                str4=(str2 +"\n"+"   prime_now \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("dividers")!=-1){
                str4=(str2 +"\n"+"   dividers \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("primBet")!=-1){
                str4=(str2 +"\n"+"   primBet \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("interval")!=-1){
                str4=(str2 +"\n"+"   interval \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("greatest")!=-1){
                str4=(str2 +"\n"+"   greatest \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("charge")!=-1){
                str4=(str2 +"\n"+"   charge \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("arr")!=-1){
                str4=(str2 +"\n"+"   arr \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("multcum")!=-1){
                str4=(str2 +"\n"+"   multcum \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }
            if(str_now.indexOf("pgcd")!=-1){
                str4=(str2 +"\n"+"   pgcd \n"+str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

        }
        else {
            ET.setText(str);
        }
    }






    //no_editable:For making the old text no removable and no editable
    public void no_editable(){
        final EditText ET_no_edit=(EditText)findViewById(R.id.Edit1);
        final String str_now=ET_no_edit.getText().toString();
        ET_no_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith(str_now)){
                    ET_no_edit.setText(str_now);
                    Selection.setSelection(ET_no_edit.getText(), ET_no_edit.getText().length());
                }
            }
        });
    }
}
