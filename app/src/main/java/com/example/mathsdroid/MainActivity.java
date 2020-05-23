package com.example.mathsdroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private EditText ET;
    String str, str_initial, str4;
    Scanner scan = new Scanner(System.in);
    Intent intent;




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
                        + "  .greatest(X) recevoir une notification du nombre premier calculé chaque x secondes\n"
                        + "  .charge\n"
                        + "  .C(X):Combinaison de X\n"
                        + "  .A(X):Arrangement de X\n"
                        + "  .ppmc(X,Y):ppmc de X et Y\n"
                        + "  .pgcd(C,Y):pgcd de X et Y\n" + str);
                ET.setText(str4);
                ET.setSelection(str4.length());
                no_editable();
            }

//--------------------------------------nombre premier----------------------------------------------

            if (str_now.indexOf("prime") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
                str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(")"));
                if (android.text.TextUtils.isDigitsOnly(str_nb1) == true) {
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
                if (android.text.TextUtils.isDigitsOnly(str_nb1) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    ArrayList<Long> list = new ArrayList<Long>();
                    for (i = 1; i <= nb1; i++) {

                        if (nb1 % i == 0) {
                            list.add(i);
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
                if (android.text.TextUtils.isDigitsOnly(str_nb1) == true && android.text.TextUtils.isDigitsOnly(str_nb2) == true) {
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
                if (android.text.TextUtils.isDigitsOnly(str_nb1) == true && android.text.TextUtils.isDigitsOnly(str_nb2) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    nb2 = Integer.parseInt(str_nb2);
                    ArrayList<Long> list = new ArrayList<Long>();
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
                            list.add(i);
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
                if (android.text.TextUtils.isDigitsOnly(str_nb1) == true) {
                    nb1 = Integer.parseInt(str_nb1);
                    str4 = (str2 + "\n" + " Vous receverez une notification chaque " + nb1 + " secondes \n" + str);

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
            str4 = (str2 + "\n" + "   charge \n" + str);
            ET.setText(str4);
            ET.setSelection(str4.length());
            no_editable();
        }

//---------------------------------------Factoriel d'un nombre--------------------------------------

        if (str_now.indexOf("fact") != -1 && redondance1(str_now) == 1 && str_now.indexOf("(") < str_now.indexOf(")")) {
            str_nb1 = str_now.substring(str_now.indexOf("(") + 1, str_now.indexOf(")"));
            if (android.text.TextUtils.isDigitsOnly(str_nb1) == true) {
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
            if (android.text.TextUtils.isDigitsOnly(str_nb1) == true && android.text.TextUtils.isDigitsOnly(str_nb2) == true) {
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
            if (android.text.TextUtils.isDigitsOnly(str_nb1) == true && android.text.TextUtils.isDigitsOnly(str_nb2) == true) {
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
            if (android.text.TextUtils.isDigitsOnly(str_nb1) == true && android.text.TextUtils.isDigitsOnly(str_nb2) == true) {
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
            if (android.text.TextUtils.isDigitsOnly(str_nb1) == true && android.text.TextUtils.isDigitsOnly(str_nb2) == true) {
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
        if (j == 0)
            str4 = (str2 + "\n" + "   " + nb1 + " est un nombre premier\n" + str);
        else
            str4 = (str2 + "\n" + "   " + nb1 + " n'est pas un nombre\n   premier\n" + str);

    }



//--------------------------------------------------------------------------------------------------


    public void primeBet(long nb1,long nb2,String str2){
        int i,j=0;
        for(i=2;i<=Math.min(nb1, nb2);i++){
            if(nb1%i==0 && nb2%i==0)
                j++;
        }
        if(j==0)
            str4=(str2 +"\n"+"   "+ nb1 + " et " + nb2 + " sont premier\n   entre eux \n"+str);
        else
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




}
