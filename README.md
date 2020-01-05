# Dubbo-Trace-Follow
---

**Use**
- 通过`@Configuration`注解引入me.sxl.trace.web.WebFilter,可参照me.sxl.trace.config.FilterConfig#filterRegistrationBean
- 日志配置文件在输出格式中添加`[%X{trace_id}:%X{parent_id}:%X{span_id}]`

**Test**
- 自定义Filter使用junit进行单元测试似乎不生效,使用Rest Client/Postman测试通过 