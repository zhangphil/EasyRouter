package io.github.xiewinson.easyrouter.library.inner;

import android.app.Fragment;

import io.github.xiewinson.easyrouter.library.FragmentRequest;

/**
 * Created by winson on 2017/12/5.
 */

public final class FragmentRequestBuilder extends FragmentRequest.Builder<Fragment, FragmentRequestBuilder> {
    public FragmentRequestBuilder(Class<Fragment> cls) {
        super(cls);
    }
}