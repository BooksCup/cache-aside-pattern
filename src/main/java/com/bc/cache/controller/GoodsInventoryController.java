package com.bc.cache.controller;

import com.bc.cache.entity.GoodsInventory;
import com.bc.cache.request.Request;
import com.bc.cache.request.impl.GoodsInventoryCacheRefreshRequest;
import com.bc.cache.request.impl.GoodsInventoryDbUpdateRequest;
import com.bc.cache.response.Response;
import com.bc.cache.service.GoodsInventoryService;
import com.bc.cache.service.RequestAsyncProcessService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 库存控制器
 *
 * @author zhou
 */
@RestController
@RequestMapping("goodsInventory")
public class GoodsInventoryController {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GoodsInventoryController.class);

    @Resource
    private GoodsInventoryService goodsInventoryService;

    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;

    /**
     * 更新商品库存
     */
    @ApiOperation(value = "修改商品库存", notes = "修改商品库存")
    @PutMapping(value = "/")
    public Response updateProductInventory(@RequestParam Integer goodsId,
                                           @RequestParam Long inventoryCount) {
        GoodsInventory goodsInventory = new GoodsInventory(goodsId, inventoryCount);

        logger.info("接收到更新商品库存的请求，商品id: " + goodsInventory.getGoodsId()
                + ", 商品库存数量: " + goodsInventory.getInventoryCount());

        Response response;

        try {
            Request request = new GoodsInventoryDbUpdateRequest(
                    goodsInventory, goodsInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }

    /**
     * 获取商品库存
     */
    @ApiOperation(value = "获取商品库存", notes = "获取商品库存")
    @GetMapping(value = "/")
    public GoodsInventory getGoodsInventory(Integer goodsId) {
        logger.info("接收到一个商品库存的读请求，商品id: " + goodsId);

        GoodsInventory goodsInventory;

        try {
            Request request = new GoodsInventoryCacheRefreshRequest(
                    goodsId, goodsInventoryService);
            requestAsyncProcessService.process(request);

            // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
            // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long endTime;
            long waitTime = 0L;

            // 等待超过200ms没有从缓存中获取到结果
            while (true) {
                // 一般公司里面，面向用户的读请求控制在200ms就可以了
                if (waitTime > 200) {
                    break;
                }

                // 尝试去redis中读取一次商品库存的缓存数据
                goodsInventory = goodsInventoryService.getGoodsInventoryCache(goodsId);

                // 如果读取到了结果，那么就返回
                if (goodsInventory != null) {
                    logger.info("在200ms内读取到了redis中的库存缓存，商品id: " + goodsInventory.getGoodsId()
                            + ", 商品库存数量: " + goodsInventory.getInventoryCount());
                    return goodsInventory;
                }

                // 如果没有读取到结果，那么等待一段时间
                else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }

            // 直接尝试从数据库中读取数据
            goodsInventory = goodsInventoryService.findGoodsInventory(goodsId);
            if (goodsInventory != null) {
                // 将缓存刷新一下
                goodsInventoryService.setGoodsInventoryCache(goodsInventory);
                return goodsInventory;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new GoodsInventory(goodsId, -1L);
    }
}
