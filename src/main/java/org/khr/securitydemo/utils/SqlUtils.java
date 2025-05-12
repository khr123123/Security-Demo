package org.khr.securitydemo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL 工具
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField
     * @return
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }

    // HTML 标签过滤
    public static String filterHtml(String message) {
        if (message == null) {
            return null;
        }
        message = message.replaceAll("&", "&amp;");
        message = message.replaceAll("<", "&lt;");
        message = message.replaceAll(">", "&gt;");
        message = message.replaceAll("\"", "&quot;");
        message = message.replaceAll("'", "&#39;");
        message = message.replaceAll(" ", "&nbsp;");
        return message;
    }
}