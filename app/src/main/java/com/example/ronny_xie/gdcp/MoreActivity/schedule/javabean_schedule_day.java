package com.example.ronny_xie.gdcp.MoreActivity.schedule;

/**
 * Created by ronny_xie on 2017/2/5.
 */

public class javabean_schedule_day {
    /**
     * reason : successed
     * result : {"id":"2548","yangli":"2017-02-05","yinli":"丁酉(鸡)年正月初九","wuxing":"大海水 收执位","chongsha":"冲蛇(丁已)煞西","baiji":"癸不词讼理弱敌强 亥不嫁娶不利新郎","jishen":"母仓 六合 五富 圣心","yi":"祭祀 沐浴 理发 作灶 结网 栽种","xiongshen":"河魁 劫煞 重日 勾陈","ji":"嫁娶 词讼 行丧 安葬 牧养 伐木 作梁 开市 纳畜 造畜稠"}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * id : 2548
         * yangli : 2017-02-05
         * yinli : 丁酉(鸡)年正月初九
         * wuxing : 大海水 收执位
         * chongsha : 冲蛇(丁已)煞西
         * baiji : 癸不词讼理弱敌强 亥不嫁娶不利新郎
         * jishen : 母仓 六合 五富 圣心
         * yi : 祭祀 沐浴 理发 作灶 结网 栽种
         * xiongshen : 河魁 劫煞 重日 勾陈
         * ji : 嫁娶 词讼 行丧 安葬 牧养 伐木 作梁 开市 纳畜 造畜稠
         */

        private String id;
        private String yangli;
        private String yinli;
        private String wuxing;
        private String chongsha;
        private String baiji;
        private String jishen;
        private String yi;
        private String xiongshen;
        private String ji;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYangli() {
            return yangli;
        }

        public void setYangli(String yangli) {
            this.yangli = yangli;
        }

        public String getYinli() {
            return yinli;
        }

        public void setYinli(String yinli) {
            this.yinli = yinli;
        }

        public String getWuxing() {
            return wuxing;
        }

        public void setWuxing(String wuxing) {
            this.wuxing = wuxing;
        }

        public String getChongsha() {
            return chongsha;
        }

        public void setChongsha(String chongsha) {
            this.chongsha = chongsha;
        }

        public String getBaiji() {
            return baiji;
        }

        public void setBaiji(String baiji) {
            this.baiji = baiji;
        }

        public String getJishen() {
            return jishen;
        }

        public void setJishen(String jishen) {
            this.jishen = jishen;
        }

        public String getYi() {
            return yi;
        }

        public void setYi(String yi) {
            this.yi = yi;
        }

        public String getXiongshen() {
            return xiongshen;
        }

        public void setXiongshen(String xiongshen) {
            this.xiongshen = xiongshen;
        }

        public String getJi() {
            return ji;
        }

        public void setJi(String ji) {
            this.ji = ji;
        }
    }
}
