package com.even.mricheditor.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.even.mricheditor.ActionType;
import com.even.mricheditor.R;
import com.even.mricheditor.RichEditorAction;

/**
 * The Interface of Action Button
 * Created by even.wu on 22/8/17.
 */

public class ActionImageView extends AppCompatImageView {
    private ActionType mActionType;
    private RichEditorAction mRichEditorAction;
    private Context mContext;

    private boolean enabled = true;
    private boolean activated = true;

    private int enabledColor;
    private int disabledColor;
    private int activatedColor;
    private int deactivatedColor;

    public ActionImageView(Context context) {
        this(context, null);
    }

    public ActionImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ActionImageView);
        mActionType =
            ActionType.fromInteger(ta.getInteger(R.styleable.ActionImageView_actionType, 0));
        ta.recycle();
    }

    public ActionType getActionType() {
        return mActionType;
    }

    public void setActionType(ActionType mActionType) {
        this.mActionType = mActionType;
    }

    public RichEditorAction getRichEditorAction() {
        return mRichEditorAction;
    }

    public void setRichEditorAction(RichEditorAction mRichEditorAction) {
        this.mRichEditorAction = mRichEditorAction;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public void command() {
        //TODO RichEditorAction can not be null

        switch (mActionType) {
            case BOLD:
                mRichEditorAction.bold();
                break;
            case ITALIC:
                mRichEditorAction.italic();
                break;
            case UNDERLINE:
                mRichEditorAction.underline();
                break;
            case SUBSCRIPT:
                mRichEditorAction.subscript();
                break;
            case SUPERSCRIPT:
                mRichEditorAction.superscript();
                break;
            case STRIKETHROUGH:
                mRichEditorAction.strikethrough();
                break;
            case NORMAL:
                mRichEditorAction.formatPara();
                break;
            case H1:
                mRichEditorAction.formatH1();
                break;
            case H2:
                mRichEditorAction.formatH2();
                break;
            case H3:
                mRichEditorAction.formatH3();
                break;
            case H4:
                mRichEditorAction.formatH4();
                break;
            case H5:
                mRichEditorAction.formatH5();
                break;
            case H6:
                mRichEditorAction.formatH6();
                break;
            case JUSTIFY_LEFT:
                mRichEditorAction.justifyLeft();
                break;
            case JUSTIFY_CENTER:
                mRichEditorAction.justifyCenter();
                break;
            case JUSTIFY_RIGHT:
                mRichEditorAction.justifyRight();
                break;
            case JUSTIFY_FULL:
                mRichEditorAction.justifyFull();
                break;
            case ORDERED:
                mRichEditorAction.insertOrderedList();
                break;
            case UNORDERED:
                mRichEditorAction.insertUnorderedList();
                break;
            case INDENT:
                mRichEditorAction.indent();
                break;
            case OUTDENT:
                mRichEditorAction.outdent();
                break;
            case LINE:
                mRichEditorAction.insertHorizontalRule();
                break;
            case BLOCK_QUOTE:
                mRichEditorAction.formatBlockquote();
                break;
            case BLOCK_CODE:
                mRichEditorAction.formatBlockCode();
                break;
            case CODE_VIEW:
                mRichEditorAction.codeView();
                break;
        }
    }

    public void command(String value) {

        //case FAMILY:
        //    mEditorMenuFragment.updateFontFamilyStates(value);
        //    break;
        //case SIZE:
        //    mEditorMenuFragment.updateFontStates(ActionType.SIZE, Double.valueOf(value));
        //    break;
        //case FORE_COLOR:
        //case BACK_COLOR:
        //    mEditorMenuFragment.updateFontColorStates(type, value);
        //    break;
        //case LINE_HEIGHT:
        //    mEditorMenuFragment.updateFontStates(ActionType.LINE_HEIGHT, Double.valueOf(value));
        //    break;

        switch (mActionType) {
            case FAMILY:
                break;
            case SIZE:
                break;
            case LINE_HEIGHT:
                break;
            case FORE_COLOR:
                break;
            case BACK_COLOR:
                break;
            case IMAGE:
                break;
            case LINK:
                break;
            case TABLE:
                break;
        }
    }

    public void resetStatus() {
    }

    public int getEnabledColor() {
        return enabledColor;
    }

    public void setEnabledColor(int enabledColor) {
        this.enabledColor = enabledColor;
    }

    public int getDisabledColor() {
        return disabledColor;
    }

    public void setDisabledColor(int disabledColor) {
        this.disabledColor = disabledColor;
    }

    public int getActivatedColor() {
        return activatedColor;
    }

    public void setActivatedColor(int activatedColor) {
        this.activatedColor = activatedColor;
    }

    public int getDeactivatedColor() {
        return deactivatedColor;
    }

    public void setDeactivatedColor(int deactivatedColor) {
        this.deactivatedColor = deactivatedColor;
    }

    public void notifyFontStyleChange(final ActionType type, final String value) {
        post(new Runnable() {
            @Override public void run() {
                switch (type) {
                    case BOLD:
                    case ITALIC:
                    case UNDERLINE:
                    case SUBSCRIPT:
                    case SUPERSCRIPT:
                    case STRIKETHROUGH:
                    case NORMAL:
                    case H1:
                    case H2:
                    case H3:
                    case H4:
                    case H5:
                    case H6:
                    case JUSTIFY_LEFT:
                    case JUSTIFY_CENTER:
                    case JUSTIFY_RIGHT:
                    case JUSTIFY_FULL:
                    case ORDERED:
                    case UNORDERED:
                        setColorFilter(ContextCompat.getColor(mContext,
                            Boolean.valueOf(value) ? getActivatedColor() : getDeactivatedColor()));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
