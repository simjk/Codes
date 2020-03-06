package e.j_enn.repa.boundary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import e.j_enn.repa.R;

public class HomeLauncher extends AppCompatActivity {
    //SLEEP_TIME = 3;
    private int SLEEP_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread {
        public void run() {
            try {
                sleep(1000 * SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(HomeLauncher.this, LoginActivity.class);
            startActivity(intent);
            HomeLauncher.this.finish();
        }
    }
}
