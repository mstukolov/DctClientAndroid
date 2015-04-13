package com.test;

import com.dct.model.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stukolov on 10.04.2015.
 */
public class GenerateTestData {

    public List<Goods> goodsList = new ArrayList<Goods>();

    public GenerateTestData(){
        Goods good1 = new Goods(1, "YYYYYYYY", 779866757);
        Goods good2 = new Goods(2, "ZZZZZZZZZ", 687578576);
        Goods good3 = new Goods(3, "AAAAAAAAAA", 555675756);

        goodsList.add(good1);
        goodsList.add(good2);
        goodsList.add(good3);

    }


}
