/**
 * Copyright 2013 Joan Zapata
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.infrastructure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static cn.infrastructure.adapter.BaseAdapterHelper.get;

/**
 * Abstraction class of a BaseAdapter in which you only need to provide the
 * convert() implementation.<br/>
 * Using the provided BaseAdapterHelper, your code is minimalist.
 * 
 * @param <T>
 *            The type of the items in the list.
 */
public abstract class QuickAdapter<T> extends
		BaseQuickAdapter<T, BaseAdapterHelper> {

	/**
	 * Create a QuickAdapter.
	 * 
	 * @param context
	 *            The context.
	 * @param layoutResId
	 *            The layout resource id of each item.
	 */
	public QuickAdapter(Context context, int layoutResId) {
		super(context, layoutResId);
	}

	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with some
	 * initialization data.
	 * 
	 * @param context
	 *            The context.
	 * @param layoutResId
	 *            The layout resource id of each item.
	 * @param data
	 *            A new list is created out of this one to avoid mutable list
	 */
	public QuickAdapter(Context context, int layoutResId, List<T> data) {
		super(context, layoutResId, data);
	}

	public QuickAdapter(Context context, ArrayList<T> data,
			MultiItemTypeSupport<T> multiItemSupport) {
		super(context, data, multiItemSupport);
	}

	protected BaseAdapterHelper getAdapterHelper(int position,
												 View convertView, ViewGroup parent) {

		if (mMultiItemSupport != null) {
			return get(
					context,
					convertView,
					parent,
					mMultiItemSupport.getLayoutId(position, data.get(position)),
					position);
		} else {
			return get(context, convertView, parent, layoutResId, position);
		}
	}

	/**
	 * 把新增集合添加到data集合前面
	 * 
	 * @param items
	 *            新增项集合
	 */
	public void addToFirst(List<T> items) {
		for (int i = items.size() - 1; i >= 0; i--) {
			data.add(0, items.get(i));
		}
		notifyDataSetChanged();
	}

	/**
	 * 把新增集合添加到data集合后面
	 * 
	 * @param items
	 *            新增项集合
	 */
	public void addToLast(List<T> items) {
		for (T item : items) {
			data.add(item);
		}
		notifyDataSetChanged();
	}

}
