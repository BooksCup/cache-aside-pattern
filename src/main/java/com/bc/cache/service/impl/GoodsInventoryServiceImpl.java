package com.bc.cache.service.impl;

import com.bc.cache.dao.RedisDao;
import com.bc.cache.entity.GoodsInventory;
import com.bc.cache.mapper.GoodsInventoryMapper;
import com.bc.cache.service.GoodsInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品库存业务实现类
 * @author zhou
 */
@Service("goodsInventoryService")
public class GoodsInventoryServiceImpl implements GoodsInventoryService {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(GoodsInventoryServiceImpl.class);

    @Resource
    private GoodsInventoryMapper goodsInventoryMapper;
    @Resource
    private RedisDao redisDao;

    @Override
    public void updateGoodsInventory(GoodsInventory goodsInventory) {
        goodsInventoryMapper.updateGoodsInventory(goodsInventory);
        logger.info("已修改数据库中的库存，商品id: " + goodsInventory.getGoodsId()
                + ", 商品库存数量=" + goodsInventory.getInventoryCount());
    }

    @Override
    public void removeGoodsInventoryCache(GoodsInventory goodsInventory) {
        String key = "goods:inventory:" + goodsInventory.getGoodsId();
        redisDao.delete(key);
        logger.info("已删除redis中的缓存，key: " + key);
    }

    /**
     * 根据商品id查询商品库存
     *
     * @param goodsId 商品id
     * @return 商品库存
     */
    @Override
    public GoodsInventory findGoodsInventory(Integer goodsId) {
        return goodsInventoryMapper.findGoodsInventory(goodsId);
    }

    /**
     * 设置商品库存的缓存
     *
     * @param goodsInventory 商品库存
     */
    @Override
    public void setGoodsInventoryCache(GoodsInventory goodsInventory) {
        String key = "goods:inventory:" + goodsInventory.getGoodsId();
        redisDao.set(key, String.valueOf(goodsInventory.getInventoryCount()));
        logger.info("已更新商品库存的缓存，商品id: " + goodsInventory.getGoodsId()
                + ", 商品库存数量: " + goodsInventory.getInventoryCount());
    }

    /**
     * 获取商品库存的缓存
     *
     * @param goodsId 商品ID
     * @return 商品库存缓存
     */
    @Override
    public GoodsInventory getGoodsInventoryCache(Integer goodsId) {
        Long inventoryCount;

        String key = "goods:inventory:" + goodsId;
        String result = redisDao.get(key);

        if (result != null && !"".equals(result)) {
            try {
                inventoryCount = Long.valueOf(result);
                return new GoodsInventory(goodsId, inventoryCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
