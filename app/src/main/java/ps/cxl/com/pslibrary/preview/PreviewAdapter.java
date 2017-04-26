package ps.cxl.com.pslibrary.preview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ps.cxl.com.pslibrary.R;

/**
 * Created : chenxianglin on 17/4/26.
 */

public class PreviewAdapter extends PagerAdapter {
    private List<String> imgs = new ArrayList<>();
    private int colors[] = {R.color.red, R.color.azure, R.color.blue};

    public PreviewAdapter() {
    }

    public PreviewAdapter(List<String> list) {
        this.imgs.clear();
        this.imgs.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //data,view
        ImageView imageView = new ImageView(container.getContext());
        imageView.setBackgroundColor(container.getContext().getResources().getColor(colors[position % colors.length]));
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100, 100);
//        imageView.setLayoutParams(params);
        container.addView(imageView, 100, 100);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    int getPosition(int position) {
        int size = imgs.size();
        if (size == 0) {
            size = 1;
        }
        return position % size;
    }
}
