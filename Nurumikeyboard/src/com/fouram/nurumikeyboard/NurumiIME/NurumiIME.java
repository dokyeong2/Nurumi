package com.fouram.nurumikeyboard.NurumiIME;

import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.view.KeyEvent;
//import android.view.inputmethod.InputConnection;
//import android.widget.EditText;
//import android.util.Log;

/////////////////////////////////////////////
/// @class NurumiIME
///com.fouram.nurumikeyboard.NurumiIME \n
///   ¤¤ NurumiIME.java
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
///   ¤¤ NurumiIME.java
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
public class NurumiIME extends InputMethodService implements OnKeyboardActionListener {
	
	@Override
	public void onFinishInputView(boolean finishingInput) {
		// TODO Auto-generated method stub
		super.onFinishInputView(finishingInput);
	}

	private View mKeyboardView;
		
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
		mKeyboardView = (View)getLayoutInflater().inflate(layoutId, null);
		return mKeyboardView;
	}
	
	@Override
	public boolean onShowInputRequested (int flags, boolean configChange)
	{
		//Log.d("startView", "View started!");
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
		int layoutId = R.layout.mkeyboardlayout;
		mKeyboardView = (View)getLayoutInflater().inflate(layoutId, null);
		setInputView(mKeyboardView);
	}
	
	/* From here for full-screen mode */
	@Override
    public void onUpdateExtractingVisibility(EditorInfo ei) {
        // TODO Auto-generated method stub
        setExtractViewShown(true);
    }
	
	@Override
    public boolean onEvaluateFullscreenMode() {
        // TODO Auto-generated method stub
        return false;
    }
	
	@Override
    public boolean isFullscreenMode() {
        // TODO Auto-generated method stub
        return true;
    }
	
	@Override
    public void setExtractViewShown(boolean shown) {
        // TODO Auto-generated method stub
        super.setExtractViewShown(true);
    }

	
	/* key listeners */
	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub
		
	}
}
