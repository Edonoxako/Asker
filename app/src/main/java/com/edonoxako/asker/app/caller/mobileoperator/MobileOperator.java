package com.edonoxako.asker.app.caller.mobileoperator;

import java.util.Map;

/**
 * Created by EugeneM on 02.03.2015.
 */
public interface MobileOperator {
    public void init(MobileOperatorCallback callback);
    public String[] getSpecSymbols();
    public void setOperator(String operatorName);
    public void destroy();
}
