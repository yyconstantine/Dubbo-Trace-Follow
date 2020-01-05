package me.sxl.trace.dubbo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import me.sxl.trace.core.ProductContext;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.util.StringUtils;

@Slf4j
public abstract class DubboFilter implements Filter {

    /**
     * 获取dubbo隐式上下文信息
     */
    protected void pull(Invocation invocation) {
        String contextStr = RpcContext.getContext().getAttachment(String.valueOf(ProductContext.class.getName().hashCode()));
        if (!StringUtils.isEmpty(contextStr)) {
            Gson gson = new GsonBuilder().create();
            ProductContext.setProductContext(gson.fromJson(contextStr, ProductContext.class));
            ProductContext productContext = ProductContext.getProductContext();
            productContext.setSpanId(ProductContext.generateSpanId());
            productContext.setParentId(productContext.getSpanId());
        } else {
            log.debug("获取dubbo隐式上下文信息失败");
        }
    }

    /**
     * 填充dubbo隐式上下文信息
     */
    protected void push(Invocation invocation) {
        ProductContext productContext = ProductContext.getProductContext();
        Gson gson = new GsonBuilder().create();
        RpcContext.getContext().setAttachment(String.valueOf(ProductContext.class.getName().hashCode()), gson.toJson(productContext));
    }

}
