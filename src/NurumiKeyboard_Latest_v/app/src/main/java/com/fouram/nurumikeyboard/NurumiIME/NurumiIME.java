package com.fouram.nurumikeyboard.NurumiIME;

import android.app.AlertDialog;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ToggleButton;

import org.w3c.dom.Text;
//import android.widget.EditText;

/////////////////////////////////////////////
/// @class NurumiIME
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� NurumiIME.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26 |
/// @section Description
///  - Prototype of gesture based keyboard.
/////////////////////////////////////////////


/////////////////////////////////////////////
/// @class NurumiIME
///com.fouram.nurumikeyboard.NurumiIME \n
///   �� NurumiIME.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description
///  - Input method service class.\n
///  - This class makes user to replace keyboard.\n
/////////////////////////////////////////////
public class NurumiIME extends InputMethodService 
					   implements OnMKeyboardGestureListener {
	
	private final int FIVE_FINGERS = 5;
	//private final int TEN_FINGERS = 10;
	private int numFingers;
	private View entireView;
	private ViewGroup vg;
	private MKeyboardView mKeyboardView;
	private int[] motion;

    // Soyeong [2015.04.14]
    private Button btn_inform;
    private ToggleButton btn_tutorial;
    private ToggleButton btn_mute;
    private Button btn_setting;

	@Override
	public void onFinishInputView(boolean finishingInput) {
		super.onFinishInputView(finishingInput);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Function information
	/// @remark
	/// - Description
	///	When user put cursor in text box,\n
	///	custom layout will popup.\n
	///	The xml file of custom keyboard layout is in res\layout\mkeyboardlayout.xml\n
	/// @see android.inputmethodservice.InputMethodService#onCreateInputView()
	/////////////////////////////////////////////
	@Override
	public View onCreateInputView() {
		int layoutId = R.layout.mkeyboardlayout;
		entireView = (View)getLayoutInflater().inflate(layoutId, null);
		vg = (ViewGroup) entireView;
		mKeyboardView = (MKeyboardView) vg.findViewById(R.id.MKeyboardView);
		mKeyboardView.setIme(this);

        layoutId = R.layout.inform;
        View view = (View) getLayoutInflater().inflate(layoutId, null);
        ViewGroup vg2 = (ViewGroup) view;
     //   mInform = (Information) vg2.findViewById(R.id.inform); // 여기 수정해야함

		
		numFingers = FIVE_FINGERS;
		motion = new int[numFingers];
        Log.i("++MAIN", "SUCCESS");
        setViewId();

		return entireView;
	}
    /**
     * @function setViewId
     * @brief This method sets default value when the application executes.
     *
     * @data vg is Layout's View Group.
     * @see android.inputmethodservice.InputMethodService#onCreateInputView()
     * @author Soyeong Park
     * @date 2015-04-15
     */
    private void setViewId() {
        btn_inform = (Button)vg.findViewById(R.id.btn_inform);
        btn_inform.setOnClickListener(mClickListener);

        btn_tutorial = (ToggleButton)vg.findViewById(R.id.tbtn_tutorial);
        btn_tutorial.setOnClickListener(mClickListener);

        btn_mute = (ToggleButton)vg.findViewById(R.id.tbtn_mute);
        btn_mute.setOnClickListener(mClickListener);

        btn_setting = (Button)vg.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(mClickListener);
    }

    /**
     * @brief This method manages all View's function through one Listener like 'onClick'
     * @brief Examples about View are Button, TextView, ImageView ...
     * @details If you touch back button in Setting Dialog, setting's dialog will cancel. (->setCancelable(true))
     *
     * @param v has all View's ID information.
     *
     * @author Soyeong Park
     * @date 2015-04-15
     */
    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_inform: // DEFAULT: OFF
                    Log.i("++INFORM", v.toString());
                    onCreateDialog(v);
                    break;
            /*    case R.id.btn_tutorial: // DEFAULT: OFF
                    Log.i("++TUTORIAL", "1SUCCESS");

                    break;
                case R.id.btn_mute: // DEFAULT: ON
                    Log.i("++MUTE", "1SUCCESS");

                    break;*/
                case R.id.btn_setting: // DEFAULT: OFF
                    Log.i("++SETTING", "1SUCCESS");
                    onCreateDialog(v);
                    break;
            }
        }
    };


    /**
     * @brief This method open AlertDialog about Setting and Information.
     *
     * @author Soyeong Park
     * @date 2015-04-15
     */
    private void onCreateDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NurumiIME.this);

        // Get size of display
        Display display = ((WindowManager)getSystemService((Context.WINDOW_SERVICE))).getDefaultDisplay();
        int width = (int)(display.getWidth() * 0.95);
        int height = (int)(display.getHeight() * 0.95);

        switch(v.getId()) {
            case R.id.btn_inform:
                View infView = (View)getLayoutInflater().inflate(R.layout.inform, null);
                ViewGroup vg = (ViewGroup)infView;
                Information mInform = (Information)vg.findViewById(R.id.Inform);

                width = mInform.getImageWidth();
                height = mInform.getImageHeight();

                vg.removeView(mInform);
                builder.setView(mInform);

                break;
            case R.id.btn_setting:
                View view_setting = (View)getLayoutInflater().inflate(R.layout.setting, null);
                Setting mSetting = new Setting(getBaseContext());

            // TODO: reset width, height
            //    width = view_setting.getWidth(;
            //    height = view_setting.getHeight();


                builder.setView(view_setting);
                break;
        }

        final AlertDialog alert = builder.create();
        Window window = alert.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.token = mKeyboardView.getWindowToken();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;

        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        alert.show();
        // Set alertdialog's size
        alert.getWindow().setLayout(width, (int) (height * 1.2));

        Button cancel = (Button)alert.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

        Button confirm = (Button)alert.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

	@Override
	public boolean onShowInputRequested (int flags, boolean configChange) {
		return true;
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Function information
	/// @remark
	/// - Description
	///	Reset keyboard view when the input method window has been hidden from the user.
	/// @see android.inputmethodservice.InputMethodService#onWindowHidden()
	/////////////////////////////////////////////
	@Override
	public void onWindowHidden() {
		super.onWindowHidden();
		mKeyboardView.initialize();
	}	
	
	/* From here for full-screen mode */
	@Override
    public void onUpdateExtractingVisibility(EditorInfo ei) {
        setExtractViewShown(true);
    }
	
	@Override
    public boolean onEvaluateFullscreenMode() {
        return false;
    }
	
	@Override
    public boolean isFullscreenMode() {
        return true;
    }
	
	@Override
    public void setExtractViewShown(boolean shown) {
        super.setExtractViewShown(true);
    }

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mKeyboardView.recycleBitmap();
	}

	/* Custom gesture listener */
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Gesture listener
	/// @remark
	/// - Description Get gesture input from MKeyboardView
	/// @see com.fouram.nurumikeyboard.NurumiIME.OnMKeyboardGestureListener#onFinishGesture(java.lang.String)
	/////////////////////////////////////////////
	@Override
	public void onFinishGesture(int[] motion) {
		for(int i = 0; i<numFingers; i++)
			this.motion[i] = motion[i]; // get gesture input

		String tmp;
		InputConnection ic = getCurrentInputConnection();
		/*
		if(motion[0] == 0)
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0));
		if(motion[4] == 0)
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
		else
			ic.commitText(String.valueOf('a'),1);
		*/
		ic.commitText(String.valueOf(Automata_type_3.execute(motion,ic)),1);// yoon // 150412 // automata 3 
	}
}
