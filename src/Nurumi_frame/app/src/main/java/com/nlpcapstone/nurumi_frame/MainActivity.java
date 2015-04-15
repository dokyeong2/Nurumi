package com.nlpcapstone.nurumi_frame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import static com.nlpcapstone.nurumi_frame.R.layout.dialog_setting;

/*
 * - Developed by Soyeong Park
 * 1) Implement Menu Frame and Delete Action Bar [03.26]
 * 2) Solve Portability (Layout, View ...) [03.26]
 * 3) Apply Background Transparency to Layout [unsolved]
 * 4) Menu Button Function [unsolved]
 *      (1) Inform [03.28]
 *      (2) Tutorial [03.31]
 *      (3) Mute
 *      (4) Setting [03.27]
 */

/**
 * @mainpage
 *
 * @version 0.0.1
 * @file MainActivity.java
 *
 * @brief This class forms Application Frame.
 * @details This class connects many function such as Inform, Tutorial, Mute, Setting.
 *
 * @author Soyeong Park, parksyeong39@gmail.com
 * @date 2015-03-26
 */
public class MainActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ToggleButton tbtn_inform, tbtn_tutorial, tbtn_mute;
    private Button btn_setting;

    private SettingActivity dlg_setting;
    private InformActivity dlg_inform;

    TextView tv; // sample textview (나중에 삭제)
    /**
     * @brief This method finds View's IDs and maps them on variables.
     *
     * @author Soyeong Park
     * @date 2015-03-26
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.txt_sample);

        tbtn_inform = (ToggleButton)findViewById(R.id.tbtn_inform);
        tbtn_tutorial = (ToggleButton)findViewById(R.id.tbtn_tutorial);
        tbtn_mute = (ToggleButton)findViewById(R.id.tbtn_mute);
        btn_setting = (Button)findViewById(R.id.btn_setting);

        tbtn_inform.setOnCheckedChangeListener(this);
        tbtn_tutorial.setOnCheckedChangeListener(this);
        tbtn_mute.setOnCheckedChangeListener(this);
        btn_setting.setOnClickListener(this);

        // about inform button
        dlg_inform = new InformActivity(MainActivity.this);
        dlg_inform.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dlg_inform.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        Log.i("inform", "00");
    }

    /**
     * @brief This method manages ToggleButton's function through one Listener like 'onCheckedChanged'.
     *
     * @param buttonView has ToggleButton's ID information.
     * @param isChecked has ToggleButton's current condition information.
     *
     * @author Soyeong Park
     * @date 2015-03-26
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tbtn_inform:
                tbtn_inform.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tbtn_inform.isChecked() == true) {
                            dlg_inform.requestWindowFeature(Window.FEATURE_NO_TITLE);

                            dlg_inform.show();
                        } else {
                            Log.i("inform", "33");
                            // kill LifeCycle
                            dlg_inform.dismiss();
                            // spare LifeCycle
                            dlg_inform = new InformActivity(MainActivity.this);
                            dlg_inform.requestWindowFeature(Window.FEATURE_NO_TITLE);

                            dlg_inform.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dlg_inform.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        }
                    }
                });
                //    break; // 꼭 해야하는지 연동 후 확인
            case R.id.tbtn_tutorial:
                SharedPreferences pref_setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                boolean bool_finger, bool_hand, bool_language, bool_tutorial;

                bool_finger = pref_setting.getBoolean("DATA_FINGER", true);
                bool_hand = pref_setting.getBoolean("DATA_HAND", true);
                bool_language = pref_setting.getBoolean("DATA_LANGUAGE", true);
                bool_tutorial = tbtn_tutorial.isChecked();

                if (bool_tutorial == true) {
                    setDefaultKeyImage(bool_finger);

                    // Call Hyeongsoon's code
                } else {
                    setTutorialKeyImage(bool_finger, bool_hand, bool_language);

                    // Call Hyeongsoon's code
                }
                break;
            case R.id.tbtn_mute:
                if (tbtn_mute.isChecked()) {
                    tv.setText("MUTE_ON");
                } else {
                    tv.setText("MUTE_OFF");
                }
                break;
        }
    }

    /**
     * @brief This method manages all View's function through one Listener like 'onClick'
     * @brief Examples about View are Button, TextView, ImageView ...
     * @details If you touch back button in Setting Dialog, setting's dialog will cancel. (->setCancelable(true))
     *
     * @param v has all View's ID information.
     *
     * @author soyeong Park
     * @date 2015-03-27
     */
    public void onClick(View v) {
        switch (v.getId()) {
            // about Setting CustomDialog
            case R.id.btn_setting:
            /*    SharedPreferences pref_setting = getSharedPreferences("setting", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref_setting.edit();
                Log.i("++Main", "1");
               if(!pref_setting.getString("DATA_FIRST_SETTING", "").equals("NO")) {
                    editor.putString("DATA_FIRST_SETTING", "YES");
                    editor.commit();
                    Log.i("++Main", "2");
                }
            */    dlg_setting = new SettingActivity(MainActivity.this);
                dlg_setting.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                //        saveSettingState();
                        Log.i("++Main", "4");
                    }
                });

                dlg_setting.show();

                break;
        }
    }

    /**
     * @brief This method saves setting's information about radio buttons.
     * @details If state value is true then state value is default setting value.
     * @details Setting is a file of <key, value>.
     * @details To save setting's information, this application uses SharedPreferences function.
     * @details If you want to use setting's information, call '@data' about DATA_HAND, DATA_FINGER, DATA_LANGUAGE.
     *
     * @data DATA_HAND has information about selected hand's direction.
     * @data DATA_FINGER has information about selected the number of fingers.
     * @data DATA_LANGUAGE has information about selected language.
     *
     * @author Soyeong Park
     * @date 2015-03-27
     */
    private void saveSettingState() {
        SharedPreferences pref_setting = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref_setting.edit();
        editor.putString("DATA_FIRST_SETTING", "NO");
        editor.commit();
        Log.i("++Main", "3");
        if(dlg_setting.getHand() == true) { editor.putBoolean("DATA_HAND", true); }
        else { editor.putBoolean("DATA_HAND", false); }

        if(dlg_setting.getFinger() == true) { editor.putBoolean("DATA_FINGER", true); }
        else { editor.putBoolean("DATA_FINGER", false); }

        if(dlg_setting.getLanguage() == true) { editor.putBoolean("DATA_LANGUAGE", true); }
        else { editor.putBoolean("DATA_LANGUAGE", false); }

        editor.commit();
    }

    /**
     * @brief When tutorial button is pressed, This method sets right finger key's image.
     *
     * @param bool_finger is information about the number of finger.
     * @param bool_hand is information direction of hand.
     * @param bool_language is information about selecting language.
     *
     * @author Soyeong Park
     * @date 2015-03-31
     *
     * @TODO 코드 연동 후 이미지 설정할 때 정확하게 구현 (현재 틀만 잡혀있음)
     */
    private void setTutorialKeyImage(boolean bool_finger, boolean bool_hand, boolean bool_language) {
        // TODO : 실제로는 setImageResource 이용하여 이미지 설정해야 함
        //    img_inform.setImageResource(R.drawable.ic_launcher);
        if(bool_finger == true && bool_hand == true && bool_language == true) {

        } // CASE1 : [5 손가락 / 오른손 / 한국어]
        else if(bool_finger == true && bool_hand == true && bool_language == false) {

        } // CASE2 : [5 손가락 / 오른손 / 영어]
        else if(bool_finger == true && bool_hand == false && bool_language == true) {

        } // CASE3 : [5 손가락 / 왼손 / 한국어]
        else if(bool_finger == true && bool_hand == false && bool_language == false) {

        } // CASE4 : [5 손가락 / 왼손 / 영어]
    /*
        else {

        } // [10 손가락]
    */
    }
    /**
     * @brief When tutorial button is unpressed, This method sets default finger key's image.
     *
     * @param bool_finger is information about the number of finger.
     *
     * @author Soyeong Park
     * @date 2015-03-31
     *
     * @TODO 코드 연동 후 이미지 설정할 때 정확하게 구현 (현재 틀만 잡혀있음)
     */
    private void setDefaultKeyImage(boolean bool_finger) {
        if(bool_finger == true) {
            // btn_1_finger.setImageResouce();
            // btn_2_finger.setImageResouce();
            // btn_3_finger.setImageResouce();
            // btn_4_finger.setImageResouce();
            // btn_5_finger.setImageResouce();
        } // [5 손가락]
    /*
       else {

        } // [10 손가락]
    */
    }

    @Override
    /////////////////////////////////////////////
    /// @fn
    /// @brief (Override method) Override onDestroy method.
    /// @author Park, Hyung Soon
    /// @date 2015-04-13
    /// @remark
    /// - Description
    ///	If the dialog is activated, dismiss it to evade leak.\n
    /////////////////////////////////////////////
    public void onDestroy() {
        super.onDestroy();
        if(dlg_inform != null && dlg_inform.isShowing()) {
            dlg_inform.dismiss();
            dlg_inform = null;
        }
    }
}