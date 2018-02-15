package ir.smrahmadi.mrnote.UI;

import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.smrahmadi.mrnote.R;

import static ir.smrahmadi.mrnote.app.DB;
import static ir.smrahmadi.mrnote.app.passStatus;
import static ir.smrahmadi.mrnote.app.password;

public class Settings extends AppCompatActivity {

    @BindView(R.id.settings_Linear_SetPassword) protected LinearLayout Linear_SetPassword ;
    @BindView(R.id.settings_Linear_RemovePassword) protected LinearLayout Linear_RemovePassword ;


    @BindView(R.id.settings_edt_Set_Password) protected EditText edt_Set_Password ;
    @BindView(R.id.settings_edt_Set_Verify) protected EditText edt_Set_Verify ;
    @BindView(R.id.settings_Btn_SetPassword) protected Button Btn_SetPassword ;


    @BindView(R.id.settings_edt_Remove_Password) protected EditText edt_Remove_Password ;
    @BindView(R.id.settings_Btn_RemovePassword) protected Button Btn_RemovePassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle("Settings");
        ButterKnife.bind(this);
        passwordView();


        Btn_SetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_Set_Password.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Insert Password", 1500).show();

                    return;
                }

                if (TextUtils.isEmpty(edt_Set_Verify.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Insert Verify Password", 1500).show();

                    return;
                }

                if(edt_Set_Password.getText().toString().equals(edt_Set_Verify.getText().toString())){

                    if(edt_Set_Password.getText().toString().length()<4){
                        Snackbar.make(getWindow().getDecorView().getRootView(), "The password must be at least four letters", 1500).show();
                        return;
                    }

                    String UpdateQuery = "UPDATE user SET passwordStatus=1,password='"+edt_Set_Password.getText().toString()+"'";
                    Cursor cursor = DB.rawQuery(UpdateQuery, null);
                    cursor.moveToFirst();
                    passStatus=true;
                    password=edt_Set_Password.getText().toString();
                    passwordView();

                    Snackbar.make(getWindow().getDecorView().getRootView(), "Password was inserted", 1500).show();




                }else
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Password and Verify Password mismatch", 1500).show();

            }
        });



        Btn_RemovePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edt_Remove_Password.getText().toString())){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Insert Password", 1500).show();
                    return;
                }

                if(edt_Remove_Password.getText().toString().length()<4){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "The password is wrong", 1500).show();
                    return;
                }


                if(edt_Remove_Password.getText().toString().equals(password)) {


                    String UpdateQuery = "UPDATE user SET passwordStatus=0,password='" + edt_Remove_Password.getText().toString() + "'";
                    Cursor cursor = DB.rawQuery(UpdateQuery, null);
                    cursor.moveToFirst();
                    passStatus = false;
                    password = edt_Remove_Password.getText().toString();
                    passwordView();

                }else
                    Snackbar.make(getWindow().getDecorView().getRootView(), "The password is wrong", 1500).show();

            }
        });



    }

    void passwordView(){
        Linear_SetPassword.setVisibility(View.GONE);
        Linear_RemovePassword.setVisibility(View.GONE);

        if(passStatus){
            Linear_RemovePassword.setVisibility(View.VISIBLE);
        }else
            Linear_SetPassword.setVisibility(View.VISIBLE);
    }
}
