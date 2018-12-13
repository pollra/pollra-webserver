package com.pollra.web.function.page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageVO {
    public String prevPageNik;
    public String pageCategory;
    public String pagePostNum;

    /**
     * null 이면 트루
     * @return
     */
    public boolean isNull(){
        if(prevPageNik.equals("") || pageCategory.equals("") || pagePostNum.equals("")){
            return true;
        }
        return false;
    }
}
