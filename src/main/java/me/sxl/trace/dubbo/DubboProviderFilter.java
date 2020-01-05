package me.sxl.trace.dubbo;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

public class DubboProviderFilter extends DubboFilter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        this.push(invocation);
        return invoker.invoke(invocation);
    }
}
