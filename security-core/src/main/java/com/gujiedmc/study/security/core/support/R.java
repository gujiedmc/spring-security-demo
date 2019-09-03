package com.gujiedmc.study.security.core.support;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author duyinchuan
 * @date 2019-09-01
 */
@Data
@AllArgsConstructor
public class R<T> {
    private T data;
    public static <T> R ok(T data) {
        return new R<>(data);
    }
}
