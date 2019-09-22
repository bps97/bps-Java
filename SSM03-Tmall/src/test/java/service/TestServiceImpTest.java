//package service;
//
//        import cn.bps.pojo.*;
//        import cn.bps.service.*;
//        import org.junit.Test;
//        import org.junit.runner.RunWith;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.test.context.ContextConfiguration;
//        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//        import java.util.List;
//        import java.util.Map;
//        import java.util.Set;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
//public class TestServiceImpTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private LabelCategoryService labelCategoryService;
//
//    @Autowired
//    private LabelService labelService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private ProductBindLabelService productBindLabelService;
//
//    @Autowired
//    private ProductImageService productImageService;
//
//    @Autowired
//    private PropertyService propertyService;
//
//    @Autowired
//    private AdministrativeAreaService administrativeAreaService;
//
//
//    @Test
//    public void AdministrativeAreaTest(){
//
//        AdministrativeArea demo = administrativeAreaService.getProvinces().get(11);
//
//        List<AdministrativeArea> administrativeAreas = administrativeAreaService.getChildrenCities(demo.generatorRandomCode());
//
//        for(AdministrativeArea administrativeArea:administrativeAreas){
//            System.out.println(administrativeArea.getName());
//        }
//
//    }
//
//
//    @Test
//    public void propertyTest(){
//
//        for(Property property:propertyService.getPropertyListByCategoryId(1)){
//            System.out.println(property.getName());
//        }
//    }
//
//
//
//    @Test
//    public void productBindLabelTest(){
////        Set<Integer> set = new HashSet<>();
////        set.add(6);
////        set.add(15);
////        Set<Integer> xx = productBindLabelService.getProductIdSet(set);
////        for(Integer i: xx){
////            System.out.println(i);
////        }
//
//
////        Set<Integer> xx = productBindLabelService.getProductIdSetByFilterId(1);
////        for(Integer i: xx){
////            System.out.print(i+" ");
////        }
////        System.out.println("");
////        Set<Integer> yy = productBindLabelService.getProductIdSetByFilterId(20);
////        for(Integer i: yy){
////            System.out.print(i+" ");
////        }
////        System.out.println("");
////        xx.retainAll(yy);
////        for(Integer i: xx){
////            System.out.print(i+" ");
////        }
//
//
//        Set<Integer> productIdSet = productBindLabelService.getAllProductIdSet();
//        List<Product> products = productService.rowBoundsProduct(productIdSet, 24, 8);
//        for(Product product:products){
//            System.out.println(product.getId()+":"+product.getName());
//        }
//
//    }
//
//
//    @Test
//    public void ProductImageTest(){
//        Map<Integer, String> map = productImageService.getImageUrls(productService.getProductList(1, 3));
//        for(String url:map.values()){
//            System.out.println(url);
//        }
//    }
//
//
//    @Test
//    public void ProductTest(){
//        List<Product> products = productService.getProductList(1, 3);
//        for(Product product: products){
//            System.out.println(product.getId());
//
//        }
//
//        System.out.println("-------------------");
//        List<Product> products2 = productService.getProductListByProductIdSet(productBindLabelService.getAllProductIdSet());
//        for(Product product: products){
//            System.out.println(product.getId());
//
//        }
//    }
//
//
//    @Test
//    public void userTest(){
//        User user = userService.getUserByUsername("admin");
//
//        if(user == null){
//            System.out.println("???");
//        }else{
//            System.out.println(user.getName());
//        }
//    }
//
//
//    @Test
//    public void LabelTest(){
//        List<Integer> list = labelCategoryService.getAllLabelCategoryIds();
//        Map<Integer, List<Label>> map = labelService.getLabelMap(list);
//        for (Label con : map.get(1)){
//            System.out.println(con.getValue());
//        }
//    }
//
//    @Test
//    public void labelCategoryTest(){
//        List<LabelCategory> list = labelCategoryService.getAllLabelCategory();
//
//        for(LabelCategory ca:list){
//            System.out.println(ca.getName());
//        }
//    }
//}
