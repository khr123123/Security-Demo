package org.khr.securitydemo.blackFilter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

/**
 * 黑名单过滤工具类
 */
@Slf4j
public class BlackIpUtils {

    private static BitMapBloomFilter bloomFilter;

    // 判断 ip 是否在黑名单里
    public static boolean isBlackIp(String ip) {
        return bloomFilter.contains(ip);
    }

    /**
     * 重建 ip 黑名单
     *
     * @param configInfo
     */
    public static void rebuildBlackIp(String configInfo) {
        if (StrUtil.isBlank(configInfo)) {
            configInfo = "{}";
        }

        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(configInfo, Map.class);
        List<String> blackIpList;
        try {
            blackIpList = (List<String>) map.get("blackIpList");
            if (blackIpList == null) {
                blackIpList = List.of();
            }
        } catch (ClassCastException e) {
            log.error("黑名单配置格式不正确，blackIpList 不是字符串列表！实际内容: {}", map.get("blackIpList"));
            blackIpList = List.of(); // fallback
        }

        synchronized (BlackIpUtils.class) {
            if (CollUtil.isNotEmpty(blackIpList)) {
                log.info("重建黑名单布隆过滤器，当前黑名单: {},{}", blackIpList.size(), blackIpList);
                try {
                    int bloomSize = Math.max(1 << 10, blackIpList.size() * 10);
                    BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(bloomSize);
                    for (String blackIp : blackIpList) {
                        if (StrUtil.isNotBlank(blackIp)) {
                            bitMapBloomFilter.add(blackIp);
                        } else {
                            log.warn("跳过空白 IP");
                        }
                    }
                    bloomFilter = bitMapBloomFilter;
                    log.info("布隆过滤器初始化完成，总计加入黑名单: {},{}", blackIpList.size(), blackIpList);
                } catch (Exception e) {
                    log.error("BloomFilter 初始化失败！回退为空 BloomFilter", e);
                    bloomFilter = new BitMapBloomFilter(100);
                }
            } else {
                log.warn("黑名单为空，使用默认 BloomFilter");
                bloomFilter = new BitMapBloomFilter(100);
            }
        }
    }


}