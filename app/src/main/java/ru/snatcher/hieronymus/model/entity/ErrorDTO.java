package ru.snatcher.hieronymus.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * {@link ErrorDTO}
 *
 * @author Usman Akhmedoff.
 * @version 1.0
 */

class ErrorDTO {

    @SerializedName("code")
    @Expose
    private Integer mCode;

    @SerializedName("message")
    @Expose
    private String mErrorMessage;

    public
    @ResponseErrorCodes
    Integer getCode() {
        return mCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
