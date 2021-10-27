package com.tlw;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * HttpObject:客户端与服务端相互通信的数据被封装成HttpObject
 */
@Slf4j
public class HTTPHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest req = (HttpRequest) msg;
            String uri = req.uri();
            log.debug("uri:{}",uri);

            /**
             * 过滤图标请求
             */
//            if("/favicon.ico".equals(uri)){
//                //不做响应
//                return;
//            }

            if(HttpMethod.GET == req.method()){
                /**
                 * 解析uri
                 */

                QueryStringDecoder decoder = new QueryStringDecoder(uri);
                Map<String, List<String>> parame = decoder.parameters();

                Map<String, String> requestParams = new HashMap<>();
                Iterator<Map.Entry<String, List<String>>> iterator = parame.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, List<String>> next = iterator.next();
                    requestParams.put(next.getKey(), next.getValue().get(0));
                }

                for(Map.Entry<String,String> entry:requestParams.entrySet()){
                    log.debug("key:{} value:{}",entry.getKey(),entry.getValue());
                }
            }
        }


        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent) msg;
            if (content.content().readableBytes() > 0) {
                String buf = content.content().toString(CharsetUtil.UTF_8);

                JSONObject jsonContent = null;

                try {
                    jsonContent = JSON.parseObject(buf);
                    log.debug("jsonContent:{}",jsonContent);
                } catch (Exception e) {
                    log.debug("error:{}",e);
                }

            }
        }

        /**
         * 回复消息给浏览器:
         * 构造一个HTTP响应
         * HTTP_1_1 版本
         * OK 状态码
         * content 内容
         */
        ByteBuf content = Unpooled.copiedBuffer("已接收到您的请求", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

        //设置返回类型为文本形式,设置文本长度
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

        ctx.writeAndFlush(response);
    }
}
