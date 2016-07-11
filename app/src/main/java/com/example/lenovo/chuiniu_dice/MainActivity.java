package com.example.lenovo.chuiniu_dice;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by Lenovo on 2016/7/4.
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {
    ImageView dice_01, dice_02, dice_03, dice_04, dice_05, dice_06, cover;
    Button shakingDice_Button;
    ImageView[] images = new ImageView[6];
    int diceNum=6;
    int[] showDices=new int[diceNum];
    boolean shaking;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("骰子王");
        toolbar.inflateMenu(R.menu.toobar_menu);
        toolbar.setNavigationIcon(R.drawable.home_picture);
        dice_01 = (ImageView) findViewById(R.id.dice_01);
        dice_02 = (ImageView) findViewById(R.id.dice_02);
        dice_03 = (ImageView) findViewById(R.id.dice_03);
        dice_04 = (ImageView) findViewById(R.id.dice_04);
        dice_05 = (ImageView) findViewById(R.id.dice_05);
        dice_06 = (ImageView) findViewById(R.id.dice_06);
        cover = (ImageView) findViewById(R.id.cover);
        shakingDice_Button = (Button) findViewById(R.id.shakingDice_Button);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        images[0] = dice_01;
        images[1] = dice_02;
        images[2] = dice_03;
        images[3] = dice_04;
        images[4] = dice_05;
        images[5] = dice_06;
        for (int i = 0; i < 6; i++) {
            images[i].setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < diceNum; i++) {
            images[i].setVisibility(View.VISIBLE);
            images[i].setImageResource(R.drawable.dice_1);
        }

        shaking = false;
        shakingDice_Button.setOnClickListener(this);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
              AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this).setTitle("请输入");
                dialog.setIcon(android.R.drawable.ic_dialog_info);
                final EditText editText_num=new EditText(MainActivity.this);
                dialog.setView(editText_num);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        diceNum=Integer.parseInt(editText_num.getText().toString());
                        animClose();

                    }
                });
                 dialog.setNegativeButton("取消", null).show();

                return true;

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(shaking){
            return;
        }
           animClose();
    }


    protected void animClose() {

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,

                Animation.RELATIVE_TO_SELF, 0,

                Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, DPUtils.dip2px(this, 300));

        animation.setDuration(100);

        animation.setFillAfter(true);

        Animation.AnimationListener listener = new Animation.AnimationListener() {

            @Override

            public void onAnimationEnd(Animation animation) {

                for (int i = 0; i < 6; i++) {

                    images[i].setVisibility(View.INVISIBLE);

                }
                animTranslate();

            }

            @Override

            public void onAnimationRepeat(Animation animation) {
            }

            @Override

            public void onAnimationStart(Animation animation) {
                shaking=true;
            }};

        animation.setAnimationListener(listener);

        cover.startAnimation(animation);
    }

    protected void animOpen() {

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
        Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, 0 - DPUtils.dip2px(this, 100));

        animation.setDuration(100);

        animation.setFillAfter(true);

        Animation.AnimationListener listener = new Animation.AnimationListener() {

            @Override

            public void onAnimationEnd(Animation animation) {
                cover.clearAnimation();
                 shaking=false;


            }

            @Override

            public void onAnimationRepeat(Animation animation) {
            }

            @Override

            public void onAnimationStart(Animation animation) {
                Map<String,String> map=new HashMap<>();
                map.put("0",String.valueOf(R.drawable.dice_1));
                map.put("1",String.valueOf(R.drawable.dice_2));
                map.put("2",String.valueOf(R.drawable.dice_3));
                map.put("3",String.valueOf(R.drawable.dice_4));
                map.put("4",String.valueOf(R.drawable.dice_5));
                map.put("5",String.valueOf(R.drawable.dice_6));

                for (int i = 0; i < diceNum; i++) {
                    showDices[i]=new Random().nextInt(6);
                }

                for(int i=0;i<diceNum;i++) {
                    images[i].setVisibility(View.VISIBLE);
                    if (showDices[i] == 0) {
                        images[i].setImageResource(Integer.parseInt(map.get("0")));
                    }else if(showDices[i]==1){
                        images[i].setImageResource(Integer.parseInt(map.get("1")));

                    }else if(showDices[i]==2){
                        images[i].setImageResource(Integer.parseInt(map.get("2")));

                    }else if(showDices[i]==3){
                        images[i].setImageResource(Integer.parseInt(map.get("3")));

                    }else if(showDices[i]==4){
                        images[i].setImageResource(Integer.parseInt(map.get("4")));

                    }else if(showDices[i]==5){
                        images[i].setImageResource(Integer.parseInt(map.get("5")));
                    }
                }
            }

        };

        animation.setAnimationListener(listener);

        cover.startAnimation(animation);
    }


    protected void animTranslate() {

//        if (cover == null) {
//
//            return;
//        }
//        cover.clearAnimation();
        TranslateAnimation animation = new TranslateAnimation(

                Animation.ABSOLUTE,0-DPUtils.dip2px(this, 100), Animation.ABSOLUTE, DPUtils.dip2px(this, 100),

                Animation.ABSOLUTE, DPUtils.dip2px(this,300),Animation.ABSOLUTE, DPUtils.dip2px(this,300));

        animation.setDuration(50);
        animation.setRepeatCount(10);
        animation.setRepeatMode(Animation.REVERSE);


        Animation.AnimationListener listener = new Animation.AnimationListener() {

            @Override

            public void onAnimationEnd(Animation animation) {

               animOpen();
            }


            @Override

            public void onAnimationRepeat(Animation animation) {
            }

            @Override

            public void onAnimationStart(Animation animation) {
            }

        };

        animation.setAnimationListener(listener);
        cover.startAnimation(animation);

    }

    protected void onDestroy(){
        super.onDestroy();
        if(sensorManager!=null){
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener=new SensorEventListener(){


        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue=Math.abs(event.values[0]);
            float yValue=Math.abs(event.values[1]);
            float zValue=Math.abs(event.values[2]);
            if(xValue>15||yValue>15||zValue>15){
                if(shaking){
                    return;
                }
                animClose();

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


}
