package idv.chauyan.transitionanimation.transition;

/**
 * Created by ChauyanWang on 2/5/17.
 */

public class TransitionProvider {

    public enum TransitionType {
        Type_WaterWave,
        Type_Circle,
        Type_Grid,
        Type_Grid_FadeIn,
    }


    public static Transition CreateTransition(TransitionType type) {
        switch (type) {
            case Type_Circle:
                return new CircleTransition();
            case Type_Grid:
                return new GridTransition();
            case Type_WaterWave:
                return new WaterWaveTransition();
            case Type_Grid_FadeIn:
                return new GridFadeInTransition();
        }

        return null;
    }
}
