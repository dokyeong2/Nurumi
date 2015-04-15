package com.fouram.nurumikeyboard.NurumiIME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.widget.Toast;
import android.util.Log;
import android.util.AttributeSet;
import android.view.View;

/////////////////////////////////////////////
/// @class MKeyboardView
///com.fouram.nurumikeyboard.NurumiIME \n
///   い MKeyboardView.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description
///	- This file is for the view of motion keyboard.\n
///	- This view will popup when user\n put cursor in textbox.\n
/////////////////////////////////////////////
//
///////////////////////////////////////////
/// @class MKeyboardView
///com.fouram.nurumikeyboard.NurumiIME \n
///   い MKeyboardView.java
/// @section Class information
///    |    Item    |    Contents    |
///    | :-------------: | -------------   |
///    | Company | 4:00 A.M. |    
///    | Author | Park, Hyung Soon |
///    | Date | 2015. 3. 26. |
/// @section Description
///	- View of motion keyboard.\n
/////////////////////////////////////////////
public class MKeyboardView extends View {
	private Context ctx; 
	private NurumiIME ime;
	
	private static final int INVALID_CIRCLE		= -1;	
	private static final int INVALID_DIRECTION 	= -1;
	private static final int DIRECTION_DOT		= 0;
	private static final int DIRECTION_DOWN 	= 1;
	private static final int DIRECTION_LEFT 	= 2;
	private static final int DIRECTION_UP  		= 3;
	private static final int DIRECTION_RIGHT	= 4;
	private static final int SWIPE_MIN_DISTANCE = 140;
	private static final int FIVE_FINGERS 		= 5;
	private static final int TEN_FINGERS 		= 10;
	private static final int OUTER_CIRCLE_SIZE	= 140;
	private static final int INNER_CIRCLE_SIZE	= 100;
	/////////////////////////////////////////////
	/// @class CircleLinkedWithPtId
	///com.fouram.nurumikeyboard.NurumiIME \n
	///   い MKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description
	///	- This class will bind pointerID with circleNum.\n
	/////////////////////////////////////////////
	public class CircleLinkedWithPtId
	{
		int pointerId;
		int circleNum;		
	}
	/////////////////////////////////////////////
	/// @class PtIdLinkedWithPtIndex
	///com.fouram.nurumikeyboard.NurumiIME \n
	///   い MKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description
	///  - This class will bind pointerID with pointerIndex.\n
	/// 
	/////////////////////////////////////////////
	public class PtIdLinkedWithPtIndex
	{
		int pointerId;
		int pointerIndex;	
	}
	
	final Comparator<PointF> comparatorX = 
	/////////////////////////////////////////////
	/// @class 1
	///com.fouram.nurumikeyboard.NurumiIME \n
	///   い MKeyboardView.java
	/// @section Class information
	///    |    Item    |    Contents    |
	///    | :-------------: | -------------   |
	///    | Company | 4:00 A.M. |    
	///    | Author | Park, Hyung Soon |
	///    | Date | 2015. 3. 26. |
	/// @section Description
	///  - Comparator function for sort Circle number.\n
	/////////////////////////////////////////////
	new Comparator<PointF> () {
		public int compare(PointF pt1, PointF pt2)
		{
			return (int) (pt1.x - pt2.x);
		}
		/*public int compareLHand(PointF pt1, PointF pt2)
		{
			return (int) (pt2.x - pt1.x);
		}*/
	};
	
	// variables in MKeyboardView
	private Paint pnt;
	
	private int numFingers;
	private int innerCircleSize;
	private int outerCircleSize;
	private int[] motion;
	private boolean[] circleAvailable;	
	
	
	private LinkedList<PtIdLinkedWithPtIndex> plp;
	private ArrayList<CircleLinkedWithPtId> clp;
	private ArrayList<PointF> startPtArr;
	private ArrayList<PointF> oldPtArr;
	private ArrayList<PointF> ptArr;
	
	private boolean motionStartFlag;
	private boolean start;
	private boolean startFlag;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief Constructor of Motion keyboard view 
	/// @remark
	/// - Description : Initialize all variables and lists for gesture recognition.
	/// @param context : View context
	/// @param attrs : View attribute set
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public MKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		initialize();		
	}
	
	/////////////////////////////////////////////
	/// @fn initialize
	/// @brief Initialize function
	/// @remark
	/// - Description : Initialize all variables and lists.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void initialize()
	{
		pnt = new Paint();
		numFingers = FIVE_FINGERS;
		outerCircleSize = OUTER_CIRCLE_SIZE;
		innerCircleSize = INNER_CIRCLE_SIZE;
		
		startFlag = false;
		start = false;
		motionStartFlag = false;
		motion = new int[numFingers];
		circleAvailable = new boolean[numFingers];
		for(int i=0; i<numFingers; i++)
			circleAvailable[i] = true;
		
		plp = new LinkedList<PtIdLinkedWithPtIndex>();
		clp = new ArrayList<CircleLinkedWithPtId>();
		
		startPtArr = new ArrayList<PointF>();
		oldPtArr = new ArrayList<PointF>();
		ptArr = new ArrayList<PointF>();
		
		plp.clear();
		clp.clear();
		
		startPtArr.clear();
		oldPtArr.clear();
		ptArr.clear();
	}

	public MKeyboardView(Context context) {
		super(context);
		this.ctx = context;
	}

	/////////////////////////////////////////////
	/// @fn setIme
	/// @brief Set parent IME
	/// @remark
	/// - Description : Set parent ime and link with IME variable MKeyboard View.\n
	/// @param ime Parent IME
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void setIme(NurumiIME ime) {
		this.ime = ime;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) Screen drawing function.
	/// @remark
	/// - Description
	///	Draw 5 or 10 start point circles and touched point circles.\n
	/// @see android.view.View#onDraw(android.graphics.Canvas)
	/////////////////////////////////////////////
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	    canvas.drawColor(Color.TRANSPARENT);
	    
	    pnt.setTextSize(64.0f);
	    
	    /* standard position */
		if(startPtArr.isEmpty())
			return;
		int index=0;
		for(PointF spt : startPtArr)
		{
			index++;
			pnt.setColor(Color.BLACK);
			pnt.setStyle(Paint.Style.STROKE);
			pnt.setStrokeWidth(1);
			canvas.drawCircle(spt.x,spt.y, outerCircleSize, pnt);

			pnt.setStyle(Paint.Style.FILL);
			canvas.drawText(String.valueOf(index),spt.x,spt.y-((int)(outerCircleSize/0.8)),pnt);
		}
		
		/* down event position */
		if(!oldPtArr.isEmpty())
		{
			for (PointF pt : oldPtArr)
			{
				int circleNum = checkTouchedCircle((int)pt.x, (int)pt.y);
				pnt.setStyle(Paint.Style.STROKE);
				canvas.drawCircle(pt.x,pt.y, innerCircleSize, pnt);

				pnt.setStyle(Paint.Style.FILL);
				canvas.drawText(String.valueOf(circleNum),pt.x,pt.y-((int)(outerCircleSize/0.8)),pnt);
			}
		}
		
		/* current finger */
		index=0;
		if(!ptArr.isEmpty() && !plp.isEmpty() && !clp.isEmpty())
		{			
			for (PointF pt : ptArr)
			{
				int pointerId = plp.get(index++).pointerId;
				if(pointerId >= clp.size())
					break;					
				int circleNum = clp.get(pointerId).circleNum;
				
				pnt.setStyle(Paint.Style.STROKE);
				canvas.drawCircle(pt.x,pt.y, innerCircleSize, pnt);

				pnt.setStyle(Paint.Style.FILL);
				canvas.drawText(String.valueOf(circleNum),pt.x,pt.y-((int)(outerCircleSize/0.8)),pnt);
			}
		}		
	} // onDraw fin
	
	@Override
	public boolean performClick() {
	    return super.performClick();
	}
	
	//@Override
	/////////////////////////////////////////////
	/// @fn onTouchEvent
	/// @brief Touch event method
	/// @remark
	/// - Description : This method will classify motion events.\n
	///	Used MotionEvent.ACTION_MASK for recognize ACTION_POINTER events.\n
	/// ACTION_DOWN, ACTION_POINTER_DOWN, ACTION_UP, ACTION_POINTER_UP, ACTION_MOVE, ACTION_CANCEL, and other event(default) will be recognzied.
	/// @param e A motion event
	/// @return Returns boolean value wether the touch event is valid or not.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean onTouchEvent (MotionEvent e)
	{
		int action = e.getAction() & MotionEvent.ACTION_MASK;
		
		if(start == false)
			return startMultiTouch(e);
		else
		{				
			switch(action)
			{
				case MotionEvent.ACTION_DOWN :
				{
					if( checkTouchedCircle((int)e.getX(), (int)e.getY()) == INVALID_CIRCLE )
						return false;
					for(int i=0; i<numFingers; i++) // initialize motion array
						motion[i] = -1;
					motionStartFlag = true;
					Log.d("Motion Start", "------------------------------");
				}
				case MotionEvent.ACTION_POINTER_DOWN :
				{
					int touchCount = e.getPointerCount();
					int circleNum = checkTouchedCircle((int)e.getX(touchCount-1), (int)e.getY(touchCount-1));
					if(touchCount>numFingers || circleNum == -1 || !motionStartFlag || !circleAvailable[circleNum-1])
					{
						invalidate();
						return true;
					}						
					circleAvailable[circleNum-1] = false;
					motion[circleNum-1] = DIRECTION_DOT;
					
					PointF ptf = new PointF();
					ptf.x = e.getX(touchCount-1);
					ptf.y = e.getY(touchCount-1);
					oldPtArr.add(ptf);

					CircleLinkedWithPtId cp = new CircleLinkedWithPtId();
					cp.pointerId = e.getPointerId(touchCount-1);
					cp.circleNum = circleNum;
					clp.add(cp);
					
					invalidate();
					return true;
				}
				
				case MotionEvent.ACTION_MOVE :
				{
					ptArr.clear();
					plp.clear();
					int touchCount = e.getPointerCount();
					if(touchCount>numFingers)
						touchCount = numFingers;
					for (int i=0; i<touchCount; i++)
					{
						PointF ptf = new PointF();
						ptf.x = e.getX(i);
						ptf.y = e.getY(i);
						ptArr.add(ptf);
						
						PtIdLinkedWithPtIndex pp = new PtIdLinkedWithPtIndex();
						pp.pointerIndex = i;
						pp.pointerId = e.getPointerId(i);
						plp.add(pp);
						
						checkDirection(pp, ptf);
					}
					invalidate();
					return true;
				}
				case MotionEvent.ACTION_POINTER_UP :
				{
					motionStartFlag = false;
					
					int touchCount = e.getPointerCount();
					if(touchCount>numFingers)
						touchCount = numFingers;
					return true;
				}
				case MotionEvent.ACTION_UP :
				{
					if(!startFlag && start)
					{
						startFlag = true;
						return true;
					}
					int touchCount = e.getPointerCount();
					if(touchCount>numFingers)
						touchCount = numFingers;
					motionCheck();					
					
					/* initialization for next motion */
					oldPtArr.clear();
					ptArr.clear();
					clp.clear();
					plp.clear();
					for(int i=0; i<numFingers; i++)
						circleAvailable[i] = true;
					performClick();
					invalidate();					
					return true;
				}
				case MotionEvent.ACTION_CANCEL :
				{ // cancel all motions and  initialize
					oldPtArr.clear();
					ptArr.clear();
					clp.clear();
					plp.clear();
					for(int i=0; i<numFingers; i++)
						circleAvailable[i] = true;
					invalidate();
					return false;
				}
				default :
				{
					invalidate();
					return false;
				}		
			}
		}
	} // onTouchEvent fin
	
	/////////////////////////////////////////////
	/// @fn motionCheck
	/// @brief Motion checking method 
	/// @remark
	/// - Description : In ACTION_UP motion event, this method will be called.\n 
	/// Checks motion array and check motion of each pointer.\n
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void motionCheck()
	{
		int checkEmpty=5;
		for(int i=0; i<numFingers; i++)
			checkEmpty += motion[i];
		Log.d("Motion End", "------------------------------");
		if(checkEmpty == 0)
			return;
		/* key event will be here. */
		ime.onFinishGesture(motion);
	}
	
	/////////////////////////////////////////////
	/// @fn checkTouchedCircle
	/// @brief Find touched circle
	/// @remark
	/// - Description : This method will check which circle is touched
	/// @param x x grid of touched point
	/// @param y y grid of touched point
	/// @return Returns touched circle number. If any of circle is touched, return -1.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public int checkTouchedCircle(int x, int y)
	{		
		int index=0;
		for(PointF spt : startPtArr)
		{
			index++;
			if( (Math.abs((int)spt.x - x) < OUTER_CIRCLE_SIZE) && (Math.abs((int)spt.y - y) < OUTER_CIRCLE_SIZE) )
				return index;
		}
		return -1;
	} // checkTouchedCircle fin
	
	
	/////////////////////////////////////////////
	/// @fn startMultiTouch
	/// @brief Start multi touch recognition. 
	/// @remark
	/// - Description : If 'numFingers' of fingers are touched, set 'start' flag true and start multi touch motion recognition.\n
	/// Set 'numFingers' of starting points.
	/// @param e A motion event
	/// @return Returns the boolean value of motion event is valid or not.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public boolean startMultiTouch(MotionEvent e)
	{
		startPtArr.clear();
		if ( e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE )
		{
			int touchCount = e.getPointerCount();		
			if(touchCount == numFingers)
			{
				start = true;
				Log.d("start" , "start : " + start);
				for (int i=0; i<touchCount; i++)
				{
					PointF ptf = new PointF();
					ptf.x = e.getX(i);
					ptf.y = e.getY(i);
					startPtArr.add(ptf);
				}
				Collections.sort(startPtArr, comparatorX);
				/*
				PointF pt1, pt2;
				pt1 = startPtArr.get(0);
				pt2 = startPtArr.get(4);
				if(pt2.x-pt1.x < 800)
					Collections.sort(startPtArr, comparatorY);
				*/
				return true;
			}
			else {return true;}
		}
		else {return false;}
	} // startMultiTouch fin
	
	
	/////////////////////////////////////////////
	/// @fn checkDirection
	/// @brief Check the direction of movement of pointers
	/// @remark
	/// - Description : Calculate the moved distances of pointers and save them in 'motion' array. 
	/// @param pp : List of class which has Pointer ID and Pointer Index to link them.
	/// @param pt : Grid of currently moving pointer.
	///~~~~~~~~~~~~~{.java}
	/// // core code
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void checkDirection(PtIdLinkedWithPtIndex pp, PointF pt)
	{
		CircleLinkedWithPtId cp = new CircleLinkedWithPtId();
		for(int i=0; i<numFingers; i++)
		{
			if(clp.size() <= i)
				return;
			cp = clp.get(i);
			if(pp.pointerId == cp.pointerId)
				break;
		}
		
		PointF oldPt = new PointF();
		int circleNum = -1;
		for(int i=0; i<numFingers; i++)
		{
			if(oldPtArr.size() <= i)
				break;
			oldPt = oldPtArr.get(i);
			circleNum = checkTouchedCircle((int)oldPt.x, (int)oldPt.y);
			if(cp.circleNum == circleNum)
				break;
		}
		if(circleNum == -1) return;
		
		float distanceX = oldPt.x - pt.x;
		float distanceY = oldPt.y - pt.y;
		
		if( Math.abs(distanceX) < SWIPE_MIN_DISTANCE && Math.abs(distanceY) < SWIPE_MIN_DISTANCE )
			motion[circleNum-1] = DIRECTION_DOT;
		else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && Math.abs(distanceY) > SWIPE_MIN_DISTANCE )
			motion[circleNum-1] = INVALID_DIRECTION;
		else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && distanceX > 0)
			motion[circleNum-1] = DIRECTION_LEFT;
		else if( Math.abs(distanceX) > SWIPE_MIN_DISTANCE && distanceX < 0)
			motion[circleNum-1] = DIRECTION_RIGHT;
		else if( Math.abs(distanceY) > SWIPE_MIN_DISTANCE && distanceY < 0)
			motion[circleNum-1] = DIRECTION_DOWN;
		else if( Math.abs(distanceY) > SWIPE_MIN_DISTANCE && distanceY > 0)
			motion[circleNum-1] = DIRECTION_UP;
		else
			motion[circleNum-1] = INVALID_DIRECTION;
	} // checkDirection fin
}