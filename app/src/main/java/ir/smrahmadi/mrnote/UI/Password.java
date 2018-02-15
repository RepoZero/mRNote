package ir.smrahmadi.mrnote.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.smrahmadi.mrnote.R;

import static ir.smrahmadi.mrnote.app.password;
import static ir.smrahmadi.mrnote.app.unlock;

public class Password extends AppCompatActivity {

    @BindView(R.id.Password_imgLock) protected ImageView imgLock ;
    @BindView(R.id.Password_edtPass) protected EditText edtPass ;
    @BindView(R.id.Password_btnEnter) protected Button btnEnter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);


        btnEnter.setVisibility(View.GONE);



        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                btnEnter.setVisibility(View.GONE);

                if(edtPass.getText().toString().length()<4){
                    btnEnter.setVisibility(View.GONE);
                }else{
                    btnEnter.setVisibility(View.VISIBLE);
                }



            }
        });


        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(edtPass.getText().toString())){
                    edtPass.setError( "is Empty");
                    return;
                }

                if(edtPass.getText().toString().equals(password)){
                    unlock =true ;
                    startActivity(new Intent(Password.this,Home.class));
                    finish();
                }else {

                    edtPass.setError( "Entered password is wrong");

                    YoYo.with(Techniques.Shake)
                            .duration(200)
                            .repeat(2)
                            .playOn(findViewById(R.id.Password_imgLock));


                }

            }
        });


    }
}
