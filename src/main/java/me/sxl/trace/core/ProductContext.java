package me.sxl.trace.core;

import lombok.Data;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;

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
        // 链路初始化时不存在上下文信息
        if (context.get() == null) {
            setProductContext(new ProductContext());
        }
        return context.get();
    }

    public static void setProductContext(ProductContext productContext) {
        context.set(productContext);
        productContext.updateMDC();
    }

    private ProductContext() {
        // 每次初始化视为一次链路调用开始,生成全局traceId
        this.traceId = UUID.randomUUID().toString().replaceAll("-", "");
        this.updateMDC();
    }

    private void updateMDC() {
        if (!StringUtils.isEmpty(this.traceId)) {
            MDC.put("trace_id", this.traceId);
        }
        if (!StringUtils.isEmpty(this.spanId)) {
            MDC.put("span_id", this.spanId);
        }
        if (!StringUtils.isEmpty(this.parentId)) {
            MDC.put("parent_id", this.parentId);
        }
    }

    public static String generateSpanId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
