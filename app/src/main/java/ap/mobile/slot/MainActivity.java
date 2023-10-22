package ap.mobile.slot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView gambar1, gambar2, gambar3, sensational;
    private Thread thread;
    private Handler handler;
    private int slot = 0;

    private boolean isWinning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btStartStop).setOnClickListener(this);

        this.sensational = findViewById(R.id.SENSATIONAL);

        this.gambar1 = (ImageView) findViewById(R.id.gambar1);
        this.gambar2 = (ImageView) findViewById(R.id.gambar2);
        this.gambar3 = (ImageView) findViewById(R.id.gambar3);
        this.handler = new Handler(Looper.getMainLooper());
        createThread();
    }

    private void createThread() {
        int[] images = {R.drawable.slot_1, R.drawable.slot_2, R.drawable.slot_3, R.drawable.slot_4, R.drawable.slot_5, R.drawable.slot_6,R.drawable.slot_7, R.drawable.slot_8, R.drawable.slot_9};
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int[] gambar = new int[3];
                    while (true) {
                        switch (slot){
                            case 3:
                                gambar[0] = (int) (Math.random() * images.length);
                            case 2:
                                gambar[1] = (int) (Math.random() * images.length);
                            case 1:
                                gambar[2] = (int) (Math.random() * images.length);
                                break;
                        }

                        // Set the images of gambar1, gambar2, and gambar3.
                        final int[] finalGambar = gambar;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                gambar1.setImageDrawable(getDrawable(images[finalGambar[0]]));
                                gambar2.setImageDrawable(getDrawable(images[finalGambar[1]]));
                                gambar3.setImageDrawable(getDrawable(images[finalGambar[2]]));

                                if (finalGambar[0] == finalGambar[1] && finalGambar[1] == finalGambar[2]) {
                                    isWinning = true;
                                } else {
                                    isWinning = false;
                                }
                            }
                        });
                        Thread.sleep(900);
                    }
                } catch (Exception e) {}
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (slot > 1) {
            slot--;
        } else if (slot == 1) {
            slot--;
            this.thread.interrupt();
        } else {
            slot = 3;
            this.createThread();
            this.thread.start();
        }

        if(isWinning) {
            sensational.setVisibility(View.VISIBLE);
            isWinning = false;
        } else {
            sensational.setVisibility(View.INVISIBLE);
        }

    }
}