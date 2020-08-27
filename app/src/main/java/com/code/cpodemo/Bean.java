package com.code.cpodemo;

import java.util.HashMap;
import java.util.List;

/**
 * author : balance
 * date : 2020/8/19 10:02 AM
 * description :
 */
public class Bean {

    /**
     * message : ok
     * nu : 11111111111
     * ischeck : 1
     * com : yuantong
     * status : 200
     * condition : F00
     * state : 3
     * data : [{"time":"2020-08-06 09:57:06","context":"查无结果","ftime":"2020-08-06 09:57:06"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String com;
    private String status;
    private String condition;
    private String state;
    private List<DataBean> data;




    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
        new HashMap<String,String>(){{
            put("1","1");
        }};
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2020-08-06 09:57:06
         * context : 查无结果
         * ftime : 2020-08-06 09:57:06
         */

        private String time;
        private String context;
        private String ftime;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }
    }
}
