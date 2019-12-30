package me.sxl.trace.core;

import lombok.Data;
import org.slf4j.MDC;

/**
 * 链路ID上下文信息
 */
@Data
public class ProductContext {

    /**
     * 全局ID
     */
    private String traceId;

    /**
     * 当前节点ID
     */
    private String spanId;

    /**
     * 父节点ID
     */
    private String parentId;

    private static ThreadLocal<ProductContext> context = new ThreadLocal<ProductContext>() {
        @Override
        protected ProductContext initialValue() {
            return new ProductContext();
        }
    };

    public static ProductContext getProductContext() {
        return context.get();
    }

    public static void setProductContext() {

    }

    private ProductContext() {
        this.updateMDC();
    }

    private void updateMDC() {
        MDC.put("trace_id", this.traceId);
        MDC.put("span_id", this.spanId);
        MDC.put("parent_id", this.parentId);
    }

}
