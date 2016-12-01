package edu.ptu.demo.test.rxjava;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by anshu.wang on 2016/11/28.
 */

public class RxjavaTest {
    public static void main(String[] args) {

        String[] names = {"Tom", "Lily", "Alisa", "Sheldon", "Bill"};
        Observable
                .from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        System.out.println("===》" + name);
                    }
                });
    }
}
