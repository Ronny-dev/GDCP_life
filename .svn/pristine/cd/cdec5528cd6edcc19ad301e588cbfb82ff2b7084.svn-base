package com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag;

import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.SimpleTitleTip;
import com.example.ronny_xie.gdcp.Fragment.jw2012SyetemFragment.tag.bean.Tip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class TipDataModel {

    public static List<Tip> getDragTips(ArrayList<String> dragTips){
        List<Tip> result = new ArrayList<>();
        for(int i=0;i<dragTips.size();i++){
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(dragTips.get(i));
            tip.setId(i);
            result.add(tip);
        }
        return result;
    }

    public static List<Tip> getAddTips(ArrayList<String> addTips){
        List<Tip> result = new ArrayList<>();
        for(int i=0;i<addTips.size();i++){
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(addTips.get(i));
            tip.setId(i+addTips.size());
            result.add(tip);
        }
        return result;
    }
}
