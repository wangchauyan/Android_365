package idv.chauyan.transitionanimation;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import idv.chauyan.transitionanimation.customizedview.AnimationContainer;
import idv.chauyan.transitionanimation.fragment.MainImageFragment;
import idv.chauyan.transitionanimation.transition.Transition;
import idv.chauyan.transitionanimation.transition.TransitionProvider;


public class MainActivity extends AppCompatActivity {

    private AnimationContainer containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Circle Transition").withIdentifier(1).withIcon(R.mipmap.ic_delete_forever_black_24dp),
                        new PrimaryDrawerItem().withName("Rectangle Transition").withIdentifier(2).withIcon(R.mipmap.ic_face_black_24dp),
                        new PrimaryDrawerItem().withName("WaterWave Transition").withIdentifier(3).withIcon(R.mipmap.ic_favorite_black_24dp),
                        new PrimaryDrawerItem().withName("Grid FadeIn Transition").withIdentifier(3).withIcon(R.mipmap.ic_motorcycle_black_24dp),
                        new DividerDrawerItem().withTag("Section -")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                // Circle Transition
                                launchTransition(TransitionProvider.TransitionType.Type_Circle, 0, 0);
                                break;
                            case 1:
                                // Rectangle Transition
                                launchTransition(TransitionProvider.TransitionType.Type_Grid, 0, 0);
                                break;
                            case 2:
                                // WaterWave Transition
                                launchTransition(TransitionProvider.TransitionType.Type_WaterWave, 0, 0);
                                break;
                            case 3:
                                // Grid FadeIn Transition
                                launchTransition(TransitionProvider.TransitionType.Type_Grid_FadeIn, 0, 0);
                                break;
                            default:
                                launchTransition(TransitionProvider.TransitionType.Type_Circle, 0, 0);
                                break;
                        }
                        return false;
                    }
                })
                .build();


        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragmentContainer,
                MainImageFragment.newInstance(true)
        ).commit();

        final GestureDetectorCompat tapGesture = new GestureDetectorCompat(MainActivity.this,
                new AnimationContainerGestureListener());

        containerView = (AnimationContainer) findViewById(R.id.activity_main);
        containerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return tapGesture.onTouchEvent(motionEvent);
            }
        });


    }


    private class AnimationContainerGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {}

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            launchTransition(TransitionProvider.TransitionType.Type_Grid,
                    (int) motionEvent.getX(), (int) motionEvent.getY());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }


    private void launchTransition(TransitionProvider.TransitionType type, int startX, int startY) {
        final Transition transition = TransitionProvider.CreateTransition(type);
        transition.setValue(containerView, startX, startY);

        containerView.startAnimation(transition);

        // handle gesture tap here, and replace content
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragmentContainer,
                MainImageFragment.newInstance(true)
        ).commit();
    }
}

