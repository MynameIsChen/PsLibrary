package ps.cxl.com.pslibrary.test.mvp.presenter;

import android.graphics.Bitmap;

import java.io.File;

import ps.cxl.com.common.util.Lg;
import ps.cxl.com.pslibrary.test.mvp.bean.UserBean;
import ps.cxl.com.pslibrary.test.mvp.model.IUserModel;
import ps.cxl.com.pslibrary.test.mvp.model.UserModel;
import ps.cxl.com.pslibrary.test.mvp.view.IUserView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created : chenxianglin on 17/4/21.
 */

public class UserPresenter {
    private static final String TAG = "observer";
    private IUserView mIUserView;
    private IUserModel mIUserModel;

    public UserPresenter(IUserView IUserView) {
        mIUserView = IUserView;
        mIUserModel = new UserModel();
    }

    public void saveUser(long id, String firstName, String lastName) {
        mIUserModel.setId(id);
        mIUserModel.setFirstName(firstName);
        mIUserModel.setLastName(lastName);
    }

    public void loadUser(long id) {
        UserBean user = mIUserModel.load(id);
        mIUserView.setFirstName(user.getFirstName());
        mIUserView.setLastName(user.getLastName());
    }

    private void onObsever() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Lg.d(TAG, "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Lg.d(TAG, "Error");
            }

            @Override
            public void onNext(String s) {
                Lg.d(TAG, "info：" + s);
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                Lg.d(TAG, "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Lg.d(TAG, "Error");
            }

            @Override
            public void onNext(String s) {
                Lg.d(TAG, "info：" + s);
            }
        };

        Observable a = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        Observable b = Observable.just("Hello", "Hi", "Aloha");

        String[] words = {"Hello", "Hi", "Aloha"};
        Observable c = Observable.from(words);

        //The thing is start when subscribe it.
        a.subscribe(observer);//a,b,c
        a.subscribe(subscriber);//a,b,c

        //maybe unSubscribe
        if (subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    private void doOther(Observable observable) {
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Lg.d(TAG, "next：" + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //error
            }
        };
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Lg.d(TAG, "Completed");
            }
        };
        observable.subscribe(onNextAction);
        observable.subscribe(onNextAction, onErrorAction);
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    private void doFile() {
        File[] folders = null;
        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File o) {
                        return o.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File o) {
                        //getBitmap
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //setBitmap
                    }
                });
    }
}
