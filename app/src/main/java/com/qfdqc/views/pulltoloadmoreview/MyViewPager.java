package com.qfdqc.views.pulltoloadmoreview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页顶部滚动图片
 * 
 * @author Administrator
 * 
 */
public class MyViewPager extends CtZoomViewPager {
	// 图片的连接地址
	private List<Integer> imgUrls;
	// 点击图片的连接地址
	private List<String> actUrls;
	// ViewPager的适配器
	private MyPagerAdapter myPagerAdapter;
	// 图片加载工具
	// private BitmapUtils bitmapUtils;
	// 第一次设定Item的位置
	private boolean isFirst = true;
	// 点的集合
	private List<View> points;
	// 按下ViewPager时的坐标
	private float downX;

	private onSelectedPosLinstener mSelectedPosLinstener;

	private int res = -1;

	public void setRes(int res) {
		this.res = res;
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyViewPager(Context context) {
		this(context, null);
	}

	private int position = 0;
	private float downY;

	private void init() {
		runnableTask = new RunnableTask();
		// bitmapUtils = new BitmapUtils(getContext());

		addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				MyViewPager.this.position = position % imgUrls.size();
				if (imgUrls != null) {
					int mPosition = position % imgUrls.size();
					// 设置选中的点
					if (points != null && points.size() > 1) {
						for (int j = 0; j < points.size(); j++) {
							if (j == mPosition) {
								points.get(j).setBackgroundResource(R.drawable.point_normal_withe);
							} else {
								points.get(j).setBackgroundResource(R.drawable.point_normal_gray);
							}
						}
					}
				}
				if (mSelectedPosLinstener != null) {
					mSelectedPosLinstener.onSelectedPos(
							position % imgUrls.size() + 1, imgUrls.size());
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private class MyPagerAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(getContext());
			iv.setScaleType(ScaleType.FIT_XY);
			// 网络加载图片
			if (imgUrls != null) {
				int mPosition = position % imgUrls.size();
				// BitmapManager.displayImageView(iv, imgUrls.get(mPosition));
				if (res == -1) {

					iv.setBackgroundResource(imgUrls.get(mPosition));

					// iv.setImageResource(R.drawable.c_ef);
					//BitmapManager.displayImageView(iv, imgUrls.get(mPosition),R.drawable.c_ef);
					// BmUtils.display(iv, imgUrls.get(mPosition),
					// R.drawable.c_ef);
				} else {
					iv.setBackgroundResource(imgUrls.get(mPosition));
					//BitmapManager.displayImageView(iv, imgUrls.get(mPosition),res);
					// BmUtils.display(iv, imgUrls.get(mPosition), res);
				}

				// bitmapUtils.display(iv, imgUrls.get(mPosition));
			}
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			if (imgUrls == null || imgUrls.size() <= 1) {
				return 1;
			}
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	/**
	 * 开始轮播
	 */
	public void startRoll() {
		if (imgUrls != null && imgUrls.size() > 0) {
			if (myPagerAdapter == null) {
				myPagerAdapter = new MyPagerAdapter();
				setAdapter(myPagerAdapter);
			} else {
				myPagerAdapter.notifyDataSetChanged();
			}
			if (isFirst) {
				setCurrentItem(1000 - (1000 % imgUrls.size())
						+ MyViewPager.this.getCurrentItem() % imgUrls.size());
				isFirst = false;
			}
			if (imgUrls.size() > 1) {

				roll();
			} else {
				try {
					points.get(0).setVisibility(View.GONE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 滚动
	 */
	private void roll() {
		if (imgUrls.size() <= 1) {
			return;
		}
		clearRoll();
		handler.sendEmptyMessage(0);
	}

	/**
	 * 清空轮播
	 */
	public void clearRoll() {
		handler.removeCallbacks(runnableTask);
		handler.removeCallbacksAndMessages(null);
	}

	private RunnableTask runnableTask;

	private class RunnableTask implements Runnable {
		@Override
		public void run() {
			// 维护一个计算规则，0,1,2,3,0,1,2,3
			MyViewPager.this
					.setCurrentItem(MyViewPager.this.getCurrentItem() + 1);
			// 发送一个消息，给handlerMessage方法处理
			handler.obtainMessage().sendToTarget();
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 改变当前的页面选项
			handler.postDelayed(runnableTask, 5000);
		};
	};

	/**
	 * 向子控件传递事件
	 */
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 请求父控件不截拦事件
		getParent().requestDisallowInterceptTouchEvent(true);
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		return false;
	};

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		clearRoll();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = ev.getX();
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float mY = ev.getY();
			if (Math.abs(mY - downY) > 100) {
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			roll();
			break;
		case MotionEvent.ACTION_UP:
			roll();
			float upX = ev.getX();
			if (Math.abs(upX - downX) < 10) {
				// TSUtil.show("点击");
				// 点击代码
				// if (actUrls != null && actUrls.size() > 0) {
				// String url = actUrls.get(position);
				// if (!StringUtils.isEmpty(url)) {
				// Intent intent = new Intent(getContext(),
				// WebActivity.class);
				// intent.putExtra("url", url);
				// getContext().startActivity(intent);
				// }
				// }
				if (mLinstener != null) {
					mLinstener.onImageClicked(position);
				}
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 移除所有的消息
	 */
	protected void onDetachedFromWindow() {
		clearRoll();
		super.onDetachedFromWindow();
	};

	public void setImgList(List<Integer> imgList) {
		this.imgUrls = new ArrayList<Integer>(imgList);
	}

	public void setPoints(List<View> points) {
		this.points = new ArrayList<View>(points);
	}

	public void setActUrls(List<String> actUrls) {
		this.actUrls = new ArrayList<String>(actUrls);
	}

	public void setonSelectedPosLinstener(onSelectedPosLinstener linstener) {
		this.mSelectedPosLinstener = linstener;
	}

	public interface onSelectedPosLinstener {
		void onSelectedPos(int pos, int size);
	}




	public List<Integer> getImgUrls() {
		return imgUrls;
	}

	public interface OnImagedClickLinstener {
		void onImageClicked(int position);
	}

	private OnImagedClickLinstener mLinstener;

	public void setOnImagedClickLinstener(OnImagedClickLinstener linstener) {
		mLinstener = linstener;
	}

}
