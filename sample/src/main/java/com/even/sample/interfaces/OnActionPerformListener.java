package com.even.sample.interfaces;

import com.even.mricheditor.ActionType;

/**
 * OnActionPerformListener
 *
 * @author even.wu
 * @date 17/8/17
 */

public interface OnActionPerformListener {
    void onActionPerform(ActionType type, Object... values);
}
