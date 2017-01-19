package cn.infrastructure.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolderUtils
 * 
 * reuse the view,when the convertView is not null,avoid reload the layout xml
 * 
 */

public class ViewHolderUtils {
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
