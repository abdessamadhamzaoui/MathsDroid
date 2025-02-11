package com.example.mathsdroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageButton;

import android.widget.Toast;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class MainActivity extends AppCompatActivity /*implements View.OnClickListener */{

    private ImageButton btn;
    private ImageButton btnplus;
    private EditText ET;
    String str, str_initial, str4;
    Scanner scan = new Scanner(System.in);
    Intent intent;
    DataBaseConnection db =new DataBaseConnection(this);
    boolean verify_format=false;
    boolean verify_prime=false;
    boolean verify_interval=false;
    boolean verify_div=false;
    long numprime;
    long numdiv;
    ArrayList<Long> listcopy = new ArrayList<Long>();
    ArrayList<Long> listcopydiv = new ArrayList<Long>();
    static int a=0;
    ValueEventListener valueEventListener;
    int count=0;
    int id =0;
    private Query query;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String userId;
    private Integer numb;
    String maxScoreLevel;
    String maxScoreName;
    final ArrayList<Long> list=new ArrayList<>();
    private String strrr=" ";
    private ArrayList<HashMap<String, Object>> hashList;
    final ArrayList<Long> list1=new ArrayList<>();

    private DatabaseReference ref = database.getInstance().getReference("PrimaryNumbers");



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET=(EditText)findViewById(R.id.Edit1);
        btn= findViewById(R.id.btn);
        //btn.setOnClickListener( this);
        btnplus = findViewById(R.id.btnplus);
        //btnplus.setOnClickListener(this);
        str4=ET.getText().toString();
        list.add(0, (long) 75);
        list.add(1, (long) 751);
        list.add(2, (long) 71);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verification();
                addData(v);
            }
        });
        btnplus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deletAllData(v);
            }
        });
        DatabaseReference reference = database.getReference("PrimaryNumbers");
        reference.keepSynced(true);
        getRecord();
        infinit();

    }

    // add primary numbers to firebase DB
    public void sendmsg(long nb) {
        DatabaseReference myRef = database.getReference();
        String userId = myRef.push().getKey();
        HashMap hash = new HashMap();
        hash.put("Value",nb);
        myRef.child("PrimaryNumbers").child("Number"+userId).setValue(hash);
        id++;
    }





                private void getRecord() {

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            int cmp=0;
                            GenericTypeIndicator<Map<String, Long>> to = new GenericTypeIndicator<Map<String, Long>>() {};

                            for(DataSnapshot Snapshot : dataSnapshot.getChildren()){

                                Map<String, Long> PN= (Map<String, Long>) Snapshot.getValue(to);
                                long ya=PN.get("Value");
                                if(!list1.contains(ya)) {
                                    list1.add(ya);
                                    //dup=ya;
                                    cmp++;
                                }
                            }
                            Collections.sort(list1);
                            int ten=0;

                                for (int i = cmp - 1; i > cmp-11; i--)
                                        strrr = (strrr + "\n   " + list1.get(i));

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("efd", "onCancelled", databaseError.toException());
                        }
                    });

                }



    /*@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                verification(myRef);
                addData(v);
                break;
            case R.id.btnplus:
                deletAllData(v);
        }

    }*/



    //infinit: used in the first time for making the text no editable and
    public void infinit(){
        str=ET.getText().toString();
        ET.setSelection(str.length());
        Selection.setSelection(ET.getText(), ET.getText().length());
        no_editable();
    }





    //verification: for verify that the command entered is a valid command and run the code of this command
    public void verification() {
        String str2 = ET.getText().toString(), str_now;
        //str_now: pour avoir seulement la commande entrée
        str_now = str2.substring(str4.length(), str2.length());
        ET = (EditText) findViewById(R.id.Edit1);

        String str_nb1, str_nb2;
        long nb1, nb2, i, j = 0, k, cmpt = 0;


        if (str_now.indexOf("clear") == -1) {

            if (str_now.indexOf("help") != -1) {
                str4 = (str2 + "\n" + "  .prime(X):X premier ou non\n"
                        + "  .div(X):Les diviseurs de X\n"
                        + "  .primeBet(X,Y):X et Y\n   premier entre eux\n"
                        + "  .interval(X,Y):les nombres\n   premiers entre X et Y\n"
                        + "  .greatest(X) recevoir une\n   notification du nombre premier\n   calculé chaque x secondes\n"
                        + "  .charge\n"
                        + "  .C(X,Y):Combinaison Y de X\n"
                        + "  .A(X,Y):Arrangement Y de X\n"
                        + "  .ppmc(X,Y):ppmc de X et Y\n"
                        + "  .pgcd(C,Y):pgcd de X et Y\n" + str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//--------------------------------------nombre premier----------------------------------------------

            if (str_now.indexOf("prime") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true) {

                    this.verify_format=true;
                    nb1 = Integer.parseInt(str_nb1);
                    prime(nb1, str2);
                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//---------------------------------------les diviseurs----------------------------------------------

            if (str_now.indexOf("div") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true) {
                    this.verify_format=true;
                    this.verify_div=true;
                    nb1 = Integer.parseInt(str_nb1);
                    this.numdiv=nb1;
                    ArrayList<Long> list = new ArrayList<Long>();
                    for (i = 1; i <= nb1; i++) {

                        if (nb1 % i == 0) {
                            list.add(i);
                            this.listcopydiv.add(i);
                            cmpt++;
                        }
                    }
                    for (i = 0; i < cmpt; i++) {
                        str2 = str2 + "\n   " + list.get((int) i).toString() + " ";
                        str4 = (str2 + "\n" + str);
                    }
                    ET.setText(str4);
                    ET.setSelection(str4.length());
                    no_editable();
                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//----------------------------------nombres premiers entre eux--------------------------------------

            if (str_now.indexOf("primBet") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(","));
                str_nb2 = str_now.substring(str_now.indexOf(",") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true && TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    primeBet(nb1, nb2, str2);
                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//----------------------------------nombres premiers dans un interval-------------------------------

            if (str_now.indexOf("interval") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(","));
                str_nb2 = str_now.substring(str_now.indexOf(",") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true && TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    ArrayList<Long> list = new ArrayList<Long>();
                    this.verify_format=true;
                    this.verify_interval=true;
                    for (i = (long) nb1; i <= nb2; i++) {
                        if(i==1 || i==0){
                            j++;
                        }else {
                            for (k = 2; k <= Math.sqrt(i); k++) {
                                if (i % k == 0)
                                    j++;
                            }
                        }
                        if (j == 0) {
                            sendmsg(i);
                            list.add(i);
                            this.listcopy.add(i);
                            cmpt++;
                        }
                        j = 0;
                    }
                    for (i = 0; i < cmpt; i++)
                        str2 = str2 + "\n   " + list.get((int) i).toString() + " ";
                    str4 = (str2 + "\n" + str);
                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }


//--------------le plus grand nombre premier calculé par l'application(Base de données)-------------
            if (str_now.indexOf("greatest") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    str4 = (str2 + "\n" + "   Vous receverez une notification\n   chaque " + nb1 + " secondes \n" + str);

                    intent = new Intent(this, ServiceNotif.class);
                    intent.putExtra("argument", nb1);
                    startService(intent);

                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }



//---------------------charger les 10 grand derniers nombres premiers calculés----------------------

            if (str_now.indexOf("charge") != -1) {


                getRecord();
                str4=(str2 + "\n" + "   "+strrr+"\n" + str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                list1.clear();
                strrr=" ";
                no_editable();

            }

//---------------------------------------Factoriel d'un nombre--------------------------------------

            if (str_now.indexOf("fact") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    if (factoriel(nb1) > 0) {
                        str4 = (str2 + "\n" + "   " + nb1 + "! = " + factoriel(nb1) + "\n" + str);
                    } else if (nb1 < 0) {
                        str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                    } else {
                        str4 = (str2 + "\n" + "   " + nb1 + "! = " + factoriel(nb1) + "\n" + str);
                    }

                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();

            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//------------------------------------Combinaison de 2 nombres--------------------------------------

            if (str_now.indexOf("C") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(","));
                str_nb2 = str_now.substring(str_now.indexOf(",") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true && TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    if (nb1 >= 0 && nb2 >= 0 && factoriel(nb1 - nb2) >= 1) {
                        str4 = (str2 + "\n" + "   C(" + nb1 + "," + nb2 + ") = " + factoriel(nb1) / (factoriel(nb2) * (factoriel(nb1 - nb2))) + "\n" + str);
                    } else {
                        str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                    }

                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//------------------------------------Arrangement de 2 nombre---------------------------------------

            if (str_now.indexOf("A") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(","));
                str_nb2 = str_now.substring(str_now.indexOf(",") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true && TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    if (nb1 >= 0 && nb2 >= 0 && factoriel(nb1 - nb2) >= 1) {
                        str4 = (str2 + "\n" + "   A(" + nb1 + "," + nb2 + ") = " + factoriel(nb1) / (factoriel(nb1 - nb2)) + "\n" + str);
                    } else {
                        str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                    }

                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//-------------------------------------------PPMC de 2 nombres--------------------------------------

            if (str_now.indexOf("ppmc") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                long ppmc;
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(","));
                str_nb2 = str_now.substring(str_now.indexOf(",") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true && TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    if (nb1 >= 0 && nb2 >= 0) {
                        ppmc = (nb1 > nb2) ? nb1 : nb2;

                        while (true) {
                            if (ppmc % nb1 == 0 && ppmc % nb2 == 0) {
                                break;
                            }
                            ++ppmc;
                        }
                        str4 = (str2 + "\n" + "   " + ppmc + "\n" + str);
                    } else
                        str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            } else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//---------------------------------------------PGCD de 2 nombres------------------------------------

            if (str_now.indexOf("pgcd") != -1 && redondance1(str_now)==1 && str_now.indexOf("(")<str_now.indexOf(")")) {
                long pgcd = 1;
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(","));
                str_nb2 = str_now.substring(str_now.indexOf(",") + 1, str_now.indexOf(")"));
                if (TextUtils.isDigitsOnly(str_nb1) == true && TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    if (nb1 >= 0 && nb2 >= 0) {
                        for (i = 1; i <= nb1 && i <= nb2; ++i) {
                            if (nb1 % i == 0 && nb2 % i == 0)
                                pgcd = i;
                        }

                        str4 = (str2 + "\n" + "   " + pgcd + "\n" + str);
                    } else
                        str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                } else {
                    str4 = (str2 + "\n" + "   Format incorrect!\n" + str);
                }

                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }else {
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//--------------------------------------------------------------------------------------------------

        }
        else{
            ET.setText(str);
        }
    }


    //FIN DE LA FONCTION DE VERIFICATION


//--------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------


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


//--------------------------------------------------------------------------------------------------



    public void prime(long nb1,String str2){
        int i,j=0;
        if(nb1==1 || nb1==0) {
            j++;
        }else {
            for (i = 2; i <= Math.sqrt(nb1); i++) {
                if (nb1 % i == 0)
                    j++;
            }
        }
        if (j == 0) {
            //NbToDB=nb1;
            sendmsg(nb1);
            boolean prime =true;
            this.verify_prime=true;
            this.numprime=nb1;
            str4 = (str2 + "\n" + "   " + nb1 + " est un nombre premier\n" + str);
        }else
            str4 = (str2 + "\n" + "   " + nb1 + " n'est pas un nombre\n   premier\n" + str);
    }



//--------------------------------------------------------------------------------------------------


    public void primeBet(long nb1,long nb2,String str2){
        int i,j=0;
        for(i=2;i<=Math.min(nb1, nb2);i++){
            if(nb1%i==0 && nb2%i==0)
                j++;
        }
        if(j==0) {

            str4 = (str2 + "\n" + "   " + nb1 + " et " + nb2 + " sont premier\n   entre eux \n" + str);
        }else
            str4=(str2 +"\n"+"   "+ nb1 + " et " + nb2 + " ne sont pas\n   premier entre eux \n"+str);
    }


//--------------------------------------------------------------------------------------------------


    long factoriel(long nb1){
        long f=1;
        int i;

        if(nb1>0){
            for(i=1;i<=nb1;i++){
                f=f*i;
            }
        }else if(nb1<0){
            f=-1;
        }
        return f;
    }


//--------------------------------------------------------------------------------------------------


    int redondance1(String str){
        int cmp,i=0,j=0;
        for (cmp=0;cmp<str.length();cmp++){
            if(str.charAt(cmp)=='(')
                i++;
            if(str.charAt(cmp)==')')
                j++;
        }
        if(i==1 && j==1)
            return 1;
        else
            return 0;
    }


    int redondance2(String str){
        int cmp,i=0,j=0,k=0;
        for (cmp=0;cmp<str.length();cmp++){
            if(str.charAt(cmp)=='(')
                i++;
            if(str.charAt(cmp)==',')
                j++;
            if(str.charAt(cmp)==')')
                k++;
        }
        if(i==1 && j==1 && k==1)
            return 1;
        else
            return 0;
    }
//---------------------------------------------insert data into database----------------------------
    public void addData(View view){
        if(verify_format==true){

            if(verify_prime==true){
               boolean resul=db.insertData(numprime);
               if(resul==true)
                   Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                   else
                   Toast.makeText(MainActivity.this,"no",Toast.LENGTH_LONG).show();

            }
            else if(verify_interval==true){

                for(int i =0;i<listcopy.size();i++){
                    long numinsert=listcopy.get(i);
                    db.insertData(numinsert);
                }


            }
            else if (verify_div==true){
                if(listcopydiv.size()==2 && listcopydiv.get(0)==1 && listcopydiv.get(1)==numdiv){
                    long numinsert1=listcopydiv.get(1);
                    db.insertData(numinsert1);
                }

                }
            }

        }
//-------------------delete all records for downloading a precise number of data from web data------
               public void deletAllData(View view){
                        db.deleteAll();
               }



    public ArrayList<HashMap<String, Object>> recArrayList(DataSnapshot snapshot){
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        if (snapshot == null){
            return list;
        }
        // This is awesome! You don't have to know the data structure of the database.
        Object fieldsObj = new Object();
        HashMap fldObj;
        for (DataSnapshot shot : snapshot.getChildren()){
            try{
                fldObj = (HashMap)shot.getValue(fieldsObj.getClass());
            }catch (Exception ex){

                // My custom error handler. See 'ErrorHandler' in Gist
//                ErrorHandler.logError(ex);
                continue;
            }
            // Include the primary key of this Firebase data record. Named it 'recKeyID'
            fldObj.put("recKeyID", shot.getKey());
            list.add(fldObj);
        }

        return list;
    }

}




