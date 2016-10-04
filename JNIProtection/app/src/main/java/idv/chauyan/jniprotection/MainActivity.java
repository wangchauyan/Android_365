package idv.chauyan.jniprotection;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI(getPackageSignature(getApplicationContext())));
    }

    private String getPackageSignature(Context context) {
        String hash_key = null;

        try {
            PackageInfo info = ((Application) context).getPackageManager().getPackageInfo(
                    ((Application) context).getPackageName(),
                    PackageManager.GET_SIGNATURES
            );

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hash_key = new String(Base64.encode(md.digest(), 0));

                if (hash_key != null) return hash_key;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return hash_key;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI(String signature);

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
