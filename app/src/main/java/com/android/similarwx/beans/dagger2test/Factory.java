package com.android.similarwx.beans.dagger2test;

import javax.inject.Inject;

/**
 * Created by hanhuailong on 2018/3/21.
 */

public class Factory {
    Product product;
    @Inject
    Factory(Product product){
        this.product=product;
    }
}
