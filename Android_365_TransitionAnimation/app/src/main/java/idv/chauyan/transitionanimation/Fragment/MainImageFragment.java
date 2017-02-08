package idv.chauyan.transitionanimation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import idv.chauyan.transitionanimation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainImageFragment extends Fragment {

    public final static String AnimationTag = "Animation";

    private final static int[] ironMans = {
            R.drawable.iron_man_1,
            R.drawable.iron_man_2,
    };

    private static int touchTimes = 0;


    public MainImageFragment() {
        // Required empty public constructor
    }

    public static MainImageFragment newInstance(boolean animate) {

        Bundle mainBundle = new Bundle();
        mainBundle.putBoolean(AnimationTag, animate);

        final MainImageFragment imageFragment = new MainImageFragment();
        imageFragment.setArguments(mainBundle);

        touchTimes++;

        return imageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final boolean animateNeed = getArguments().getBoolean(AnimationTag);
        final ImageView ironManImage = (ImageView) view.findViewById(R.id.ironManImage);

        ironManImage.setImageResource(ironMans[touchTimes %
                ironMans.length]);
        if (animateNeed) {
            ironManImage.setScaleX(1.1f);
            ironManImage.setScaleY(1.1f);
            ironManImage.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000).start();
        }
    }
}
