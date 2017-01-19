package cn.infrastructure.widget.listview.swipe;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import cn.infrastructure.lib.R;

/**
 * the adapter for swipe menu list view
 * 
 * Created by Frank on 2016/7/13.
 * 
 */
/* package */abstract class SwipeMenuAdapter extends BaseAdapter implements
		IOnSwipeItemClickListener {

	private ListAdapter mAdapter;
	private Context mContext;
	private IOnSwipeItemClickListener mOnSwipeMenuItemClickListener;

	public SwipeMenuAdapter(Context context, ListAdapter adapter) {
		mAdapter = adapter;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SwipeMenuLayout layout = null;
		if (convertView == null) {
			View contentView = mAdapter.getView(position, convertView, parent);

			SwipeMenu menu = createSwipeMenu(
					mAdapter.getItemViewType(position), position);

			SwipeMenuView menuView = null;
			if (menu != null) {
				// if menu is null, it means the item does not need swipe menu
				menuView = createSwipeMenuView(mContext, menu, position);
			}

			SwipeMenuListView listView = (SwipeMenuListView) parent;
			layout = new SwipeMenuLayout(contentView, menuView,
					listView.getCloseInterpolator(),
					listView.getOpenInterpolator(), this);
			layout.setPosition(position);
		} else {
			layout = (SwipeMenuLayout) convertView;
			SwipeMenu menu = createSwipeMenu(
					mAdapter.getItemViewType(position), position);

			SwipeMenuView menuView = null;
			if (menu != null) {
				// if menu is null, it means the item does not need swipe menu
				menuView = createSwipeMenuView(mContext, menu, position);
			}
			layout.setMenuView(menuView);
			View view = layout.getContentView();
			mAdapter.getView(position, view, parent);

			layout.closeMenu();
			layout.setPosition(position);
		}
		layout.setBackgroundResource(R.drawable.selector_common_bg_white_half);
		return layout;
	}

	protected abstract SwipeMenu createSwipeMenu(int itemViewType, int position);

	protected abstract SwipeMenuView createSwipeMenuView(Context context,
			SwipeMenu menu, int position);

	@Override
	public void onMenuItemClick(int position, int index) {
		if (mOnSwipeMenuItemClickListener != null) {
			mOnSwipeMenuItemClickListener.onMenuItemClick(position, index);
		}
	}

	public void setOnMenuItemClickListener(
			IOnSwipeItemClickListener onSwipeMenuItemClickListener) {
		this.mOnSwipeMenuItemClickListener = onSwipeMenuItemClickListener;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return mAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		return mAdapter.isEnabled(position);
	}

	@Override
	public boolean hasStableIds() {
		return mAdapter.hasStableIds();
	}

	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return mAdapter.getViewTypeCount();
	}

	@Override
	public boolean isEmpty() {
		return mAdapter.isEmpty();
	}

}
