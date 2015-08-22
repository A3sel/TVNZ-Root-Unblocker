package com.liamcottle.xposed.tvnz.rootunblock;

import android.app.Application;
import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("nz.co.tvnz.ondemand.phone.android")) {
            return;
        }

        XposedBridge.log("Loaded TVNZ OnDemand App");

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("Hooked TVNZ OnDemand Application Attach");

                Class onDemandAppClass = XposedHelpers.findClass("nz.co.tvnz.ondemand.OnDemandApp", lpparam.classLoader);
                XposedHelpers.findAndHookMethod(onDemandAppClass, "A", new XC_MethodHook() {

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        XposedBridge.log("Overriding Root Detection");
                        param.setResult(false);

                    }

                });

            }
        });

    }

}
