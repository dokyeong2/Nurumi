package com.fouram.nurumikeyboard.NurumiIME;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.KeyEvent;
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
public class NurumiIME extends InputMethodService {
	
	private View entireView;
	private ViewGroup vg;
	private MKeyboardView mKeyboardView;
	
	@Override
	public void onFinishInputView(boolean finishingInput) {
		// TODO Auto-generated method stub
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
		return entireView;
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
}
