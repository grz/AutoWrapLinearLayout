package com.example.AutoWrapLinearLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * User: rizenguo
 * Date: 14-3-12
 * Time: 下午5:02
 */
public class AutoWrapLinearLayout extends LinearLayout {
	private int mWidth;//AutoWrapLinearLayout控件的宽
	private int mHeight;//AutoWrapLinearLayout控件的高
	static final String TAG = "AutoWrapLinearLayout";

	public AutoWrapLinearLayout(Context context){
		super(context);
	}

	public AutoWrapLinearLayout(Context context,AttributeSet attrs){
		super(context,attrs);
	}

	@Override
	protected void onLayout(boolean changed,int l,int t,int r,int b){

		mWidth = r-l;//宽度是跟随父容器而定的

		//自身控件的padding
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();

		//控件自身可以被用来显示自控件的宽度
		int mDisplayWidth = mWidth - paddingLeft - paddingRight;

		//自控件的margin
		int cellMarginLeft = 0;
		int cellMarginRight = 15;
		int cellMarginTop = 0;
		int cellMarginBottom = 0;

		int x = 0;//子控件的默认左上角坐标x
		int y = 0;//子控件的默认左上角坐标y

		int totalWidth = 0;//计算每一行随着自控件的增加而变化的宽度
		int count = getChildCount();

		int cellWidth = 0;//子控件的宽，包含padding
		int cellHeight = 0;//自控件的高，包含padding

		int left = 0;//子控件左上角的横坐标
		int top = 0;//子控件左上角的纵坐标

		LayoutParams lp;

		for(int j=0;j<count;j++){
			final View childView = getChildAt(j);
			//获取子控件child的宽高
			cellWidth = childView.getMeasuredWidth();//测量自控件内容的宽度(这个时候padding有被计算进内)
			cellHeight = childView.getMeasuredHeight();//测量自控件内容的高度(这个时候padding有被计算进内)

			lp = (LayoutParams) childView.getLayoutParams();
			cellMarginLeft = lp.leftMargin;
			cellMarginRight = lp.rightMargin;
			cellMarginTop = lp.topMargin;
			cellMarginBottom = lp.bottomMargin;

			//动态计算子控件在一行里面占据的宽度
			//预判，先加上下一个要展示的子控件，计算这一行够不够放
			totalWidth += cellMarginLeft + cellWidth + cellMarginRight;

			if(totalWidth >= mDisplayWidth){//不够放的时候需要换行
				y += cellMarginTop + cellHeight + cellMarginBottom;//新增一行
				totalWidth = cellMarginLeft + cellWidth + cellMarginRight;//这时候这一行的宽度为这个子控件的宽度
				x = 0;//重置
			}

			//计算顶点横坐标
			left = x + cellMarginLeft + paddingLeft;

			//计算顶点纵坐标
			top = y + cellMarginTop + paddingTop;

			//绘制自控件
			childView.layout(left, top, left + cellWidth, top + cellHeight);

			//计算下一个横坐标
			x += cellMarginLeft + cellWidth + cellMarginRight;

		}
		mHeight = paddingTop + y + cellMarginTop + cellHeight + cellMarginBottom + paddingBottom;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

		int cellWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);//让自控件自己按需撑开

		int cellHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);//让自控件自己按需撑开

		int count = getChildCount();
		for(int i = 0;i<count; i++){
			View childView = getChildAt(i);
			try {
				childView.measure(cellWidthSpec, cellHeightSpec);
			}catch(NullPointerException e){
				//这个异常不影响展示
			}
		}

		setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec), resolveSize(mHeight, heightMeasureSpec));

	}

}