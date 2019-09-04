package cn.bps.service;

import cn.bps.mapper.ProductBindFilterMapper;
import cn.bps.pojo.Product;
import cn.bps.pojo.ProductBindFilter;
import cn.bps.pojo.ProductBindFilterExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductBindFilterServiceImp implements ProductBindFilterService {

    @Autowired
    ProductBindFilterMapper productBindFilterMapper;


    @Override
    public Set<Integer> getProductIdSet(Set<Integer> filterIdSet) {
        ProductBindFilterExample productBindFilterExample = new ProductBindFilterExample();
        ProductBindFilterExample.Criteria criteria = productBindFilterExample.createCriteria();

        if(filterIdSet != null)
        for(Integer id: filterIdSet){
            criteria.andFilter_value_idEqualTo(id);
        }

        List<ProductBindFilter> productBindFilter = productBindFilterMapper.selectByExample(productBindFilterExample);
        Set<Integer> productIdSet = new HashSet();
        for(ProductBindFilter pbf:productBindFilter){
            productIdSet.add(pbf.getProduct_id());
        }
        return productIdSet;
    }

}
