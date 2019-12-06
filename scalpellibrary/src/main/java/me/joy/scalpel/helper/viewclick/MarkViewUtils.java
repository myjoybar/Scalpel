package me.joy.scalpel.helper.viewclick;

import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joybar on 2018/12/24.
 */

public class MarkViewUtils {

	static Map<String, String> viewClassHashMap = new HashMap();
	static final String UNDER_LINE = "_";
	static final int NO_ID = 0xffffffff;



	public static boolean isTrackView(View view) {

		String className = view.getContext().getClass().getName();
		//Log.i(MainActivity.TAG, "className="+className);
		String idName = getIdName(view);
		String tagName = className + UNDER_LINE + idName;
		//Log.i(MainActivity.TAG, "tagName="+tagName);
		if (TrackConfigManager.getViewTagHashMap().containsKey(tagName)) {
			view.setTag(tagName);
			return true;
		}
		return false;
	}

	public static boolean isTrackMethod(String methodTagName) {
		if (TrackConfigManager.getMethodHashMap().containsKey(methodTagName)) {
			return true;
		}
		return false;

	}


	public static void traversalAndMarkView(Class<?> classZ, ViewGroup rootView) {
		String className = classZ.getName();
		if (viewClassHashMap.containsKey(className)) {
			return;
		}
		viewClassHashMap.put(className, className);
		for (int i = 0; i < rootView.getChildCount(); i++) {
			View childVg = rootView.getChildAt(i);
			if (childVg.getId() == NO_ID) {
				continue;
			}
			markView(classZ, childVg);
			if (childVg instanceof ViewGroup) {
				traversalAndMarkView(classZ, (ViewGroup) childVg);
			}
		}
	}

	private static void markView(Class<?> classZ, View view) {
		String tagName = classZ.getName() + UNDER_LINE + getIdName(view);
		view.setTag(tagName);

	}


	private static String getIdName(View view) {

		if (view.getId() == NO_ID) {
			return "no-id";
		} else {
			return view.getResources().getResourceEntryName(view.getId());
			// return view.getResources().getResourceName(view.getId());
		}
	}
}
