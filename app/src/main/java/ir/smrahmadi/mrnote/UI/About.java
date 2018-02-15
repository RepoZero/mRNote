package ir.smrahmadi.mrnote.UI;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.smrahmadi.mrnote.R;

public class About extends AppCompatActivity {

    @BindView(R.id.About_txt)protected TextView About_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        this.setTitle("About");

        String versionName = null;
        try {
             versionName = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        About_txt.setText("Developed By : " + "\n" +
                "Mohammad Reza Ahmadi" + "\n \n" +
                "version : "+versionName);

    }
}
