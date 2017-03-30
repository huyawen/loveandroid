package com.meiaomei.shortplay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private ImageView iv_top;
    private ImageView iv_bottom;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化 ImageView 对象
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        iv_top = (ImageView) findViewById(R.id.iv_top);
        // 加载 2张图片，图片是实现放在 drawable 资源文件中的
        Bitmap topBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.circle_close, null);
        Bitmap bottomBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.gouxuanok, null);
        /**
         * 创建一个包含透明度的 bitmap对象
         * 该代码创建的仅仅是一个空白的画纸，只不过使用的是 topBitmap的尺寸而已
         */
        bitmap = Bitmap.createBitmap(topBitmap.getWidth(), topBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 创建一个画布对象
        Canvas canvas = new Canvas(bitmap);
        // 将顶层图片绘制到 bitmap对象中
        canvas.drawBitmap(topBitmap, 0, 0, null);
        // 给 ImageView控件设置图片
        iv_bottom.setImageBitmap(bottomBitmap);
        // 给顶层 ImageView设置新绘制的位图
        iv_top.setImageBitmap(bitmap);
        // 给顶层图片控件设置监听事件
        iv_top.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
    /**
     * 我们只需要考虑用户的滑动事件，
     * 当用户滑动的时候将滑动的点周围的像素全变为透明的，
     * 这样底层的美女图片就显示出来了
     */
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
        // 获取当前屏幕坐标
            int x = (int) event.getX();
            int y = (int) event.getY();
        // 将当前坐标周围的一个矩形区域设置为透明
            for (int i = x - 10; i < x + 10; i++) {
                for (int j = y - 10; j < y + 10; j++) {
        //判断要设置为透明的区域是否已经跑出图片的区域了
                    if (i >= 0 && i < bitmap.getWidth() && j >= 0 && j < bitmap.getHeight()) {
        //设置图片的某个像素为透明
                        bitmap.setPixel(i, j, Color.TRANSPARENT);
                    }
                }
            }
    // 重新给 ivTop控件设置 bitmap 对象
            iv_top.setImageBitmap(bitmap);
        }
    // 必须返回 true，否则事件不成功
        return true;
    }
}


