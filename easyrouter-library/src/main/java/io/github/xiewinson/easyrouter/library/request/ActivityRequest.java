package io.github.xiewinson.easyrouter.library.request;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import io.github.xiewinson.easyrouter.library.Interceptor;
import io.github.xiewinson.easyrouter.library.callback.NavigateListener;
import io.github.xiewinson.easyrouter.library.config.IntentConfig;

/**
 * Created by winson on 2017/11/29.
 */

public class ActivityRequest extends IntentRequest {

    protected ActivityRequest(@NonNull IntentConfig config) {
        super(config);
    }

    private Intent checkIntent(@NonNull Context context) {
        IntentConfig config = getConfig();
        Intent intent = asIntent(context);
        ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), PackageManager.MATCH_DEFAULT_ONLY);
        boolean result = activityInfo != null;
        NavigateListener listener = config.navigateListener;
        if (listener != null) {
            listener.onNavigate(intent, result);
        }
        List<Class<? extends Interceptor>> interceptors = config.interceptors;
        for (Class<? extends Interceptor> interceptor : interceptors) {
            try {
                Interceptor instance = interceptor.newInstance();
                if (instance != null) {
                    boolean intercept = instance.intercept(context, intent);
                    if (intercept) return null;
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result ? intent : null;
    }


    public void navigation(@NonNull Context context) {
        Intent intent = checkIntent(context);
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    public void navigation(@NonNull Activity activity, int requestCode) {
        Intent intent = checkIntent(activity);
        if (intent != null) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public void navigation(@NonNull Activity activity) {
        navigation(activity, -1);
    }

    public void navigation(@NonNull Activity activity, @NonNull Bundle options, int requestCode) {
        Intent intent = checkIntent(activity);
        if (intent != null) {
            ActivityCompat.startActivityForResult(activity, intent, requestCode, options);
        }
    }

    public void navigation(@NonNull Activity activity, @NonNull Bundle options) {
        Intent intent = checkIntent(activity);
        if (intent != null) {
            ActivityCompat.startActivity(activity, intent, options);
        }
    }

    public void navigation(@NonNull Fragment fragment, int requestCode) {
        Intent intent = checkIntent(fragment.getActivity());
        if (intent != null) {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public void navigation(@NonNull Fragment fragment) {
        navigation(fragment, -1);
    }

    public void navigation(@NonNull android.support.v4.app.Fragment fragment, int requestCode) {
        Intent intent = checkIntent(fragment.getActivity());
        if (intent != null) {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    public void navigation(@NonNull android.support.v4.app.Fragment fragment) {
        navigation(fragment, -1);
    }

    public static class Builder<B extends Builder> extends IntentRequest.Builder<B> {

        protected Builder(Class<?> cls) {
            super(cls);
        }

        public Builder() {
            super();

        }

        @Override
        public ActivityRequest build() {
            return new ActivityRequest(getConfig());
        }

    }
}
